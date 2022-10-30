package com.faheem.readers.ui.home

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.faheem.readers.databinding.ActivityHomeBinding
import com.faheem.readers.ui.base.BaseActivity
import com.faheem.readers.ui.home.adapter.ArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : BaseActivity() {
    private lateinit var mViewBinding: ActivityHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(mViewBinding.root)
        addObservers()
        initViews()
    }

    private fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mViewBinding.recyclerView.adapter = adapter
    }

    private fun bindViewState(viewState: HomeUiState) {
        when (viewState) {
            is HomeUiState.Loading -> {
                mViewBinding.progressBar.isVisible = viewState.isLoading
            }
            is HomeUiState.Success -> {
                adapter.setList(viewState.articles)
            }
            is HomeUiState.Error -> {
                showToast(viewState.message)
            }
        }
    }

    private fun addObservers() {
        viewModel.uiState.observe(this, ::bindViewState)
    }
}