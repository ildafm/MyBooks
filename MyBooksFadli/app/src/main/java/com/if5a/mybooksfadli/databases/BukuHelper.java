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

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.if5a.mybooksfadli.models.Buku;

import java.util.ArrayList;

public class BukuHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public BukuHelper(Context context) {
        this.context = context;
    }

    public BukuHelper open() throws SQLException{
        databaseHelper = new DatabaseHelper(context);
        database = databaseHelper.getReadableDatabase();
        return this;
    }

    public void close(){databaseHelper.close();}

    public ArrayList<Buku> getAllDataBooks(){
        Cursor cursor = database.query(TABLE_BOOKS,
                null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null
                );

        cursor.moveToFirst();
        ArrayList<Buku> arrayList = new ArrayList<>();
        Buku buku;

        if(cursor.getCount() > 0){
            do{
                buku = new Buku();
                buku.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                buku.setIsbn(cursor.getString(cursor.getColumnIndexOrThrow(ISBN)));
                buku.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                buku.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR)));
                buku.setYearOfPublication(cursor.getString(cursor.getColumnIndexOrThrow(YEAROFPUBLICATION)));
                buku.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(PUBLISHER)));
                buku.setImage_url_s(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLS)));
                buku.setImage_url_m(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLM)));
                buku.setImage_url_l(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLL)));

                arrayList.add(buku);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Buku> getAllDataBooksByTitle(String title){
        Cursor cursor = database.query(TABLE_BOOKS,
                null,
                TITLE + " LIKE ?",
                new String[]{"%" + title + "%"},
                null,
                null,
                _ID + " ASC",
                null
        );

        cursor.moveToFirst();
        ArrayList<Buku> arrayList = new ArrayList<>();
        Buku buku;

        if(cursor.getCount() > 0){
            do{
                buku = new Buku();
                buku.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                buku.setIsbn(cursor.getString(cursor.getColumnIndexOrThrow(ISBN)));
                buku.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                buku.setAuthor(cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR)));
                buku.setYearOfPublication(cursor.getString(cursor.getColumnIndexOrThrow(YEAROFPUBLICATION)));
                buku.setPublisher(cursor.getString(cursor.getColumnIndexOrThrow(PUBLISHER)));
                buku.setImage_url_s(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLS)));
                buku.setImage_url_m(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLM)));
                buku.setImage_url_l(cursor.getString(cursor.getColumnIndexOrThrow(IMAGEURLL)));

                arrayList.add(buku);
                cursor.moveToNext();
            }while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertDataBooks(Buku buku){
        ContentValues cv = new ContentValues();
        cv.put(ISBN, buku.getIsbn());
        cv.put(TITLE, buku.getTitle());
        cv.put(AUTHOR, buku.getAuthor());
        cv.put(YEAROFPUBLICATION, buku.getYearOfPublication());
        cv.put(PUBLISHER, buku.getPublisher());
        cv.put(IMAGEURLS, buku.getImage_url_s());
        cv.put(IMAGEURLM, buku.getImage_url_m());
        cv.put(IMAGEURLL, buku.getImage_url_l());
        return database.insert(TABLE_BOOKS, null, cv);
    }

    public void beginTransaction(){database.beginTransaction();}

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){database.endTransaction();}

    public void insertTransactionDataBooks(Buku buku){
        String sql = "INSERT INTO "
                + TABLE_BOOKS + " ("
                + ISBN + ", "
                + TITLE + ", "
                + AUTHOR + ", "
                + YEAROFPUBLICATION + ", "
                + PUBLISHER + ", "
                + IMAGEURLS + ", "
                + IMAGEURLM + ", "
                + IMAGEURLL + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, buku.getIsbn());
        stmt.bindString(1, buku.getTitle());
        stmt.bindString(1, buku.getAuthor());
        stmt.bindString(1, buku.getYearOfPublication());
        stmt.bindString(1, buku.getPublisher());
        stmt.bindString(1, buku.getImage_url_s());
        stmt.bindString(1, buku.getImage_url_m());
        stmt.bindString(1, buku.getImage_url_l());
        stmt.execute();
        stmt.clearBindings();
    }

    public long updateDataBooks(Buku buku){
        ContentValues cv = new ContentValues();
        cv.put(ISBN, buku.getIsbn());
        cv.put(TITLE, buku.getTitle());
        cv.put(AUTHOR, buku.getAuthor());
        cv.put(YEAROFPUBLICATION, buku.getYearOfPublication());
        cv.put(PUBLISHER, buku.getPublisher());
        cv.put(IMAGEURLS, buku.getImage_url_s());
        cv.put(IMAGEURLM, buku.getImage_url_m());
        cv.put(IMAGEURLL, buku.getImage_url_l());

        return database.update(TABLE_BOOKS,
                cv,
                _ID + " = " + buku.getId() + "'",
                null);
    }

    public long deleteDataBooks(int id){
        return database.delete(TABLE_BOOKS,
                _ID + " = " + id + "'",
                null);
    }
}
