package com.suitmedia.suitmediatest.ui.user.thirdscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.suitmedia.suitmediatest.data.remote.response.UserDetail
import com.suitmedia.suitmediatest.data.repository.UserPrefRepository
import com.suitmedia.suitmediatest.data.repository.UserRepository
import kotlinx.coroutines.launch

class ThirdScreenViewModel(
    private val userPrefRepository: UserPrefRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        _isLoading.value = true
    }

    fun getUsers(): LiveData<PagingData<UserDetail>> {
        _isLoading.value = false
        return userRepository.getUser().cachedIn(viewModelScope)
    }

    fun saveSelected(selectedName: String) {
        viewModelScope.launch {
            userPrefRepository.saveSelected(selectedName);
        }
    }
}