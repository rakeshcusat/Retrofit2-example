package com.code4reference.retrofit2android.Utils;

import android.support.annotation.NonNull;
import com.google.gson.GsonBuilder;

/**
 * Utility methods for Json.
 */
public class JsonUtils {

   public static String toJsonString(@NonNull final Object object){
      return new GsonBuilder().create().toJson(object);
   }
}
