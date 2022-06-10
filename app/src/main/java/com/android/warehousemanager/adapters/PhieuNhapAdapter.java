package com.android.warehousemanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.warehousemanager.models.PhieuNhap;
import com.android.warehousemanager.interfaces.IClickItemPhieuNhapListener;
import com.android.warehousemanager.R;

import java.util.List;

public class PhieuNhapAdapter extends RecyclerView.Adapter<PhieuNhapAdapter.ViewHolder> {

    private List<PhieuNhap> data;
    private IClickItemPhieuNhapListener clickItemPhieuNhapListener;

    public List<PhieuNhap> getData() {
        return data;
    }

    public void setData(List<PhieuNhap> data) {
        this.data = data;
    }

    public void setItem(PhieuNhap value) {
        data.add(value);
    }

    public void editData(PhieuNhap value) {
        int position;
        for (PhieuNhap item : data) {
            if(item.getSoPhieu() == value.getSoPhieu()){
                position = data.indexOf(item);
                data.set(position,value);
                break;
            }
        }
    }

    public void removeData(PhieuNhap value) {
        int position;
        for (PhieuNhap item : data) {
            if(item.getSoPhieu() == value.getSoPhieu()){
                position = data.indexOf(item);
                data.remove(position);
                break;
            }
        }
    }

    public PhieuNhapAdapter(List<PhieuNhap> data, IClickItemPhieuNhapListener listener) {
        this.data = data;
        this.clickItemPhieuNhapListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_phieu_nhap,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setEvent(holder,position);
    }

    private void setEvent(ViewHolder holder, int position) {
        PhieuNhap value = data.get(position);
        holder.tvSoPhieu.setText(String.valueOf(value.getSoPhieu()));
        holder.tvMaKho.setText(value.getMaKho());
        holder.tvNgayLap.setText(String.valueOf(value.getNgayLap()));
        holder.imgArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemPhieuNhapListener.onClickItemPhieuNhap(value);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvSoPhieu, tvMaKho, tvNgayLap;
        public ImageView imgArrow;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setControl(itemView);
        }

        private void setControl(View view) {
            tvSoPhieu = view.findViewById(R.id.tvSoPhieu);
            tvMaKho = view.findViewById(R.id.tvMaKho);
            tvNgayLap = view.findViewById(R.id.tvNgayLap);
            imgArrow = view.findViewById(R.id.imgArrow);
        }
    }
}
