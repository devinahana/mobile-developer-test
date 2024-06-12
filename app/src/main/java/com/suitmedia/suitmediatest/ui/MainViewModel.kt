package com.suitmedia.suitmediatest.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suitmedia.suitmediatest.data.repository.UserPrefRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val userPrefRepository: UserPrefRepository,
) : ViewModel() {

    fun saveName(name: String) {
        viewModelScope.launch {
            userPrefRepository.saveName(name);
        }
    }
}