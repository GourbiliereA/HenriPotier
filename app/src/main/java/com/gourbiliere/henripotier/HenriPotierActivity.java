package com.gourbiliere.henripotier;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
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

    private BookDetailsFragment bookDetailsFragment;
    private BookListFragment bookListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_henri_potier);

        Timber.plant(new Timber.DebugTree());

        // Retrieving the book when orientation changed
        if (savedInstanceState != null) {
            this.bookListFragment = savedInstanceState.getParcelable("bookListFragment");
            this.bookDetailsFragment = savedInstanceState.getParcelable("bookDetailsFragment");
        } else {
            bookDetailsFragment = new BookDetailsFragment();
            bookListFragment = new BookListFragment();
        }


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (bookDetailsFragment.getBook() != null) {
                // Not first display = already the details of a book displayed so we display it
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerFrameLayout, bookDetailsFragment, "Details")
                        .commit();
            } else {
                // First display = launch of the app so we display the list of books
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerFrameLayout, bookListFragment, "List")
                        .commit();
            }
        } else {
            // Displaying both list and fragment on the screen
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerBookListFrameLayout, bookListFragment, "List")
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerBookDetailsFrameLayout, bookDetailsFragment, "Details")
                    .commit();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("bookListFragment", bookListFragment);
        outState.putParcelable("bookDetailsFragment", bookDetailsFragment);

        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBookSelected(Book book) {
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
            // Refreshing manually because not working otherwise
            Fragment frg = getSupportFragmentManager().findFragmentByTag("Details");
            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.detach(frg).attach(frg).commit();
        }
    }
}
