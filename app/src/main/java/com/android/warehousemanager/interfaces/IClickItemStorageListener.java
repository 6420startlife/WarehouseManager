package com.android.warehousemanager.interfaces;

import com.android.warehousemanager.models.Storage;

public interface IClickItemStorageListener {
    void onClickItemStorage(Storage storage);
    void onLongClickItemStorage(int position);
}
