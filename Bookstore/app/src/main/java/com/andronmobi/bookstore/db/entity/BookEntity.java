package com.andronmobi.bookstore.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.andronmobi.bookstore.db.converter.SynopsisConverter;
import com.andronmobi.bookstore.model.Book;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "books",
        indices = {@Index("id"), @Index(value="isbn", unique=true)})
public class BookEntity implements Book {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @SerializedName("title")
    private String title;
    @NonNull
    @SerializedName("isbn")
    private String isbn;
    @SerializedName("cover")
    private String cover;
    @SerializedName("price")
    private int price;
    @SerializedName("synopsis")
    @TypeConverters(SynopsisConverter.class)
    private List<String> synopsis;

    public BookEntity(int id, String title, String isbn, String cover, int price, List<String> synopsis) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.cover = cover;
        this.price = price;
        this.synopsis = synopsis;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    @Override
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public List<String> getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(List<String> synopsis) {
        this.synopsis = synopsis;
    }
}
