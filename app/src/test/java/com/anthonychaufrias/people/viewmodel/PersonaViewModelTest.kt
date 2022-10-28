package com.anthonychaufrias.people.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.getOrAwaitValue
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.model.PersonaSaveResult
import com.anthonychaufrias.people.model.ValidationResult
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
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
        assertTrue(result.size > 0)
        assertTrue(personaViewModel.peopleList.size > 0)
    }

    @Test
    fun `validate if everything is OK when we insert a person`(){
        // given
        val personID: Int = 0
        val names: String = "Anthony Chau Frias"
        val documentID: String  = "33991100"
        val countryCode: Int = 1
        val countryName: String = "Perú"
        val person: Persona = Persona(personID, names, documentID, countryCode, countryName)

        // when
        personaViewModel.savePersona(person, Constantes.INSERT)

        // then
        val result = personaViewModel.liveDataPeopleSave.getOrAwaitValue()
        assertTrue(result is PersonaSaveResult.OK)
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
        assertTrue(result is PersonaSaveResult.OK)
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
        val invalidResult = result as PersonaSaveResult.InvalidInputs
        val errors = invalidResult.errors
        assertTrue(errors[0] == ValidationResult.INVALID_NAME)
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
        val invalidResult = result as PersonaSaveResult.InvalidInputs
        val errors = invalidResult.errors
        assertTrue(errors[0] == ValidationResult.INVALID_DOCUMENT_ID)
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
        val invalidResult = result as PersonaSaveResult.InvalidInputs
        val errors = invalidResult.errors
        val expectedErrors = listOf(ValidationResult.INVALID_NAME, ValidationResult.INVALID_DOCUMENT_ID)
        assertEquals(expectedErrors, errors)
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
        val invalidResult = result as PersonaSaveResult.InvalidInputs
        val errors = invalidResult.errors
        val expectedErrors = listOf(ValidationResult.INVALID_NAME, ValidationResult.INVALID_DOCUMENT_ID)
        assertEquals(expectedErrors, errors)
    }

}