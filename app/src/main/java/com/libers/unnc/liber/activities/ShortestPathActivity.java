package com.libers.unnc.liber.activities;

import android.os.Bundle;
import android.widget.Toast;

import com.libers.unnc.liber.model.bean.Book;
import com.libers.unnc.liber.R;
import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengye on 3/2/17.
 */

public class ShortestPathActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortest_path);
        List<Book> books = DataSupport.findAll(Book.class);
        ArrayList data = new ArrayList<>();
//        for (int i = 0; i < books.size(); i++) {
//            Book book_db = books.get(i);
//            data.add(book_db.getIsbn13());
//
//
//        }
//        Book book = books.get(0);
        Toast.makeText(ShortestPathActivity.this,"lalalalala", Toast.LENGTH_SHORT).show();
    }
}