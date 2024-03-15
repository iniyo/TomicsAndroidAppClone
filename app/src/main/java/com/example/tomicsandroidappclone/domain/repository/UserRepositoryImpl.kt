package com.example.tomicsandroidappclone.domain.repository

import dagger.hilt.android.AndroidEntryPoint

class UserRepositoryImpl(){

}
/*
class UserRepositoryImpl(apiService: ApiService, userDatabase: UserDatabase) : UserRepository {
    private val apiService: ApiService
    private val userDatabase: UserDatabase

    init {
        this.apiService = apiService
        this.userDatabase = userDatabase
    }

    fun getUser(userId: String?): User? {
        // 캐시된 사용자 정보가 있는지 확인합니다.
        var user: User = userDatabase.userDao().getUser(userId)

        // 캐시된 사용자 정보가 없는 경우 서버에서 사용자 정보를 가져옵니다.
        if (user == null) {
            user = apiService.getUser(userId)
            userDatabase.userDao().insertUser(user)
        }
        return user
    }
}*/
