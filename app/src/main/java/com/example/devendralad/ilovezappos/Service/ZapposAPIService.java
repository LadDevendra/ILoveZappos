package com.example.devendralad.ilovezappos.Service;

import com.example.devendralad.ilovezappos.Model.Product;
import com.example.devendralad.ilovezappos.Model.UserResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;


/**
 * Created by devendralad on 2/4/17.
 */

//Interface for Zappos API services, more API calls could be added here
public interface ZapposAPIService {

    @GET("Search")
    public Call<UserResponse> getProducts(@Query("term") String keyWord, @Query("key") String key);


}
