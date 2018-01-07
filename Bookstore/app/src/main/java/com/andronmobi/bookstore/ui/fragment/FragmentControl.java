package com.andronmobi.bookstore.ui.fragment;

public interface FragmentControl {

    void displayFragment(Class fragmentClass);
    void replaceFragment(Class fragmentClass);
    void onFragmentChanged(NavFragment newFragment);
}
