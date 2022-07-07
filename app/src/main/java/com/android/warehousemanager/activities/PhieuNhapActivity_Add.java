package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.DetailPhieuNhapActivity.REQUEST_ADD_DETAIL_PHIEU_NHAP;
import static com.android.warehousemanager.activities.DetailPhieuNhapActivity.REQUEST_EDIT_DETAIL_PHIEU_NHAP;
import static com.android.warehousemanager.activities.PhieuNhapActivity.REQUEST_ADD_PHIEU_NHAP;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.warehousemanager.api.ApiService;
import com.android.warehousemanager.adapters.DetailPhieuNhapAdapter;
import com.android.warehousemanager.adapters.SpinnerAdapter;
import com.android.warehousemanager.models.ApiResponse;
import com.android.warehousemanager.models.DetailGoodsReceipt;
import com.android.warehousemanager.models.GoodsReceipt;
import com.android.warehousemanager.interfaces.IClickItemDetailPhieuNhapListener;
import com.android.warehousemanager.R;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhieuNhapActivity_Add extends AppCompatActivity {
    private ImageView ivAddDetailPhieuNhap, ivCancelPhieuNhap, ivAddPhieuNhap;
    private EditText etNgayLap;
    private AutoCompleteTextView actvKho;

    private DatePickerDialog.OnDateSetListener listener;
    private Calendar calendar;

    private RecyclerView rvDetailPhieuNhap;
    private DetailPhieuNhapAdapter adapter;
    private SpinnerAdapter spinnerAdapter;
    private int soPhieu;


    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == REQUEST_ADD_DETAIL_PHIEU_NHAP) {
                        if (result.getData().getExtras() != null) {
                            DetailGoodsReceipt value = (DetailGoodsReceipt) result.getData().getExtras().get("add_detail_phieu_nhap");
                            adapter.setItem(value);
                            adapter.notifyDataSetChanged();
                        }
                    } else if (result.getResultCode() == REQUEST_EDIT_DETAIL_PHIEU_NHAP) {
                        if (result.getData().getExtras() != null) {
                            DetailGoodsReceipt value = (DetailGoodsReceipt) result.getData().getExtras().get("add_detail_phieu_nhap");
                            adapter.editData(value);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_phieu_nhap);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Thêm phiếu nhập");
        setControl();
        setEvent();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setEvent() {
        ivCancelPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        ivAddPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPhieuNhap();
            }
        });
        ivAddDetailPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickGoToAddDetailPhieuNhap();
            }
        });

        calendar = Calendar.getInstance();
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final int month = calendar.get(Calendar.MONTH);
        final int year = calendar.get(Calendar.YEAR);
        etNgayLap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(PhieuNhapActivity_Add.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month += 1;
                        String date = "";
                        if (day < 10) {
                            date += "0" + day;
                        } else {
                            date += day;
                        }
                        if (month < 10) {
                            date += "/0" + month + "/" + year;
                        } else {
                            date += "/" + month + "/" + year;
                        }
                        etNgayLap.setText(date);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        List<DetailGoodsReceipt> data = new LinkedList<>();
        adapter = new DetailPhieuNhapAdapter(data, new IClickItemDetailPhieuNhapListener() {
            @Override
            public void onClickDetailPhieuNhap(DetailGoodsReceipt value) {
                onClickGoToEditDetailPhieuNhap(value);
            }
        });
        rvDetailPhieuNhap.setAdapter(adapter);
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                adapter.removeData(position);
                adapter.notifyDataSetChanged();
            }
        }).attachToRecyclerView(rvDetailPhieuNhap);

        ApiService.API_SERVICE.getKhoSpinner().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PhieuNhapActivity_Add.this, "Request fail", Toast.LENGTH_SHORT).show();
                    return;
                }
                spinnerAdapter = new SpinnerAdapter(PhieuNhapActivity_Add.this, 0, response.body());
                actvKho.setAdapter(spinnerAdapter);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void onClickGoToEditDetailPhieuNhap(DetailGoodsReceipt value) {
        Intent intent = new Intent(PhieuNhapActivity_Add.this, DetailPhieuNhapActivity_Edit.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("edit_detail_phieu_nhap", value);
        intent.putExtras(bundle);
        launcher.launch(intent);
    }

    private void onClickGoToAddDetailPhieuNhap() {
        Intent intent = new Intent(PhieuNhapActivity_Add.this, DetailPhieuNhapActivity_Add.class);
        launcher.launch(intent);
    }

    private void addPhieuNhap() {
        if (actvKho.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Chọn ", Toast.LENGTH_SHORT).show();
            return;
        } else if (etNgayLap.getText().toString().trim().length() == 0) {
            etNgayLap.setError("hãy nhập ngày");
            return;
        } else if (adapter.getItemCount() == 0) {
            Toast.makeText(PhieuNhapActivity_Add.this, "Hãy thêm ít nhất 1 vật tư", Toast.LENGTH_SHORT).show();
            return;
        }
        String[] maKho = actvKho.getText().toString().trim().split("\\s", 2);
        GoodsReceipt value = new GoodsReceipt(etNgayLap.getText().toString().trim(), maKho[0]);
        ApiService.API_SERVICE.createPhieuNhap(value).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(PhieuNhapActivity_Add.this, "Request fail " + response.code(), Toast.LENGTH_SHORT).show();
                    return;
                }
                ApiResponse apiResponse = response.body();
                soPhieu = Integer.valueOf(apiResponse.getData());
                createCTPNToApi(soPhieu, value);
                Intent intent = new Intent();
                setResult(REQUEST_ADD_PHIEU_NHAP, intent);
                finish();
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(PhieuNhapActivity_Add.this, "Call Api create Phieu Nhap fail", Toast.LENGTH_SHORT).show();
                Log.e("ErrorApi", t.getMessage());
            }
        });
    }

    private void createCTPNToApi(int soPhieu, GoodsReceipt value) {
        adapter.updateSoPhieu(soPhieu);
        for (DetailGoodsReceipt item : adapter.getData()) {
            ApiService.API_SERVICE.createChiTietPhieuNhap(item).enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {

                }

                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }
    }

    private void setControl() {
        actvKho = findViewById(R.id.actvKho);
        etNgayLap = findViewById(R.id.etNgayLap);
        ivAddDetailPhieuNhap = findViewById(R.id.ivAddDetailPhieuNhap);
        ivCancelPhieuNhap = findViewById(R.id.ivCancelPhieuNhap);
        ivAddPhieuNhap = findViewById(R.id.ivAddPhieuNhap);
        rvDetailPhieuNhap = findViewById(R.id.rvDetailPhieuNhap);
    }
}