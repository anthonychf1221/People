package com.anthonychaufrias.people.data.service

import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaListResponse
import com.anthonychaufrias.people.data.model.PersonaSaveResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

class PersonaService(private val service: IPersonaService){
    //private val retrofit = RetrofitHelper.getRetrofit()
    //private val service = retrofit.create(IPersonaService::class.java)

    suspend fun getPersonaList(busqueda: String): MutableList<Persona> {
        return withContext(Dispatchers.IO){
            val response: Response<PersonaListResponse> = service.getPersonaList(busqueda)
            response.body()?.respuesta ?: mutableListOf<Persona>()
        }
    }
    suspend fun setPersona(persona: Persona): PersonaSaveResponse? {
        return withContext(Dispatchers.IO){
            val response: Response<PersonaSaveResponse> = service.addPersona(persona)
            response.body()
        }
    }
    suspend fun updatePersona(persona: Persona): PersonaSaveResponse? {
        return withContext(Dispatchers.IO){
            val response: Response<PersonaSaveResponse> = service.updatePersona(persona)
            response.body()
        }
    }
    suspend fun deletePersona(persona: Persona): PersonaSaveResponse? {
        return withContext(Dispatchers.IO){
            val response: Response<PersonaSaveResponse> = service.deletePersona(persona)
            response.body()
        }
    }

}