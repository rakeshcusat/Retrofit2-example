package com.code4reference.retrofit2android.activitiy;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.code4reference.retrofit2android.R;
import com.code4reference.retrofit2android.adapter.UserListAdapter;
import com.code4reference.retrofit2android.model.User;
import com.code4reference.retrofit2android.model.UserList;
import com.code4reference.retrofit2android.service.ServiceFactory;
import com.google.gson.Gson;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

   final static String TAG = MainActivity.class.getSimpleName();

   /** View member variables **/
   @BindView(R.id.user_list_recycler_view)
   protected RecyclerView userListRecyclerView;
   protected UserListAdapter recyclerViewAdapter;
   protected RecyclerView.LayoutManager recyclerViewlayoutManager;

   @BindView(R.id.name_edit_text)
   protected EditText nameEditText;

   @BindView(R.id.user_id_edit_text)
   protected EditText userIdEditText;

   /** Data member variables **/

   /************* Activity Callback methods *******************/

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      ButterKnife.bind(this);

      // Recycler view related
      userListRecyclerView.setHasFixedSize(true);
      recyclerViewlayoutManager = new LinearLayoutManager(this);
      userListRecyclerView.setLayoutManager(recyclerViewlayoutManager);
      // Set recycler view adapter
      recyclerViewAdapter = new UserListAdapter(new ArrayList<User>());
       userListRecyclerView.setAdapter(recyclerViewAdapter);
      // it is time to fetch users
      getUsers();
   }

   /**
    * OnClick listener for various buttons.
    * @param view View object which click operation was performed.
    */
   @OnClick({R.id.add_user_button, R.id.get_user_button})
   protected void onClick(final View view){

      switch (view.getId()){
         case R.id.add_user_button:
            if (!nameEditText.getText().toString().isEmpty()) {
               addUser(nameEditText.getText().toString());
            } else {
               Toast.makeText(this, "Invalid name", Toast.LENGTH_SHORT).show();
               nameEditText.setText("");
            }
            break;
         case R.id.get_user_button:
               if (!userIdEditText.getText().toString().isEmpty()
                   && -1 <= Long.valueOf(-1).compareTo(Long.valueOf(userIdEditText.getText().toString()))) {
                  // Retorfit synchronous call wrapped in AsyncTask.
                  new ApiTask().execute(Long.valueOf(userIdEditText.getText().toString()));
                  // Non-Retrofit synchronous call wrapped in AsyncTask
//                  new NonRetrofitTask().execute(Long.valueOf(userIdEditText.getText().toString()));
               } else {
                  Toast.makeText(this, "Invalid userId", Toast.LENGTH_SHORT).show();
               }
            break;

         default:
            Log.d(TAG, String.format(Locale.ENGLISH, "Wrong view id: %d", view.getId()));
      }
   }

   /**
    * Method to add the user name in the user service my making API call. This method call addUser api asynchronously.
    * @param userName
    *    User name.
    */
   private void addUser(@NonNull final String userName){

      // Here is an example of asynchronous API call.
      final Call<User> call = ServiceFactory.getService().createUser(new User(userName));

      call.enqueue(new Callback<User>() {
         @Override
         public void onResponse(Call<User> call, Response<User> response) {
            if (response.isSuccessful()) {
               Toast.makeText(MainActivity.this,
                              "Successfully added user:  " + response.body(),
                              Toast.LENGTH_SHORT).show();

               // Reset the edit text field
               nameEditText.setText("");
               // time to update the list as well.
               getUsers();
            } else {
               Toast.makeText(MainActivity.this,
                              "Could not add user; status code: " + response.code(),
                              Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(Call<User> call, Throwable t) {
            Toast.makeText(MainActivity.this, "Could not add user; Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
         }
      });
   }

   /**
    * This method fetches users from the Userservice. It makes the asynchronous API call.
    */
   private void getUsers(){
      // Here in another example of asynchronous API call.
      final Call<UserList> call = ServiceFactory.getService().getUsers();

      call.enqueue(new Callback<UserList>() {
         @Override
         public void onResponse(Call<UserList> call, Response<UserList> response) {
            if (response.isSuccessful() && response.body() != null) {
               UserList userList = response.body();
               recyclerViewAdapter.setData(userList.getUsers());
            } else {
               Toast.makeText(MainActivity.this,
                              "Couldn't fetch the users; status code: " + response.code(),
                              Toast.LENGTH_SHORT).show();
            }
         }

         @Override
         public void onFailure(Call<UserList> call, Throwable t) {
            Toast.makeText(MainActivity.this,
                           "Couldn't fetch user; error: " + t.getMessage(),
                           Toast.LENGTH_SHORT).show();
         }
      });
   }

   /******** Async Task for Synchronous api call *****************/

   private class ApiTask extends AsyncTask<Long, Void, String> {

      @Override
      @Nullable
      protected String doInBackground(Long... userIds) {

         // Synchronous API call example.
         Call<User> userCall = ServiceFactory.getService().getUser(userIds[0]);
         try {
            Response<User> response = userCall.execute();
            int statusCode = response.code();
            User user = response.body();

            Log.d(TAG, String.format(Locale.ENGLISH, "Status code: %d, user: %s", statusCode, user));

            if (user  != null && statusCode == HttpURLConnection.HTTP_OK) {
               return user.getName();
            }
         } catch(IOException e) {
            e.printStackTrace();
         }
         return null;
      }

      @Override
      protected void onPostExecute(@Nullable final String name){
         String message;
         if (name == null) {
            message = "Couldn't find any name for given id";
         } else {
            message = String.format(Locale.ENGLISH, "Name: %s", name);
            if (userIdEditText != null){
               userIdEditText.setText("");
            }
         }
         Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
      }
   }

   /**
    * This async task doesn't use Retrofit to communicate with RESTful api.
    */
   private class NonRetrofitTask extends AsyncTask<Long, Void, String> {
      private final String TAG = NonRetrofitTask.class.getSimpleName();

      @Override
      @Nullable
      protected String doInBackground(Long... userIds) {
         InputStream is = null;
         int len = 500;
         try {
            URL url = new URL(String.format(Locale.ENGLISH, "%s/api/user/%d", ServiceFactory.BASE_URL, userIds[0]));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            Log.d(TAG, "The response is: " + response);
            if (response < 200 || response >= 400){
               Log.d(TAG, "unsuccessful response");
               return null;
            }
            is = conn.getInputStream();

            final Reader reader = new InputStreamReader(is, "UTF-8");
            User user = new Gson().fromJson(reader, User.class);

            return user != null ? user.getName() : null;

         } catch(IOException e) {
            e.printStackTrace();
         }  finally {
            if (is != null) {
               try {
                  is.close();
               } catch(IOException e) {
                  e.printStackTrace();
               }
            }
         }

         return null;
      }

      @Override
      protected void onPostExecute(@Nullable final String name){
         String message;
         if (name == null) {
            message = "Couldn't find any name for given id";
         } else {
            message = String.format(Locale.ENGLISH, "Name: %s", name);
            if (userIdEditText != null){
               userIdEditText.setText("");
            }
         }
         Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
      }
   }
}
