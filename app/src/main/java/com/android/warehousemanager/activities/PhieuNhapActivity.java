package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.MainActivity.STATUS_CODE_NO_CONTENT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.warehousemanager.api.ApiService;
import com.android.warehousemanager.adapters.PhieuNhapAdapter;
import com.android.warehousemanager.models.PhieuNhap;
import com.android.warehousemanager.interfaces.IClickItemPhieuNhapListener;
import com.android.warehousemanager.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhieuNhapActivity extends AppCompatActivity {
    protected static final int REQUEST_ADD_PHIEU_NHAP = 2;
    protected static final int REQUEST_EDIT_PHIEU_NHAP = 3;
    protected static final int REQUEST_REMOVE_PHIEU_NHAP = 4;
    private RecyclerView rvPhieuNhap;
    private EditText etTimKiem;
    private ProgressDialog progressDialog;

    private List<PhieuNhap> list = new ArrayList<>();
    private PhieuNhapAdapter adapter;

    public List<PhieuNhap> getList() {
        return list;
    }

    public void setList(List<PhieuNhap> list) {
        this.list = list;
    }

    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == REQUEST_ADD_PHIEU_NHAP){
                        uploadDataFromApi();
                    }
                    else if(result.getResultCode() == REQUEST_EDIT_PHIEU_NHAP){
                        if(result.getData().getExtras() != null){
                            PhieuNhap value = (PhieuNhap) result.getData().getExtras().get("edit_phieu_nhap");
                            updateDataToApi(value);
                        }
                    }else if(result.getResultCode() == REQUEST_REMOVE_PHIEU_NHAP){
                        if(result.getData().getExtras() != null){
                            PhieuNhap value = (PhieuNhap) result.getData().getExtras().get("remove_phieu_nhap");
                            removeDataFromApi(value);
                        }
                    }
                }
            });

    private void removeDataFromApi(PhieuNhap value) {
        ApiService.API_SERVICE.removePhieuNhap(value.getSoPhieu()).enqueue(new Callback<PhieuNhap>() {
            @Override
            public void onResponse(Call<PhieuNhap> call, Response<PhieuNhap> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(PhieuNhapActivity.this, "Request fail " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromApi();
                }
            }

            @Override
            public void onFailure(Call<PhieuNhap> call, Throwable t) {
                Toast.makeText(PhieuNhapActivity.this, "Call Api Remove Phieu Nhap fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });

    }

    private void updateDataToApi(PhieuNhap value) {
        ApiService.API_SERVICE.updatePhieuNhap(value.getSoPhieu(),value).enqueue(new Callback<PhieuNhap>() {
            @Override
            public void onResponse(Call<PhieuNhap> call, Response<PhieuNhap> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(PhieuNhapActivity.this, "Request fail" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.code() == STATUS_CODE_NO_CONTENT){
                    uploadDataFromApi();
                }
            }

            @Override
            public void onFailure(Call<PhieuNhap> call, Throwable t) {
                Toast.makeText(PhieuNhapActivity.this, "Call Api Update Phieu Nhap fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phieu_nhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Phiếu nhập");
        setControl();
        setEvent();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.add:
                onClickGoToAddPhieuNhap();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        initProgressDialog();
        initRecycleView();
        uploadDataFromApi();
    }

    private void initRecycleView() {
        rvPhieuNhap.startLayoutAnimation();
        adapter = new PhieuNhapAdapter(list ,new IClickItemPhieuNhapListener() {
            @Override
            public void onClickItemPhieuNhap(PhieuNhap value) {
                onClickGoToDetail(value);
            }
        });
        rvPhieuNhap.setAdapter(adapter);
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        progressDialog.setCancelable(false);
    }

    private void uploadDataFromApi() {
        progressDialog.show();
        ApiService.API_SERVICE.getAllPhieuNhap().enqueue(new Callback<List<PhieuNhap>>() {
            @Override
            public void onResponse(Call<List<PhieuNhap>> call, Response<List<PhieuNhap>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(PhieuNhapActivity.this, "Request Fail " + response.code(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                    return;
                }
                list.clear();
                list.addAll(response.body());
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<List<PhieuNhap>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(PhieuNhapActivity.this, "Call Api get All Phieu Nhap fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void onClickGoToAddPhieuNhap() {
        Intent intent = new Intent(PhieuNhapActivity.this, PhieuNhapActivity_Add.class);
        launcher.launch(intent);
    }

    private void setControl() {
        rvPhieuNhap = findViewById(R.id.rvPhieuNhap);
        etTimKiem = findViewById(R.id.etTimKiem);
    }

    public void onClickGoToDetail(PhieuNhap value) {
        Intent intent = new Intent(PhieuNhapActivity.this, DetailPhieuNhapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_phieu_nhap", value);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }

}