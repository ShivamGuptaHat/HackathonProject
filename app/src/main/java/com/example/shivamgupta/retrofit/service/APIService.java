package com.example.shivamgupta.retrofit.service;

import com.example.shivamgupta.retrofit.model.Student;
import java.util.List;
import retrofit.Call;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;

public interface APIService {
    @GET("hack/json_get_data.php")
    Call<List<Student>> getStudentDetails();

    @FormUrlEncoded
     @POST("hack/put_data.php")
    Call<Student> insertStudentInfo(@Field("name") String name, @Field("address") String address, @Field("mobile") int mobile);

}
