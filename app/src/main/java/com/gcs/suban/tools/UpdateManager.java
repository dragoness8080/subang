package com.gcs.suban.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gcs.suban.R;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

public class UpdateManager {
	 private Context mContext; //������
	 protected RequestQueue mRequestQueue;
	 private String TAG="UpdateManager";
	    private String apkUrl;; //apk���ص�ַ
	    private static final String savePath = "/sdcard/updateAPK/"; //apk���浽SD����·��
	    private static final String saveFileName = savePath + "suban.apk"; //����·����

	    private ProgressBar mProgress; //���ؽ������ؼ�
	    private static final int DOWNLOADING = 1; //��ʾ��������
	    private static final int DOWNLOADED = 2; //�������
	    private static final int DOWNLOAD_FAILED = 3; //����ʧ��
	    private int progress; //���ؽ���
	    private boolean cancelFlag = false; //ȡ�����ر�־λ
	    private String  banben; 
	    private double serverVersion; //�ӷ�������ȡ�İ汾��
	    private double clientVersion = 25.0; //�ͻ��˵�ǰ�İ汾��
	    private String updateDescription; //��������������Ϣ
	    private boolean forceUpdate = false; //�Ƿ�ǿ�Ƹ���

	    private AlertDialog alertDialog1, alertDialog2; //��ʾ��ʾ�Ի��򡢽������Ի���

	    /** ���캯�� */
	    public UpdateManager(Context context) {
	        this.mContext = context;
	    	mRequestQueue = Volley.newRequestQueue(context);
	    }
	    
	  //��ȡ����������
	    public void VolleyGet() {
			//String url = "http://sbyssh.86tudi.cn/index.php/Update/update";
			String url = "http://ios.sbyssh.com/index.php/Update/update";
			StringRequest request = new StringRequest(Request.Method.GET, url,
					new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							Log.i(TAG, "GET����ɹ�-->" + response);
							try {
								JSONObject jsonObject = new JSONObject(response);
								serverVersion = jsonObject.getDouble("serverversion");
								apkUrl = jsonObject.getString("link");
								banben= jsonObject.getString("banben");
								updateDescription= jsonObject.getString("desc");
								String is=jsonObject.getString("forceUpdate");
								if(is.equals("0"))
								{
									forceUpdate=true;
								}
								else
								{
									forceUpdate=false;
								}
								showNoticeDialog();
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError volleyError) {
							Log.i(TAG, "GET����ʧ�� -> " + volleyError.toString());
						}
					});
			// ����ȡ��ȡ��http�����ǩ Activity�����������е�onStiop()�е���
			request.setTag("volleyget");
			mRequestQueue.add(request);
		}
	    
	    /** ��ʾ���¶Ի��� */
	    public void showNoticeDialog() {
	        //����汾���£�����Ҫ����
	        if (serverVersion <= clientVersion)
	        {
	        	ToastTool.showToast(mContext, "�Ѿ������°汾��");
	            return;
	        }
	        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	        dialog.setTitle("�����°汾 ��" + banben);
	        dialog.setMessage(updateDescription);
	        dialog.setPositiveButton("���ڸ���", new OnClickListener() {
	            @Override
	            public void onClick(DialogInterface arg0, int arg1) {
	                // TODO Auto-generated method stub
	                arg0.dismiss();
	                showDownloadDialog();
	            }
	        });
	        //�Ƿ�ǿ�Ƹ���
	        if (forceUpdate == false) {
	            dialog.setNegativeButton("�������", new OnClickListener() {
	                @Override
	                public void onClick(DialogInterface arg0, int arg1) {
	                    // TODO Auto-generated method stub
	                    arg0.dismiss();
	                }
	            });
	        }
	        alertDialog1  = dialog.create();
	        alertDialog1.setCancelable(false);
	        alertDialog1.show();
	    }

	    /** ��ʾ�������Ի��� */
	    public void showDownloadDialog() {
	        AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
	        dialog.setTitle("���ڸ���");
	        final LayoutInflater inflater = LayoutInflater.from(mContext);
	        View v = inflater.inflate(R.layout.diolog_updata, null);
	        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
	        dialog.setView(v);
	        //�����ǿ�Ƹ��£�����ʾȡ����ť
	        if (forceUpdate == false) {
	            dialog.setNegativeButton("ȡ��", new OnClickListener() {
	                @Override
	                public void onClick(DialogInterface arg0, int arg1) {
	                    // TODO Auto-generated method stub
	                    arg0.dismiss();
	                    cancelFlag = false;
	                }
	            });
	        }
	        alertDialog2  = dialog.create();
	        alertDialog2.setCancelable(false);
	        alertDialog2.show();

	        //����apk
	        downloadAPK();
	    }

	    /** ����apk���߳� */
	    public void downloadAPK() {
	        new Thread(new Runnable() {
	            @Override
	            public void run() {
	                // TODO Auto-generated method stub
	                try {
	                    URL url = new URL(apkUrl);
	                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	                    conn.connect();

	                    int length = conn.getContentLength();
	                    InputStream is = conn.getInputStream();

	                    File file = new File(savePath);
	                    if(!file.exists()){  
	                        file.mkdir();  
	                    }  
	                    String apkFile = saveFileName;  
	                    File ApkFile = new File(apkFile);  
	                    FileOutputStream fos = new FileOutputStream(ApkFile);  

	                    int count = 0;  
	                    byte buf[] = new byte[1024];  

	                    do{                   
	                        int numread = is.read(buf);  
	                        count += numread;  
	                        progress = (int)(((float)count / length) * 100);  
	                        //���½���  
	                        mHandler.sendEmptyMessage(DOWNLOADING);  
	                        if(numread <= 0){      
	                            //�������֪ͨ��װ  
	                            mHandler.sendEmptyMessage(DOWNLOADED);  
	                            break;  
	                        }  
	                        fos.write(buf, 0, numread);  
	                    }while(!cancelFlag); //���ȡ����ֹͣ����.  

	                    fos.close();  
	                    is.close(); 
	                } catch(Exception e) {
	                    mHandler.sendEmptyMessage(DOWNLOAD_FAILED);
	                    e.printStackTrace();
	                }
	            }
	        }).start();
	    }

	    /** ����UI��handler */
	    private Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            // TODO Auto-generated method stub
	            switch (msg.what) {
	            case DOWNLOADING:
	                mProgress.setProgress(progress);
	                break;
	            case DOWNLOADED:
	                if (alertDialog2 != null)
	                    alertDialog2.dismiss();
	                installAPK();
	                break;
	            case DOWNLOAD_FAILED:
	                Toast.makeText(mContext, "����Ͽ������Ժ�����", Toast.LENGTH_LONG).show();
	                break;
	            default:
	                break;
	            }
	        }
	    };

	    /** ������ɺ��Զ���װapk */
	    public void installAPK() {
	        File apkFile = new File(saveFileName);
	        if (!apkFile.exists()) {
	            return;
	        }
	        Intent intent = new Intent(Intent.ACTION_VIEW);
	        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        intent.setDataAndType(Uri.parse("file://" + apkFile.toString()), "application/vnd.android.package-archive");   
	        mContext.startActivity(intent); 
	    }
	}
