package com.example.myanimefigures;

import android.os.Parcel;
import android.os.Parcelable;

public class Mask implements Parcelable{
    private int Id;
    private String Anime;
    private String Name;
    private String Status;
    private String Image;

    public Mask(int Id, String Anime, String Name, String Status, String Image) {
        this.Id = Id;
        this.Anime = Anime;
        this.Name = Name;
        this.Status = Status;
        this.Image = Image;



    }
    protected Mask(Parcel in) {
        Id = in.readInt();
        Anime = in.readString();
        Name = in.readString();
        Status = in.readString();
        Image = in.readString();
    }
    public static final Creator<Mask> CREATOR = new Creator<Mask>() {
        @Override
        public Mask createFromParcel(Parcel in) {
            return new Mask(in);
        }

        @Override
        public Mask[] newArray(int size) {
            return new Mask[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getAnime() {
        return Anime;
    }

    public void setAnime(String anime) {
        this.Anime = anime;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        this.Status = status;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        this.Image = image;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(Id);
        parcel.writeString(Anime);
        parcel.writeString(Name);
        parcel.writeString(Status);
        parcel.writeString(Image);
    }
}
