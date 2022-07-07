package com.android.warehousemanager.activities;

import static com.android.warehousemanager.activities.StorageActivity.REQUEST_ADD_KHO;
import static com.android.warehousemanager.activities.StorageActivity.REQUEST_EDIT_KHO;

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

public class EditStorageActivity extends AppCompatActivity {
    private TextInputEditText tietIdStorage, tietNameStorage;
    private Button btnSaveStorage;

    private boolean isAdd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_storage);
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
        tietIdStorage = findViewById(R.id.tietMaKho);
        tietNameStorage = findViewById(R.id.tietTenKho);
        btnSaveStorage = findViewById(R.id.btnLuuKho);
    }

    private void setEvent(){
        btnSaveStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                returnSaveResult();
            }
        });
        Bundle bundle = getIntent().getExtras();
        if(bundle == null){
            isAdd = true;
        }else{
            Storage value = (Storage) bundle.get("edit_kho");
            tietIdStorage.setText(value.getId());
            tietIdStorage.setEnabled(false);
            tietNameStorage.setText(value.getName());
        }
    }

    private void returnSaveResult() {
        if(tietIdStorage.getText().toString().trim().length() == 0){
            tietIdStorage.setError("hãy nhập mã kho");
            return;
        }else if(tietNameStorage.getText().toString().trim().length() == 0){
            tietNameStorage.setError("hãy nhập tên kho");
            return;
        }
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        Storage storage = new Storage(tietIdStorage.getText().toString().trim(), tietNameStorage.getText().toString().trim());
        if(isAdd){
            bundle.putSerializable("add_kho", storage);
            intent.putExtras(bundle);
            setResult(REQUEST_ADD_KHO,intent);
        }else {
            bundle.putSerializable("edit_kho", storage);
            intent.putExtras(bundle);
            setResult(REQUEST_EDIT_KHO,intent);
        }
        finish();
    }

}