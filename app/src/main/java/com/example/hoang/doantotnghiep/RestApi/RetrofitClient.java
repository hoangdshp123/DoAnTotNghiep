package com.example.hoang.doantotnghiep.RestApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
//    final static String BASE_URL="http://45.32.109.146:8080";
    final static String BASE_URL="http://172.16.200.210:1337";

    private static Retrofit retrofit = null;
    public static String MyauthHeaderContent = "Bearer {your_token}";

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private  static OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(logging).addNetworkInterceptor(new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request.Builder builder = chain.request().newBuilder();
            builder.addHeader("Authorization", MyauthHeaderContent);

            return chain.proceed(builder.build());
        }
    });

    public static Retrofit getRetrofitInstance() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(httpClient.connectTimeout(1, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build())
                    .build();
        }
        return retrofit;
    }

}
