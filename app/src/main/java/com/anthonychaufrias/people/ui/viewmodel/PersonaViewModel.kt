package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.data.model.PersonaSaveResponse
import com.anthonychaufrias.people.data.model.PersonaSaveResult
import com.anthonychaufrias.people.domain.SetPersonasUseCase
import com.anthonychaufrias.people.domain.UpdPersonasUseCase
import kotlinx.coroutines.launch

class PersonaViewModel : ViewModel(){
    val liveDataPeopleList = MutableLiveData<MutableList<Persona>>()
    val peopleList  = mutableListOf<Persona>()
    val liveDataPeopleSave = MutableLiveData<PersonaSaveResult>()

    private val repository = PersonaRepository()
    private val setPersonasUseCase = SetPersonasUseCase()
    private val updPersonasUseCase = UpdPersonasUseCase()

    fun loadListaPersonas(busqueda: String){
        try{
            viewModelScope.launch {
                val list: MutableList<Persona> = repository.getPersonaList(busqueda)
                peopleList.addAll(list)
                liveDataPeopleList.postValue(list)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    fun savePersona(persona: Persona, action: Int){
        try{
            if( action == Constantes.INSERT ){
                addPersona(persona)
            }
            else{
                updatePersona(persona)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    private fun addPersona(persona: Persona){
        try{
            viewModelScope.launch {
                val result: PersonaSaveResult = setPersonasUseCase(persona)
                liveDataPeopleSave.value = result
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }
    private fun updatePersona(persona: Persona){
        try{
            viewModelScope.launch {
                val result: PersonaSaveResult = updPersonasUseCase(persona)
                liveDataPeopleSave.value = result
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    fun deletePersona(persona: Persona){
        try{
            viewModelScope.launch {
                val deletedPersona: PersonaSaveResponse? = repository.deletePersona(persona)
                removeElement(persona)
                liveDataPeopleList.value = peopleList
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }
    fun refreshList(persona: Persona, action: Int){
        if( action == Constantes.INSERT ){
            addPersonaToList(persona)
        }
        else{
            updatePersonaFromList(persona)
        }
    }

    private fun addPersonaToList(persona: Persona){
        peopleList.add(persona)
        liveDataPeopleList.value = peopleList
    }
    private fun updatePersonaFromList(persona: Persona){
        for (item in peopleList.indices) {
            if( peopleList[item].idPersona == persona.idPersona ){
                peopleList[item] = persona
            }
        }
        liveDataPeopleList.value = peopleList
    }

    //@RequiresApi(Build.VERSION_CODES.N)
    private fun removeElement(persona: Persona){
        try{
            // Esta forma de eliminar es más general
            // Más información sobre bucles aquí:
            // https://www.programiz.com/kotlin-programming/for-loop
            for (item in peopleList.indices) {
                if( peopleList[item].idPersona == persona.idPersona ){
                    peopleList.removeAt(item)
                }
            }
            // Esta porción de código también se puede usar
            // Pero solo funcionará correctamente si la versión del dispositivo
            // es mayor a API LEVEL 24 (Android 7.0)
            /*var filter = Predicate { p: Persona -> p == persona }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                listPersonas.removeIf(filter)
            }*/
        }
        catch (e: Exception){
            print(e.message)
        }
    }
}