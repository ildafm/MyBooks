package com.if5a.mybooksfadli.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Buku implements Parcelable {
    private int id;
    private String isbn, title, author,
            yearOfPublication, publisher, image_url_s,
            image_url_m, image_url_l;

    public Buku(){

    }

    public Buku(String isbn, String title,
                String author, String yearOfPublication,
                String publisher, String image_url_s,
                String image_url_m, String imager_url_l) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.publisher = publisher;
        this.image_url_s = image_url_s;
        this.image_url_m = image_url_m;
        this.image_url_l = imager_url_l;
    }

    public Buku(int id, String isbn, String title,
                String author, String yearOfPublication,
                String publisher, String image_url_s,
                String image_url_m, String imager_url_l) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.publisher = publisher;
        this.image_url_s = image_url_s;
        this.image_url_m = image_url_m;
        this.image_url_l = imager_url_l;
    }

    protected Buku(Parcel in){
        id = in.readInt();
        isbn = in.readString();
        title = in.readString();
        author = in.readString();
        yearOfPublication = in.readString();
        publisher = in.readString();
        image_url_s = in.readString();
        image_url_m = in.readString();
        image_url_l = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(isbn);
        parcel.writeString(title);
        parcel.writeString(author);
        parcel.writeString(yearOfPublication);
        parcel.writeString(publisher);
        parcel.writeString(image_url_s);
        parcel.writeString(image_url_m);
        parcel.writeString(image_url_l);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Buku> CREATOR = new Creator<Buku>() {
        @Override
        public Buku createFromParcel(Parcel parcel) {
            return new Buku(parcel);
        }

        @Override
        public Buku[] newArray(int size) {
            return new Buku[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(String yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getImage_url_s() {
        return image_url_s;
    }

    public void setImage_url_s(String image_url_s) {
        this.image_url_s = image_url_s;
    }

    public String getImage_url_m() {
        return image_url_m;
    }

    public void setImage_url_m(String image_url_m) {
        this.image_url_m = image_url_m;
    }

    public String getImage_url_l() {
        return image_url_l;
    }

    public void setImage_url_l(String image_url_l) {
        this.image_url_l = image_url_l;
    }
}
