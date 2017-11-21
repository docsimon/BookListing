package com.example.android.booklisting;

/**
 * Created by doc on 21/11/2017.
 */

import java.net.MalformedURLException;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods .
 */
public final class Utils {

    /**
     * Tag for the log messages
     */
    public static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link Utils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name Utils (and an object instance of Utils is not needed).
     */

    private Utils() {

    }


    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }


        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link Book} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Book> fetchBooks(String requestUrl) {


//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


        // Create an empty ArrayList that we can start adding books to
        List<Book> books = new ArrayList<>();

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(createUrl(requestUrl));
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }


        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            JSONObject jsonFile = new JSONObject(jsonResponse);
           // Log.v("books array len: ", "" + jsonFile);

            JSONArray booksArray = jsonFile.getJSONArray("items");

            //Log.v("books array len: ", "" + booksArray);

            for (int i = 0; i < booksArray.length(); i++) {

                /**
                 * get the title and author
                 */
                JSONObject singleBook = booksArray.getJSONObject(i);
               // String kinds = singleBook.getString("kind");

                JSONObject volumeInfo = singleBook.getJSONObject("volumeInfo");
                String title = volumeInfo.getString("title");
                JSONArray authorsArray = volumeInfo.getJSONArray("authors");

               // Log.v("books array len: ", "" + title + "\n");

                // Log.v("books array : ", "" + booksArray + "\n");
                String authors = authorsArray.join(", ");

                 //Log.v("authors: ", "" + authors + "\n");

//
//                JSONObject properties = arrSingleFeature.getJSONObject("volumeInfo");
//                String title = properties.optString("title");
//                JSONArray authors = properties.getJSONArray("authors");
//                String firstAuthor = authors.getString(i);
//
//                Log.v("book title: ", "" + title);

               books.add(new Book(title, authors));


            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("Utils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes
        return books;
    }

}