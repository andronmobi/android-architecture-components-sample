package com.andronmobi.bookstore.ui.fragment;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andronmobi.bookstore.AppExecutors;
import com.andronmobi.bookstore.R;
import com.andronmobi.bookstore.common.Status;
import com.andronmobi.bookstore.db.BookDb;
import com.andronmobi.bookstore.model.Book;
import com.andronmobi.bookstore.repository.BookRepository;
import com.andronmobi.bookstore.databinding.FragmentBooksListBinding;
import com.andronmobi.bookstore.ui.BookClickCallback;
import com.andronmobi.bookstore.ui.BooksAdapter;

public class FragmentBookList extends NavFragment {

    private FragmentBooksListBinding mBinding;
    private BooksAdapter mBooksAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_books_list;
    }

    public String getName() {
        return null; // The app name will be displayed by default
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false);

        mBooksAdapter = new BooksAdapter(this.getContext(), mBookClickCallback);
        mBinding.booksList.setAdapter(mBooksAdapter);

        final BookDb db = BookDb.getInstance(getContext());

        mBinding.btnFragmentOne.setOnClickListener(v -> {
            mBinding.setIsLoading(true);
            BookRepository bookRepository = new BookRepository(new AppExecutors(), db);
            bookRepository.loadBook().observeForever(resource -> {
                mBinding.setIsLoading(false);
                Log.d(TAG, "live data onChanged status " + resource.status);
                if (resource.status == Status.SUCCESS) {
                    mBooksAdapter.setBooks(resource.data);
                    for (Book book : resource.data) {
                        Log.d(TAG, "id: " + book.getId());
                        Log.d(TAG, "title: " + book.getTitle());
                        Log.d(TAG, "book ISBN: " + book.getIsbn());
                        Log.d(TAG, "title: " + book.getCover());
                        Log.d(TAG, "price: " + book.getPrice());
                        if (book.getSynopsis() != null) {
                            for (String syn : book.getSynopsis()) {
                                Log.d(TAG, "synopsis: " + syn);
                            }
                        }
                        Log.d(TAG, "--------------------");
                    }
                }
            });
        });
        return mBinding.getRoot();
    }

    private final BookClickCallback mBookClickCallback = book -> {
        Log.d(TAG, "Book " + book.getTitle() + "has been clicked");
        // TODO start FragmentBookInfo
    };
}
