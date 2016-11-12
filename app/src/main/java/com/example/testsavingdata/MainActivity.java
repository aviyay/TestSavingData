package com.example.testsavingdata;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    String file_name = "testSavingData.txt";
    String file_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).getPath() + "/";

    public void save (View view) {
        String message = ((EditText) findViewById(R.id.text_field)).getText().toString();

        SQLite_database_operations();
    }
    private void print (String message) {
        TextView textView = (TextView) findViewById(R.id.printer);
        textView.setText(message);
    }

    private void SQLite_database_operations () {
        MySQLiteHelper db = new MySQLiteHelper(this);
        List<Book> list;
        Book book;

        //db.addBook(new Book("Book1","aviyay"));
        //db.addBook(new Book("Book1","aviyay"));
        //db.addBook(new Book("Book2", "aviyay2"));
        //db.addBook(new Book("Book3", "aviyay3"));


        list = db.getAllBooks();
        //db.deleteBook(list.get(0));
        //db.deleteBook(list.get(1));

        //book = db.getBook(3);
        //book.setAuthor("aviyay");
        //db.updateBook(book);
        //list = db.getAllBooks();
    }

    private boolean isExternalAvailable () {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    private void save_external_file (String message) {
        try {
            File file = new File(file_path, file_name);
            FileOutputStream fileOutputStream= new FileOutputStream(file);
            fileOutputStream.write (message.getBytes());
            fileOutputStream.close();
            print(file.getPath());

            fileOutputStream.close();
        } catch (Exception e) {
            print(e.getMessage());
        }
    }
    private String load_external_file() {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream inputStream = new FileInputStream(file_path + file_name);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            inputStream.close();
            inputStreamReader.close();
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
    private void remove_external_file() {
        try {
            File file = new File (file_path, file_name);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void save_internal_file (String message) {
        String file_name = "testSavingData";
        File file = new File(getFilesDir(), file_name);
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(file_name, Context.MODE_PRIVATE);
            outputStream.write(message.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        print (file.getPath());
    }
    private String load_internal_file() {
        String file_name = "testSavingData";
        StringBuilder stringBuilder = new StringBuilder();

        try {
            FileInputStream inputStream = openFileInput(file_name);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
    private void remove_internal_file() {
        String file_name = "testSavingData";

        try {
            deleteFile(file_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void save_key (String message) {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(getString(R.string.saved_message), message);
        editor.commit();
    }
    private String load_key () {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        String default_message = "not found";
        return sharedPreferences.getString(getString(R.string.saved_message), default_message);
    }
    private void remove_key () {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.remove(getString(R.string.saved_message));
        editor.commit();
    }
}
