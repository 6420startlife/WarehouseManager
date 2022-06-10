package com.android.warehousemanager.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.android.warehousemanager.R;
import com.github.mikephil.charting.charts.BarChart;

public class ChartActivity extends AppCompatActivity {
    private BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Biểu đồ");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.btnAdd:
                finish();
                return true;
            case android.R.id.home:
                this.finish();
                return true;
        }
        return(super.onOptionsItemSelected(item));
    }
}