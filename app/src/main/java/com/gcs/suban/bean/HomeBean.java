package com.gcs.suban.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HomeBean {
    private String sign;
    private String text;
    private String item;
    private int style;

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<MenuBean> getMenuItem() {
        List<MenuBean> list = null;
        try {
            JSONArray jsonArray = new JSONArray(item);
            int count = jsonArray.length();
            if(count > 0){
                list = new ArrayList<>();
                for (int z = 0; z < count; z ++){
                    JSONObject jsonObject = (JSONObject)jsonArray.opt(z);
                    MenuBean menuBean = new MenuBean();
                    menuBean.setImgurl(jsonObject.getString("imgurl"));
                    menuBean.setTitle(jsonObject.getString("title"));
                    int isarr = jsonObject.getInt("isarr");
                    menuBean.setIsarr(isarr);
                    if(isarr == 1){
                        menuBean.setHrefurl("");
                        menuBean.setPcate(jsonObject.getString("pcate"));
                        menuBean.setCcate(jsonObject.getString("ccate"));
                        menuBean.setPlugin(jsonObject.getString("p"));
                        menuBean.setPageid(jsonObject.getString("pageid"));
                    }else if(isarr == 0){
                        menuBean.setHrefurl(jsonObject.getString("hrefurl"));
                    }
                    list.add(menuBean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<MenuBean> getPcitureItem(){
        List<MenuBean> list = null;
        try {
            JSONArray jsonArray = new JSONArray(item);
            if(jsonArray.length() > 0){
                list = new ArrayList<>();
                for (int z = 0; z < jsonArray.length(); z ++){
                    JSONObject jsonObject = (JSONObject)jsonArray.opt(z);
                    MenuBean bean = new MenuBean();
                    bean.setImgurl(jsonObject.getString("imgurl"));
                    int isarr = jsonObject.getInt("isarr");
                    bean.setIsarr(isarr);
                    if(isarr == 1){
                        bean.setId(jsonObject.getString("id"));
                    }else{
                        bean.setHrefurl(jsonObject.getString("hrefurl"));
                    }

                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<MenuBean> getGoodsItem(){
        List<MenuBean> list = null;
        try {
            JSONArray jsonArray = new JSONArray(item);
            if(jsonArray.length() > 0){
                list = new ArrayList<>();
                for (int z = 0; z < jsonArray.length(); z ++){
                    JSONObject jsonObject = (JSONObject)jsonArray.opt(z);
                    MenuBean bean = new MenuBean();
                    bean.setGoodsid(jsonObject.getString("goodsid"));
                    bean.setImgurl(jsonObject.getString("imgurl"));
                    bean.setTitle(jsonObject.getString("title"));
                    bean.setPricenow(jsonObject.getDouble("pricenow"));
                    bean.setPriceold(jsonObject.getDouble("priceold"));
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public ArrayList<String> getBannerItem(){
        ArrayList<String> list = null;
        try {
            JSONArray jsonArray = new JSONArray(item);
            if(jsonArray.length() > 0){
                list = new ArrayList<>();
                for (int z = 0; z < jsonArray.length(); z ++){
                    JSONObject jsonObject2 = (JSONObject)jsonArray.opt(z);
                    String url = jsonObject2.getString("imgurl");
                    list.add(url);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<MenuBean> getClubItem(){
        List<MenuBean> list = null;
        try {
            JSONArray jsonArray = new JSONArray(item);
            if(jsonArray.length() >0 ){
                for (int z = 0; z < jsonArray.length(); z ++){
                    JSONObject jsonObject2 = (JSONObject)jsonArray.opt(z);
                    int isarr = jsonObject2.getInt("isarr");
                    MenuBean bean = new MenuBean();
                    if(isarr == 0){
                        bean.setHrefurl(jsonObject2.getString("hrefurl"));
                    }else{
                        bean.setId(jsonObject2.getString("id"));
                    }
                    bean.setImgurl(jsonObject2.getString("imgurl"));
                    bean.setIsarr(isarr);
                    bean.setCol(jsonObject2.getInt("col"));
                    bean.setRow(jsonObject2.getInt("row"));
                    bean.setCur_cols(jsonObject2.getInt("cur_cols"));
                    bean.setCur_rows(jsonObject2.getInt("cur_rows"));
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return list;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }
}
