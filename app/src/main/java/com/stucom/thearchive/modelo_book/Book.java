package com.stucom.thearchive.modelo_book;

import com.google.gson.annotations.SerializedName;

public class Book {

    @SerializedName("volumeInfo")
    private BookDetail bookInfo;

    public Book(){

    }

    public BookDetail getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookDetail bookInfo) {
        this.bookInfo = bookInfo;
    }
}