package com.libers.unnc.liber.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import java.util.ArrayList;
import java.util.List;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.model.bean.Book;

/**
 * Created by a11 on 17/3/11.
 */

public class MapListAdapter extends BaseAdapter {
    private List<Book> list;
    private LayoutInflater inflater;
    Context context;
    View oldView;

    public MapListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        oldView = null;
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
        MapListViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MapListViewHolder();
            convertView = inflater.inflate(R.layout.guidemap_list_item, null);
            viewHolder.cardView = (CardView) convertView.findViewById(R.id.maplist_item);
            viewHolder.maplist_item_image = (ImageView) convertView.findViewById(R.id.maplist_item_image);
            viewHolder.maplist_item_title = (TextView) convertView.findViewById(R.id.maplist_item_title);
            viewHolder.maplist_item_author = (TextView) convertView.findViewById(R.id.maplist_item_author);
            viewHolder.maplist_item_divider = (TextView) convertView.findViewById(R.id.maplist_item_divider);
            viewHolder.maplist_item_translator = (TextView) convertView.findViewById(R.id.maplist_item_translator);
            viewHolder.maplist_item_publisher = (TextView) convertView.findViewById(R.id.maplist_item_publisher);
            viewHolder.numShelf = (TextView) convertView.findViewById(R.id.numShelf);
            viewHolder.numLevel = (TextView) convertView.findViewById(R.id.numLevel);
            //convertView.setTag(position);     //this call of method leads to // STOPSHIP: 17/3/12
            convertView.setTag(viewHolder);
        }

        viewHolder = (MapListViewHolder) convertView.getTag();
        Book bean = list.get(position);

        // set the book cover image
        Glide.with(viewHolder.maplist_item_image.getContext())
                .load(bean.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(viewHolder.maplist_item_image.getContext()).icon(GoogleMaterial.Icon.gmd_import_contacts).color(Color.GRAY).paddingDp(10))
                .into(viewHolder.maplist_item_image);

        // set other info of books
        viewHolder.maplist_item_title.setText(bean.getTitle());
        viewHolder.maplist_item_author.setText(bean.getAuthor());
        viewHolder.maplist_item_publisher.setText(bean.getPublisher());

        if (bean.getTranslator().isEmpty()) {
            viewHolder.maplist_item_divider.setVisibility(View.GONE);
            viewHolder.maplist_item_translator.setText("");
        } else {
            viewHolder.maplist_item_translator.setText(bean.getTranslator() + " translated");
        }

        //convertView.setTag(position);
/*
        convertView.setOnClickListener(new View.OnClickListener()  {

            @Override
            public void onClick(View view)  {
                if(oldView == null) {
                    oldView = view;
                    view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.listview_item_selected_bg));
                }else{
                    oldView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.listview_item_pressed));
                    view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.listview_item_selected_bg));
                    oldView = view;
                }
            }
        });

        //这一段是状态保存
        if(oldView != null && (position == (Integer)oldView.getTag())){// 为点击 item
            convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.listview_item_selected_bg));
        }else{
            convertView.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.listview_item_pressed));
        }*/

        return convertView;
    }
}

class MapListViewHolder {
    public ImageView maplist_item_image;
    public TextView maplist_item_title;
    public TextView maplist_item_author;
    public TextView maplist_item_divider;
    public TextView maplist_item_translator;
    public TextView maplist_item_publisher;
    public CardView cardView;

    public TextView numShelf;
    public TextView numLevel;
}



