package com.andronmobi.bookstore.repository;

import android.app.Activity;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.andronmobi.bookstore.AppExecutors;
import com.andronmobi.bookstore.api.ApiResponse;
import com.andronmobi.bookstore.api.XebiaWebservice;
import com.andronmobi.bookstore.common.Resource;
import com.andronmobi.bookstore.db.dao.BookDao;
import com.andronmobi.bookstore.db.entity.BookEntity;
import com.andronmobi.bookstore.util.LiveDataCallAdapterFactory;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BookRepository {

    private static final String TAG = "BookRepository";

    private final AppExecutors appExecutors;
    //private final BookDao bookDao;
    private final XebiaWebservice xebiaWebservice;

    // TODO delete them (workaround since there is no DB support yet)
    private List<BookEntity> bookList = new ArrayList<>(0);
    private MutableLiveData<List<BookEntity>> booksFromDb = new MutableLiveData<>();

    public BookRepository(AppExecutors appExecutors/*, BookDao bookDao, XebiaWebservice xebiaWebservice*/) {
        this.appExecutors = appExecutors;
        //this.bookDao = bookDao;
        //this.xebiaWebservice = xebiaWebservice;

        // TODO delete
        this.xebiaWebservice = new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(XebiaWebservice.class);
    }

    public LiveData<Resource<List<BookEntity>>> loadBook() {

        return new NetworkBoundResource<List<BookEntity>, List<BookEntity>>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull List<BookEntity> books) {
                //bookDao.insertAll(books);
                bookList = books; // TODO since there is no DB support yet, store it in the property
            }

            @Override
            protected boolean shouldFetch(@Nullable List<BookEntity> data) {
                return true; // TODO if we don't have data
            }

            @NonNull
            @Override
            protected LiveData<List<BookEntity>> loadFromDb() {
                booksFromDb.setValue(bookList);
                return booksFromDb;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<List<BookEntity>>> createCall() {
                return xebiaWebservice.getBooks();
            }
        }.asLiveData();
    }
}
