package com.anthonychaufrias.people.domain

import com.anthonychaufrias.people.data.PaisRepository
import com.anthonychaufrias.people.data.model.Pais

class GetPaisesUseCase {
    private val repository = PaisRepository()

    suspend operator fun invoke(): List<Pais> = repository.getPaisesList()
}