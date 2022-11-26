package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaListResponse
import com.anthonychaufrias.people.data.model.PersonaSaveResponse
import retrofit2.Call
import retrofit2.Response

class PersonaMockService: IPersonaService {
    private lateinit var listResponse: PersonaListResponse

    override suspend fun getPersonaList(busqueda: String): Response<PersonaListResponse> {
        val results = mutableListOf<Persona>()
        results.add(Persona(1, "Anthony", "11111111", 1, "Perú"))
        results.add(Persona(2, "GGGGGGG", "11111111", 1, "Perú"))
        listResponse = PersonaListResponse("Ok", results)
        return Response.success(listResponse)
    }

    override fun addPersona(nomb: String, doc: String, idps: Int?): Call<PersonaListResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun addPersona(persona: Persona): Response<PersonaSaveResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun updatePersona(persona: Persona): Response<PersonaSaveResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun deletePersona(persona: Persona): Response<PersonaSaveResponse> {
        TODO("Not yet implemented")
    }
}