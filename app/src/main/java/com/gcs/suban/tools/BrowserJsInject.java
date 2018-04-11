package com.gcs.suban.tools;

public class BrowserJsInject {
	 /**
     * Jsע��
     * @param url ���ص���ҳ��ַ
     * @return ע���js���ݣ���������Ҫ�������ַ�򷵻ؿ�javascript
     */
    public static String fullScreenByJs(String url){
        String refer = referParser(url);
        if (null != refer) {
            return "javascript:document.getElementsByClassName('" + referParser(url) + "')[0].addEventListener('click',function(){local_obj.playing();return false;});";
        }else {
            return "javascript:";
        }
    }

    /**
     * �Բ�ͬ����Ƶ��վ������Ӧ��ȫ���ؼ�
     * @param url ���ص���ҳ��ַ
     * @return ��Ӧ��վȫ����ť��class��ʶ
     */
    public static String referParser(String url){
        if (url.contains("letv")) {
            return "hv_ico_screen";               //����Tv
        }else if (url.contains("youku")) {
            return "x-zoomin";                    //�ſ�
        }else if (url.contains("bilibili")) {
            return "icon-widescreen";             //bilibili
        }else if (url.contains("qq")) {
            return "tvp_fullscreen_button";       //��Ѷ��Ƶ
        }

        return null;
    }
}