package com.anthonychaufrias.people.data

import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaSaveResponse
import com.anthonychaufrias.people.data.service.IPersonaService
import com.anthonychaufrias.people.data.service.PersonaService

class PersonaRepository(private val api: PersonaService) {
    //private val api = PersonaService()

    suspend fun getPersonaList(busqueda: String): MutableList<Persona> {
        val response: MutableList<Persona> = api.getPersonaList(busqueda)
        return response
    }
    suspend fun setPersona(persona: Persona): PersonaSaveResponse? {
        val response: PersonaSaveResponse? = api.setPersona(persona)
        return response
    }
    suspend fun updatePersona(persona: Persona): PersonaSaveResponse? {
        val response: PersonaSaveResponse? = api.updatePersona(persona)
        return response
    }
    suspend fun deletePersona(persona: Persona): PersonaSaveResponse? {
        val response: PersonaSaveResponse? = api.deletePersona(persona)
        return response
    }

}