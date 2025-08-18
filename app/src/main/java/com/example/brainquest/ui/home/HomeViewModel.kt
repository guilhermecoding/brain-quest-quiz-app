package com.example.brainquest.ui.home

import androidx.lifecycle.ViewModel
import com.example.brainquest.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun onLogoutClicked() {
        authRepository.logout()
    }
}