package com.anthonychaufrias.people.service

import com.anthonychaufrias.people.model.PersonaListResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface Servicio {
    @GET("listarpersonas")
    fun getPersonaList(): Call<PersonaListResponse>

}