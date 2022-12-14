package com.anthonychaufrias.people.service

import com.anthonychaufrias.people.model.*
import retrofit2.Call
import retrofit2.http.*

interface Servicio {

    @GET("listarpersonas")
    fun getPersonaList(@Query("busqueda") busqueda: String): Call<PersonaListResponse>

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