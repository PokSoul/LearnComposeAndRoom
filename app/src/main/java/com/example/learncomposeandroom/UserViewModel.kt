package com.example.learncomposeandroom

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class UserViewModel(private val repository: UserRepository): ViewModel() {

    fun insertUser(user: User){
        viewModelScope.launch {
            repository.insertUser(user)
        }
    }

    suspend fun getAllUsers(): List<User> {
        return repository.getAllUsers()
    }

}