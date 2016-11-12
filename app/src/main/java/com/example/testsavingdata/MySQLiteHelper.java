package com.example.testsavingdata;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by אביה on 25/09/2015.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "BookDB";

    private static final String TABLE_BOOKS = "books";

    private static final String KEY_ID = "id";
    private static final String KEY_TITLE = "title";
    private static final String KEY_AUTHOR = "author";

    private static final String[] COLUMNS = {KEY_ID,KEY_TITLE,KEY_AUTHOR};


    public MySQLiteHelper (Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_BOOK_TABLE = "CREATE TABLE books ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "title TEXT, " +
                "author TEXT )";
        db.execSQL(CREATE_BOOK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS books");

        onCreate(db);
    }

    public void addBook (Book book) {
        Log.d("addBook", book.toString());

        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TITLE, book.getTitle());
        contentValues.put(KEY_AUTHOR, book.getAuthor());

        db.insert(TABLE_BOOKS, null, contentValues);
        db.close();
    }

    public Book getBook (int id) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor =
                db.query(TABLE_BOOKS,
                        COLUMNS,
                        " id = ?",
                        new String[]{String.valueOf(id)},
                        null,
                        null,
                        null,
                        null);

        if (cursor != null)
            cursor.moveToFirst();

        Book book = new Book();
        book.setId(Integer.parseInt(cursor.getString(0)));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));

        Log.d("getBook(" + id + ")", book.toString());

        return book;
    }
    public List<Book> getAllBooks() {
        List<Book> books = new LinkedList<Book>();

        String query = "SELECT  * FROM " + TABLE_BOOKS;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        Book book = null;
        if (cursor.moveToFirst()) {
            do {
                book = new Book();
                book.setId(Integer.parseInt(cursor.getString(0)));
                book.setTitle(cursor.getString(1));
                book.setAuthor(cursor.getString(2));

                books.add(book);
            } while (cursor.moveToNext());
        }

        Log.d("getAllBooks()", books.toString());

        return books;
    }

    public int updateBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, book.getTitle());
        values.put(KEY_AUTHOR, book.getAuthor());

        int i = db.update(TABLE_BOOKS,
                values,
                KEY_ID+" = ?",
                new String[] { String.valueOf(book.getId()) });

        db.close();

        return i;
    }

    public void deleteBook(Book book) {
        SQLiteDatabase db = getWritableDatabase();

        db.delete(TABLE_BOOKS,
                KEY_ID+" = ?",
                new String[] { String.valueOf(book.getId()) });
        db.close();

        Log.d("deleteBook", book.toString());
    }
}
