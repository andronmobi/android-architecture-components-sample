package com.andronmobi.bookstore.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.andronmobi.bookstore.db.dao.BookDao;
import com.andronmobi.bookstore.db.entity.BookEntity;

@Database(entities = {BookEntity.class}, version = 1)
public abstract class BookDb extends RoomDatabase {

    public static final String DATABASE_NAME = "book-db";
    private static BookDb sInstance;

    abstract public BookDao bookDao();

    public static BookDb getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (BookDb.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static BookDb buildDatabase(final Context appContext) {
        return Room.databaseBuilder(appContext, BookDb.class, DATABASE_NAME).build();
    }
}
