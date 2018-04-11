package com.gcs.suban.listener;

import com.gcs.suban.bean.TeamBean;

import java.util.List;

public interface OnTeamListener {
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<TeamBean> mListType,String page);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);

    void onSearchSuccess(List<TeamBean> listType, String page2);
}
