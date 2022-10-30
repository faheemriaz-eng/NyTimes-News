package com.faheem.readers.ui.home.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.faheem.readers.databinding.FragmentMasterHomeBinding
import com.faheem.readers.ui.base.BaseFragment
import com.faheem.readers.ui.home.master.adapter.ArticlesAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeMasterFragment : BaseFragment() {
    private lateinit var mViewBinding: FragmentMasterHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    @Inject
    lateinit var adapter: ArticlesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = FragmentMasterHomeBinding.inflate(inflater, container, false);
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        viewModel.uiState.observe(viewLifecycleOwner, ::bindViewState)
    }
}