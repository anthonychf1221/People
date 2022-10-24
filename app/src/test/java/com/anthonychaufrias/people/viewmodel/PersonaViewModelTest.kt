package com.anthonychaufrias.people.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.getOrAwaitValue
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.model.PersonaSaveResult
import com.anthonychaufrias.people.model.ValidationResult
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class PersonaViewModelTest{

    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var personaViewModel: PersonaViewModel

    @Before
    fun setup() {
        personaViewModel = PersonaViewModel()
    }

    @Test
    fun `check if the list is filled after listing from the database`(){
        // given
        val search: String = ""
        personaViewModel.peopleList.clear()

        // when
        personaViewModel.loadListaPersonas(search)

        // then
        val result = personaViewModel.liveDataPeopleList.getOrAwaitValue()
        assert(result.size > 0)
        assert(personaViewModel.peopleList.size > 0)
    }

    @Test
    fun `validate if everything is OK when we insert a person`(){
        // given
        val personID: Int = 0
        val names: String = "AAA BBB"
        val documentID: String  = "33991100"
        val countryCode: Int = 1
        val countryName: String = "Perú"
        val person: Persona = Persona(personID, names, documentID, countryCode, countryName)

        // when
        personaViewModel.savePersona(person, Constantes.INSERT)

        // then
        val result = personaViewModel.liveDataPeopleSave.getOrAwaitValue()
        assert(result is PersonaSaveResult.OK)
    }

    @Test
    fun `validate if everything is OK when we update a person`(){
        // given
        val personID: Int = 41
        val names: String = "Anthony Chau Frias"
        val documentID: String  = "11111111"
        val countryCode: Int = 1
        val countryName: String = "Perú"
        val person: Persona = Persona(personID, names, documentID, countryCode, countryName)

        // when
        personaViewModel.savePersona(person, Constantes.UPDATE)

        // then
        val result = personaViewModel.liveDataPeopleSave.getOrAwaitValue()
        assert(result is PersonaSaveResult.OK)
    }

    @Test
    fun `validate only empty Name when we try to save a person`(){
        // given
        val personID: Int = 0
        val names: String = ""
        val documentID: String  = "12345678"
        val countryCode: Int = 1
        val countryName: String = "Perú"
        val person: Persona = Persona(personID, names, documentID, countryCode, countryName)

        // when
        personaViewModel.savePersona(person, Constantes.INSERT)

        // then
        val result = personaViewModel.liveDataPeopleSave.getOrAwaitValue()
        var errors: List<ValidationResult> = mutableListOf<ValidationResult>()
        if (result is PersonaSaveResult.InvalidInputs)
            errors = result.errors

        assert(result is PersonaSaveResult.InvalidInputs)
        assert(errors[0] == ValidationResult.INVALID_NAME)
        /*assert(
            if (result is PersonaSaveResult.InvalidInputs)
                result.errors[0] == ValidationResult.INVALID_NAME
            else
                false
        )*/
    }

    @Test
    fun `validate only empty DocumentID when we try to save a person`(){
        // given
        val personID: Int = 0
        val names: String = "Anthony Chau Frias"
        val documentID: String  = ""
        val countryCode: Int = 1
        val countryName: String = "Perú"
        val person: Persona = Persona(personID, names, documentID, countryCode, countryName)

        // when
        personaViewModel.savePersona(person, Constantes.INSERT)

        // then
        val result = personaViewModel.liveDataPeopleSave.getOrAwaitValue()
        var errors: List<ValidationResult> = mutableListOf<ValidationResult>()
        if (result is PersonaSaveResult.InvalidInputs)
            errors = result.errors

        assert(result is PersonaSaveResult.InvalidInputs)
        assert(errors[0] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if Name and DocumentID are empty  when we try to save a person`(){
        // given
        val personID: Int = 0
        val names: String = ""
        val documentID: String  = ""
        val countryCode: Int = 1
        val countryName: String = "Perú"
        val person: Persona = Persona(personID, names, documentID, countryCode, countryName)

        // when
        personaViewModel.savePersona(person, Constantes.INSERT)

        // then
        val result = personaViewModel.liveDataPeopleSave.getOrAwaitValue()
        var errors: List<ValidationResult> = mutableListOf<ValidationResult>()
        if (result is PersonaSaveResult.InvalidInputs)
            errors = result.errors

        assert(result is PersonaSaveResult.InvalidInputs)
        assert(errors[0] == ValidationResult.INVALID_NAME && errors[1] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if Name is empty and the length of DocumentID is not valid  when we try to save a person`(){
        // given
        val personID: Int = 0
        val names: String = ""
        val documentID: String  = "123"
        val countryCode: Int = 1
        val countryName: String = "Perú"
        val person: Persona = Persona(personID, names, documentID, countryCode, countryName)

        // when
        personaViewModel.savePersona(person, Constantes.INSERT)

        // then
        val result = personaViewModel.liveDataPeopleSave.getOrAwaitValue()
        var errors: List<ValidationResult> = mutableListOf<ValidationResult>()
        if (result is PersonaSaveResult.InvalidInputs)
            errors = result.errors

        assert(result is PersonaSaveResult.InvalidInputs)
        assert(errors[0] == ValidationResult.INVALID_NAME && errors[1] == ValidationResult.INVALID_DOCUMENT_ID)
    }

}