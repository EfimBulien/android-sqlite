package com.example.spsp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;
    private ArrayList<Book> bookArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
        Log.d("LoadBooks", "Количество книг: " + bookArrayList.size());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);
        bookArrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.list_books);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(this, bookArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);


        Button addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddBookActivity.class)));
        loadBooks();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void loadBooks() {
        bookArrayList.clear();
        Cursor cursor = dataBaseHelper.getAllBooks();

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_NAME));
                String author = cursor.getString(cursor.getColumnIndexOrThrow(DataBaseHelper.COLUMN_AUTHOR));
                bookArrayList.add(new Book(id, author, name));
            } while (cursor.moveToNext()); // Переход к следующей записи
        }

        cursor.close(); // Закрытие курсора
        recyclerViewAdapter.notifyDataSetChanged(); // Уведомление адаптера об изменениях
    }
}