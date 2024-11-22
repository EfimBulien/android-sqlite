package com.example.spsp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddBookActivity extends AppCompatActivity {
    private DataBaseHelper dataBaseHelper;
    private EditText editTextName;
    private EditText editTextAuthor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        editTextName = findViewById(R.id.edit_name);
        editTextAuthor = findViewById(R.id.edit_author);
        Button addButton = findViewById(R.id.add_button);

        dataBaseHelper = new DataBaseHelper(this);
        addButton.setOnClickListener(view -> addBookToDataBase());
    }

    private void addBookToDataBase() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            return;
        }

        long result = dataBaseHelper.addBook(bookName, bookAuthor);

        if (result > 0) {
            Log.d("AddBook", "Книга добавлена: " + bookName + " автор: " + bookAuthor);
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddBookActivity.this, MainActivity.class));
            finish();
        } else {
            Log.e("Database Error", "Failed to add book: " + bookName + " by " + bookAuthor);
            Toast.makeText(this, "Ошибка добавления книги", Toast.LENGTH_SHORT).show();
        }
    }
}
