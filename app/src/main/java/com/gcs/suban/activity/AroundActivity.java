package com.gcs.suban.activity;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.adapter.AroundAdapter;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.AroundBean;
import com.gcs.suban.listener.OnAroundListener;
import com.gcs.suban.model.AroundModel;
import com.gcs.suban.model.AroundModelImpl;
import com.gcs.suban.tools.ToastTool;

import java.util.ArrayList;
import java.util.List;

public class AroundActivity extends BaseActivity implements OnAroundListener {
    private boolean upDateLocation = false;
    private String locationProvider;
    private AroundModel mAroundModel= new AroundModelImpl();;
    private ListView mListView;
    private AroundAdapter mAdapter;
    private ArrayList<AroundBean> mList = new ArrayList<>();

    @Override
    protected void init() {
        setContentView(R.layout.activity_around);
        initView();
        getLocation();
        initData();

    }


    private void initData() {
        mAdapter = new AroundAdapter(this,mList);
        mListView.setAdapter(mAdapter);
    }

    private void initView() {
        mListView = (ListView) findViewById(R.id.list_view);
        findViewById(R.id.back).setOnClickListener(this);
    }

    public void getLocation() {
        LocationManager locationManager;
        String serviceName = Context.LOCATION_SERVICE;
        locationManager = (LocationManager) getSystemService(serviceName); // 查找到服务信息
        //locationManager.setTestProviderEnabled("gps", true);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 0, mLocationListener01);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 3000, 0, mLocationListener01);
    }

    private Location updateToNewLocation(Location location) {
        System.out.println("--------zhixing--2--------");
        String latLongString;
        double lat = 0;
        double lng=0;

        if (location != null&&!upDateLocation) {
            lat = location.getLatitude();
            lng = location.getLongitude();
            mAroundModel.postLocation(Url.get_lat_lng,lat+"",lng+"",this);
            upDateLocation = true;
        }
        return location;

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    public void onSuccess() {
        mAroundModel.getAround(Url.side_member,this);
    }

    /**
     * ???????? ??????????
     */
    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        if(dialog!=null)
        {
            dialog.dismiss();
        }
        ToastTool.showToast(context, error);
    }

    @Override
    public void getAroundData(List<AroundBean> mListType) {
        mList.addAll(mListType);
        mAdapter.notifyDataSetChanged();
    }
    public final LocationListener mLocationListener01 = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            updateToNewLocation(location);
        }


        @Override
        public void onProviderDisabled(String provider) {
            updateToNewLocation(null);
        }

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}
    };
}
