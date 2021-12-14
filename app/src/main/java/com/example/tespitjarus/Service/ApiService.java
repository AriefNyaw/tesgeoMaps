package com.example.tespitjarus.Service;

import com.example.tespitjarus.Model.ResponseLogin;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("api/sariroti_md/index.php/login/loginTest")
    Call<ResponseLogin> login (@Field("username") String username,
                               @Field("password") String password);
}
