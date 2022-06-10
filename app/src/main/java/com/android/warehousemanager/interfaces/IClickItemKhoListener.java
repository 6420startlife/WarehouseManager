package com.android.warehousemanager.interfaces;

import com.android.warehousemanager.models.Kho;

public interface IClickItemKhoListener {
    void onClickItemKho(Kho kho);
    void onLongClickItemKho(int position);
}
