package com.andronmobi.bookstore.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.andronmobi.bookstore.db.entity.BookEntity;

import java.util.List;

@Dao
public interface BookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(BookEntity book);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<BookEntity> books);

    @Query("SELECT * FROM books WHERE id = :bookId")
    LiveData<BookEntity> loadBook(int bookId);

    @Query("SELECT * FROM books")
    LiveData<List<BookEntity>> loadAllBooks();
}
