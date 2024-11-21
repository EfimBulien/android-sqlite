package com.example.a08112024;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import androidx.annotation.Nullable;


public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "book_db";//НАЗВАНИЕ БД
    private static final int SCHEMA = 1;//ВЕРСИЯ БД
    static final String TABLE_NAME = "books";//ИМЯ ТАБЛИЦЫ
    public static final String COLUMN_ID = "id_book";
    public static final String COLUMN_NAME = "book_name";
    public static final String COLUMN_AUTHOR = "book_author";


    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDataBase) {
        sqLiteDataBase.execSQL("CREATE TABLE " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_NAME + "TEXT" + COLUMN_AUTHOR + "INTEGER);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDataBase, int oldVersion, int newVersion) {
        sqLiteDataBase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDataBase);
    }


    public long addBook(String bookName, String bookAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, bookName);
        values.put(COLUMN_AUTHOR, bookAuthor);

        long result = db.insert(TABLE_NAME, null, values);
        db.close();
        return result;

    }


    public int deleteBookById(long bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
        return result;
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

}

