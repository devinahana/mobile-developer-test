package com.suitmedia.suitmediatest.data.repository

import com.suitmedia.suitmediatest.data.local.pref.UserPreference
import kotlinx.coroutines.flow.Flow

class UserPrefRepository private constructor(
    private val userPreference: UserPreference
) {

    suspend fun saveName(name: String) {
        userPreference.saveName(name)
    }

    fun getName(): Flow<String> {
        return userPreference.getName()
    }

    suspend fun saveSelected(selectedName: String) {
        userPreference.saveSelected(selectedName)
    }

    fun getSelected(): Flow<String> {
        return userPreference.getSelected()
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserPrefRepository? = null
        fun getInstance(
            userPreference: UserPreference
        ): UserPrefRepository =
            instance ?: synchronized(this) {
                instance ?: UserPrefRepository(userPreference)
            }.also { instance = it }
    }
}