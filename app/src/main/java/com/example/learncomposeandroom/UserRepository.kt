package com.example.learncomposeandroom

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {

    suspend fun insertUser(user: User){
        withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun getAllUsers(): List<User>{
        return withContext(Dispatchers.IO){
            userDao.getAllUsers()
        }
    }
}