package com.example.retrofifull.api

import com.example.retrofifull.models.Info
import com.example.retrofifull.models.PostRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @GET("plan")
    fun getAllInfo():Call<List<Info>>

    @POST("plan/")
    fun addInfo(@Body postRequest: PostRequest):Call<Info>

    @PUT("plan/{id}/")
    fun updateInfo(@Path("id") id:Int, @Body request: PostRequest):Call<Info>

    @DELETE("plan/{id}/")
    fun deleteInfo(@Path("id") id:Int):Call<ResponseBody>
}