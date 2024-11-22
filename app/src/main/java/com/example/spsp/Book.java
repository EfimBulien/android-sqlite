package com.example.spsp;

public class Book {

    private int Id_Book;
    private String Book_Name;
    private String Book_Author;

    public Book(int id_Book, String book_Author, String book_Name) {
        this.Id_Book = id_Book;
        this.Book_Author = book_Author;
        this.Book_Name = book_Name;
    }

    public void setId_Book(int id_Book) {
        Id_Book = id_Book;
    }

    public int getId_Book() {
        return Id_Book;
    }

    public void setBook_Name(String book_Name) {
        Book_Name = book_Name;
    }

    public String getBook_Name() {
        return Book_Name;
    }

    public void setBook_Author(String book_Author) {
        Book_Author = book_Author;
    }

    public String getBook_Author() {
        return Book_Author;
    }
}
