package com.suitmedia.suitmediatest.ui.user.secondscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.suitmedia.suitmediatest.data.repository.UserPrefRepository
import kotlinx.coroutines.launch

class SecondScreenViewModel(
    private val userPrefRepository: UserPrefRepository,
) : ViewModel() {

    val name: LiveData<String> =
        userPrefRepository.getName().asLiveData()

    val selectedName: LiveData<String> =
        userPrefRepository.getSelected().asLiveData()

    fun logout() {
        viewModelScope.launch {
            userPrefRepository.logout()
        }
    }

}