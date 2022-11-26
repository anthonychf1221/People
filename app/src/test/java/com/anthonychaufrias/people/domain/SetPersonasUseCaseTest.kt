package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaSaveResult
import com.anthonychaufrias.people.data.service.IPersonaService
import com.anthonychaufrias.people.data.service.PersonaService
import org.junit.Test

internal class SetPersonasUseCaseTest{

    @Test
    suspend fun `validate result after inserting a person`(){
        // given
        val retrofit = RetrofitHelper.getRetrofit()
        val service = retrofit.create(IPersonaService::class.java)
        val api = PersonaService(service)
        val repository = PersonaRepository(api)

        val setPersonasUseCase = SetPersonasUseCase(repository)
        val persona = Persona(0, "Anthony Chau Frias", "12345678", 1, "Perú")

        // when
        val result: PersonaSaveResult = setPersonasUseCase(persona)

        // then
        assert(result is PersonaSaveResult.OK)
    }

}