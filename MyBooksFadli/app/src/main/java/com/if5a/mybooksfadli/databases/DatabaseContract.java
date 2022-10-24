package com.if5a.mybooksfadli.databases;

import android.provider.BaseColumns;

public class DatabaseContract {
    static String TABLE_BOOKS = "books";

    static final class BooksColumns implements BaseColumns{
        static String ISBN = "isbn";
        static String TITLE = "title";
        static String AUTHOR = "author";
        static String YEAROFPUBLICATION = "yearofpublication";
        static String PUBLISHER = "publisher";
        static String IMAGEURLS = "imageurls";
        static String IMAGEURLM = "imageurlm";
        static String IMAGEURLL = "imageurll";
    }
}
