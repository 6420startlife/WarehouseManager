package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.VatTuActivity.REQUEST_ADD_VAT_TU;
import static com.android.warehousemanager.activities.VatTuActivity.REQUEST_EDIT_VAT_TU;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.warehousemanager.api.ApiService;
import com.android.warehousemanager.models.ApiResponse;
import com.android.warehousemanager.models.VatTu;
import com.android.warehousemanager.R;
import com.android.warehousemanager.utils.RealPathUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VatTuActivity_Edit extends AppCompatActivity {
    public static final int REQUEST_READ_EXTERNAL_STORAGE = 10;
    public static final int REQUEST_READ_EXTERNAL_STORAGE_REAL_PATH = 11;
    private ImageView ivAnhVatTu_Edit;
    private TextInputEditText tietMaVatTu, tietTenVatTu, tietDonViTinh, tietXuatXu;
    private Button btnLuuVatTu;

    private String urlImage;
    private Uri _uri;
    private ProgressDialog progressDialog;
    private boolean isAdd = false;
    private final String defaultImageUrl = "https://res.cloudinary.com/thuan6420/image/upload/v1652627208/xo5qx46gfuat6bas9jd0.jpg";;

    public Uri get_uri() {
        return _uri;
    }
    public void set_uri(Uri _uri) {
        this._uri = _uri;
    }
    public String getUrlImage() {
        return urlImage;
    }
    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        if(result.getData() != null){
                            set_uri(result.getData().getData());
                            try{
                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), get_uri());
                                ivAnhVatTu_Edit.setImageBitmap(bitmap);
                            }catch (IOException e){
                                Log.e("Error",e.getMessage());
                            }
                        }
                    }
                }
            });



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_vat_tu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Vật tư");
        setControl();
        setEvent();
    }

    private void setEvent() {
        progressDialog = new ProgressDialog(VatTuActivity_Edit.this);
        progressDialog.setMessage("Please wait ...");
        btnLuuVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImageToGetUrl();
            }
        });
        ivAnhVatTu_Edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            isAdd = true;
            Picasso.get().load(defaultImageUrl).into(ivAnhVatTu_Edit);
        }else{
            VatTu value = (VatTu) bundle.get("edit_vat_tu");
            if(value != null){
                tietMaVatTu.setText(value.getMaVatTu());
                tietMaVatTu.setEnabled(false);
                tietTenVatTu.setText(value.getTenVatTu());
                tietDonViTinh.setText(value.getDonViTinh());
                tietXuatXu.setText(value.getXuatXu());
                setUrlImage(value.getAnhVatTu());
                try{
                    Picasso.get().load(value.getAnhVatTu()).into(ivAnhVatTu_Edit);
                }catch (Exception e){
                    Picasso.get().load(defaultImageUrl).into(ivAnhVatTu_Edit);
                }
            }
        }
    }

    private void chooseImage() {
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        if(checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            openGallery();
        }else {
            String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission, REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        launcher.launch(Intent.createChooser(intent, "Select Pictures"));
    }

    private void uploadImageToGetUrl() {
        if(tietMaVatTu.getText().toString().trim().length() == 0){
            tietMaVatTu.setError("hãy nhập mã vật tư");
            return;
        }else if(tietTenVatTu.getText().toString().trim().length() == 0){
            tietTenVatTu.setError("hãy nhập tên vật tư");
            return;
        }else if(tietDonViTinh.getText().toString().trim().length() == 0){
            tietDonViTinh.setError("hãy nhập đơn vị tính");
            return;
        }else if(tietXuatXu.getText().toString().trim().length() == 0){
            tietXuatXu.setError("hãy nhập xuất xứ");
            return;
        }
        if(get_uri() == null) {
            progressDialog.show();
            VatTu value = new VatTu(tietMaVatTu.getText().toString().trim()
                    ,tietTenVatTu.getText().toString().trim()
                    ,defaultImageUrl
                    ,tietDonViTinh.getText().toString().trim()
                    ,tietXuatXu.getText().toString().trim());
            returnResult(value);
            return;
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.show();
                    }
                });
                String strRealPath = RealPathUtil.getRealPath(VatTuActivity_Edit.this,get_uri());
                File file = new File(strRealPath);
                RequestBody requestBodyFile = RequestBody.create(MediaType.parse("multipart/form-data"),file);
                MultipartBody.Part partFile = MultipartBody.Part.createFormData("file",file.getName(),requestBodyFile);
                ApiService.API_SERVICE.uploadImage(partFile).enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if(!response.isSuccessful()) {
                            progressDialog.dismiss();
                            return;
                        }
                        ApiResponse apiResponse = response.body();
                        VatTu value = new VatTu(tietMaVatTu.getText().toString().trim()
                                ,tietTenVatTu.getText().toString().trim()
                                ,apiResponse.getData()
                                ,tietDonViTinh.getText().toString().trim()
                                ,tietXuatXu.getText().toString().trim());
                        returnResult(value);
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        if (t.getMessage().equalsIgnoreCase("timeout")){
                            Toast.makeText(VatTuActivity_Edit.this, "Timeout", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(VatTuActivity_Edit.this, "Call API upload image fail", Toast.LENGTH_SHORT).show();
                        Log.e("ErrorApi", t.getMessage());
                    }
                });
            }
        });



    }

    private void returnResult(VatTu value) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        if(isAdd){
            bundle.putSerializable("add_vat_tu", value);
            intent.putExtras(bundle);
            setResult(REQUEST_ADD_VAT_TU,intent);
        }else {
            bundle.putSerializable("edit_vat_tu", value);
            intent.putExtras(bundle);
            setResult(REQUEST_EDIT_VAT_TU,intent);
        }
        progressDialog.dismiss();
        finish();
    }

    private void setControl() {
        ivAnhVatTu_Edit = findViewById(R.id.ivAnhVatTu_Edit);
        tietMaVatTu = findViewById(R.id.tietMaVatTu);
        tietTenVatTu = findViewById(R.id.tietTenVatTu);
        tietDonViTinh = findViewById(R.id.tietDonViTinh);
        tietXuatXu = findViewById(R.id.tietXuatXu);
        btnLuuVatTu = findViewById(R.id.btnLuuVatTu);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_READ_EXTERNAL_STORAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }
        }else if(requestCode == REQUEST_READ_EXTERNAL_STORAGE_REAL_PATH){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                uploadImageToGetUrl();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}