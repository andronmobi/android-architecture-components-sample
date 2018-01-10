package com.andronmobi.bookstore.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andronmobi.bookstore.AppExecutors;
import com.andronmobi.bookstore.R;
import com.andronmobi.bookstore.common.Status;
import com.andronmobi.bookstore.model.Book;
import com.andronmobi.bookstore.repository.BookRepository;

public class FragmentBookList extends NavFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_books_list;
    }

    public String getName() {
        return null; // The app name will be displayed by default
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        view.findViewById(R.id.btn_fragment_one).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (getActivity() instanceof FragmentControl) {
//                    FragmentControl fragmentControl = (FragmentControl) getActivity();
//                    fragmentControl.displayFragment(FragmentBookInfo.class);
//                }
                BookRepository bookRepository = new BookRepository(new AppExecutors());
                bookRepository.loadBook().observeForever(resource -> {
                    Log.d(TAG, "live data onChanged status " + resource.status);
                    if (resource.status == Status.SUCCESS) {
                        for (Book book : resource.data) {
                            Log.d(TAG, "book ISBN: " + book.getIsbn() + ", title: " + book.getTitle());
                        }
                    }
                });
            }
        });
        return view;
    }
}
