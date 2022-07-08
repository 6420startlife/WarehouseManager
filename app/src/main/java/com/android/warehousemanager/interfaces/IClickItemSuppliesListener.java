package com.android.warehousemanager.interfaces;

import com.android.warehousemanager.models.Supplies;

public interface IClickItemSuppliesListener {
    void onClickItemVatTu(Supplies supplies);
    void onLongClickItemVatTu(int position);
}
