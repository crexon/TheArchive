package com.stucom.thearchive.modelo_shelve;

import com.google.gson.annotations.SerializedName;

public class Shelve {

    @SerializedName("id")
    private String id;
    @SerializedName("identifier")
    private String idBook;
    @SerializedName("title")
    private String title;
    @SerializedName("authors")
    private String autor;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("description")
    private String description;
    @SerializedName("publishedDate")
    private String pubDate;
    @SerializedName("pageCount")
    private String pages;
    @SerializedName("categories")
    private String category;
    @SerializedName("thumbnail")
    private String miniatura;
    @SerializedName("estanteria")
    private int[] relacion;

    public Shelve(String id, String idBook, String title, String autor, String publisher, String description, String pubDate, String pages, String category, String miniatura, int[] relacion) {
        this.id = id;
        this.idBook = idBook;
        this.title = title;
        this.autor = autor;
        this.publisher = publisher;
        this.description = description;
        this.pubDate = pubDate;
        this.pages = pages;
        this.category = category;
        this.miniatura = miniatura;
        this.relacion = relacion;
    }

    public String getId() {
        return id;
    }

    public String getIdBook() {
        return idBook;
    }

    public String getTitle() {
        return title;
    }

    public String getAutor() {
        return autor;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getPages() {
        return pages;
    }

    public String getCategory() {
        return category;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public int[] getRelacion() {
        return relacion;
    }

}
