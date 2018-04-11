package com.gcs.suban.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gcs.suban.R;
import com.gcs.suban.Url;
import com.gcs.suban.base.BaseActivity;
import com.gcs.suban.bean.TeamBean;
import com.gcs.suban.listener.OnTeamListener;
import com.gcs.suban.model.TeamModel;
import com.gcs.suban.model.TeamModelImpl;
import com.gcs.suban.tools.ToastTool;
import com.gcs.suban.view.LoadExpandableListView;
import com.gcs.suban.view.LoadExpandableListView.onLoadListViewListener;
import com.heng.tree.adapter.ParentAdapter;
import com.heng.tree.adapter.ParentAdapter.OnChildTreeViewClickListener;
import com.heng.tree.entity.ChildEntity;
import com.heng.tree.entity.ParentEntity;

import java.util.ArrayList;
import java.util.List;

public class TeamActivity extends BaseActivity implements OnTeamListener, onLoadListViewListener, OnRefreshListener, OnGroupExpandListener, OnChildTreeViewClickListener {
    private ImageButton IBtn_back;
    private Button Btn_exchange;
    private TextView Tv_title;

    private LoadExpandableListView listView;

    private List<TeamBean> mListType = new ArrayList<TeamBean>();

    private LinearLayout layout;

    private Button Btn_confirm;

    private TeamModel model;

    private String page = "0";
    private int position = 0;
    private String searchPage = "0";

    private Boolean isRefresh = false;

    private ArrayList<ParentEntity> parents = new ArrayList<>();

