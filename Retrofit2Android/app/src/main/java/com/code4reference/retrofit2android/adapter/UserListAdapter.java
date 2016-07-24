package com.code4reference.retrofit2android.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.code4reference.retrofit2android.R;
import com.code4reference.retrofit2android.model.User;

import java.util.List;

/**
 * UserListAdapter to manage User list RecyclerView
 */
public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {

   private List<User> userList;

   public UserListAdapter(List<User> userList){
      this.userList = userList;
   }

   // This method creates new views and it is invoked by the layout manager
   @Override
   public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
      final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_list_row, parent, false);
      return new ViewHolder(view);
   }

   // This method replaces the contents of a view and it is invoked by the layout manager
   @Override
   public void onBindViewHolder(final ViewHolder holder, int position) {
      // - get element from your dataset at this position
      // - replace the contents of the view with that element
      if (position >= userList.size()) {
         return;
      }
      holder.nameTextView.setText(userList.get(position).getName());
   }

   @Override
   public int getItemCount() {
      return userList.size();
   }

   /**
    * This method is used to update the data of the adapter. This method also updates the view.
    * @param userList User list. This should not be a null object.
    */
   public void setData(@NonNull final List<User> userList){
      this.userList = userList;
      // once the data has changed notify the view to update itself.
      notifyDataSetChanged();
   }

   public static class ViewHolder extends RecyclerView.ViewHolder {
      public TextView nameTextView;
      public ViewHolder(final View view) {
         super(view);
         nameTextView = (TextView)view.findViewById(R.id.name_user_list);
      }
   }
}
