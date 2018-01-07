package com.andronmobi.bookstore.ui.fragment;


import com.andronmobi.bookstore.R;

public class FragmentCart extends NavFragment {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_cart;
    }

    public String getName() {
        return getResources().getString(R.string.fragment_cart);
    }
}