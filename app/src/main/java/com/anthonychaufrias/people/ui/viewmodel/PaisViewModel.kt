package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.data.model.Pais
import com.anthonychaufrias.people.domain.GetPaisesUseCase
import kotlinx.coroutines.launch

class PaisViewModel : ViewModel(){
    val liveDataCountriesList = MutableLiveData<List<Pais>>()
    var countriesList  = mutableListOf<Pais>()
    var countryNamesList = mutableListOf<String>()
    var selectedIndex: Int = 0

    var getPaisesUseCase = GetPaisesUseCase()

    fun getPaisesList(selectedId:Int? = 0){
        viewModelScope.launch {
            val list: List<Pais> = getPaisesUseCase()

            countriesList.addAll(list)
            for (item in countriesList.indices) {
                countryNamesList.add(countriesList[item].nombre)
                if( countriesList[item].idPais == selectedId ){
                    selectedIndex = item
                }
            }
            liveDataCountriesList.postValue(list)
        }
    }

    /*fun getPaisesList(selectedId:Int? = 0){
        val call = service.getPaisesList()
        call.enqueue(object : Callback<PaisListResponse> {
            override fun onResponse(call: Call<PaisListResponse>, response: Response<PaisListResponse>) {
                response.body()?.respuesta?.let { list ->
                    countriesList.addAll(list)
                    //for(pais in list){
                    for (item in countriesList.indices) {
                        countryNamesList.add(countriesList[item].nombre)
                        if( countriesList[item].idPais == selectedId ){
                            selectedIndex = item
                        }
                    }
                    liveDataCountriesList.postValue(list)
                }
            }
            override fun onFailure(call: Call<PaisListResponse>, t: Throwable) {
                call.cancel()
            }
        })
    }*/

}