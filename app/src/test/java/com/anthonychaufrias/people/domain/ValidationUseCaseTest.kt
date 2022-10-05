package com.anthonychaufrias.people.domain

import org.junit.Test

internal class ValidationUseCaseTest{

    @Test
    fun validateEmptyName(){
        // given
        val nombre: String = ""
        val docID: String  = ""

        // when
        val result = ValidatePersonaUseCase.getFormValidation(nombre, docID)

        // then
        //assert(result == ValidationResult.NAME_EMPTY)
    }

    @Test
    fun validateEmptyDocumento(){
        // given
        val nombre: String = "Anthony"
        val documento: String = ""

        // when
        val result = ValidatePersonaUseCase.getFormValidation(nombre, documento)

        // then
        //assert(result == ValidationResult.DOCUMENT_ID_INVALID)
    }
}