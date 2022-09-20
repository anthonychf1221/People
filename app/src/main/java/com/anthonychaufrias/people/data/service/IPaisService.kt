package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.*
import retrofit2.Response
import retrofit2.http.GET

interface IPaisService {

    @GET("listarpaises")
    suspend fun getPaisesList(): Response<PaisListResponse>

}