package com.example.spsp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "book_db";
    private static final int SCHEMA = 2;

    static final String TABLE_NAME = "books";

    public static final String COLUMN_ID = "id_book";
    public static final String COLUMN_NAME = "book_name";
    public static final String COLUMN_AUTHOR = "book_author";

    public DataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_AUTHOR + " TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
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

    public void deleteBookById(long bookId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(bookId)});
        db.close();
    }

    public Cursor getAllBooks() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.query(TABLE_NAME, null,
                null, null, null, null, null);
    }

    public Book getBookById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_AUTHOR},
                COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            int bookId = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
            String bookName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
            String bookAuthor = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR));
            cursor.close();
            return new Book(bookId, bookAuthor, bookName);
        }

        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    public void updateBook(int bookId, String newName, String newAuthor) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_AUTHOR, newAuthor);

        db.update(TABLE_NAME, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(bookId)});
        db.close();
    }
}