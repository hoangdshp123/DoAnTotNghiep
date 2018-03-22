package com.example.hoang.doantotnghiep.RestApi;

public class ApiUtils {

    private ApiUtils() {}

    public static APIServices getApiService() {
        return RetrofitClient.getRetrofitInstance().create(APIServices.class);
    }
}
