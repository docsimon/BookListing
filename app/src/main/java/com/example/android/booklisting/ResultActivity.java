package com.example.android.booklisting;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity implements LoaderCallbacks<List<Book>> {

    private static final int BOOK_LOADER_ID = 1;
    BookAdapter bookAdapter;
    TextView emptyView;
    ProgressBar progressBar;
    private static final String BOOK_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?maxResults=10&q=";
    private String queryString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        String query = getIntent().getStringExtra("query");
        queryString = BOOK_REQUEST_URL + query;

        // Check network connection
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();


        // create an instance of empty view
        emptyView = (TextView) findViewById(R.id.empty_view);
        // Instance of Listview
        final ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setEmptyView(emptyView);

        if (!isConnected) {
            String net_problem = getString(R.string.net_status);
            emptyView.setText(net_problem);
            progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE);

        } else {

            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getLoaderManager();

            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            loaderManager.initLoader(BOOK_LOADER_ID, null, this);

            // Instance of BookAdapter
            // Create a new {@link ArrayAdapter} of book
            bookAdapter = new BookAdapter(this, new ArrayList<Book>());
            //bookAdapter = new BookAdapter(this, books);

            // Set the adapter on the {@link ListView}
            // so the list can be populated in the user interface
            bookListView.setAdapter(bookAdapter);

            bookListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Book listItem = (Book) bookListView.getItemAtPosition(position);
                    String url = listItem.getUrl();
                    // Convert the String URL into a URI object (to pass into the Intent constructor)
                    Uri bookUri = Uri.parse(url);
                    // Create a new intent to view the book URI
                    Intent bookUrlPage = new Intent(Intent.ACTION_VIEW, bookUri);
                    startActivity(bookUrlPage);
                }
            });
        }
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        // Create a new loader for the given URL
        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        // Clear the adapter of previous data
        bookAdapter.clear();
        String empty_list = getString(R.string.empty_list);
        emptyView.setText(empty_list);
        progressBar = (ProgressBar) findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);

        // If there is a valid list of {@link Book}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (books != null && !books.isEmpty()) {
            bookAdapter.addAll(books);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        // Loader reset, so we can clear out our existing data.
        bookAdapter.clear();
    }
}