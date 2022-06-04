package com.example.karter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface PostOrder {

    @POST("posts")
    Call<Order> postOrder(@Body Order order);
}