    private ParentAdapter adapter;
    private EditText mEdit;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                Intent intent = new Intent(context, QrCodeActivity.class);
                startActivity(intent);
                context.finish();
                break;
            case R.id.btn_exchange:
                Intent intent_exchange = new Intent(context, CustomerActivity.class);
                startActivity(intent_exchange);
                context.finish();
                break;
            case R.id.back:
                finish();
                break;
            case R.id.search:
                getSearchData();
                break;
            default:
                break;
        }
    }

    @Override
    protected void init() {
        loadData();
        TAG = TAG + "TEAM";
        setContentView(R.layout.activity_team);
        // TODO Auto-generated method stub

        Tv_title = (TextView) findViewById(R.id.title);
        Tv_title.setText("已购买会员");

        IBtn_back = (ImageButton) context.findViewById(R.id.back);
        IBtn_back.setOnClickListener(this);

        Btn_exchange = (Button) context.findViewById(R.id.btn_exchange);
        Btn_exchange.setOnClickListener(this);

        listView = (LoadExpandableListView) context.findViewById(R.id.list_team);
        listView.setLayoutAnimation(controller);
        listView.setOnLoadListViewListener(this);

        listView.setOnGroupExpandListener(this);

        layout = (LinearLayout) context.findViewById(R.id.layout_null_team);

        Btn_confirm = (Button) context.findViewById(R.id.layout_team).findViewById(R.id.btn_confirm);
        Btn_confirm.setOnClickListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) context.findViewById(R.id.swipeRefreshLayout_team);
        swipeRefreshLayout.setOnRefreshListener(this);

        mEdit = (EditText) findViewById(R.id.search_content);
        final ImageView search = (ImageView) findViewById(R.id.search);
        mEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    getSearchData();
                }
                return false;
            }
        });

        search.setOnClickListener(this);
        InitSwipeRefreshLayout();
        adapter = new ParentAdapter(context, parents);
        listView.setAdapter(adapter);
        model = new TeamModelImpl();
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });
        this.onRefresh();
    }

    private void getSearchData() {
        if (!TextUtils.isEmpty(mEdit.getText().toString().trim())) {
            searchPage = "0";
            mListType.clear();
            parents.clear();
            adapter.notifyDataSetChanged();
            String searchContent = mEdit.getText().toString().trim();
            model.searchInfo(Url.search, "1", searchContent, searchPage, this);
            model.searchInfo(Url.search, "2", searchContent, searchPage, this);
            model.searchInfo(Url.search, "3", searchContent, searchPage, this);
        } else {
            Toast.makeText(context, "请输入搜索内容", Toast.LENGTH_SHORT).show();
        }
    }


    private void setData(List<TeamBean> ListType) {

        position = Integer.parseInt(page);

        if (position != 0) {
            position = position - 10;
        }

        if (isRefresh == true) {
            isRefresh = false;
            parents.clear();
            mListType.clear();
        }

        listView.loadComplete();
        if (ListType.size() > 0) {
            mListType.addAll(ListType);
            loadData();
            adapter.setOnChildTreeViewClickListener(this);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(context, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ???????? ??????????
     */
    @Override
    public void onSuccess(List<TeamBean> ListType, String page) {
        // TODO Auto-generated method stub
        layout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        this.page = page;
        if (ListType == null) {
            // ??????????????
            if (isRefresh != true) {
                listView.setComplete(true);
                listView.loadComplete();
                return;
            }
            // ???????
            else {
                mListType.clear();
                listView.loadComplete();
                layout.setVisibility(View.VISIBLE);
            }
        } else {
            listView.setComplete(false);
            setData(ListType);
        }
    }

    /**
     * ???????? ??????????
     */
    @Override
    public void onError(String error) {
        // TODO Auto-generated method stub
        ToastTool.showToast(context, error);
        swipeRefreshLayout.setRefreshing(false);
        listView.loadComplete();
    }

    @Override
    public void onSearchSuccess(List<TeamBean> listType, String page2) {
        layout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
        if (listType == null) {
            // ?????????????
            listView.setComplete(true);
            listView.loadComplete();
            return;

        } else {
            listView.setComplete(false);
            setData2(listType);
        }

    }

    private void setData2(List<TeamBean> ListType) {
        listView.loadComplete();
        mListType.addAll(ListType);
        loadData();
        adapter.notifyDataSetChanged();
    }

    /**
     * ???????
     */
    @Override
    public void onRefresh() {
        // TODO Auto-generated method stub
        Log.i(TAG, "onRefresh");
        page = "0";
        isRefresh = true;
        model.getInfo(Url.myteam, page, this);
        mEdit.setText(null);
    }

    /**
     * ????????
     */
    @Override
    public void onLoad() {
        // TODO Auto-generated method stub
        //            model.getInfo(Url.myteam, page, this);
        if (listView != null) {
            listView.loadComplete();
        }
        Toast.makeText(context, "没有更多数据", Toast.LENGTH_SHORT).show();
    }

    /**
     * @author Apathy????
     * <p>
     * ?????????????
     */
    private void loadData() {
        for (int i = 0; i < mListType.size(); i++) {

            ParentEntity parent = new ParentEntity();

            parent.setId(mListType.get(i).id);

            parent.setGroupName(mListType.get(i).nickname);

            parent.setLogo(mListType.get(i).avatar);

            parent.setGroupColor(Color.parseColor("#FFFFFF"));

            ArrayList<ChildEntity> childs = new ArrayList<ChildEntity>();

            for (int j = 0; j < mListType.get(i).bean2.size(); j++) {

                ChildEntity child = new ChildEntity();

                child.setGroupName(mListType.get(i).bean2.get(j).groupName);

                child.setGroupColor(Color.parseColor("#4288CA"));

                child.setGroupId(mListType.get(i).bean2.get(j).groupId);

                child.setGroupLogo(mListType.get(i).bean2.get(j).groupLogo);

                ArrayList<String> childNames = new ArrayList<String>();

                ArrayList<String> childLogos = new ArrayList<String>();

                ArrayList<String> childIds = new ArrayList<String>();

                ArrayList<Integer> childColors = new ArrayList<Integer>();

                ArrayList<String> childTeamcounts = new ArrayList<String>();

                for (int k = 0; k < mListType.get(i).bean2.get(j).childNames.size(); k++) {

                    childNames.add(mListType.get(i).bean2.get(j).childNames.get(k));

                    childIds.add(mListType.get(i).bean2.get(j).childIds.get(k));

                    childLogos.add(mListType.get(i).bean2.get(j).childLogos.get(k));

                    childTeamcounts.add(mListType.get(i).bean2.get(j).childteamcounts.get(k));

                    childColors.add(Color.parseColor("#333333"));

                }

                child.setChildNames(childNames);
                child.setChildIds(childIds);
                child.setChildLogos(childLogos);
                child.setTeamcount(childTeamcounts);

                childs.add(child);

            }

            parent.setChilds(childs);

            parents.add(parent);

        }
    }

    /**
     * @author Apathy????
     * <p>
     * ?????ExpandableListView???????????????????????????????????????????
     */
    @Override
    public void onClickPosition(int parentPosition, int groupPosition, int childPosition) {

    }

    /**
     * @author Apathy????
     * <p>
     * ??????????????????????????????
     */
    @Override
    public void onGroupExpand(int groupPosition) {
        for (int i = 0; i < parents.size(); i++) {
            if (i != groupPosition) {
                listView.collapseGroup(i);
            }
        }
    }

}
