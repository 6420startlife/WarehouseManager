package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.MainActivity.STATUS_CODE_NO_CONTENT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
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
import com.android.warehousemanager.adapters.KhoAdapter;
import com.android.warehousemanager.interfaces.IClickItemKhoListener;
import com.android.warehousemanager.models.Storage;
import com.android.warehousemanager.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KhoActivity extends AppCompatActivity {
    protected static final int REQUEST_ADD_KHO = 1;
    protected static final int REQUEST_EDIT_KHO = 2;
    private ListView lvKho;
    private ProgressDialog progressDialog;

    private List<Storage> listStorage = new ArrayList<>();
    private KhoAdapter adapter;

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == REQUEST_ADD_KHO){
                        if(result.getData().getExtras() != null){
                            Storage value = (Storage) result.getData().getExtras().get("add_kho");
                            addDataToApi(value);
                        }
                    }else if(result.getResultCode() == REQUEST_EDIT_KHO){
                        if(result.getData().getExtras() != null){
                            Storage value = (Storage) result.getData().getExtras().get("edit_kho");
                            updateDataToApi(value);
                        }
                    }
                }
            });

    private void updateDataToApi(Storage value) {
        ApiService.API_SERVICE.updateKho(value).enqueue(new Callback<Storage>() {
            @Override
            public void onResponse(Call<Storage> call, Response<Storage> response) {
                uploadDataFromApi();
            }

            @Override
            public void onFailure(Call<Storage> call, Throwable t) {
                Toast.makeText(KhoActivity.this, "Call Api update Kho fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void addDataToApi(Storage value) {
        ApiService.API_SERVICE.createKho(value).enqueue(new Callback<Storage>() {
            @Override
            public void onResponse(Call<Storage> call, Response<Storage> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromApi();
                }
            }
            @Override
            public void onFailure(Call<Storage> call, Throwable t) {
                Toast.makeText(KhoActivity.this,"Call API create Kho fail" ,Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Danh sách kho");
        setcontrol();
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                onClickToAddKho();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onClickToAddKho() {
        Intent intent = new Intent(KhoActivity.this,KhoActivity_Edit.class);
        launcher.launch(intent);
    }

    private void setEvent() {
        initProgressDialog();
        initListView();
        uploadDataFromApi();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
    }

    private void initListView() {
        adapter = new KhoAdapter(KhoActivity.this, 0, listStorage, new IClickItemKhoListener() {
            @Override
            public void onClickItemKho(Storage storage) {
                onClickToEditKho(storage);
            }

            @Override
            public void onLongClickItemKho(int position) {
                onLongClickToRemoveKho(position);
            }
        });
        lvKho.setAdapter(adapter);
    }

    private void uploadDataFromApi() {
        progressDialog.show();
        ApiService.API_SERVICE.getAllKho().enqueue(new Callback<List<Storage>>() {
            @Override
            public void onResponse(Call<List<Storage>> call, Response<List<Storage>> response) {
                if(!response.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(KhoActivity.this,"Code : " + response.code(),Toast.LENGTH_SHORT).show();
                    Log.e("CodeResponse", "" + response.code());
                    return;
                }
                listStorage.clear();
                listStorage.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<Storage>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(KhoActivity.this, "Call Api Get All Kho fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void onLongClickToRemoveKho(int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(KhoActivity.this);
        alertDialogBuilder.setMessage("Bán có muốn xóa kho này!");
        alertDialogBuilder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Storage item = adapter.getItem(position);
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

    private void removeDataFromApi(Storage item) {
        ApiService.API_SERVICE.removeKho(item.getId()).enqueue(new Callback<Storage>() {
            @Override
            public void onResponse(Call<Storage> call, Response<Storage> response) {
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromApi();
                }
            }

            @Override
            public void onFailure(Call<Storage> call, Throwable t) {
                Toast.makeText(KhoActivity.this, "Call Api Remove Kho fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClickToEditKho(Storage storage) {
        Intent intent = new Intent(KhoActivity.this, KhoActivity_Edit.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_kho", storage);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }

    private void setcontrol() {
        lvKho = findViewById(R.id.lvKho);
    }

}