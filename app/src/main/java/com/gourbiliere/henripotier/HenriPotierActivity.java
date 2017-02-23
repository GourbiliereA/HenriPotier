package com.gourbiliere.henripotier;

import android.content.res.Configuration;
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

public class HenriPotierActivity extends AppCompatActivity implements BookListFragment.OnBookSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_henri_potier);

        Timber.plant(new Timber.DebugTree());

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerFrameLayout, new BookListFragment(), "List")
                        .commit();
            }
        } else {
//            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerBookListFrameLayout, new BookListFragment(), "List")
                        .commit();
//            }

            Book book = new Book();
            book.setTitle("Test Book");
            book.setCover("http://www.konbini.com/fr/files/2016/02/harry-potter.jpg");
            String[] array = {};
            book.setSynopsis(array);
            BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
            bookDetailsFragment.setBook(book);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerBookDetailsFrameLayout, bookDetailsFragment, "Details")
                    .commit();
        }
    }

    @Override
    public void onBookSelected(Book book) {
        BookDetailsFragment bookDetailsFragment = new BookDetailsFragment();
        bookDetailsFragment.setBook(book);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerFrameLayout, bookDetailsFragment, "Details")
                    .addToBackStack("List")
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerBookDetailsFrameLayout, bookDetailsFragment, "Details")
                    .commit();
        }
    }
}
