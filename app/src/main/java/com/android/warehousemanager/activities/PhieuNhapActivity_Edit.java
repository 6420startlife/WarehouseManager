package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.PhieuNhapActivity.REQUEST_EDIT_PHIEU_NHAP;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.warehousemanager.api.ApiService;
import com.android.warehousemanager.adapters.SpinnerAdapter;
import com.android.warehousemanager.models.PhieuNhap;
import com.android.warehousemanager.R;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhieuNhapActivity_Edit extends AppCompatActivity {
    private ImageView ivSaveEditPhieuNhap, ivCancelPhieuNhap;
    private EditText etNgayLap;
    private AutoCompleteTextView actvKho;

    private int soPhieu;
    private SpinnerAdapter spinnerAdapter;

    private DatePickerDialog.OnDateSetListener listener;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_phieu_nhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa phiếu nhập");
        setControl();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter data = new ArrayAdapter(PhieuNhapActivity_Edit.this,
                R.layout.item_vat_tu, new LinkedList());
        actvKho.setAdapter(data);
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
        Bundle bundle = getIntent().getExtras();
        if(bundle == null) {
            return;
        }
        PhieuNhap value_pn = (PhieuNhap) bundle.get("edit_phieu_nhap");
        if(value_pn != null){
            soPhieu = value_pn.getSoPhieu();
            etNgayLap.setText(value_pn.getNgayLap());
        }

        ivCancelPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivSaveEditPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnSaveResult();
            }
        });

        calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        etNgayLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PhieuNhapActivity_Edit.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String date = "";
                        if(day < 10){
                            date += "0" + day;
                        }else {
                            date += day;
                        }
                        if(month < 10) {
                            date += "/0" + month + "/" + year;
                        }else{
                            date += "/" + month + "/" + year;
                        }
                        etNgayLap.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        ApiService.API_SERVICE.getKhoSpinner().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(PhieuNhapActivity_Edit.this, "Request fail", Toast.LENGTH_SHORT).show();
                    return;
                }
                spinnerAdapter = new SpinnerAdapter(PhieuNhapActivity_Edit.this,0,response.body());
                actvKho.setAdapter(spinnerAdapter);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ErrorApi",t.getMessage());
            }
        });
    }

    private void returnSaveResult() {
        if(actvKho.getText().toString().trim().length() == 0){
            Toast.makeText(this, "Chọn kho", Toast.LENGTH_SHORT).show();
            return;
        }else if(etNgayLap.getText().toString().trim().length() == 0){
            etNgayLap.setError("hãy nhập ngày");
            return;
        }
        String[] maKho =  actvKho.getText().toString().trim().split("\\s",2);
        PhieuNhap value = new PhieuNhap(soPhieu,etNgayLap.getText().toString().trim(), maKho[0]);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_phieu_nhap",value);
        intent.putExtras(bundle);
        setResult(REQUEST_EDIT_PHIEU_NHAP,intent);
        finish();
    }

    private void setControl() {
        actvKho = findViewById(R.id.actvKho);
        etNgayLap = findViewById(R.id.etNgayLap);
        ivSaveEditPhieuNhap = findViewById(R.id.ivSaveEditPhieuNhap);
        ivCancelPhieuNhap = findViewById(R.id.ivCancelPhieuNhap);
    }
}