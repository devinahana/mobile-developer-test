package com.suitmedia.suitmediatest.di

import android.content.Context
import com.suitmedia.suitmediatest.data.local.database.UserDatabase
import com.suitmedia.suitmediatest.data.local.pref.UserPreference
import com.suitmedia.suitmediatest.data.local.pref.dataStore
import com.suitmedia.suitmediatest.data.remote.retrofit.ApiConfig
import com.suitmedia.suitmediatest.data.repository.UserPrefRepository
import com.suitmedia.suitmediatest.data.repository.UserRepository

object Injection {
    fun provideUserPrefRepository(context: Context): UserPrefRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        return UserPrefRepository.getInstance(pref)
    }

    fun provideUserRepository(context: Context): UserRepository {
        val userDatabase = UserDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(userDatabase, apiService)
    }
}