package com.example.spsp;

import android.app.Activity;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

public class AddBookActivity extends Activity {
    private DataBaseHelper dataBaseHelper;

    EditText editTextName = findViewById(R.id.edit_name);
    EditText editTextAuthor = findViewById(R.id.edit_author);

    private void addBookToDataBase() {
        String bookName = editTextName.getText().toString().trim();
        String bookAuthor = editTextAuthor.getText().toString().trim();

        if (bookName.isEmpty() || bookAuthor.isEmpty()) {
            Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
        }

        long result = dataBaseHelper.addBook(bookName, bookAuthor);

        if (result > 0) {
            Toast.makeText(this, "Книга добавлена", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AddBookActivity.this, MainActivity.class));
            finish();
        }  else {
            Toast.makeText(this, "Ошибка добавления книги", Toast.LENGTH_SHORT).show();
        }
    }
}
