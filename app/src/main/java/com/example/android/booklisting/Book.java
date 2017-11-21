package com.example.android.booklisting;

/**
 * Created by doc on 21/11/2017.
 */

public class Book {

    private String title;
    private String author;
    private String url;
    /*
  Constructor
   */
    public Book(String title, String author, String url){
        this.title = title;
        this.author = author;
        this.url = url;
    }

     /*
    public getters
     */

    public String getTitle(){
        return this.title;
    }
    public String getAuthor() { return this.author; }
    public String getUrl() { return this.url; }

}


