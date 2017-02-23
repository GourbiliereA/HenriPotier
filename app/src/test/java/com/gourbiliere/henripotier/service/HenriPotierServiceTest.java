package com.gourbiliere.henripotier.service;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;

import com.gourbiliere.henripotier.BookRecyclerAdapter;
import com.gourbiliere.henripotier.model.Book;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by Alex GOURBILIERE on 23/02/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class HenriPotierServiceTest {
    @Mock
    HenriPotierService service;

    @Test
    public void testGetBooks() throws Exception {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://henri-potier.xebia.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        HenriPotierService service = retrofit.create(HenriPotierService.class);
        Call<List<Book>> booksCall = service.getBooks();

        booksCall.enqueue(new Callback<List<Book>>() {
            @Override
            public void onResponse(Call<List<Book>> call, Response<List<Book>> response) {
                Mockito.verify(response.body() != null);
                Mockito.verify(response.body().size() > 0);
            }

            @Override
            public void onFailure(Call<List<Book>> call, Throwable t) {
                Mockito.doThrow(t);
            }
        });
    }
}
