package com.android.warehousemanager.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.warehousemanager.interfaces.IClickItemVatTuListener;
import com.android.warehousemanager.models.Supply;
import com.android.warehousemanager.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class VatTuAdapter extends ArrayAdapter<Supply> {
    private Context context;
    public List<Supply> data;

    private ImageView ivAnhVatTu;
    private TextView tvMaVatTu, tvTenVatTu, tvDonViTinh, tvXuatXu;
    private ConstraintLayout layoutItemVatTu;
    private IClickItemVatTuListener listener;

    public List<Supply> getData() {
        return data;
    }

    public void setData(List<Supply> data) {
        this.data = data;
    }

    public void addItem(Supply supply) {
        data.add(supply);
    }

    public void editItem(Supply supply) {
        int position;
        for (Supply item : data) {
            if (item.getId().equalsIgnoreCase(supply.getId())) {
                position = data.indexOf(item);
                data.set(position, supply);
                break;
            }
        }
    }

    public void removeItem(Supply supply) {
        int position;
        for (Supply item : data) {
            if (item.getId().equalsIgnoreCase(supply.getId())) {
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
    public Supply getItem(int position) {
        return data.get(position);
    }

    public VatTuAdapter(@NonNull Context context, int resource, @NonNull List<Supply> objects, IClickItemVatTuListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_vat_tu, parent, false);
        setControl(view);
        setEvent(position);
        return view;
    }

    private void setEvent(int position) {
        Supply value = data.get(position);
        tvMaVatTu.setText(value.getId());
        tvTenVatTu.setText(value.getName());
        tvDonViTinh.setText(value.getUnit());
        tvXuatXu.setText(value.getFrom());
        try {
            Picasso.get().load(value.getImage()).into(ivAnhVatTu);
        } catch (Exception e) {
            Picasso.get().load("https://res.cloudinary.com/thuan6420/image/upload/v1652627208/xo5qx46gfuat6bas9jd0.jpg").into(ivAnhVatTu);
        }
        layoutItemVatTu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClickItemVatTu(value);
            }
        });
        layoutItemVatTu.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                listener.onLongClickItemVatTu(position);
                return false;
            }
        });
    }

    private void setControl(View view) {
        tvMaVatTu = view.findViewById(R.id.tvMaVatTu);
        tvTenVatTu = view.findViewById(R.id.tvTenVatTu);
        tvDonViTinh = view.findViewById(R.id.tvDonViTinh);
        tvXuatXu = view.findViewById(R.id.tvXuatXu);
        ivAnhVatTu = view.findViewById(R.id.ivAnhVatTu);
        layoutItemVatTu = view.findViewById(R.id.layoutItemVatTu);
    }
}
