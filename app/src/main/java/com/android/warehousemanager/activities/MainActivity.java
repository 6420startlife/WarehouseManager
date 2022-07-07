package com.android.warehousemanager.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.warehousemanager.R;

public class MainActivity extends AppCompatActivity {
    public static final int STATUS_CODE_NO_CONTENT = 204;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("QUẢN LÝ KHO");
    }

    public void gotoPhieuNhap(View view) {
        startActivity(new Intent(this, PhieuNhapActivity.class));
    }

    public void gotoKho(View view) {
        startActivity(new Intent(MainActivity.this, KhoActivity.class));
    }

    public void gotoVatTu(View view) {
        startActivity(new Intent(MainActivity.this, VatTuActivity.class));
    }

    public void gotoChart(View view) {
        startActivity(new Intent(MainActivity.this, ChartActivity.class));
    }

    public void gotoFeedBack(View view) {
        startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
    }

    public void gotoPhieuXuat(View view) {
    }
}