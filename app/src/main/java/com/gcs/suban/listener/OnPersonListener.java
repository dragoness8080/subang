package com.gcs.suban.listener;

import com.gcs.suban.bean.BankBean;
import com.gcs.suban.bean.MemberBean;

public interface OnPersonListener {
    /**
     * 成功时回调
     *
     * @param 
     */
    void onSuccess(MemberBean bean,BankBean bean2);
    
    /**
     * 头像成功回调
     */
    void onLogo(String result,String avatar);
    /**
     * 性别成功回调
     */
    void onSex(String result);
    /**
     * 失败时回调
     */
    void onError(String error);
}
