package com.libers.unnc.liber.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.libers.unnc.liber.R;
import com.libers.unnc.liber.model.bean.Book;
import com.libers.unnc.liber.model.bean.User;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengye on 3/4/17.
 */

public class MyBookGridAdapter extends BaseAdapter {
    private List<Book> list;
    private LayoutInflater inflater;
    Context context;

    public MyBookGridAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Book> list) {
        this.list = list;
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
        return list.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyBookGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyBookGridViewHolder();
            convertView = inflater.inflate(R.layout.fragment_my_book_item, null);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.dueTime = (TextView) convertView.findViewById(R.id.duetime);

            convertView.setTag(viewHolder);
        }

        viewHolder = (MyBookGridViewHolder) convertView.getTag();
        Book bean = list.get(position);
        //User  user = ;
        // 设置图片
        Glide.with(viewHolder.ivCover.getContext())
                .load(bean.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(viewHolder.ivCover.getContext()).icon(GoogleMaterial.Icon.gmd_import_contacts).color(Color.GRAY).paddingDp(10))
                .into(viewHolder.ivCover);

        // 设置其他
        viewHolder.tvTitle.setText(bean.getTitle());
        //设置 duetime




        return convertView;
    }
}

class MyBookGridViewHolder {
    public ImageView ivCover;
    public TextView tvTitle;
    public TextView dueTime;
}

