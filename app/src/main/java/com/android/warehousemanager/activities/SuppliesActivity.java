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
import com.android.warehousemanager.adapters.SuppliesAdapter;
import com.android.warehousemanager.interfaces.IClickItemVatTuListener;
import com.android.warehousemanager.models.Supplies;
import com.android.warehousemanager.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuppliesActivity extends AppCompatActivity {
    protected static final int REQUEST_ADD_VAT_TU = 1;
    protected static final int REQUEST_EDIT_VAT_TU = 2;
    private ListView lvSupplies;
    private ProgressDialog progressDialog;

    private SuppliesAdapter adapter;
    private List<Supplies> listSupplies = new ArrayList<>();

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == REQUEST_ADD_VAT_TU){
                        if(result.getData().getExtras() != null){
                            Supplies value = (Supplies) result.getData().getExtras().get("add_vat_tu");
                            addDataToApi(value);
                        }
                    }else if(result.getResultCode() == REQUEST_EDIT_VAT_TU){
                        if(result.getData().getExtras() != null){
                            Supplies value = (Supplies) result.getData().getExtras().get("edit_vat_tu");
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
        lvSupplies = findViewById(R.id.lvVatTu);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
    }

    private void initListView() {
        adapter = new SuppliesAdapter(SuppliesActivity.this, 0, listSupplies, new IClickItemVatTuListener() {
            @Override
            public void onClickItemVatTu(Supplies supplies) {
                onClickToEditVatTu(supplies);
            }

            @Override
            public void onLongClickItemVatTu(int position) {
                onLongClickToRemoveVatTu(position);
            }
        });
        lvSupplies.setAdapter(adapter);
    }

    private void addDataToApi(Supplies value) {
        ApiService.API_SERVICE.createVatTu(value).enqueue(new Callback<Supplies>() {
            @Override
            public void onResponse(Call<Supplies> call, Response<Supplies> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromData();
                }
            }
            @Override
            public void onFailure(Call<Supplies> call, Throwable t) {
                Toast.makeText(SuppliesActivity.this,"Call API create Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void updateDataToApi(Supplies value) {
        ApiService.API_SERVICE.updateVatTu(value).enqueue(new Callback<Supplies>() {
            @Override
            public void onResponse(Call<Supplies> call, Response<Supplies> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromData();
                }
            }

            @Override
            public void onFailure(Call<Supplies> call, Throwable t) {
                Toast.makeText(SuppliesActivity.this,"Call API update Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void removeDataFromApi(Supplies item) {
        ApiService.API_SERVICE.removeVatTu(item.getId()).enqueue(new Callback<Supplies>() {
            @Override
            public void onResponse(Call<Supplies> call, Response<Supplies> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromData();
                }
            }

            @Override
            public void onFailure(Call<Supplies> call, Throwable t) {
                Toast.makeText(SuppliesActivity.this,"Call API remove Vat Tu fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void uploadDataFromData(){
        progressDialog.show();
        ApiService.API_SERVICE.getAllVatTu().enqueue(new Callback<List<Supplies>>() {
            @Override
            public void onResponse(Call<List<Supplies>> call, Response<List<Supplies>> response) {
                if(!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(SuppliesActivity.this, "Request fail " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                listSupplies.clear();
                listSupplies.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Supplies>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(SuppliesActivity.this, "Call Api fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void onClickToAddVatTu() {
        Intent intent = new Intent(SuppliesActivity.this, EditSuppliesActivity.class);
        launcher.launch(intent);
    }

    private void onLongClickToRemoveVatTu(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SuppliesActivity.this);
        alertDialogBuilder.setMessage("Bán có muốn xóa vật tư này!");
        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Supplies item = adapter.getItem(position);
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

    private void onClickToEditVatTu(Supplies supplies) {
        Intent intent = new Intent(SuppliesActivity.this, EditSuppliesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_vat_tu", supplies);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }
}