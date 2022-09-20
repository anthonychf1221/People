package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface IPersonaService {

    @GET("listarpersonas")
    suspend fun getPersonaList(@Query("busqueda") busqueda: String): Response<PersonaListResponse>

    @GET("listarpaises")
    fun getPaisesList(): Call<PaisListResponse>

    @Headers(
        "Content-Type: application/x-www-form-urlencoded"
    )
    @FormUrlEncoded
    @POST("insertarpersona")
    fun addPersona(
        @Field("nomb") nomb: String,
        @Field("doc") doc: String,
        @Field("idps") idps: Int?
    ): Call<PersonaListResponse>

    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("insertarpersona")
    fun addPersona(
        @Body persona: Persona
    ): Call<PersonaSaveResponse>


    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("modificarpersona")
    fun updatePersona(
        @Body persona: Persona
    ): Call<PersonaSaveResponse>


    @Headers(
        "Accept: application/json",
        "Content-Type: application/json"
    )
    @POST("eliminarpersona")
    fun deletePersona(
        @Body persona: Persona
    ): Call<PersonaSaveResponse>

}