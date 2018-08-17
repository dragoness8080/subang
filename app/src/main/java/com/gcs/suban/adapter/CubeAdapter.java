package com.gcs.suban.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.gcs.suban.R;
import com.gcs.suban.view.NoSlideGridView;

import java.util.List;

public class CubeAdapter extends Adapter<CubeAdapter.ViewHolider> {

    private Context context;
    private List<CubeChildAdapter> mListType;

    public CubeAdapter(Context context, List<CubeChildAdapter> list){
        this.context = context;
        mListType = list;
    }

    @Override
    public ViewHolider onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_cube, null);
        return new ViewHolider(view);
    }

    @Override
    public void onBindViewHolder(ViewHolider holder, int position) {
        CubeChildAdapter bean = mListType.get(position);
        holder.gridView.setAdapter(bean);
    }

    @Override
    public int getItemCount() {
        return mListType.size();
    }

    static class ViewHolider extends RecyclerView.ViewHolder{

        public NoSlideGridView gridView;

        public ViewHolider(View itemView) {
            super(itemView);
            gridView = (NoSlideGridView) itemView.findViewById(R.id.child_cube);
        }
    }
}
