package com.anthonychaufrias.people.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.mock.PersonaMockService
import com.anthonychaufrias.people.data.service.PersonaService
import com.anthonychaufrias.people.domain.SetPersonasUseCase
import com.anthonychaufrias.people.domain.UpdPersonasUseCase
import com.anthonychaufrias.people.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


internal class PersonaViewModelTest {
    @get: Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var personaViewModel: PersonaViewModel
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)

        val service = PersonaMockService()
        val api = PersonaService(service)
        val repository = PersonaRepository(api)

        val setPersonasUseCase = SetPersonasUseCase(repository)
        val updPersonasUseCase = UpdPersonasUseCase(repository)

        personaViewModel = PersonaViewModel(repository, setPersonasUseCase, updPersonasUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @Test
    fun loadListaPersonas() {
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
}