package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaSaveResult
import org.junit.Test

internal class SetPersonasUseCaseTest{

    @Test
    suspend fun `validate result after inserting a person`(){
        // given
        val setPersonasUseCase = SetPersonasUseCase()
        val persona = Persona(0, "Anthony Chau Frias", "12345678", 1, "Perú")

        // when
        val result: PersonaSaveResult = setPersonasUseCase(persona)

        // then
        assert(result is PersonaSaveResult.OK)
    }

}