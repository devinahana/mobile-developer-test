package com.suitmedia.suitmediatest.utils

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.suitmedia.suitmediatest.data.repository.UserPrefRepository
import com.suitmedia.suitmediatest.data.repository.UserRepository
import com.suitmedia.suitmediatest.ui.MainViewModel
import com.suitmedia.suitmediatest.di.Injection
import com.suitmedia.suitmediatest.ui.user.secondscreen.SecondScreenViewModel
import com.suitmedia.suitmediatest.ui.user.thirdscreen.ThirdScreenViewModel

class ViewModelFactory(
    private val userPrefRepository: UserPrefRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(userPrefRepository) as T
            }

            modelClass.isAssignableFrom(SecondScreenViewModel::class.java) -> {
                SecondScreenViewModel(userPrefRepository) as T
            }

            modelClass.isAssignableFrom(ThirdScreenViewModel::class.java) -> {
                ThirdScreenViewModel(userPrefRepository, userRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(
                    Injection.provideUserPrefRepository(context),
                    Injection.provideUserRepository(context)
                )
            }.also { instance = it }
    }
}