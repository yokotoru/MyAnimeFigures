package com.example.myanimefigures;

public class DataModal {
    private String anime;
    private String name;
    private String status;
    private String image;

    public DataModal(String Anime,  String Name,  String Status, String Image) {
        this.anime = Anime;
        this.name = Name;
        this.status = Status;
        this.image = Image;
    }
    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
