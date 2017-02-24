package com.gourbiliere.henripotier;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gourbiliere.henripotier.model.Book;
import com.squareup.picasso.Picasso;

/**
 * Created by Alex GOURBILIERE on 22/02/2017.
 */

public class BookItemView extends LinearLayout {

    private TextView textView_name;
    private TextView textView_price;
    private ImageView imageView_cover;

    public BookItemView(Context context) {
        this(context, null);
    }

    public BookItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BookItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        textView_name = (TextView) findViewById(R.id.nameTextView);
        textView_price = (TextView) findViewById(R.id.priceTextView);
        imageView_cover = (ImageView) findViewById(R.id.coverImageView);
    }

    public void bindView(Book book) {
        textView_name.setText(book.getTitle());
        textView_price.setText(String.valueOf(book.getPrice()) + "€");
        // Changing size here in addition to the layout file to make them have all the same size (display)
        Picasso.with(getContext()).load(book.getCover()).resize(816, 1200).centerCrop().into(imageView_cover);
    }
}
