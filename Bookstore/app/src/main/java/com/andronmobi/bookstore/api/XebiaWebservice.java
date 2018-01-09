package com.andronmobi.bookstore.api;

import android.arch.lifecycle.LiveData;

import com.andronmobi.bookstore.db.entity.BookEntity;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface XebiaWebservice {

    @GET("books")
    LiveData<ApiResponse<List<BookEntity>>> getBooks();

    @GET("books")
    Call<List<BookEntity>> getTestBooks();
}
