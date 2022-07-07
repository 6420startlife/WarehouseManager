package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.KhoActivity.REQUEST_ADD_KHO;
import static com.android.warehousemanager.activities.KhoActivity.REQUEST_EDIT_KHO;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.warehousemanager.models.Storage;
import com.android.warehousemanager.R;
import com.google.android.material.textfield.TextInputEditText;

public class KhoActivity_Edit extends AppCompatActivity {
    private TextInputEditText tietMaKho, tietTenKho;
    private Button btnLuuKho;

    private boolean isAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kho_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kho");
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

    private void setControl() {
        tietMaKho = findViewById(R.id.tietMaKho);
        tietTenKho = findViewById(R.id.tietTenKho);
        btnLuuKho = findViewById(R.id.btnLuuKho);
    }

    private void setEvent() {
        btnLuuKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnSaveResult();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            isAdd = true;
        } else {
            Storage value = (Storage) bundle.get("edit_kho");
            tietMaKho.setText(value.getId());
            tietMaKho.setEnabled(false);
            tietTenKho.setText(value.getName());
        }
    }

    private void returnSaveResult() {
        if (tietMaKho.getText().toString().trim().length() == 0) {
            tietMaKho.setError("hãy nhập mã kho");
            return;
        } else if (tietTenKho.getText().toString().trim().length() == 0) {
            tietTenKho.setError("hãy nhập tên kho");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Storage storage = new Storage(tietMaKho.getText().toString().trim(), tietTenKho.getText().toString().trim());
        if (isAdd) {
            bundle.putSerializable("add_kho", storage);
            intent.putExtras(bundle);
            setResult(REQUEST_ADD_KHO, intent);
        } else {
            bundle.putSerializable("edit_kho", storage);
            intent.putExtras(bundle);
            setResult(REQUEST_EDIT_KHO, intent);
        }
        finish();
    }

}