package com.example.recyclerdatalab;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
//интерфейс для получения всех данных с сайта
public interface JSONPlaceHolderApi {
    @GET("posts")
    Call<List<Post>> getAllPosts();
}
