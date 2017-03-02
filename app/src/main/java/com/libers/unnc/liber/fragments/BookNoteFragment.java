package com.libers.unnc.liber.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import com.libers.unnc.liber.R;
import com.libers.unnc.liber.activities.BookNoteEditActivity;
import com.libers.unnc.liber.model.bean.Book;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookNoteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookNoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookNoteFragment extends Fragment {

    private static final String ARG_BOOK_ID = "book_id";
    private int booId = 1;

    private TextView tvContent;
    private TextView tvDate;
    private ImageView ivIconDate;

    public static BookNoteFragment newInstance(int itemId) {
        BookNoteFragment fragment = new BookNoteFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            booId = getArguments().getInt(ARG_BOOK_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View contentPanel = inflater.inflate(R.layout.fragment_book_note, container, false);

        tvContent = (TextView) contentPanel.findViewById(R.id.tv_content);
        tvDate = (TextView) contentPanel.findViewById(R.id.tv_date);
        ivIconDate = (ImageView) contentPanel.findViewById(R.id.iv_icon_date);

        // Edit Button
        ImageView ivEdit = (ImageView) contentPanel.findViewById(R.id.iv_edit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), BookNoteEditActivity.class);
                intent.putExtra("id", booId);
                startActivity(intent);
            }
        });

        return contentPanel;
    }

    public void loadData() {
        // 图书数据
        Book book = DataSupport.find(Book.class, booId);

        String note = book.getNote();
        String note_date = book.getNote_date();

        if (note.isEmpty()) {
            note = "\nEmpty note，click right corner and start your first note!\n";
        }
        tvContent.setText(note);

        if (note_date.isEmpty()) {
            ivIconDate.setAlpha(0f);
            tvContent.setGravity(Gravity.CENTER);
        } else {
            ivIconDate.setAlpha(1f);
            tvContent.setGravity(Gravity.NO_GRAVITY);
        }
        tvDate.setText(note_date);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }
}
