package com.gcs.suban.listener;

import com.gcs.suban.bean.BankBean;
import com.gcs.suban.bean.MemberBean;

public interface OnPersonListener {
    /**
     * �ɹ�ʱ�ص�
     *
     * @param 
     */
    void onSuccess(MemberBean bean,BankBean bean2);
    
    /**
     * ͷ��ɹ��ص�
     */
    void onLogo(String result,String avatar);
    /**
     * �Ա�ɹ��ص�
     */
    void onSex(String result);
    /**
     * ʧ��ʱ�ص�
     */
    void onError(String error);
}
