package com.gcs.suban.listener;

import java.util.List;

import com.gcs.suban.bean.CommentBean;

public interface OnCommentListener {
	 /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(List<CommentBean> mListType,String page);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
