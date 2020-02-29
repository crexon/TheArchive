package com.stucom.thearchive.modelo_book;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class BookDetail {
    @SerializedName("title")
    private String title;
    @SerializedName("authors")
    private String[] autor;
    @SerializedName("publisher")
    private String publisher;
    @SerializedName("description")
    private String description;
    @SerializedName("publishedDate")
    private String pubDate;
    @SerializedName("pageCount")
    private String pages;
    @SerializedName("categories")
    private String[] category;
    @SerializedName("imageLinks")
    private Image img;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAutor() {
        String autores = "";
        if (autor != null){
            for (String a : autor){
                autores += a + " ";
            }
            return autores;

        }
        return "";
    }

    public void setAutor(String[] autor) {
        this.autor = autor;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDescription() {
        return description;
    }


    public String getCategory() {
        String categorias = "";
        if (category != null){
            for (String a : category){
                categorias += a + " ";
            }
            return categorias;

        }
        return "";
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
