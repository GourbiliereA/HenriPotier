package com.gourbiliere.henripotier;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gourbiliere.henripotier.model.Book;
import com.gourbiliere.henripotier.service.HenriPotierService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Alex GOURBILIERE on 22/02/2017.
 */

public class BookListFragment extends Fragment implements Parcelable {

    private Book[] books;

    private RecyclerView recyclerView;
    private OnBookSelectedListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (OnBookSelectedListener) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_books, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.bookRecyclerView);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (books == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://henri-potier.xebia.fr/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            HenriPotierService service = retrofit.create(HenriPotierService.class);
            Call<List<Book>> booksCall = service.getBooks();

            booksCall.enqueue(new Callback<List<Book>>() {
                @Override
                public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                    books = new Book[response.body().size()];
                    for (int b = 0 ; b < response.body().size() ; b++) {
                        books[b] = response.body().get(b);
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager((Context) listener));
                    recyclerView.setAdapter(new BookRecyclerAdapter(LayoutInflater.from((Context) listener), books, listener));
                }

                @Override
                public void onFailure(Call<List<Book>> call, Throwable t) {
                    Timber.e("Failure when calling service to get books : %s", t.getMessage());
                }
            });
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new BookRecyclerAdapter(LayoutInflater.from(getContext()), books, listener));
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(books, flags);
    }

    public interface OnBookSelectedListener {
        public void onBookSelected(Book book);
    }
}
