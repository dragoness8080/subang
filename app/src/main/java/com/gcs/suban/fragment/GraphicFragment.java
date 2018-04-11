package com.gcs.suban.fragment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.activity.ShopDetailActivity;
import com.gcs.suban.listener.OnGraphicListener;
import com.gcs.suban.model.GraphicModeImpl;
import com.gcs.suban.model.GraphicModel;

/**
 * 商品图文详情
 */
public class GraphicFragment extends Fragment implements OnGraphicListener {
	protected WebView webView;
	protected String goodsid;
	private String webinfo;
	ShopDetailActivity activity;
	private String url;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_graphic, container,
				false);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getInfo();
	}

	private void getInfo() {
		GraphicModel graphicModel = new GraphicModeImpl();
		graphicModel.getInfo(getUrl(), this);
	}
	
	/**
	 * 获取url地址
	 */
	private String getUrl() {
		url = Url.shopgraphic + "?goodsid=" + goodsid;
		return url;
	}

	/**
	 * 设置webinfo
	 */
	private void SetInfo(String info) {
		webinfo = info;
		webinfo = webinfo.replaceAll("\n", "");
		webinfo = webinfo.replaceAll("\r", "");
		Document doc_Dis = Jsoup.parse(webinfo);
		Elements ele_Img = doc_Dis.getElementsByTag("img");
		if (ele_Img.size() != 0) {
			for (Element e_Img : ele_Img) {
				e_Img.attr("style",
						"max-width: 100%; width:auto; max-height: 100%; height:auto;");
			}
		}
		webinfo = doc_Dis.toString();
		getHtmlData(webinfo);
		setWebView();
	}

	/**
	 * web加载html数据
	 */
	@SuppressLint("NewApi")
	private void setWebView() {
		webView = (WebView) getActivity().findViewById(R.id.webView_graphic);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			webView.getSettings().setLayoutAlgorithm(
					WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
		} else {
			webView.getSettings().setLayoutAlgorithm(
					WebSettings.LayoutAlgorithm.NORMAL);
		}
		webView.getSettings().setJavaScriptEnabled(true);
		webView.loadDataWithBaseURL(null, webinfo, "text/html", "UTF-8", null);
		webView.setFocusable(false);
		webView.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				webView.setFocusable(false);
				int contentHeight = webView.getContentHeight();
				int viewHeight = webView.getHeight();
				Log.d("aaa", "***" + contentHeight + "-" + viewHeight);
			}
		});
	}

	/**
	 * 添加头文件
	 */
	private String getHtmlData(String bodyHTML) {
		String head = "<head>"
				+ "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> "
				+ "<style>img{max-width: 100%; width:auto; max-height: 100%; height:auto;}</style>"
				+ "</head>";
		return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onSuccess(String data) {
		// TODO Auto-generated method stub
		SetInfo(data);
	}

	/**
	 * 网络请求 结果返回失败
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub

	}

}
