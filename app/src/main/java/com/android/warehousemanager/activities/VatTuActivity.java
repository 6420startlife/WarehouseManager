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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VatTuActivity extends AppCompatActivity {
    protected static final int REQUEST_ADD_VAT_TU = 1;
    protected static final int REQUEST_EDIT_VAT_TU = 2;
    private ListView lvVatTu;

    private VatTuAdapter adapter;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == REQUEST_ADD_VAT_TU){
                        if(result.getData().getExtras() != null){
                            VatTu value = (VatTu) result.getData().getExtras().get("add_vat_tu");
                            addDataToApi(value);
                            adapter.addItem(value);
                            adapter.notifyDataSetChanged();
                        }
                    }else if(result.getResultCode() == REQUEST_EDIT_VAT_TU){
                        if(result.getData().getExtras() != null){
                            VatTu value = (VatTu) result.getData().getExtras().get("edit_vat_tu");
                            updateDataToApi(value);
                            adapter.editItem(value);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

    private void addDataToApi(VatTu value) {
        ApiService.API_SERVICE.createVatTu(value).enqueue(new Callback<VatTu>() {
            @Override
            public void onResponse(Call<VatTu> call, Response<VatTu> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    Toast.makeText(VatTuActivity.this,"Thêm thành công",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(VatTuActivity.this,"Sửa thành công",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VatTu> call, Throwable t) {
                Toast.makeText(VatTuActivity.this,"Call API update Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vat_tu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách vật tư");
        setControl();
        setEvent();
    }

    private void setEvent() {
        getDataFromApi();
    }

    private void removeDataFromApi(VatTu item) {
        ApiService.API_SERVICE.removeVatTu(item.getMaVatTu()).enqueue(new Callback<VatTu>() {
            @Override
            public void onResponse(Call<VatTu> call, Response<VatTu> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    Toast.makeText(VatTuActivity.this,"Xoá Thành Công" ,Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VatTu> call, Throwable t) {
                Toast.makeText(VatTuActivity.this,"Call API remove Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void setControl() {
        lvVatTu = findViewById(R.id.lvVatTu);
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

    private void onClickToAddVatTu() {
        Intent intent = new Intent(VatTuActivity.this, VatTuActivity_Edit.class);
        launcher.launch(intent);
    }

    private void getDataFromApi() {
        ApiService.API_SERVICE.getAllVatTu().enqueue(new Callback<List<VatTu>>() {
            @Override
            public void onResponse(Call<List<VatTu>> call, Response<List<VatTu>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(VatTuActivity.this,"Code : " + response.code(),Toast.LENGTH_SHORT).show();
                    Log.e("Code : ", "" + response.code());
                    return;
                }
                adapter = new VatTuAdapter(VatTuActivity.this, 0, response.body(), new IClickItemVatTuListener() {
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
            @Override
            public void onFailure(Call<List<VatTu>> call, Throwable t) {
                Toast.makeText(VatTuActivity.this,"Call API Get All Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void onLongClickToRemoveVatTu(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(VatTuActivity.this);
        alertDialogBuilder.setMessage("Bán có muốn xóa vật tư này!");
        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                VatTu item = adapter.getItem(position);
                removeDataFromApi(item);
                adapter.removeItem(position);
                adapter.notifyDataSetChanged();
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