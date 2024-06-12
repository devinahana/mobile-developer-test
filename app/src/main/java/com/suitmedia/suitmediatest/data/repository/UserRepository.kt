package com.suitmedia.suitmediatest.data.repository

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.suitmedia.suitmediatest.data.local.database.UserDatabase
import com.suitmedia.suitmediatest.data.local.paging.UserRemoteMediator
import com.suitmedia.suitmediatest.data.remote.response.UserDetail
import com.suitmedia.suitmediatest.data.remote.retrofit.ApiService

class UserRepository private constructor(private val userDatabase: UserDatabase, private val apiService: ApiService) {
    @OptIn(ExperimentalPagingApi::class)
    fun getUser(): LiveData<PagingData<UserDetail>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = UserRemoteMediator(userDatabase, apiService),
            pagingSourceFactory = {
                userDatabase.userDao().getAllUser()
            }
        ).liveData
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userDatabase: UserDatabase,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userDatabase, apiService)
            }.also { instance = it }
    }
}