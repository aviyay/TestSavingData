package com.example.testsavingdata;

/**
 * Created by אביה on 25/09/2015.
 */
public class Book {

    private int id;
    private String title;
    private String author;

    public Book () {}

    public Book (String t, String a) {
        super ();
        author = a;
        title = t;
    }

    @Override
    public String toString() {
        return "Book [id=" + id +", title=" + title + ", author=" + author + "]";
    }

    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
}
