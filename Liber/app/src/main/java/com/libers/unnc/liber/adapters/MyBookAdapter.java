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
import com.libers.unnc.liber.model.bean.State;
import com.libers.unnc.liber.model.bean.User;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.model.bean.Book;

import org.litepal.crud.DataSupport;

/**
 * Created by a11 on 17/2/28.
 */

public class MyBookAdapter extends BaseAdapter {

    private List<Book> list;
    private LayoutInflater inflater;
    Context context;

    public MyBookAdapter(Context context) {
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


    public String getItemISBN(int position) {
        return list.get(position).getIsbn13();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyBookViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyBookViewHolder();
            convertView = inflater.inflate(R.layout.fragment_my_book_item, null);
            viewHolder.ivCover = (ImageView) convertView.findViewById(R.id.iv_cover);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.tvduetime = (TextView) convertView.findViewById(R.id.tv_duetime);
            convertView.setTag(viewHolder);
        }

        viewHolder = (MyBookViewHolder) convertView.getTag();
        Book bean = list.get(position);


        // 设置图片
        Glide.with(viewHolder.ivCover.getContext())
                .load(bean.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(viewHolder.ivCover.getContext()).icon(GoogleMaterial.Icon.gmd_import_contacts).color(Color.GRAY).paddingDp(10))
                .into(viewHolder.ivCover);

        // 设置其他
        viewHolder.tvTitle.setText(bean.getTitle());
//        viewHolder.rbRate.setRating((Float.parseFloat(bean.getAverage())/2));

        String username = DataSupport.findLast(State.class).getUsername();
        String isbn = bean.getIsbn13();
        List<User> usersdata = DataSupport.where("username = ? and isbn13 = ?",username,isbn).find(User.class);
        String duetime = usersdata.get(0).getDueTime();

        viewHolder.tvduetime.setText("Due time: "+ duetime);

        return convertView;
    }
}

class MyBookViewHolder {
    public ImageView ivCover;
    public TextView tvTitle;
    public TextView tvduetime;
}
