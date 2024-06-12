package com.suitmedia.suitmediatest.data.remote.retrofit

import com.suitmedia.suitmediatest.data.remote.response.UsersResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUser(
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): UsersResponse
}