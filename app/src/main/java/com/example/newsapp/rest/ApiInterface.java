package com.example.newsapp.rest;

import com.example.newsapp.models.NewsHeadlines;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/v2/top-headlines")
    Call<NewsHeadlines> getNewsHeadlines(@Query("country") String country,@Query("apiKey") String apiKey);
}
