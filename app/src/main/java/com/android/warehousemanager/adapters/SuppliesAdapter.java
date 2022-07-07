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
import com.android.warehousemanager.models.Supplies;
import com.android.warehousemanager.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SuppliesAdapter extends ArrayAdapter<Supplies> {
    private Context context;
    public List<Supplies> data;

    private ImageView ivAnhVatTu;
    private TextView tvMaVatTu, tvTenVatTu, tvDonViTinh, tvXuatXu;
    private ConstraintLayout layoutItemVatTu;
    private IClickItemVatTuListener listener;

    public List<Supplies> getData() {
        return data;
    }

    public void setData(List<Supplies> data) {
        this.data = data;
    }

    public void addItem(Supplies supplies) {
        data.add(supplies);
    }

    public void editItem(Supplies supplies) {
        int position;
        for (Supplies item : data) {
            if (item.getId().equalsIgnoreCase(supplies.getId())) {
                position = data.indexOf(item);
                data.set(position, supplies);
                break;
            }
        }
    }

    public void removeItem(Supplies supplies) {
        int position;
        for (Supplies item : data) {
            if (item.getId().equalsIgnoreCase(supplies.getId())) {
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
    public Supplies getItem(int position) {
        return data.get(position);
    }

    public SuppliesAdapter(@NonNull Context context, int resource, @NonNull List<Supplies> objects, IClickItemVatTuListener listener) {
        super(context, resource, objects);
        this.context = context;
        this.data = objects;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_supplies, parent, false);
        setControl(view);
        setEvent(position);
        return view;
    }

    private void setEvent(int position) {
        Supplies value = data.get(position);
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
        tvMaVatTu = view.findViewById(R.id.tvIdSupplies);
        tvTenVatTu = view.findViewById(R.id.tvTenVatTu);
        tvDonViTinh = view.findViewById(R.id.tvDonViTinh);
        tvXuatXu = view.findViewById(R.id.tvXuatXu);
        ivAnhVatTu = view.findViewById(R.id.ivAnhVatTu);
        layoutItemVatTu = view.findViewById(R.id.layoutItemVatTu);
    }
}
