package com.if5a.mybooksfadli.databases;

import static android.provider.BaseColumns._ID;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.AUTHOR;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.IMAGEURLL;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.IMAGEURLM;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.IMAGEURLS;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.ISBN;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.PUBLISHER;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.TITLE;
import static com.if5a.mybooksfadli.databases.DatabaseContract.BooksColumns.YEAROFPUBLICATION;
import static com.if5a.mybooksfadli.databases.DatabaseContract.TABLE_BOOKS;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbbooks";
    private static final int DATABASE_VERSION = 1;
    private  static String CREATE_TABLE_BOOKS = "CREATE TABLE " + TABLE_BOOKS + " ("
            + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + ISBN + " TEXT NOT NULL, "
            + TITLE + " TEXT NOT NULL, "
            + AUTHOR + " TEXT NOT NULL, "
            + YEAROFPUBLICATION + " TEXT NOT NULL, "
            + PUBLISHER + " TEXT NOT NULL, "
            + IMAGEURLS + " TEXT NOT NULL, "
            + IMAGEURLM + " TEXT NOT NULL, "
            + IMAGEURLL + " TEXT NOT NULL);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_BOOKS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOKS);
        onCreate(db);
    }
}
