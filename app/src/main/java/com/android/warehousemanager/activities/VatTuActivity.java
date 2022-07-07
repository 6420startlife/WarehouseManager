package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.MainActivity.STATUS_CODE_NO_CONTENT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.warehousemanager.api.ApiService;
import com.android.warehousemanager.adapters.VatTuAdapter;
import com.android.warehousemanager.interfaces.IClickItemVatTuListener;
import com.android.warehousemanager.models.VatTu;
import com.android.warehousemanager.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VatTuActivity extends AppCompatActivity {
    protected static final int REQUEST_ADD_VAT_TU = 1;
    protected static final int REQUEST_EDIT_VAT_TU = 2;
    private ListView lvVatTu;
    private ProgressDialog progressDialog;

    private VatTuAdapter adapter;
    private List<VatTu> listVatTu = new ArrayList<>();

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == REQUEST_ADD_VAT_TU){
                        if(result.getData().getExtras() != null){
                            VatTu value = (VatTu) result.getData().getExtras().get("add_vat_tu");
                            addDataToApi(value);
                        }
                    }else if(result.getResultCode() == REQUEST_EDIT_VAT_TU){
                        if(result.getData().getExtras() != null){
                            VatTu value = (VatTu) result.getData().getExtras().get("edit_vat_tu");
                            updateDataToApi(value);
                        }
                    }
                }
            });

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_tu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách vật tư");
        setControl();
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                onClickToAddVatTu();
                return true;
            case android.R.id.home:
                finish();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }

    private void setEvent() {
        initProgressDialog();
        initListView();
        uploadDataFromData();
    }

    private void setControl() {
        lvVatTu = findViewById(R.id.lvVatTu);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
    }

    private void initListView() {
        adapter = new VatTuAdapter(VatTuActivity.this, 0, listVatTu, new IClickItemVatTuListener() {
            @Override
            public void onClickItemVatTu(VatTu vatTu) {
                onClickToEditVatTu(vatTu);
            }

            @Override
            public void onLongClickItemVatTu(int position) {
                onLongClickToRemoveVatTu(position);
            }
        });
        lvVatTu.setAdapter(adapter);
    }

    private void addDataToApi(VatTu value) {
        ApiService.API_SERVICE.createVatTu(value).enqueue(new Callback<VatTu>() {
            @Override
            public void onResponse(Call<VatTu> call, Response<VatTu> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromData();
                }
            }
            @Override
            public void onFailure(Call<VatTu> call, Throwable t) {
                Toast.makeText(VatTuActivity.this,"Call API create Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void updateDataToApi(VatTu value) {
        ApiService.API_SERVICE.updateVatTu(value).enqueue(new Callback<VatTu>() {
            @Override
            public void onResponse(Call<VatTu> call, Response<VatTu> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromData();
                }
            }

            @Override
            public void onFailure(Call<VatTu> call, Throwable t) {
                Toast.makeText(VatTuActivity.this,"Call API update Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void removeDataFromApi(VatTu item) {
        ApiService.API_SERVICE.removeVatTu(item.getId()).enqueue(new Callback<VatTu>() {
            @Override
            public void onResponse(Call<VatTu> call, Response<VatTu> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromData();
                }
            }

            @Override
            public void onFailure(Call<VatTu> call, Throwable t) {
                Toast.makeText(VatTuActivity.this,"Call API remove Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void uploadDataFromData(){
        progressDialog.show();
        ApiService.API_SERVICE.getAllVatTu().enqueue(new Callback<List<VatTu>>() {
            @Override
            public void onResponse(Call<List<VatTu>> call, Response<List<VatTu>> response) {
                if(!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(VatTuActivity.this, "Request fail " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listVatTu.clear();
                listVatTu.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<VatTu>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(VatTuActivity.this, "Call Api fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void onClickToAddVatTu() {
        Intent intent = new Intent(VatTuActivity.this, VatTuActivity_Edit.class);
        launcher.launch(intent);
    }

    private void onLongClickToRemoveVatTu(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VatTuActivity.this);
        alertDialogBuilder.setMessage("Bán có muốn xóa vật tư này!");
        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VatTu item = adapter.getItem(position);
                removeDataFromApi(item);
                uploadDataFromData();
            }
        });
        alertDialogBuilder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialogBuilder.show();
    }

    private void onClickToEditVatTu(VatTu vatTu) {
        Intent intent = new Intent(VatTuActivity.this, VatTuActivity_Edit.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_vat_tu", vatTu);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }
}