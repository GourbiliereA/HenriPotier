package com.gourbiliere.henripotier;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gourbiliere.henripotier.model.Book;

import java.util.List;

/**
 * Created by Alex GOURBILIERE on 22/02/2017.
 */
public class BookRecyclerAdapter extends RecyclerView.Adapter {
    private final List<Book> books;
    private final LayoutInflater inflater;
    private BookListFragment.OnBookSelectedListener listener;

    public BookRecyclerAdapter(LayoutInflater from, List<Book> books, BookListFragment.OnBookSelectedListener listener) {
        this.inflater = from;
        this.books = books;
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.book_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int pos = position;
        BookItemView itemView = (BookItemView) holder.itemView;
        itemView.bindView(books.get(pos));
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onBookSelected(books.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
