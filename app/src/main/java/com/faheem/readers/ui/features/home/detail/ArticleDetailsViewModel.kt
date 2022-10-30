package com.faheem.readers.ui.features.home.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.faheem.readers.domain.models.Article

class ArticleDetailsViewModel : ViewModel() {

    private val _article = MutableLiveData<Article>()
    val article: LiveData<Article> get() = _article

    fun initBundleData(article: Article?) {
        article?.let {
            _article.value = it
        }
    }
}