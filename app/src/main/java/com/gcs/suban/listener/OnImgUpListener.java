package com.gcs.suban.listener;

public interface OnImgUpListener {
	 /**
     * ͼƬ�ϴ��ɹ�
     *
     * @param 
     */
    void onUpImg(String resulttext,String url);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
