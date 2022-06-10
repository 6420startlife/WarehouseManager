package com.android.warehousemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.warehousemanager.R;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {
    private  Context context;
    public List<String> data;

    private TextView tvSpinner;
    public SpinnerAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_spinner, parent, false);
        setControl(view);
        setEvent(position);
        return view;
    }

    private void setEvent(int position) {
        tvSpinner.setText(data.get(position));
    }

    private void setControl(View view) {
        tvSpinner = view.findViewById(R.id.tvSpinner);
    }
}
