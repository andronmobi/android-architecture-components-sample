package com.andronmobi.bookstore.ui.fragment;


import com.andronmobi.bookstore.R;

public class FragmentBookInfo extends NavFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_book_info;
    }

    public String getName() {
        return getResources().getString(R.string.fragment_book_info);
    }
}
