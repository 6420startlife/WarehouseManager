package com.android.warehousemanager.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.warehousemanager.models.DetailGoodsReceipt;
import com.android.warehousemanager.interfaces.IClickItemDetailPhieuNhapListener;
import com.android.warehousemanager.R;

import java.util.List;

public class DetailPhieuNhapAdapter extends RecyclerView.Adapter<DetailPhieuNhapAdapter.ViewHolder> {
    private List<DetailGoodsReceipt> data;
    private IClickItemDetailPhieuNhapListener clickItemDetailPhieuNhapListener;

    public List<DetailGoodsReceipt> getData() {
        return data;
    }

    public void setData(List<DetailGoodsReceipt> data) {
        this.data = data;
    }

    public void setItem(DetailGoodsReceipt value) {
        data.add(value);
    }

    public void editData(DetailGoodsReceipt value) {
        int position;
        for (DetailGoodsReceipt item : data) {
            if (item.getId() == value.getId() && item.getIdSupply().equalsIgnoreCase(value.getIdSupply())) {
                position = data.indexOf(item);
                data.set(position, value);
                break;
            }
        }
    }

    public void removeData(int position) {
        data.remove(position);
    }

    public void updateSoPhieu(int soPhieu) {
        for (DetailGoodsReceipt item : data) {
            item.setId(soPhieu);
        }
    }

    public DetailPhieuNhapAdapter(List<DetailGoodsReceipt> data, IClickItemDetailPhieuNhapListener listener) {
        this.data = data;
        this.clickItemDetailPhieuNhapListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_detail_phieu_nhap, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setEvent(holder, position);
    }

    private void setEvent(ViewHolder holder, int position) {
        DetailGoodsReceipt value = data.get(position);
        holder.tvMaVatTu.setText(value.getIdSupply());
        holder.tvSoLuong.setText(String.valueOf(value.getAmount()));
        holder.ivDetailPhieuNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickItemDetailPhieuNhapListener.onClickDetailPhieuNhap(value);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMaVatTu, tvSoLuong, tvDonViTinh;
        public ImageView ivDetailPhieuNhap;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            setControl(itemView);
        }

        private void setControl(View itemView) {
            tvMaVatTu = itemView.findViewById(R.id.tvMaVatTu);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuong);
            ivDetailPhieuNhap = itemView.findViewById(R.id.ivDetailPhieuNhap);
        }
    }
}
