package com.gcs.suban.tools;

import java.io.File;
import java.math.BigDecimal;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

/** * ��Ӧ��������������� */
public class DataCleanManager {
	
	 public static String getTotalCacheSize(Context context) throws Exception {
	    	long cacheSize = getFolderSize(context.getCacheDir());
	    	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
	    		cacheSize += getFolderSize(context.getExternalCacheDir());
	        }  
	    	return getFormatSize(cacheSize);
	    }
 
 
   public static void clearAllCache(Context context) {
   	deleteDir(context.getCacheDir());
   	if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {  
   		deleteDir(context.getExternalCacheDir());
       }  
   }
 
   private static boolean deleteDir(File dir) {
       if (dir != null && dir.isDirectory()) {
           String[] children = dir.list();
           for (int i = 0; i < children.length; i++) {
               boolean success = deleteDir(new File(dir, children[i]));
               if (!success) {
                   return false;
               }
           }
       }
       return dir.delete();
   }
     
   // ��ȡ�ļ�  
   //Context.getExternalFilesDir() --> SDCard/Android/data/���Ӧ�õİ���/files/ Ŀ¼��һ���һЩ��ʱ�䱣�������  
   //Context.getExternalCacheDir() --> SDCard/Android/data/���Ӧ�ð���/cache/Ŀ¼��һ������ʱ��������  
   public static long getFolderSize(File file) throws Exception {  
       long size = 0;  
       try {  
           File[] fileList = file.listFiles();  
           for (int i = 0; i < fileList.length; i++) {  
               // ������滹���ļ�  
               if (fileList[i].isDirectory()) {  
                   size = size + getFolderSize(fileList[i]);  
               } else {  
                   size = size + fileList[i].length();  
               }  
           }  
       } catch (Exception e) {  
           e.printStackTrace();  
       }  
       return size;  
   }  
     
   /** 
    * ��ʽ����λ 
    *  
    * @param size 
    * @return 
    */  
   public static String getFormatSize(double size) {  
       double kiloByte = size / 1024;  
       if (kiloByte < 1) {  
//           return size + "Byte";  
       	return "0K";
       }  
 
       double megaByte = kiloByte / 1024;  
       if (megaByte < 1) {  
           BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
           return result1.setScale(2, BigDecimal.ROUND_HALF_UP)  
                   .toPlainString() + "KB";  
       }  
 
       double gigaByte = megaByte / 1024;  
       if (gigaByte < 1) {  
           BigDecimal result2 = new BigDecimal(Double.toString(megaByte));  
           return result2.setScale(2, BigDecimal.ROUND_HALF_UP)  
                   .toPlainString() + "MB";  
       }  
 
       double teraBytes = gigaByte / 1024;  
       if (teraBytes < 1) {  
           BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
           return result3.setScale(2, BigDecimal.ROUND_HALF_UP)  
                   .toPlainString() + "GB";  
       }  
       BigDecimal result4 = new BigDecimal(teraBytes);  
       return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()  
               + "TB";  
   }  
   
   
   /**
    * ��ȡӦ��ר������Ŀ¼
    * android 4.4������ϵͳ����Ҫ����SD����дȨ��
    * ���Ҳ���ÿ���6.0ϵͳ��̬����SD����дȨ�����⣬����Ӧ�ñ�ж�غ��Զ���� ������Ⱦ�û��洢�ռ�
    * @param context ������
    * @param type �ļ������� ����Ϊ�գ�Ϊ���򷵻�API�õ���һ��Ŀ¼
    * @return �����ļ��� ���û��SD����SD���������򷵻��ڴ滺��Ŀ¼���������ȷ���SD������Ŀ¼
    */
   public static File getCacheDirectory(Context context,String type) {
       File appCacheDir = getExternalCacheDirectory(context,type);
       if (appCacheDir == null){
           appCacheDir = getInternalCacheDirectory(context,type);
       }

       if (appCacheDir == null){
           Log.e("getCacheDirectory","getCacheDirectory fail ,the reason is mobile phone unknown exception !");
       }else {
           if (!appCacheDir.exists()&&!appCacheDir.mkdirs()){
               Log.e("getCacheDirectory","getCacheDirectory fail ,the reason is make directory fail !");
           }
       }
       return appCacheDir;
   }

   /**
    * ��ȡSD������Ŀ¼
    * @param context ������
    * @param type �ļ������� ���Ϊ���򷵻� /storage/emulated/0/Android/data/app_package_name/cache
    *             ���򷵻ض�Ӧ���͵��ļ�����Environment.DIRECTORY_PICTURES ��Ӧ���ļ���Ϊ .../data/app_package_name/files/Pictures
    * {@link android.os.Environment#DIRECTORY_MUSIC},
    * {@link android.os.Environment#DIRECTORY_PODCASTS},
    * {@link android.os.Environment#DIRECTORY_RINGTONES},
    * {@link android.os.Environment#DIRECTORY_ALARMS},
    * {@link android.os.Environment#DIRECTORY_NOTIFICATIONS},
    * {@link android.os.Environment#DIRECTORY_PICTURES}, or
    * {@link android.os.Environment#DIRECTORY_MOVIES}.or �Զ����ļ�������
    * @return ����Ŀ¼�ļ��� �� null����SD����SD������ʧ�ܣ�
    */
   public static File getExternalCacheDirectory(Context context,String type) {
       File appCacheDir = null;
       if( Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
           if (TextUtils.isEmpty(type)){
               appCacheDir = context.getExternalCacheDir();
           }else {
               appCacheDir = context.getExternalFilesDir(type);
           }

           if (appCacheDir == null){// ��Щ�ֻ���Ҫͨ���Զ���Ŀ¼
               appCacheDir = new File(Environment.getExternalStorageDirectory(),"Android/data/"+context.getPackageName()+"/cache/"+type);
           }

           if (!appCacheDir.exists()&&!appCacheDir.mkdirs()){
		       Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is make directory fail !");
		   }
       }else {
           Log.e("getExternalDirectory","getExternalDirectory fail ,the reason is sdCard nonexistence or sdCard mount fail !");
       }
       return appCacheDir;
   }

   /**
    * ��ȡ�ڴ滺��Ŀ¼
    * @param type ��Ŀ¼������Ϊ�գ�Ϊ��ֱ�ӷ���һ��Ŀ¼
    * @return ����Ŀ¼�ļ��� �� null������Ŀ¼�ļ�ʧ�ܣ�
    * ע���÷�����ȡ��Ŀ¼���ܹ���ǰӦ���Լ�ʹ�ã��ⲿӦ��û�ж�дȨ�ޣ��� ϵͳ���Ӧ��
    */
   public static File getInternalCacheDirectory(Context context,String type) {
       File appCacheDir = null;
       if (TextUtils.isEmpty(type)){
           appCacheDir = context.getCacheDir();// /data/data/app_package_name/cache
       }else {
           appCacheDir = new File(context.getFilesDir(),type);// /data/data/app_package_name/files/type
       }

       if (!appCacheDir.exists()&&!appCacheDir.mkdirs()){
           Log.e("getInternalDirectory","getInternalDirectory fail ,the reason is make directory fail !");
       }
       return appCacheDir;
   }
     
}