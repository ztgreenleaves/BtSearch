package com.example.ztgreenleaves.search.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ztgreenleaves.search.R;
import com.example.ztgreenleaves.search.bean.SearchResultItemBean;

import java.util.List;

/**
 * Created by ztgreenleaves on 2017/3/15.
 */

public class SearchResultAdapter extends ArrayAdapter<SearchResultItemBean> {
    private int resourceId;

    public SearchResultAdapter(@NonNull Context context, @LayoutRes int resource,
                               @NonNull List<SearchResultItemBean> objects) {
        super(context, resource, objects);
        resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SearchResultItemBean itemBean = getItem(position);
        View view;
        Holder holder = null;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            holder = new Holder();

            holder.file_num = (TextView) view.findViewById(R.id.file_num);
//            holder.file_prefix = (TextView) view.findViewById(R.id.file_prefix);
            holder.file_key = (TextView) view.findViewById(R.id.file_key);
//            holder.file_postfix = (TextView) view.findViewById(R.id.file_postfix);
            holder.file_hot = (TextView) view.findViewById(R.id.file_hot);
            holder.file_size = (TextView) view.findViewById(R.id.file_size);
            holder.file_velocity = (TextView) view.findViewById(R.id.file_velocity);
            holder.file_magnet = (TextView) view.findViewById(R.id.file_magnet);
            view.setTag(holder);
        } else {
            view = convertView;
            holder = (Holder) view.getTag();
        }
        holder.file_num.setText(itemBean.getFileNum());
//        holder.file_prefix.setText(itemBean.getFileNamePrefix());
        holder.file_key.setText(itemBean.getFileNameKey());
//        holder.file_postfix.setText(itemBean.getFileNamePostfix());
        holder.file_hot.setText(itemBean.getFileHot());
        holder.file_size.setText(itemBean.getFileSize());
        holder.file_velocity.setText(itemBean.getFileVelocity());
        holder.file_magnet.setText(itemBean.getFileMagnet());
        return view;
    }

    class Holder {
        TextView file_num;
//        TextView file_prefix;
        TextView file_key;
//        TextView file_postfix;
        TextView file_hot;
        TextView file_size;
        TextView file_velocity;
        TextView file_magnet;
    }
}
