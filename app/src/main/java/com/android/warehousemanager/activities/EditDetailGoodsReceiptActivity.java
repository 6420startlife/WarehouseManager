package com.android.warehousemanager.activities;


import static com.android.warehousemanager.activities.DetailGoodsReceiptActivity.REQUEST_EDIT_DETAIL_PHIEU_NHAP;

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

public class EditDetailGoodsReceiptActivity extends AppCompatActivity {
    /*
        DGR = Detail Goods Receipt
     */
    private ImageView ivSaveDGR, ivCancelDGR;
    private TextView tvIdSupplies;
    private EditText etAmount;
    private int idGRKeeper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_detail_goods_receipt);
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
        ivSaveDGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSaveDetailPhieuNhap();
            }
        });
        ivCancelDGR.setOnClickListener(new View.OnClickListener() {
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
            idGRKeeper = value_dpn.getId();
            tvIdSupplies.setText(value_dpn.getIdSupply());
            etAmount.setText(String.valueOf(value_dpn.getAmount()));
        }
    }

    private void onClickSaveDetailPhieuNhap() {
        if(etAmount.getText().toString().trim().length() == 0){
            etAmount.setError("hãy nhập số lượng");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        DetailGoodsReceipt value = new DetailGoodsReceipt(idGRKeeper,
                tvIdSupplies.getText().toString().trim(),
                Integer.valueOf(etAmount.getText().toString().trim()));
        bundle.putSerializable("edit_detail_phieu_nhap",value);
        intent.putExtras(bundle);
        setResult(REQUEST_EDIT_DETAIL_PHIEU_NHAP,intent);
        finish();
    }

    private void setControl() {
        tvIdSupplies = findViewById(R.id.etMaVatTu);
        etAmount = findViewById(R.id.etAmount);
        ivSaveDGR = findViewById(R.id.ivSaveDetailPhieuNhap);
        ivCancelDGR = findViewById(R.id.ivCancelDetailPhieuNhap);

    }
}