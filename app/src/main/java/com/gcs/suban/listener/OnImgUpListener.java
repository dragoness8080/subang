package com.gcs.suban.listener;

public interface OnImgUpListener {
	 /**
     * 图片上传成功
     *
     * @param 
     */
    void onUpImg(String resulttext,String url);
    /**
     * 失败时回调
     */
    void onError(String error);
}
