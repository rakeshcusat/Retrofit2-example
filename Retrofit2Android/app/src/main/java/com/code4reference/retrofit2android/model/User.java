package com.code4reference.retrofit2android.model;

import android.support.annotation.NonNull;

/**
 * User model to send and receive User information.
 */
public class User {
   private Long id;
   private String name;

   public User(){
      // Required by the system
   }
   public User(final Long id, @NonNull final String name) {
      this.id = id;
      this.name = name;
   }

   /**
    * This constructor is required when we are sending addUser request in that case client doesn't know the
    * user id in advance. Even in case we add the userId, the server will ignore that id.
    * @param name
    */
   public User(@NonNull final String name) {
      this(null, name);
   }

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof User)) {
         return false;
      }

      User user = (User) o;

      if (getId() != null ? !getId().equals(user.getId()) : user.getId() != null) {
         return false;
      }
      return getName() != null ? getName().equals(user.getName()) : user.getName() == null;

   }

   @Override
   public int hashCode() {
      int result = getId() != null ? getId().hashCode() : 0;
      result = 31 * result + (getName() != null ? getName().hashCode() : 0);
      return result;
   }

   @Override
   public String toString() {
      return "User{" +
             "id=" + id +
             ", name='" + name + '\'' +
             '}';
   }
}
