package com.soul.hodgepodge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.soul.hodgepodge.R;

import java.util.List;

/**
 * Created by Soul on 2018/10/31.
 */

public class AnimsAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mData;
    private LayoutInflater mInflater;

    public AnimsAdapter(Context context, List<String> data) {
        mContext = context;
        mData = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = new ViewHolder();
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.lv_anims_name_item,null);
            viewHolder.animName = convertView.findViewById(R.id.item_anim_name);
        }
        viewHolder.animName.setText(mData.get(position));
        return convertView;
    }
    private class ViewHolder{
        public TextView animName;
    }
}
