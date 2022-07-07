package com.android.warehousemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.warehousemanager.interfaces.IClickItemKhoListener;
import com.android.warehousemanager.models.Storage;
import com.android.warehousemanager.R;

import java.util.List;

public class KhoAdapter extends ArrayAdapter<Storage> {
    private TextView tvMaKho, tvTenKho;
    private ConstraintLayout layoutKho;

    private Context context;
    public List<Storage> data;
    private IClickItemKhoListener listener;

    public List<Storage> getData() {
        return data;
    }

    public void setData(List<Storage> data) {
        this.data = data;
    }

    public void addItem(Storage Storage) {
        data.add(Storage);
    }

    public void editItem(Storage Storage) {
        int position;
        for (Storage item : data) {
            if(item.getId().equalsIgnoreCase(Storage.getId())){
                position = data.indexOf(item);
                data.set(position, Storage);
                break;
            }
        }
    }

    public void removeItem(Storage Storage) {
        int position;
        for (Storage item : data) {
            if(item.getId().equalsIgnoreCase(Storage.getId())){
                position = data.indexOf(item);
                data.remove(position);
                break;
            }
        }
    }

    public void removeItem(int position) {
        data.remove(position);
    }

    @Nullable
    @Override
    public Storage getItem(int position) {
        return data.get(position);
    }

    public KhoAdapter(@NonNull Context context, int resource, @NonNull List<Storage> objects, IClickItemKhoListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_kho, parent, false);
        setControl(view);
        setEvent(position);
        return view;
    }

    private void setEvent(int position) {
        Storage storage = data.get(position);
        tvMaKho.setText(storage.getId());
        tvTenKho.setText(storage.getName());
        layoutKho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItemKho(storage);
            }
        });
        layoutKho.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClickItemKho(position);
                return false;
            }
        });
    }

    private void setControl(View view) {
        tvMaKho = view.findViewById(R.id.tvMaKho);
        tvTenKho = view.findViewById(R.id.tvTenKho);
        layoutKho = view.findViewById(R.id.layoutKho);
    }
}
