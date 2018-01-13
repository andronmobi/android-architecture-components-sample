package com.andronmobi.bookstore.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.andronmobi.bookstore.AppExecutors;
import com.andronmobi.bookstore.api.ApiResponse;
import com.andronmobi.bookstore.api.XebiaWebservice;
import com.andronmobi.bookstore.common.Resource;
import com.andronmobi.bookstore.db.BookDb;
import com.andronmobi.bookstore.db.entity.BookEntity;
import com.andronmobi.bookstore.model.Book;
import com.andronmobi.bookstore.util.LiveDataCallAdapterFactory;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {

    private static final String TAG = "BookRepository";

    private final AppExecutors appExecutors;
    private final BookDb bookDb;
    private final XebiaWebservice xebiaWebservice;

    public BookRepository(AppExecutors appExecutors, BookDb bookDb /*, XebiaWebservice xebiaWebservice*/) {
        this.appExecutors = appExecutors;
        this.bookDb = bookDb;
        //this.xebiaWebservice = xebiaWebservice;

        // TODO delete
        this.xebiaWebservice = new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(XebiaWebservice.class);
    }

    public LiveData<Resource<List<? extends Book>>> loadBook() {

        return new NetworkBoundResource<List<? extends Book>, List<BookEntity>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<BookEntity> books) {
                bookDb.bookDao().insertAll(books);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<? extends Book> data) {
                return true; //data == null;
            }

            @NonNull
            @Override
            protected LiveData<List<? extends Book>> loadFromDb() {
                return (LiveData<List<? extends Book>>)(LiveData<?>) bookDb.bookDao().loadAllBooks();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<BookEntity>>> createCall() {
                return xebiaWebservice.getBooks();
            }
        }.asLiveData();
    }
}
