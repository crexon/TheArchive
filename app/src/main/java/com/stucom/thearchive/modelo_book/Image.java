package com.stucom.thearchive.modelo_book;

import com.google.gson.annotations.SerializedName;

public class Image {

    @SerializedName("smallThumbnail")
    private String smallMiniatura;
    @SerializedName("thumbnail")
    private String miniatura;

    public String getSmallMiniatura() {
        return smallMiniatura;
    }

    public void setSmallMiniatura(String smallMiniatura) {
        this.smallMiniatura = smallMiniatura;
    }

    public String getMiniatura() {
        return miniatura;
    }

    public void setMiniatura(String miniatura) {
        this.miniatura = miniatura;
    }
}


