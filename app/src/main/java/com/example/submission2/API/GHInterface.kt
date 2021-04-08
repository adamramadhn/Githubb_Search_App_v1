package com.example.submission2.API

import com.example.submission2.Model.Item
import com.example.submission2.Model.Response
import com.example.submission2.Model.ResponseDetail
import retrofit2.Call
import retrofit2.http.*

interface GHInterface {
    @GET("/search/users")
    @Headers("Authorization: token ab909a68b8971dc6ab115e6b63ddd6163922f476")
    fun getUsers(@Query("q") q: String) : Call<Response>

    @GET("/users/{username}")
    @Headers("Authorization: token ab909a68b8971dc6ab115e6b63ddd6163922f476")
    fun getDetail(@Path("username") username: String) : Call<ResponseDetail>

    @GET("/users/{username}/following")
    @Headers("Authorization: token ab909a68b8971dc6ab115e6b63ddd6163922f476")
    fun getFollowing(@Path("username") username: String) : Call<List<Item>>

    @GET("/users/{username}/followers")
    @Headers("Authorization: token ab909a68b8971dc6ab115e6b63ddd6163922f476")
    fun getFollowers(@Path("username")username: String): Call<List<Item>>

}