package com.example.androidtaskbosta.domain.usecases

import com.example.androidtaskbosta.domain.models.User
import com.example.androidtaskbosta.domain.repository.DataRepository
import javax.inject.Inject

class FetchUsersUseCase   @Inject constructor(
    private val dataRepository: DataRepository)   {
     suspend fun execute(): List<User> {
        return dataRepository.fetchUsers()
    }
}