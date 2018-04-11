package com.gcs.suban.adapter;

import java.util.HashMap;
import java.util.List;

import com.gcs.suban.R;
import com.gcs.suban.bean.ShopCarBean;
import com.gcs.suban.tools.ArithTool;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class ShopAdapter extends BaseAdapter {
	private String pic;
	
	protected ImageLoader imageLoader;
	protected DisplayImageOptions options; // 设置图片显示相关参数
	
	private Handler mHandler;
	private List<ShopCarBean> list;		//数据集合List
	private LayoutInflater inflater;	//布局填充器
	private static HashMap<Integer, Boolean> isSelected; 
	@SuppressLint("UseSparseArrays")
	public ShopAdapter(Context context,List<ShopCarBean> list
			,Handler mHandler){
		InitImageLoader();
		this.list = list;
		this.mHandler = mHandler;
		inflater = LayoutInflater.from(context);
		isSelected = new HashMap<Integer, Boolean>();
		initDate();
	}
	
	// 初始化isSelected的数据  
    public void initDate() {  
        for (int i = 0; i < list.size(); i++) {  
            getIsSelected().put(i, false);  
        }  
    }  
    
    public static HashMap<Integer, Boolean> getIsSelected() {  
        return isSelected;  
    }
    
   
    
    
    public void clear() {
    	list.clear();
		this.notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ShopCarBean bean = list.get(position);
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(R.layout.list_item_car,null);
			holder = new ViewHolder();
			holder.Img_pic = (ImageView) convertView.findViewById(R.id.img_pic);
			holder.Tv_name = (TextView) convertView.findViewById(R.id.tv_name);
			holder.Tv_price = (TextView) convertView.findViewById(R.id.tv_price);
			holder.Tv_num = (TextView) convertView.findViewById(R.id.tv_num);
			holder.shop_check = (CheckBox) convertView.findViewById(R.id.shop_check);
			holder.Ibtn_plus = (ImageButton) convertView.findViewById(R.id.icon_plus);
			holder.Ibtn_minus = (ImageButton) convertView.findViewById(R.id.icon_minus);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		pic = bean.getShopPicture();
		imageLoader.displayImage(pic, holder.Img_pic, options);
		holder.Tv_name.setText(bean.getShopName());
		holder.Tv_price.setText("￥"+bean.getShopPrice());
		holder.Tv_num.setText(String.valueOf(bean.getShopNumber()));
		holder.shop_check.setTag(position);
		holder.shop_check.setChecked(getIsSelected().get(position));
		holder.shop_check.setOnCheckedChangeListener(new CheckBoxChangedListener());
		holder.Ibtn_plus.setTag(position);
		holder.Ibtn_plus.setOnClickListener(new PlusListener());
		holder.Ibtn_minus.setTag(position);
		holder.Ibtn_minus.setOnClickListener(new MinusListener());
		return convertView;
	}
	
	private final class ViewHolder{
		public ImageView Img_pic;		//商品图片
		public TextView Tv_name;			//商品名称
		public TextView Tv_price;			//商品价格
		public TextView Tv_num;		//商品数量
		private ImageButton Ibtn_plus;
		private ImageButton Ibtn_minus;
		public CheckBox shop_check;			//商品选择按钮
	}
	
	//CheckBox选择改变监听器
	private final class CheckBoxChangedListener implements OnCheckedChangeListener{
		@Override
		public void onCheckedChanged(CompoundButton cb, boolean flag) {
			int position = (Integer)cb.getTag();
			getIsSelected().put(position, flag);
			ShopCarBean bean = list.get(position);
			bean.setChoosed(flag);
			mHandler.sendMessage(mHandler.obtainMessage(10, getTotalPrice()));
			//如果所有的物品全部被选中，则全选按钮也默认被选中
			mHandler.sendMessage(mHandler.obtainMessage(11, isAllSelected()));
		}
	}
	
		private final class PlusListener implements OnClickListener{
			@Override
			public void onClick(View v) {
				int position = (Integer)v.getTag();
				ShopCarBean bean = list.get(position);
				int total=bean.getShopNumber();
				total=total+1;
				int cartid= bean.getCartid();
				Bundle bundle=new Bundle();
				bundle.putString("cartid", cartid+"");
				bundle.putString("total", total+"");
				mHandler.sendMessage(mHandler.obtainMessage(12,bundle));
			}
		}
		
		private final class MinusListener implements OnClickListener{
			@Override
			public void onClick(View v) {
				int position = (Integer)v.getTag();
				ShopCarBean bean = list.get(position);
				int total=bean.getShopNumber();
				total=total-1;
				int cartid= bean.getCartid();
				Bundle bundle=new Bundle();
				bundle.putString("cartid", cartid+"");
				bundle.putString("total", total+"");
				mHandler.sendMessage(mHandler.obtainMessage(12,bundle));
			}
		}
	
	/**
	 * 计算选中商品的金额
	 * @return	返回需要付费的总金额
	 */
	private double getTotalPrice(){
		ShopCarBean bean = null;
		double totalPrice = 0;
		for(int i=0;i<list.size();i++){
			bean = list.get(i);
			if(bean.isChoosed()){
				totalPrice =ArithTool.add(totalPrice, ArithTool.mul(bean.getShopNumber(),bean.getShopPrice()));
			}
		}
		totalPrice=ArithTool.round(totalPrice, 2);
		return totalPrice;
	}
	
	/**
	 * 判断是否购物车中所有的商品全部被选中
	 * @return	true所有条目全部被选中
	 * 			false还有条目没有被选中
	 */
	private boolean isAllSelected(){
		boolean flag = true;
		for(int i=0;i<list.size();i++){
			if(!getIsSelected().get(i)){
				flag = false;
				break;
			}
		}
		return flag;
	}
	
	// imageLoader初始化
		protected void InitImageLoader() {
			imageLoader = ImageLoader.getInstance();
			options = new DisplayImageOptions.Builder()
				//	.showImageOnLoading(R.drawable.background) // 设置图片下载期间显示的图片
				//	.showImageForEmptyUri(R.drawable.background) // 设置图片Uri为空或是错误的时候显示的图片
				//	.showImageOnFail(R.drawable.background) // 设置图片加载或解码过程中发生错误显示的图片
					.imageScaleType(ImageScaleType.IN_SAMPLE_INT) // 图片缩放方式
					.bitmapConfig(Bitmap.Config.RGB_565)// 设置图片的解码类型//
					 .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
					 .cacheOnDisk(true) // 设置下载的图片是否缓存在SD卡中
					 .displayer(new RoundedBitmapDisplayer(0)) // 设置成圆角图片
					.build(); // 构建完成
		}
}
