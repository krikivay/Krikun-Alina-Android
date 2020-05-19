package com.example.recyclerdatalab;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//класс-синглтон для использования retrofit
public class NetworkService {
    private static NetworkService mInstance;
    //сайт, откуда берутся данные
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private Retrofit mRetrofit;

    private NetworkService()
    {
        mRetrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static NetworkService getInstance()
    {
        if(mInstance == null)
        {
            mInstance = new NetworkService();
        }
        return mInstance;
    }
    //реализация интерфейса
    public JSONPlaceHolderApi getJSONApi()
    {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}
