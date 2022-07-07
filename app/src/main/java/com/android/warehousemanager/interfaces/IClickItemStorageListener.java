package com.android.warehousemanager.interfaces;

import com.android.warehousemanager.models.Storage;

public interface IClickItemKhoListener {
    void onClickItemKho(Storage storage);

    void onLongClickItemKho(int position);
}
