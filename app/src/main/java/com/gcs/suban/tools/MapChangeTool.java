package com.gcs.suban.tools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class MapChangeTool {
	// bitmap×ªBase64
	public static String bitmapToBase64(Bitmap bitmap) {

		String result = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);

				baos.flush();
				baos.close();
				byte[] bitmapBytes = baos.toByteArray();
				result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	// Base64×ªBitmap
	public static Bitmap base64ToBitmap(String base64Data) {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
	}

	// Ñ¹ËõÍ¼Æ¬µ½150*150
	public static Bitmap changeMapSize(Bitmap bitmap) {
		bitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, false);
		return bitmap;

	}
	
	// 
	public static Bitmap changeMapSize(Bitmap bitmap,int h,int s) {
		bitmap = Bitmap.createScaledBitmap(bitmap, h, s, false);
		return bitmap;

	}

}
