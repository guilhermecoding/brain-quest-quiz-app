package com.example.brainquest.ui.ranking

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainquest.data.model.User
import com.example.brainquest.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RankingState(
    val isLoading: Boolean = true,
    val rankingList: List<User> = emptyList(),
    val errorMessage: String? = null
)

@HiltViewModel
class RankingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RankingState())
    val uiState = _uiState.asStateFlow()

    init {
        fetchRanking()
    }

    private fun fetchRanking() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            userRepository.getRanking().onSuccess { list ->
                _uiState.update { it.copy(isLoading = false, rankingList = list) }
            }.onFailure { error ->
                _uiState.update { it.copy(isLoading = false, errorMessage = error.message) }
            }
        }
    }
}