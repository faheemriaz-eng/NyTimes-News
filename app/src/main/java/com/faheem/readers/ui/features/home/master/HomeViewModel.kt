package com.faheem.readers.ui.features.home.master

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.faheem.readers.domain.DataState
import com.faheem.readers.domain.models.Article
import com.faheem.readers.domain.usecases.GetArticlesUseCase
import com.faheem.readers.ui.core.SingleEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TIME_PERIOD = 7

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: GetArticlesUseCase) : ViewModel() {
    private val _uiState: MutableLiveData<HomeUiState> = MutableLiveData()
    val uiState: LiveData<HomeUiState> get() = _uiState

    private val _openArticleDetails = MutableLiveData<SingleEvent<Article>>()
    val openArticleDetails: LiveData<SingleEvent<Article>> get() = _openArticleDetails

    init {
        getArticles(TIME_PERIOD)
    }

    fun getArticles(timePeriod: Int) {
        viewModelScope.launch {
            useCase.fetchArticles(timePeriod).collectLatest {
                when (it) {
                    is DataState.Loading -> {
                        _uiState.value = HomeUiState.Loading(it.isLoading)
                    }
                    is DataState.Success -> {
                        _uiState.value = HomeUiState.Success(it.data)
                    }
                    is DataState.Error -> {
                        _uiState.value = HomeUiState.Error(it.error)
                    }
                }
            }
        }
    }

    fun openArticleDetail(article: Article) {
        _openArticleDetails.value = SingleEvent(article)
    }

}

sealed class HomeUiState {
    data class Success(val articles: List<Article>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
    data class Loading(val isLoading: Boolean) : HomeUiState()
}