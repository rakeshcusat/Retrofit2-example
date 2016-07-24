package com.code4reference.retrofit2android.model;

import java.util.ArrayList;
import java.util.List;

/**
 *  Model class to send and receive User list.
 */
public class UserList {

   private List<User> users;
   private Long totalCount;
   private Long offset;
   private Long limit;

   public UserList(){
      this.users = new ArrayList<User>();
   }

   public UserList(final List<User> users){
      this.users = users;
   }

   public UserList(final List<User> users, final Long totalCount, final Long offset, final Long limit) {
      this.users = users;
      this.totalCount = totalCount;
      this.offset = offset;
      this.limit = limit;
   }

   public List<User> getUsers() {
      return users;
   }

   public void setUsers(List<User> users) {
      this.users = users;
   }

   public Long getTotalCount() {
      return totalCount;
   }

   public void setTotalCount(Long totalCount) {
      this.totalCount = totalCount;
   }

   public Long getOffset() {
      return offset;
   }

   public void setOffset(Long offset) {
      this.offset = offset;
   }

   public Long getLimit() {
      return limit;
   }

   public void setLimit(Long limit) {
      this.limit = limit;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      }
      if (!(o instanceof UserList)) {
         return false;
      }

      UserList userList = (UserList) o;

      if (getUsers() != null ? !getUsers().equals(userList.getUsers()) : userList.getUsers() != null) {
         return false;
      }
      if (getTotalCount() != null
          ? !getTotalCount().equals(userList.getTotalCount())
          : userList.getTotalCount() != null) {
         return false;
      }
      if (getOffset() != null ? !getOffset().equals(userList.getOffset()) : userList.getOffset() != null) {
         return false;
      }
      return getLimit() != null ? getLimit().equals(userList.getLimit()) : userList.getLimit() == null;

   }

   @Override
   public int hashCode() {
      int result = getUsers() != null ? getUsers().hashCode() : 0;
      result = 31 * result + (getTotalCount() != null ? getTotalCount().hashCode() : 0);
      result = 31 * result + (getOffset() != null ? getOffset().hashCode() : 0);
      result = 31 * result + (getLimit() != null ? getLimit().hashCode() : 0);
      return result;
   }

   @Override
   public String toString() {
      return "UserList{" +
             "users=" + users +
             ", totalCount=" + totalCount +
             ", offset=" + offset +
             ", limit=" + limit +
             '}';
   }
}
