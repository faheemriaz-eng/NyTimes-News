package com.faheem.readers.ui.features.home.master

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.faheem.readers.R
import com.faheem.readers.databinding.FragmentMasterHomeBinding
import com.faheem.readers.domain.models.Article
import com.faheem.readers.ui.base.BaseFragment
import com.faheem.readers.ui.core.OnItemClickListener
import com.faheem.readers.ui.core.SingleEvent
import com.faheem.readers.ui.features.home.detail.ArticleDetailsFragment
import com.faheem.readers.ui.features.home.master.adapter.ArticlesAdapter
import com.faheem.readers.ui.utils.observe
import com.faheem.readers.ui.utils.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeMasterFragment : BaseFragment() {
    private lateinit var mViewBinding: FragmentMasterHomeBinding

    private val viewModel: HomeViewModel by viewModels()

    lateinit var adapter: ArticlesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mViewBinding = FragmentMasterHomeBinding.inflate(inflater, container, false)
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

    private fun initAdapter() {
        adapter = ArticlesAdapter(onItemClickListener)
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

    private fun navigateToDetailsScreen(singleEvent: SingleEvent<Article>) {
        singleEvent.getContentIfNotHandled()?.let {
            val bundle = Bundle().apply {
                putParcelable(ArticleDetailsFragment.ARG_ARTICLE, it)
            }
            findNavController().navigate(
                R.id.action_homeMasterFragment_to_articleDetailsFragment,
                bundle
            )
        }
    }

    private val onItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any) {
            if (data is Article) {
                viewModel.openArticleDetail(data)
            }
        }
    }

    private fun addObservers() {
        observe(viewModel.uiState, ::bindViewState)
        observeEvent(viewModel.openArticleDetails, ::navigateToDetailsScreen)
    }
}