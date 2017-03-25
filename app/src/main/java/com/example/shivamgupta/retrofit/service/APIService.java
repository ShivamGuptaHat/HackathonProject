package com.example.shivamgupta.retrofit.service;

import com.example.shivamgupta.retrofit.model.RootObject;
import java.util.List;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;

public interface APIService {

    /*
    @GET("/json_get_data.php")//no change
    Call<List<Student>> getStudentDetails();
    */



    @FormUrlEncoded
    @POST("get_data.php")
    Call<List<RootObject>> insertStudentInfo(@Field("year") String year);
}
