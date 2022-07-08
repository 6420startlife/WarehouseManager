package com.android.warehousemanager.activities;



import static com.android.warehousemanager.activities.DetailGoodsReceiptActivity.REQUEST_ADD_DETAIL_PHIEU_NHAP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.warehousemanager.api.ApiService;
import com.android.warehousemanager.adapters.SpinnerAdapter;
import com.android.warehousemanager.models.DetailGoodsReceipt;
import com.android.warehousemanager.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDetailGoodsReceiptActivity extends AppCompatActivity {
    /*
        DGR = Detail Goods Receipt
        GRKeeper = Identify Goods Receipt need keeping
     */
    private ImageView ivCancelDGR, ivAddDGR;
    private EditText etAmount;
    private AutoCompleteTextView actvSupplies;
    private int idGRKeeper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail_goods_receipt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm chi tiết phiếu nhập");
        setControl();
        setEvent();
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

    private void setEvent() {
        initIdGRKeeper();
        ivCancelDGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDetailGoodsReceiptActivity.super.onBackPressed();
            }
        });
        ivAddDGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddDGR();
            }
        });
        ApiService.API_SERVICE.getSuppliesSpinner().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    return;
                }
                SpinnerAdapter spinnerAdapter = new SpinnerAdapter(AddDetailGoodsReceiptActivity.this
                        ,0,response.body());
                actvSupplies.setAdapter(spinnerAdapter);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ErrorApi",t.getMessage());
            }
        });
    }

    private void initIdGRKeeper() {
        try {
            idGRKeeper = Integer.valueOf(getIntent().getStringExtra("new_detail_so_phieu"));
        }catch (Exception e){
            idGRKeeper = -1;
        }
    }

    private void onClickAddDGR() {
        if(actvSupplies.getText().toString().trim().length() == 0){
            Toast.makeText(this,
                    "Chọn vật tư ", Toast.LENGTH_SHORT).show();
            return;
        }else if(etAmount.getText().toString().trim().length() == 0){
            etAmount.setError("hãy nhập số lượng");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        String suppliesKeeper[] = actvSupplies.getText().toString().trim().split("\\s",2);
        DetailGoodsReceipt value = new DetailGoodsReceipt(idGRKeeper
                , suppliesKeeper[0]
                , Integer.valueOf(etAmount.getText().toString().trim()));
        bundle.putSerializable("add_detail_phieu_nhap",value);
        intent.putExtras(bundle);
        setResult(REQUEST_ADD_DETAIL_PHIEU_NHAP,intent);
        finish();
    }

    private void setControl() {
        actvSupplies = findViewById(R.id.actvSupplies);
        etAmount = findViewById(R.id.etAmount);
        ivAddDGR = findViewById(R.id.ivAddDGR);
        ivCancelDGR = findViewById(R.id.ivCancelDGR);
    }
}