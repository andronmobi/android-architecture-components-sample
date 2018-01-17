package com.andronmobi.bookstore.ui;

import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andronmobi.bookstore.R;
import com.andronmobi.bookstore.databinding.BookItemBinding;
import com.andronmobi.bookstore.model.Book;

import java.util.List;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

    private List<? extends Book> mBookList;

    @Nullable
    private final BookClickCallback mProductClickCallback;

    public BooksAdapter(@Nullable BookClickCallback mProductClickCallback) {
        this.mProductClickCallback = mProductClickCallback;
    }

    public void setBooks(final List<? extends Book> books) {
        if (mBookList == null) {
            mBookList = books;
            notifyItemRangeInserted(0, books.size());
        }
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BookItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.book_item, parent, false);
        binding.setCallback(mProductClickCallback);
        return new BookViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(BookViewHolder holder, int position) {
        holder.mBinding.setBook(mBookList.get(position));
        holder.mBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mBookList == null ? 0 : mBookList.size();
    }

    static class BookViewHolder extends RecyclerView.ViewHolder {

        final BookItemBinding mBinding;

        public BookViewHolder(BookItemBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }
    }
}
