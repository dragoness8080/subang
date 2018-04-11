package com.gcs.suban.model;

import com.gcs.suban.bean.CommentBean;
import com.gcs.suban.listener.OnBaseListener;
import com.gcs.suban.listener.OnCommentListener;
import com.gcs.suban.listener.OnImgUpListener;

public interface CommentModel {
	void UpImg(String url, String file, OnImgUpListener listener);

	void CommentsUp(String url, CommentBean bean, OnBaseListener listener);
	
	void appendCommentsUp(String url, CommentBean bean, OnBaseListener listener);

	void onInfo(String url, String goodsid, String page,
			OnCommentListener listener);
	
}
