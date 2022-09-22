package com.anthonychaufrias.people.data

import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.service.PersonaService

class PersonaRepository {
    private val api = PersonaService()

    suspend fun getPersonaList(busqueda: String): MutableList<Persona> {
        val response: MutableList<Persona> = api.getPersonaList(busqueda)
        return response
    }
    suspend fun setPersona(persona: Persona): Persona {
        val response: Persona = api.setPersona(persona)
        return response
    }
    suspend fun updatePersona(persona: Persona): Persona {
        val response: Persona = api.updatePersona(persona)
        return response
    }
    suspend fun deletePersona(persona: Persona): Persona {
        val response: Persona = api.deletePersona(persona)
        return response
    }

}