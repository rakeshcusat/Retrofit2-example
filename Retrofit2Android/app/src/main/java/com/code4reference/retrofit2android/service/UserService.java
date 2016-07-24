package com.code4reference.retrofit2android.service;

import com.code4reference.retrofit2android.model.User;
import com.code4reference.retrofit2android.model.UserList;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

/**
 * This is the service interface definition.
 */
public interface UserService {

   @GET("api/user/{userId}")
   Call<User> getUser(@Path("userId") Long userId);

   @GET("api/users")
   Call<UserList> getUsers();

   @POST("api/user")
   Call<User> createUser(@Body User user);

}
