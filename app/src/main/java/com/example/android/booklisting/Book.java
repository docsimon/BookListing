package com.example.android.booklisting;

/**
 * Created by doc on 21/11/2017.
 */

public class Book {

    private String title;
    private String author;
    /*
  Constructor
   */
    public Book(String title, String author){
        this.title = title;
        this.author = author;
    }

     /*
    public getters
     */

    public String getTitle(){
        return this.title;
    }
    public String getAuthor() { return this.author; }
}


