package com.example.android.booklisting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String s = getIntent().getStringExtra("Result Title");
        TextView mainTitle = (TextView) findViewById(R.id.searchMainTitle);
        //String compText =  getResources().getString(R.string.main_title_search, s);
        mainTitle.setText(s);
    }
}
