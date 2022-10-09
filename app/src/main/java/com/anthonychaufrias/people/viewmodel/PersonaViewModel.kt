package com.anthonychaufrias.people.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.anthonychaufrias.people.core.Constantes
import com.anthonychaufrias.people.core.RetrofitHelper
import com.anthonychaufrias.people.model.Persona
import com.anthonychaufrias.people.model.PersonaListResponse
import com.anthonychaufrias.people.model.PersonaSaveResponse
import com.anthonychaufrias.people.model.PersonaSaveResult
import com.anthonychaufrias.people.model.ValidationResult
import com.anthonychaufrias.people.service.Servicio
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PersonaViewModel : ViewModel(){
    private val retrofit = RetrofitHelper.getRetrofit()
    private val service: Servicio = retrofit.create(Servicio::class.java)

    var liveDataPeopleList = MutableLiveData<MutableList<Persona>>()
    var peopleList  = mutableListOf<Persona>()
    var liveDataPeopleSave = MutableLiveData<PersonaSaveResult>()

    init {
        liveDataPeopleList = MutableLiveData<MutableList<Persona>>()
        liveDataPeopleSave = MutableLiveData<PersonaSaveResult>()
    }

    private fun getFormValidation(name: String, docID: String):MutableList<ValidationResult> {
        val validations = mutableListOf<ValidationResult>()
        if( name.isEmpty() ){
            validations.add(ValidationResult.INVALID_NAME)
        }
        if( docID.length != Constantes.PERSON_DOCUMENT_LENGTH ){
            validations.add(ValidationResult.INVALID_DOCUMENT_ID)
        }
        if( validations.size == 0 ){
            validations.add(ValidationResult.OK)
        }
        return validations
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
        val validations = getFormValidation(persona.nombres, persona.documento)
        if( validations[0] == ValidationResult.OK ){
            val call = service.addPersona(persona)
            call.enqueue(object : Callback<PersonaSaveResponse>{
                override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                    if( response.body()?.status.equals("Ok") ){
                        response.body()?.respuesta?.let { persona ->
                            liveDataPeopleSave.postValue(PersonaSaveResult.OK(persona))
                        }
                    }
                    else{
                        liveDataPeopleSave.postValue(PersonaSaveResult.OperationFailed)
                    }
                }
                override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                    call.cancel()
                    liveDataPeopleSave.postValue(PersonaSaveResult.OperationFailed)
                }
            })
        }
        else{
            liveDataPeopleSave.postValue(PersonaSaveResult.InvalidInputs(validations))
        }
    }

    private fun updatePersona(persona: Persona){
        val validations = getFormValidation(persona.nombres, persona.documento)
        if( validations[0] == ValidationResult.OK ){
            val call = service.updatePersona(persona)
            call.enqueue(object : Callback<PersonaSaveResponse>{
                override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                    if( response.body()?.status.equals("Ok") ){
                        response.body()?.respuesta?.let { persona ->
                            liveDataPeopleSave.postValue(PersonaSaveResult.OK(persona))
                        }
                    }
                    else{
                        liveDataPeopleSave.postValue(PersonaSaveResult.OperationFailed)
                    }
                }
                override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                    call.cancel()
                    liveDataPeopleSave.postValue(PersonaSaveResult.OperationFailed)
                }
            })
        }
        else{
            liveDataPeopleSave.postValue(PersonaSaveResult.InvalidInputs(validations))
        }
    }

    fun deletePersona(persona: Persona){
        try{
            val call = service.deletePersona(persona)
            call.enqueue(object : Callback<PersonaSaveResponse>{
                override fun onResponse(call: Call<PersonaSaveResponse>,response: Response<PersonaSaveResponse>) {
                    if( response.body()?.status.equals("Ok") ){
                        response.body()?.respuesta?.let { p ->
                            removePersonaFromList(persona)
                        }
                    }
                }
                override fun onFailure(call: Call<PersonaSaveResponse>, t: Throwable) {
                    call.cancel()
                }
            })
        }
        catch(e: Exception){
            print(e.message)
        }
    }

    fun loadListaPersonas(busqueda: String){
        val call = service.getPersonaList(busqueda)
        call.enqueue(object : Callback<PersonaListResponse>{
            override fun onResponse(call: Call<PersonaListResponse>,response: Response<PersonaListResponse>) {
                response.body()?.respuesta?.let { list ->
                    peopleList.addAll(list)
                    liveDataPeopleList.postValue(list)
                }
            }
            override fun onFailure(call: Call<PersonaListResponse>, t: Throwable) {
                call.cancel()
            }
        })
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
    private fun removePersonaFromList(persona: Persona){
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
        liveDataPeopleList.value = peopleList
    }
}