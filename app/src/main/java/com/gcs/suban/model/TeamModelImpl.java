package com.gcs.suban.model;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.gcs.suban.MyDate;
import com.gcs.suban.Url;
import com.gcs.suban.app;
import com.gcs.suban.bean.TeamBean;
import com.gcs.suban.bean.TeamBean2;
import com.gcs.suban.listener.OnTeamListener;
import com.gcs.suban.volley.BaseStrVolleyInterFace;
import com.gcs.suban.volley.BaseVolleyRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TeamModelImpl implements TeamModel {
    private Context context = app.getContext();
    private List<TeamBean> mListType;
    private TeamBean bean;
    private String page = null;
    private String mPage2;

    @Override
    public void getInfo(String url, String mPage, final OnTeamListener listener) {
        // TODO Auto-generated method stub
        final String TAG = url;
        Map<String, String> params = new HashMap<String, String>();
        params.put("openid", MyDate.getMyVid());
        params.put("page", mPage);
        Log.e(TAG, url + " " + mPage + " " + MyDate.getMyVid());
        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context, BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "POST����ɹ�-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String isnull = jsonObject.getString("isnull");
                    mListType = new ArrayList<TeamBean>();
                    if (result.equals("1001")) {
                        if (isnull.equals("0")) {
                            JSONArray jsonArray;
                            page = jsonObject.getString("page");
                            jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
                                bean = new TeamBean();
                                bean.bean2 = new ArrayList<>();
                                bean.avatar = jsonObjectSon.getString("avatar");
                                bean.id = jsonObjectSon.getString("id");
                                bean.nickname = jsonObjectSon.getString("nickname");
                                bean.teamcount = jsonObjectSon.getInt("teamcount");
                                if (bean.teamcount != 0) {
                                    JSONArray jsonArray2 = jsonObjectSon.getJSONArray("teams");
                                    for (int j = 0; j < jsonArray2.length(); j++) {
                                        try {
                                            JSONObject jsonObjectSon2 = (JSONObject) jsonArray2.opt(j);
                                            TeamBean2 bean2 = new TeamBean2();
                                            bean2.groupName = jsonObjectSon2.getString("nickname");
                                            bean2.groupId = jsonObjectSon2.getString("id");
                                            bean2.groupLogo = jsonObjectSon2.getString("avatar");
                                            bean2.teamcount = jsonObjectSon2.getInt("teamcount");
                                            if (bean2.teamcount != 0) {
                                                JSONArray jsonArray3 = jsonObjectSon2.getJSONArray("teams");
                                                for (int k = 0; k < jsonArray3.length(); k++) {
                                                    JSONObject jsonObjectSon3 = (JSONObject) jsonArray3.opt(k);
                                                    bean2.childNames.add(k, jsonObjectSon3.getString("nickname"));
                                                    bean2.childLogos.add(k, jsonObjectSon3.getString("avatar"));
                                                    bean2.childIds.add(k, jsonObjectSon3.getString("id"));
                                                    bean2.childteamcounts.add(k, jsonObjectSon3.getString("teamcount"));
                                                }
                                            }
                                            bean.bean2.add(j, bean2);
                                        } catch (Exception e) {
                                        }

                                    }
                                }
                                mListType.add(bean);
                            }
                        } else {
                            mListType = null;
                        }
                        listener.onSuccess(mListType, page);
                    } else {
                        String resulttext = jsonObject.getString("resulttext");
                        listener.onError(resulttext);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    listener.onError(Url.jsonError);
                }
            }

            @Override
            public void onError(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i(TAG, "POST����ʧ��-->" + error.toString());
                listener.onError(Url.networkError);
            }
        });
    }

    @Override
    public void searchInfo(String url, String type, String content, final String mPage, final OnTeamListener listener) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("openid", MyDate.getMyVid());
        params.put("page", mPage);
        params.put("key", content);
        params.put("type", type);
        final String TAG = url + params.toString();

        BaseVolleyRequest.StringRequestPost(context, url, TAG, params, new BaseStrVolleyInterFace(context, BaseStrVolleyInterFace.mListener, BaseStrVolleyInterFace.mErrorListener) {
            @Override
            public void onSuccess(String response) {
                Log.i(TAG, "POST����ɹ�-->" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("result");
                    String isnull = jsonObject.getString("isnull");
                    mListType = new ArrayList<TeamBean>();
                    if (result.equals("1001")) {
                        if (isnull.equals("0")) {
                            JSONArray jsonArray;
                            mPage2 = jsonObject.getString("page");
                            jsonArray = jsonObject.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObjectSon = (JSONObject) jsonArray.opt(i);
                                bean = new TeamBean();
                                bean.bean2 = new ArrayList<>();
                                bean.avatar = jsonObjectSon.getString("avatar");
                                bean.id = jsonObjectSon.getString("id");
                                bean.nickname = jsonObjectSon.getString("nickname");
                                bean.teamcount = jsonObjectSon.getInt("teamcount");
                                if (bean.teamcount != 0) {
                                    JSONArray jsonArray2 = jsonObjectSon.getJSONArray("teams");
                                    for (int j = 0; j < jsonArray2.length(); j++) {
                                        JSONObject jsonObjectSon2 = (JSONObject) jsonArray2.opt(j);
                                        TeamBean2 bean2 = new TeamBean2();
                                        bean2.groupName = jsonObjectSon2.getString("nickname");
                                        bean2.groupId = jsonObjectSon2.getString("id");
                                        bean2.groupLogo = jsonObjectSon2.getString("avatar");
                                        bean2.teamcount = jsonObjectSon2.getInt("teamcount");
                                        if (bean2.teamcount != 0) {
                                            JSONArray jsonArray3 = jsonObjectSon2.getJSONArray("teams");
                                            for (int k = 0; k < jsonArray3.length(); k++) {
                                                JSONObject jsonObjectSon3 = (JSONObject) jsonArray3.opt(k);
                                                bean2.childNames.add(k, jsonObjectSon3.getString("nickname"));
                                                bean2.childLogos.add(k, jsonObjectSon3.getString("avatar"));
                                                bean2.childIds.add(k, jsonObjectSon3.getString("id"));
                                                bean2.childteamcounts.add(k, jsonObjectSon3.getString("teamcount"));
                                            }
                                        }
                                        bean.bean2.add(j, bean2);
                                    }
                                }
                                mListType.add(bean);
                            }
                        } else {
                            mListType = null;
                        }
                        listener.onSearchSuccess(mListType, mPage2);
                    } else {
                        String resulttext = jsonObject.getString("resulttext");
                        listener.onError(resulttext);
                    }
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    listener.onError(Url.jsonError);
                }
            }

            @Override
            public void onError(VolleyError error) {
                // TODO Auto-generated method stub
                Log.i(TAG, "POST����ʧ��-->" + error.toString());
                listener.onError(Url.networkError);
            }
        });
    }

}
