package com.gcs.suban.listener;

import com.gcs.suban.bean.AroundBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/15 0015.
 */

public interface OnAroundListener {
    /**
     * �ϴ���γ�ȳɹ�ʱ�ص�
     */
    void onSuccess();
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);

    /**
     * ��ȡ����ذ����б�
     */
    void getAroundData(List<AroundBean> mListType);
}
