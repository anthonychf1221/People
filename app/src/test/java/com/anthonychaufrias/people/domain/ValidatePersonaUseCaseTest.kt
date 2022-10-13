package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.model.ValidationResult
import org.junit.Test

internal class ValidatePersonaUseCaseTest{

    @Test
    fun validateOnlyEmptyName(){
        // given
        val name: String = ""
        val docID: String  = "12345678"

        // when
        val result = ValidatePersonaUseCase.getFormValidation(name, docID)

        // then
        assert(result[0] == ValidationResult.INVALID_NAME)
    }

    @Test
    fun validateOnlyEmptyDocumentoID(){
        // given
        val name: String = "Anthony"
        val docID: String  = ""

        // when
        val result = ValidatePersonaUseCase.getFormValidation(name, docID)

        // then
        assert(result[0] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun validateOnlyLengthDocumentoID(){
        // given
        val name: String = "Anthony"
        val docID: String  = "123"

        // when
        val result = ValidatePersonaUseCase.getFormValidation(name, docID)

        // then
        assert(result[0] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if the name and DocumentoID are empty`(){
        // given
        val name: String = ""
        val docID: String  = ""

        // when
        val result = ValidatePersonaUseCase.getFormValidation(name, docID)

        // then
        assert(result[0] == ValidationResult.INVALID_NAME && result[1] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if the name is empty and the length of DocumentoID is not valid`(){
        // given
        val name: String = ""
        val docID: String  = "123"

        // when
        val result = ValidatePersonaUseCase.getFormValidation(name, docID)

        // then
        assert(result[0] == ValidationResult.INVALID_NAME && result[1] == ValidationResult.INVALID_DOCUMENT_ID)
    }

    @Test
    fun `validate if everything is OK`(){
        // given
        val name: String = "Anthony"
        val docID: String  = "12345678"

        // when
        val result = ValidatePersonaUseCase.getFormValidation(name, docID)

        // then
        assert(result[0] == ValidationResult.OK)
    }

}