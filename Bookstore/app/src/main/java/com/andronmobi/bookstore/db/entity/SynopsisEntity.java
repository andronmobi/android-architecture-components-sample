package com.andronmobi.bookstore.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.andronmobi.bookstore.model.Synopsis;


@Entity(tableName = "synopsis",
        foreignKeys = @ForeignKey(
                entity = BookEntity.class,
                parentColumns = "id",
                childColumns = "book_id",
                onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = "book_id")
        })
public class SynopsisEntity implements Synopsis {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "book_id")
    private int bookId;
    private String synopsis;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getBookId() {
        return bookId;
    }

    @Override
    public String getSynopsis() {
        return synopsis;
    }
}
