package com.andronmobi.bookstore.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class NavFragment extends Fragment {

    static final String TAG = "NavFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setRetainInstance(true); // If you need to retain a fragment if the orientation is changed
    }

    protected abstract int getLayoutResId();
    public abstract String getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof FragmentControl) {
            FragmentControl fragmentControl = (FragmentControl) getActivity();
            fragmentControl.onFragmentChanged(this);
        }
    }
}
