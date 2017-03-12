package com.libers.unnc.liber.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.adapters.BookIntroAdapter;
import com.libers.unnc.liber.model.bean.TagItem;
import com.libers.unnc.liber.model.bean.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookIntroFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookIntroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookIntroFragment extends Fragment {
    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";

    private Book book;
    private List<TagItem> data;

    public static BookIntroFragment newInstance(int bookId) {
        BookIntroFragment fragment = new BookIntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookIntroFragment newInstance(Book book) {
        BookIntroFragment fragment = new BookIntroFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_BOOK_ID)) {
                book = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                book = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView lv = (ListView) inflater.inflate(R.layout.fragment_book_intro_list, container, false);

        // 数据
        data = new ArrayList<>();
        if (!book.getSummary().isEmpty()) data.add(new TagItem("Book introduction", book.getSummary()));
        if (!book.getAuthor_intro().isEmpty()) data.add(new TagItem("Author intorduction", book.getAuthor_intro()));
        if (!book.getCatalog().isEmpty()) data.add(new TagItem("Catalog", book.getCatalog()));

        // 列表适配器
        BookIntroAdapter lvBaseAdapter = new BookIntroAdapter(getContext(), data);

        // 列表
        lv.setAdapter(lvBaseAdapter);

        return lv;
    }
}
