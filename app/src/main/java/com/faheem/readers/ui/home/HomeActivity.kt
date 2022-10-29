package com.faheem.readers.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.faheem.readers.databinding.ActivityHomeBinding
import com.faheem.readers.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var mViewBinding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        addObservers()
    }

    private fun bindViewState(viewState: HomeUiState) {
        when (viewState) {
            is HomeUiState.Error -> {
                showToast(viewState.message)
            }
            is HomeUiState.Loading -> {
                mViewBinding.progressBar.isVisible = viewState.isLoading
            }
            is HomeUiState.Success -> {
                showToast(viewState.articles.toString())
            }
        }
    }

    private fun addObservers() {
        viewModel.uiState.observe(this, ::bindViewState)
    }
}