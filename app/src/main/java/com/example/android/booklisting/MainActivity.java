package com.example.android.booklisting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button searcButton = (Button) findViewById(R.id.search_button);
        searcButton.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers category is clicked on.
            @Override
            public void onClick(View view) {
                // Create a new intent to open the {@link ResultActivity}

                // get the reference and content of the edit
                EditText searchField = (EditText) findViewById(R.id.searchField);
                String query = searchField.getText().toString();
                if (query.isEmpty()) {
                    displayAlertMsg();
                } else {
                    // define the intent
                    Intent searchResultIntent = new Intent(MainActivity.this, ResultActivity.class);
                    // passing the search query to the intent
                    searchResultIntent.putExtra("query", query);
                    // Start the new activity
                    startActivity(searchResultIntent);
                }
            }
        });
    }

    private void displayAlertMsg() {
        String message = getResources().getString(R.string.search_empty);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}