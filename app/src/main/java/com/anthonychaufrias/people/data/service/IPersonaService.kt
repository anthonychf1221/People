package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IPersonaService {

    @GET("listarpersonas")
    suspend fun getPersonaList(@Query("busqueda") busqueda: String): Response<PersonaListResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("insertarpersona")
    suspend fun addPersona(
        @Body persona: Persona
    ): Response<PersonaSaveResponse>


    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("modificarpersona")
    suspend fun updatePersona(
        @Body persona: Persona
    ): Response<PersonaSaveResponse>


    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("eliminarpersona")
    suspend fun deletePersona(
        @Body persona: Persona
    ): Response<PersonaSaveResponse>

}