package com.example.spsp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class BookDetailActivity extends AppCompatActivity {
    private EditText editAuthor;
    private EditText editName;
    private int bookId;
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        TextView authorTextView = findViewById(R.id.author);
        TextView nameTextView = findViewById(R.id.name);
        editAuthor = findViewById(R.id.edit_author);
        editName = findViewById(R.id.edit_name);
        Button deleteButton = findViewById(R.id.delete_button);
        Button saveButton = findViewById(R.id.save_button);
        bookId = getIntent().getIntExtra("book_id", -1);
        dataBaseHelper = new DataBaseHelper(this);

        if (bookId != -1) {
            Book book = dataBaseHelper.getBookById(bookId);
            if (book != null) {
                authorTextView.setText(book.getBook_Author());
                nameTextView.setText(book.getBook_Name());
                editAuthor.setText(book.getBook_Author());
                editName.setText(book.getBook_Name());
            } else {
                Toast.makeText(this, "Книга не найдена", Toast.LENGTH_SHORT).show();
                finish();
            }
        } else {
            Toast.makeText(this, "Некорректный ID книги", Toast.LENGTH_SHORT).show();
            finish();
        }

        deleteButton.setOnClickListener(v -> {
            dataBaseHelper.deleteBookById(bookId);
            Toast.makeText(this, "Книга удалена", Toast.LENGTH_SHORT).show();
            finish();
        });

        saveButton.setOnClickListener(v -> {
            String newAuthor = editAuthor.getText().toString().trim();
            String newName = editName.getText().toString().trim();
            if (!newAuthor.isEmpty() && !newName.isEmpty()) {
                dataBaseHelper.updateBook(bookId, newName, newAuthor);
                Toast.makeText(this, "Книга обновлена", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
            }
        });
    }
}