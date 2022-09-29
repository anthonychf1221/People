package com.anthonychaufrias.people.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anthonychaufrias.people.data.PersonaRepository
import com.anthonychaufrias.people.data.model.Pais
import com.anthonychaufrias.people.domain.GetPaisesUseCase
import com.anthonychaufrias.people.domain.SetPersonasUseCase
import com.anthonychaufrias.people.domain.UpdPersonasUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaisViewModel @Inject constructor(
    private val getPaisesUseCase: GetPaisesUseCase
) : ViewModel(){
    val liveDataCountriesList = MutableLiveData<List<Pais>>()
    var countriesList  = mutableListOf<Pais>()
    var countryNamesList = mutableListOf<String>()
    var selectedIndex: Int = 0

    fun loadPaisesList(selectedId:Int? = 0){
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

}