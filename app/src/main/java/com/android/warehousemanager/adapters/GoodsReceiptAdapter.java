package com.android.warehousemanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.warehousemanager.models.GoodsReceipt;
import com.android.warehousemanager.interfaces.IClickItemPhieuNhapListener;
import com.android.warehousemanager.R;

import java.util.List;

public class GoodsReceiptAdapter extends RecyclerView.Adapter<GoodsReceiptAdapter.ViewHolder> {

    private List<GoodsReceipt> data;
    private IClickItemPhieuNhapListener clickItemPhieuNhapListener;

    public List<GoodsReceipt> getData() {
        return data;
    }

    public void setData(List<GoodsReceipt> data) {
        this.data = data;
    }

    public void setItem(GoodsReceipt value) {
        data.add(value);
    }

    public void editData(GoodsReceipt value) {
        int position;
        for (GoodsReceipt item : data) {
            if(item.getId() == value.getId()){
                position = data.indexOf(item);
                data.set(position,value);
                break;
            }
        }
    }

    public void removeData(GoodsReceipt value) {
        int position;
        for (GoodsReceipt item : data) {
            if(item.getId() == value.getId()){
                position = data.indexOf(item);
                data.remove(position);
                break;
            }
        }
    }

    public GoodsReceiptAdapter(List<GoodsReceipt> data, IClickItemPhieuNhapListener listener) {
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
        GoodsReceipt value = data.get(position);
        holder.tvSoPhieu.setText(String.valueOf(value.getId()));
        holder.tvMaKho.setText(value.getIdStorage());
        holder.tvNgayLap.setText(String.valueOf(value.getDate()));
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
