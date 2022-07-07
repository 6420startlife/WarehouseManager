package com.android.warehousemanager.activities;


import static com.android.warehousemanager.activities.DetailPhieuNhapActivity.REQUEST_EDIT_DETAIL_PHIEU_NHAP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.warehousemanager.models.DetailGoodsReceipt;
import com.android.warehousemanager.R;

public class DetailPhieuNhapActivity_Edit extends AppCompatActivity {
    private ImageView ivSaveDetailPhieuNhap, ivCancelDetailPhieuNhap;
    private TextView tvMaVatTu;
    private EditText etSoLuong;
    private int soPhieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_phieu_nhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa chi tiết phiếu nhập");
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
        ivSaveDetailPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSaveDetailPhieuNhap();
            }
        });
        ivCancelDetailPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        DetailGoodsReceipt value_dpn = (DetailGoodsReceipt) bundle.get("edit_detail_phieu_nhap");
        if(value_dpn != null){
            soPhieu = value_dpn.getId();
            tvMaVatTu.setText(value_dpn.getIdSupply());
            etSoLuong.setText(String.valueOf(value_dpn.getAmount()));
        }
    }

    private void onClickSaveDetailPhieuNhap() {
        if(etSoLuong.getText().toString().trim().length() == 0){
            etSoLuong.setError("hãy nhập số lượng");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        DetailGoodsReceipt value = new DetailGoodsReceipt(soPhieu,
                tvMaVatTu.getText().toString().trim(),
                Integer.valueOf(etSoLuong.getText().toString().trim()));
        bundle.putSerializable("edit_detail_phieu_nhap",value);
        intent.putExtras(bundle);
        setResult(REQUEST_EDIT_DETAIL_PHIEU_NHAP,intent);
        finish();
    }

    private void setControl() {
        tvMaVatTu = findViewById(R.id.etMaVatTu);
        etSoLuong = findViewById(R.id.etSoLuong);
        ivSaveDetailPhieuNhap = findViewById(R.id.ivSaveDetailPhieuNhap);
        ivCancelDetailPhieuNhap = findViewById(R.id.ivCancelDetailPhieuNhap);

    }
}