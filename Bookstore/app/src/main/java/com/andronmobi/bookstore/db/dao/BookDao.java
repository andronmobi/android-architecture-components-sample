package com.andronmobi.bookstore.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.andronmobi.bookstore.db.entity.BookEntity;

import java.util.List;

@Dao
public abstract class BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insert(BookEntity book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertAll(List<BookEntity> books);

    @Query("SELECT * FROM books WHERE id = :bookId")
    public abstract LiveData<BookEntity> loadBook(int bookId);

    @Query("SELECT * FROM books")
    public abstract LiveData<List<BookEntity>> loadAllBooks();
}
