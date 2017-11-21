package com.example.android.booklisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {


    BookAdapter bookAdapter;
    TextView emptyView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String s = getIntent().getStringExtra("Result Title");
        //TextView mainTitle = (TextView) findViewById(R.id.searchMainTitle);
        //String compText =  getResources().getString(R.string.main_title_search, s);
        //mainTitle.setText(s);

        // create an instance of empty view
        emptyView = (TextView) findViewById(R.id.empty_view);
        // Instance of Listview
        final ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setEmptyView(emptyView);

        //create a temp array of books (Book object)
        List books = new ArrayList<Book>();

        books.add(new Book("TCP/IP Illustated", "Richard Stevens"));
        books.add(new Book("Unix Network Programming", "Richard Stevens"));
        books.add(new Book("Swift Language", "Apple"));
        books.add(new Book("Android", "Google"));
        books.add(new Book("TCP/IP Illustated", "Richard Stevens"));
        books.add(new Book("Unix Network Programming", "Richard Stevens"));
        books.add(new Book("Swift Language", "Apple"));
        books.add(new Book("Android", "Google"));
        books.add(new Book("TCP/IP Illustated", "Richard Stevens"));
        books.add(new Book("Unix Network Programming", "Richard Stevens"));
        books.add(new Book("Swift Language", "Apple"));
        books.add(new Book("Android", "Google"));




        // Instance of BookAdapter
        // Create a new {@link ArrayAdapter} of earthquakes
       // bookAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookAdapter = new BookAdapter(this, books);


        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        bookListView.setAdapter(bookAdapter);

    }
}
