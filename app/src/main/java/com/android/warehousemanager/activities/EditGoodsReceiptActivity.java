package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.GoodsReceiptActivity.REQUEST_EDIT_PHIEU_NHAP;

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
import com.android.warehousemanager.models.GoodsReceipt;
import com.android.warehousemanager.R;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGoodsReceiptActivity extends AppCompatActivity {
    /*
        GR = Goods Receipt
     */
    private ImageView ivSaveGR, ivCancelGR;
    private EditText etDate;
    private AutoCompleteTextView actvStorage;

    private int idGRKeeper;
    private SpinnerAdapter spinnerAdapter;

    private DatePickerDialog.OnDateSetListener listener;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goods_receipt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Sửa phiếu nhập");
        setControl();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayAdapter data = new ArrayAdapter(EditGoodsReceiptActivity.this,
                R.layout.item_supplies, new LinkedList());
        actvStorage.setAdapter(data);
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
        GoodsReceipt value_pn = (GoodsReceipt) bundle.get("edit_phieu_nhap");
        if(value_pn != null){
            idGRKeeper = value_pn.getId();
            etDate.setText(value_pn.getDate());
        }

        ivCancelGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivSaveGR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnSaveResult();
            }
        });

        calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(EditGoodsReceiptActivity.this, new DatePickerDialog.OnDateSetListener() {
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
                        etDate.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });
        ApiService.API_SERVICE.getStorageSpinner().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(EditGoodsReceiptActivity.this, "Request fail", Toast.LENGTH_SHORT).show();
                    return;
                }
                spinnerAdapter = new SpinnerAdapter(EditGoodsReceiptActivity.this,0,response.body());
                actvStorage.setAdapter(spinnerAdapter);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ErrorApi",t.getMessage());
            }
        });
    }

    private void returnSaveResult() {
        if(actvStorage.getText().toString().trim().length() == 0){
            Toast.makeText(this, "Chọn kho", Toast.LENGTH_SHORT).show();
            return;
        }else if(etDate.getText().toString().trim().length() == 0){
            etDate.setError("hãy nhập ngày");
            return;
        }
        String[] maKho =  actvStorage.getText().toString().trim().split("\\s",2);
        GoodsReceipt value = new GoodsReceipt(idGRKeeper, etDate.getText().toString().trim(), maKho[0]);
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_phieu_nhap",value);
        intent.putExtras(bundle);
        setResult(REQUEST_EDIT_PHIEU_NHAP,intent);
        finish();
    }

    private void setControl() {
        actvStorage = findViewById(R.id.actvKho);
        etDate = findViewById(R.id.etNgayLap);
        ivSaveGR = findViewById(R.id.ivSaveEditPhieuNhap);
        ivCancelGR = findViewById(R.id.ivCancelPhieuNhap);
    }
}