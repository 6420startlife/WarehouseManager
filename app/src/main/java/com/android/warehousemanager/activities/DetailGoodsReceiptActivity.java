package com.android.warehousemanager.activities;


import static com.android.warehousemanager.activities.MainActivity.STATUS_CODE_NO_CONTENT;
import static com.android.warehousemanager.activities.GoodsReceiptActivity.REQUEST_EDIT_PHIEU_NHAP;
import static com.android.warehousemanager.activities.GoodsReceiptActivity.REQUEST_REMOVE_PHIEU_NHAP;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.warehousemanager.api.ApiService;
import com.android.warehousemanager.adapters.DetailGoodsReceiptAdapter;
import com.android.warehousemanager.models.DetailGoodsReceipt;
import com.android.warehousemanager.models.GoodsReceipt;
import com.android.warehousemanager.interfaces.IClickItemDetailGoodsReceiptListener;
import com.android.warehousemanager.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailGoodsReceiptActivity extends AppCompatActivity {
    /*
        GR = Goods Receipt
        DGR = Detail Goods Receipt
     */
    protected static final int REQUEST_ADD_DETAIL_PHIEU_NHAP = 5;
    protected static final int REQUEST_EDIT_DETAIL_PHIEU_NHAP = 6;
    private TextView tvIdGR, tvIdStorage, tvDate;
    private ImageView ivEditGR, ivDeleteGR;
    private RecyclerView rvDGR;
    private ImageView ivAddDGR;

    private DetailGoodsReceiptAdapter adapter;

    private final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == REQUEST_EDIT_PHIEU_NHAP){
                        if(result.getData() == null){
                            return;
                        }
                        GoodsReceipt value = (GoodsReceipt) result.getData().getExtras().get("edit_phieu_nhap");
                        tvIdGR.setText(String.valueOf(value.getId()));
                        tvIdStorage.setText(value.getIdStorage());
                        tvDate.setText(value.getDate());
                        setResult(REQUEST_EDIT_PHIEU_NHAP,result.getData());
                    }
                    else if(result.getResultCode() == REQUEST_ADD_DETAIL_PHIEU_NHAP){
                        if(result.getData().getExtras() != null){
                            DetailGoodsReceipt value = (DetailGoodsReceipt) result.getData().getExtras().get("add_detail_phieu_nhap");
                            addDataToApi(value);
                            adapter.setItem(value);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    else if(result.getResultCode() == REQUEST_EDIT_DETAIL_PHIEU_NHAP){
                        if(result.getData().getExtras() != null){
                            DetailGoodsReceipt value = (DetailGoodsReceipt) result.getData().getExtras().get("edit_detail_phieu_nhap");
                            updateDataToApi(value);
                            adapter.editData(value);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });

    private void updateDataToApi(DetailGoodsReceipt value) {
        ApiService.API_SERVICE.updateChiTietPhieuNhap(value).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DetailGoodsReceiptActivity.this, "Request fail" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.code() == STATUS_CODE_NO_CONTENT) {
                    Toast.makeText(DetailGoodsReceiptActivity.this, "S???a th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(DetailGoodsReceiptActivity.this, "Call Api update CTPN fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi",t.getMessage());
            }
        });
    }

    private void addDataToApi(DetailGoodsReceipt value) {
        ApiService.API_SERVICE.createChiTietPhieuNhap(value).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DetailGoodsReceiptActivity.this, "Request fail" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                if(response.code() == STATUS_CODE_NO_CONTENT) {
                    Toast.makeText(DetailGoodsReceiptActivity.this, "Th??m th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(DetailGoodsReceiptActivity.this, "Call Api add CTPN fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi",t.getMessage());
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_goods_receipt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi ti????t phi????u nh????p");
        setControl();
        setEvent();
    }

    private void setEvent() {
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        GoodsReceipt value = (GoodsReceipt) bundle.get("edit_phieu_nhap");
        if(value != null){
            tvIdGR.setText(String.valueOf(value.getId()));
            tvIdStorage.setText(value.getIdStorage());
            tvDate.setText(value.getDate());
        }
        ivEditGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoodsReceipt value = new GoodsReceipt(Integer.valueOf(tvIdGR.getText().toString().trim())
                        , tvDate.getText().toString().trim(), tvIdStorage.getText().toString().trim());
                onClickGoToEditPhieuNhap(value);
            }
        });
        ivDeleteGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDeletePhieuNhap();
            }
        });
        ivAddDGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToAddDetailPhieuNhap();
            }
        });
        getDataFromApi(value);
    }

    private void getDataFromApi(GoodsReceipt value) {
        ApiService.API_SERVICE.getAllChiTietPhieuNhap(value.getId()).enqueue(new Callback<List<DetailGoodsReceipt>>() {
            @Override
            public void onResponse(Call<List<DetailGoodsReceipt>> call, Response<List<DetailGoodsReceipt>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(DetailGoodsReceiptActivity.this, "Request fail" + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                fetchDataToAdapter(response.body());
            }

            @Override
            public void onFailure(Call<List<DetailGoodsReceipt>> call, Throwable t) {
                Toast.makeText(DetailGoodsReceiptActivity.this, "Call Api get All CTPN fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi",t.getMessage());
            }
        });
    }

    private void fetchDataToAdapter(List<DetailGoodsReceipt> list) {
        adapter = new DetailGoodsReceiptAdapter(list, new IClickItemDetailGoodsReceiptListener() {
            @Override
            public void onClickDGR(DetailGoodsReceipt value) {

                onClickGoToEditDetailPhieuNhap(value);
            }
        });
        rvDGR.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                DetailGoodsReceipt chiTietPhieuNhap = adapter.getData().get(position);
                removeDataFromApi(chiTietPhieuNhap);
                adapter.removeData(position);
                adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(rvDGR);
    }

    private void removeDataFromApi(DetailGoodsReceipt value) {
        ApiService.API_SERVICE.removeChiTietPhieuNhap(value.getId(),value.getIdSupply()).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                if(response.code() == STATUS_CODE_NO_CONTENT) {
                    Toast.makeText(DetailGoodsReceiptActivity.this, "Xo?? th??nh c??ng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(DetailGoodsReceiptActivity.this, "call api remove ctpn fail", Toast.LENGTH_SHORT).show();
            }
        });
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

    protected void onClickGoToEditDetailPhieuNhap(DetailGoodsReceipt value) {
        Intent intent = new Intent(DetailGoodsReceiptActivity.this, EditDetailGoodsReceiptActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_detail_phieu_nhap", value);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }

    private void onClickGoToAddDetailPhieuNhap() {
        Intent intent = new Intent(DetailGoodsReceiptActivity.this, AddDetailGoodsReceiptActivity.class);
        intent.putExtra("new_detail_so_phieu", tvIdGR.getText().toString().trim());
        launcher.launch(intent);
    }

    private void onClickDeletePhieuNhap() {
        GoodsReceipt value = new GoodsReceipt(Integer.valueOf(tvIdGR.getText().toString())
                , tvDate.getText().toString().trim(), tvIdStorage.getText().toString().trim());
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("remove_phieu_nhap",value);
        intent.putExtras(bundle);
        setResult(REQUEST_REMOVE_PHIEU_NHAP,intent);
        finish();
    }

    private void onClickGoToEditPhieuNhap(GoodsReceipt value) {
        Intent intent = new Intent(DetailGoodsReceiptActivity.this, EditGoodsReceiptActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_phieu_nhap",value);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }

    private void setControl() {
        tvIdGR = findViewById(R.id.tvSoPhieu);
        tvIdStorage = findViewById(R.id.tvMaKho);
        tvDate = findViewById(R.id.tvNgayLap);
        ivEditGR = findViewById(R.id.ivEditPhieuNhap);
        ivDeleteGR = findViewById(R.id.ivDeletePhieuNhap);
        rvDGR = findViewById(R.id.rvDetailPhieuNhap);
        ivAddDGR = findViewById(R.id.ivAddDetailPhieuNhap);
    }

}