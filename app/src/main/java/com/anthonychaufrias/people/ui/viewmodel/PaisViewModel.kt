package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.data.PaisRepository
import com.anthonychaufrias.people.data.model.Pais
import kotlinx.coroutines.launch

class PaisViewModel : ViewModel(){
    val liveDataCountriesList = MutableLiveData<List<Pais>>()
    var countriesList  = mutableListOf<Pais>()
    var countryNamesList = mutableListOf<String>()
    var selectedIndex: Int = 0

    private val repository = PaisRepository()

    fun loadPaisesList(selectedId:Int? = 0){
        viewModelScope.launch {
            val list: List<Pais> = repository.getPaisesList()

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

}