package com.gcs.suban.model;

import com.gcs.suban.listener.OnCenterListener;

public interface CenterModel {
    void getInfo(String url,OnCenterListener listener);
}
