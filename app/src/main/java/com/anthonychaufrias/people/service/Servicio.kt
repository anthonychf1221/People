package com.anthonychaufrias.people.service

import com.anthonychaufrias.people.model.PersonaListResponse
import com.anthonychaufrias.people.model.PaisListResponse
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.model.PersonaSaveResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Servicio {
    @GET("listarpersonas")
    fun getPersonaList(): Call<PersonaListResponse>

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
        //): Response<Persona>
        // ): Call<PersonaSaveResponse>
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