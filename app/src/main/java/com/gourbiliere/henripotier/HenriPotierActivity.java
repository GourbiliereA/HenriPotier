package com.gourbiliere.henripotier;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.gourbiliere.henripotier.model.Book;
import com.gourbiliere.henripotier.service.HenriPotierService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Alex GOURBILIERE on 22/02/2017.
 */

public class HenriPotierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_henri_potier);

        Timber.plant(new Timber.DebugTree());

        addBooks();
    }

    private void addBooks() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        HenriPotierService service = retrofit.create(HenriPotierService.class);

        Call<List<Book>> booksCall = service.getBooks();

        booksCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                List<Book> books = new ArrayList<Book>();

                for (Book b : response.body()) {
                    Timber.i(b.getCover());
                    books.add(b);
                }

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.bookRecyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(HenriPotierActivity.this));
                recyclerView.setAdapter(new BookRecyclerAdapter(LayoutInflater.from(HenriPotierActivity.this), books));
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Timber.e("Failure when calling service to get books : %s", t.getMessage());
            }
        });
    }
}
