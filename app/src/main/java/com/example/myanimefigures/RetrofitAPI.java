package com.example.myanimefigures;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    @GET("Shelves/{Id}")
    Call<DataModal> getDATA(@Path("Id") int Id);

    @POST("Shelves")
    Call<DataModal> createPost(@Body DataModal dataModal);

    @PUT("Shelves/{Id}")
    Call<DataModal> updateData(@Query("Id") int Id, @Body DataModal dataModal);

    @DELETE("Shelves/{Id}")
    Call<Void> deleteData(@Path("Id") int Id);


}
