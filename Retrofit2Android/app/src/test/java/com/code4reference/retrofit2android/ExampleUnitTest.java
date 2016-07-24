package com.code4reference.retrofit2android;

import com.code4reference.retrofit2android.Utils.JsonUtils;
import com.code4reference.retrofit2android.model.User;
import com.code4reference.retrofit2android.model.UserList;
import com.code4reference.retrofit2android.service.UserService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Test;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import java.net.HttpURLConnection;
import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Unit test cases Retrofit client.
 */
public class ExampleUnitTest {

   /**
    * This is unit test case which simulate createUser api call by mocking the response of the server
    * and comparing the objects. This test the synchronous api call.
    *
    * @throws Exception
    */
   @Test
   public void createUserTest()
      throws Exception {
      MockWebServer mockWebServer = new MockWebServer();

      UserService service = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                                                  .addConverterFactory(GsonConverterFactory.create())
                                                  .build()
                                                  .create(UserService.class);

      // Set the network level response.

      final User user = new User(1L, "Rakesh");

      mockWebServer.enqueue(new MockResponse().setBody(JsonUtils.toJsonString(user)));

      Call<User> call = service.createUser(user);
      Response<User> result = call.execute();
      assertEquals(user, result.body());
   }


   /**
    * This is unit test case which simulate getUser api call by mocking the response of the server
    * and comparing the objects. This test the synchronous api call.
    *
    * @throws Exception
    */
   @Test
   public void getUserTest()
      throws Exception {
      MockWebServer mockWebServer = new MockWebServer();

      UserService service = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                                                  .addConverterFactory(GsonConverterFactory.create())
                                                  .build()
                                                  .create(UserService.class);

      // Set the network level response.

      final User user = new User(1L, "Rakesh");

      mockWebServer.enqueue(new MockResponse().setBody(JsonUtils.toJsonString(user)));

      Call<User> call = service.getUser(1L);
      Response<User> result = call.execute();
      assertEquals(user, result.body());
   }

   /**
    * This is unit test case which simulate getUser api not found error case by mocking the response of the server
    * and comparing the objects. This test the synchronous api call.
    *
    * @throws Exception
    */
   @Test
   public void getUserNotFoundTest()
      throws Exception {
      MockWebServer mockWebServer = new MockWebServer();

      UserService service = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                                                  .addConverterFactory(GsonConverterFactory.create())
                                                  .build()
                                                  .create(UserService.class);

      // Set the network level response.
      mockWebServer.enqueue(new MockResponse().setResponseCode(HttpURLConnection.HTTP_NOT_FOUND));

      Call<User> call = service.getUser(1L);
      Response<User> result = call.execute();
      assertEquals(HttpURLConnection.HTTP_NOT_FOUND, result.code());
   }

   /**
    * This is unit test case which simulate getUsers api call by mocking the response of the server
    * and comparing the objects. This test the synchronous api call.
    *
    * @throws Exception
    */
   @Test
   public void getUsersTest() throws Exception {
      MockWebServer mockWebServer = new MockWebServer();

      UserService service = new Retrofit.Builder().baseUrl(mockWebServer.url("").toString())
                                                  .addConverterFactory(GsonConverterFactory.create())
                                                  .build()
                                                  .create(UserService.class);

      // Set the network level response.

      final User user = new User(1L, "Rakesh");
      final UserList userList = new UserList(Arrays.asList(user));
      mockWebServer.enqueue(new MockResponse().setBody(JsonUtils.toJsonString(userList)));

      Call<UserList> call = service.getUsers();
      Response<UserList> result = call.execute();
      assertEquals(userList, result.body());
   }
}