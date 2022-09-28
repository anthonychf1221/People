package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.data.model.Persona
import com.anthonychaufrias.people.domain.DeletePersonasUseCase
import com.anthonychaufrias.people.domain.GetPersonasUseCase
import com.anthonychaufrias.people.domain.SetPersonasUseCase
import com.anthonychaufrias.people.domain.UpdPersonasUseCase
import kotlinx.coroutines.launch

class PersonaViewModel : ViewModel(){
    var liveDataPeopleList = MutableLiveData<MutableList<Persona>>()
    var peopleList  = mutableListOf<Persona>()
    var liveDataPeopleSave = MutableLiveData<Persona>()

    var getPersonasUseCase = GetPersonasUseCase()
    var setPersonasUseCase = SetPersonasUseCase()
    var updPersonasUseCase = UpdPersonasUseCase()
    var deletePersonasUseCase = DeletePersonasUseCase()


    fun savePersona(persona: Persona){
        if( persona.idPersona == 0 ){
            addPersona(persona)
        }
        else{
            updatePersona(persona)
        }
    }

    fun loadListaPersonas(busqueda: String){
        try{
            viewModelScope.launch {
                val list: MutableList<Persona> = getPersonasUseCase(busqueda)
                peopleList.addAll(list)
                liveDataPeopleList.postValue(list)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    private fun addPersona(persona: Persona){
        try{
            viewModelScope.launch {
                val newPersona: Persona = setPersonasUseCase(persona)
                liveDataPeopleSave.postValue(newPersona)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }
    private fun updatePersona(persona: Persona){
        try{
            viewModelScope.launch {
                val updatedPersona: Persona = updPersonasUseCase(persona)
                liveDataPeopleSave.postValue(updatedPersona)
            }
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    fun deletePersona(persona: Persona){
        try{
            viewModelScope.launch {
                val deletedPersona: Persona = deletePersonasUseCase(persona)
                removeElement(persona)
                liveDataPeopleList.value = peopleList
            }
        }
        catch(e: Exception){
            print(e.message)
        }
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