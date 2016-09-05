package com.code4reference.retrofit2android.service;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.concurrent.TimeUnit;

/**
 * This is a Singleton Factory class to generate the service instance.
 */
public final class ServiceFactory {
   public static final String BASE_URL = "http://10.0.2.2:5000";

   private static UserService userService;

   private ServiceFactory() {
      // Just to prevent people to create an instance of this class.
   }

   /**
    * This is a singleton factory method to provide the service instance.
    *
    * @return UserService instance.
    */
   public static UserService getService(){
      if (userService == null) {
         // These are required for logging.
         HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
         interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

         OkHttpClient client = new OkHttpClient().newBuilder()
                                                 .connectTimeout(60, TimeUnit.SECONDS)
                                                 .readTimeout(60, TimeUnit.SECONDS)
                                                 .retryOnConnectionFailure(true)
                                                 .addInterceptor(interceptor).build();

         userService = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            // To enable http request and response logging
            .client(client)
            // This uses the GSON to convert json string to object and vice-versa.
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService.class);
      }
      return userService;
   }
}
