package com.example.android.booklisting;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by doc on 21/11/2017.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    public BookAdapter(Activity context, List<Book> books) {
        super(context, 0, books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // Create the reference to the listView
        View callingListView = convertView;
        if (callingListView == null) {
            callingListView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // reference to the Book object
        Book book = getItem(position);

        // reference to the bookTitle textview
        TextView bookTitle = (TextView) callingListView.findViewById(R.id.bookTitle);
        bookTitle.setText((book.getTitle()));

        // reference to the author
        TextView author = (TextView) callingListView.findViewById(R.id.author);
        author.setText(book.getAuthor());

        return callingListView;
    }
}