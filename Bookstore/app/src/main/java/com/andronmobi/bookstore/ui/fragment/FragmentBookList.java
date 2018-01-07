package com.andronmobi.bookstore.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andronmobi.bookstore.R;
import com.andronmobi.bookstore.ui.MainActivity;

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
                if (getActivity() instanceof FragmentControl) {
                    FragmentControl fragmentControl = (FragmentControl) getActivity();
                    fragmentControl.displayFragment(FragmentBookInfo.class);
                }
            }
        });
        return view;
    }
}
