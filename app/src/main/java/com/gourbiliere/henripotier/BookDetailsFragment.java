package com.gourbiliere.henripotier;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.gourbiliere.henripotier.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by Alex GOURBILIERE on 22/02/2017.
 */

public class BookDetailsFragment extends Fragment implements Parcelable {

    private Book book;

    private ImageView imageViewBookCover;
    private TextView textViewBookTitle;
    private TextView textViewBookSynopsis;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_book, container, false);

        imageViewBookCover = (ImageView) view.findViewById(R.id.imageView_cover);
        textViewBookTitle = (TextView) view.findViewById(R.id.textView_bookTitle);
        textViewBookSynopsis = (TextView) view.findViewById(R.id.textView_bookSynopsis);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (book != null) {
            Picasso.with(getContext()).load(book.getCover()).resize(408, 600).centerCrop().into(imageViewBookCover);

            textViewBookTitle.setText(book.getTitle());

            StringBuilder sb = new StringBuilder();
            String[] syn = book.getSynopsis();
            for (int i = 0; i < syn.length; i++) {
                sb.append(syn[i]);
                if (i != 0) {
                    sb.append("\n\n");
                }
            }
            textViewBookSynopsis.setText(sb.toString());
        }
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return this.book;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(book, flags);
    }
}
