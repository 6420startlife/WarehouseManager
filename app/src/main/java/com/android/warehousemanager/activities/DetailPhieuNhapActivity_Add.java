package com.android.warehousemanager.activities;


import static com.android.warehousemanager.activities.DetailPhieuNhapActivity.REQUEST_ADD_DETAIL_PHIEU_NHAP;

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

public class DetailPhieuNhapActivity_Add extends AppCompatActivity {
    private ImageView ivCancelDetailPN, ivAddDetailPN;
    private EditText etSoLuong;
    private AutoCompleteTextView actvVatTu;
    private int soPhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_detail_phieu_nhap);
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

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setEvent() {
        try {
            soPhieu = Integer.valueOf(getIntent().getStringExtra("new_detail_so_phieu"));
        } catch (Exception e) {
            soPhieu = -1;
        }
        ivCancelDetailPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DetailPhieuNhapActivity_Add.super.onBackPressed();
            }
        });
        ivAddDetailPN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAddDetailPN();
            }
        });
        ApiService.API_SERVICE.getVatTuSpinner().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (response.isSuccessful()) {
                    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(DetailPhieuNhapActivity_Add.this, 0, response.body());
                    actvVatTu.setAdapter(spinnerAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void onClickAddDetailPN() {
        if (actvVatTu.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Chọn vật tư ", Toast.LENGTH_SHORT).show();
            return;
        } else if (etSoLuong.getText().toString().trim().length() == 0) {
            etSoLuong.setError("hãy nhập số lượng");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        String maVatTu[] = actvVatTu.getText().toString().trim().split("\\s", 2);
        DetailGoodsReceipt value = new DetailGoodsReceipt(soPhieu, maVatTu[0], Integer.valueOf(etSoLuong.getText().toString().trim()));
        bundle.putSerializable("add_detail_phieu_nhap", value);
        intent.putExtras(bundle);
        setResult(REQUEST_ADD_DETAIL_PHIEU_NHAP, intent);
        finish();
    }

    private void setControl() {
        actvVatTu = findViewById(R.id.actvVatTu);
        etSoLuong = findViewById(R.id.etSoLuong);
        ivAddDetailPN = findViewById(R.id.ivAddDetailPN);
        ivCancelDetailPN = findViewById(R.id.ivCancelDetailPN);
    }
}