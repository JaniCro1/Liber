package com.libers.unnc.liber.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.libers.unnc.liber.activities.ShortestPathActivity;
import com.libers.unnc.liber.app.MyApplication;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.activities.BookInfoActivity;
import com.libers.unnc.liber.activities.BookInfoAddActivity;
import com.libers.unnc.liber.model.bean.Book;

/**
 * Created by HaPBoy on 5/21/16.
 */
public class GuideListAdapter extends RecyclerView.Adapter<GuideListViewHolder> {

    List<Book> list;
    LayoutInflater inflater;
    Context context;


    public GuideListAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void add(Book bookData) {
        list.add(bookData);
        notifyItemInserted(list.size() - 1);
    }

    public void setData(List<Book> booksData) {
        list.clear();
        list.addAll(booksData);
    }

    public void clear() {
        list.clear();
    }

    @Override
    public GuideListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GuideListViewHolder(inflater.inflate(R.layout.guidemap_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(GuideListViewHolder holder, final int position) {
        // 设置图片
        Glide.with(holder.bookImage.getContext())
                .load(list.get(position).getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(holder.bookImage.getContext()).icon(GoogleMaterial.Icon.gmd_book).colorRes(R.color.boo_item_icon).paddingDp(10))
                .into(holder.bookImage);

        // 设置其他
        holder.bookName.setText(list.get(position).getTitle());
        holder.bookAuthor.setText(list.get(position).getAuthor());
        holder.bookPublisher.setText(list.get(position).getPublisher());

        String isbn = list.get(position).getIsbn13();
        String bookshlf = isbn.substring(11,12);
        String level = isbn.substring(12, 13);
        int bs = Integer.parseInt(bookshlf);
        int lev = Integer.parseInt(level);
        if(bs == 0 ){
            bs = 1;
        }
        if(lev == 0){
            lev = 1;
        }

        holder.bookshelf.setText(bs+"");
        holder.level.setText(lev+"");






        // 设置翻译者
        if (list.get(position).getTranslator().isEmpty()) {
            holder.bookDivider.setVisibility(View.GONE);
            holder.bookTranslator.setText("");
        } else {
            holder.bookTranslator.setText(list.get(position).getTranslator() + " 译");
        }

        // 设置CardView点击事件
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShortestPathActivity.infor = list.get(position).getIsbn13();
                ShortestPathActivity.mapChanged();
//                Intent intent = new Intent(context, ShortestPathActivity.class);
//                intent.putExtra("id", list.get(position).getIsbn13());
//
//                context.startActivity(intent);

                //Toast.makeText(MyApplication.getContext(), list.get(position).getIsbn13(), Toast.LENGTH_SHORT).show();



            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClickInAdapter {
        public void onClickInAdapter(String content);
    }

}

class GuideListViewHolder extends RecyclerView.ViewHolder {

    ImageView bookImage;
    TextView bookName;
    TextView bookAuthor;
    TextView bookTranslator;
    TextView bookPublisher;
    TextView bookDivider;
    CardView cardView;
    TextView bookshelf;
    TextView level;

    public GuideListViewHolder(View itemView) {
        super(itemView);
        bookImage = (ImageView) itemView.findViewById(R.id.maplist_item_image);
        bookName = (TextView) itemView.findViewById(R.id.maplist_item_title);
        bookAuthor = (TextView) itemView.findViewById(R.id.maplist_item_author);
        bookTranslator = (TextView) itemView.findViewById(R.id.maplist_item_translator);
        bookPublisher = (TextView) itemView.findViewById(R.id.maplist_item_publisher);
        bookDivider = (TextView) itemView.findViewById(R.id.maplist_item_divider);
        cardView = (CardView) itemView.findViewById(R.id.maplist_item);
        bookshelf = (TextView) itemView.findViewById(R.id.numShelf);
        level = (TextView)itemView.findViewById(R.id.numLevel);


    }


}