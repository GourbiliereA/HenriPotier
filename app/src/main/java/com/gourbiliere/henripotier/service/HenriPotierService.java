package com.gourbiliere.henripotier.service;

import com.gourbiliere.henripotier.model.Book;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Alex GOURBILIERE on 22/02/2017.
 */

public interface HenriPotierService {
    @GET("books")
    Call<List<Book>> getBooks();
}
