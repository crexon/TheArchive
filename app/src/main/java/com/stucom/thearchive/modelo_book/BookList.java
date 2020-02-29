package com.stucom.thearchive.modelo_book;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookList {

    private String kind;
    private int totalItems;
    @SerializedName("items")
    private List<Book> items;


    public BookList(String kind, int totalItems, List<Book> items) {
        this.kind = kind;
        this.totalItems = totalItems;
        this.items = items;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public void setItems(List<Book> items) {
        this.items = items;
    }

    public List<Book> getItems() {
        return items;
    }


    @Override
    public String toString() {
        return "BookList{" +
                "items=" + items.toString() +
                '}';
    }
}
