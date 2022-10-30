package com.faheem.readers.ui.features.home.detail

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.faheem.readers.databinding.FragmentArticleDetailsBinding
import com.faheem.readers.domain.models.Article
import com.faheem.readers.ui.base.BaseFragment
import com.faheem.readers.ui.utils.observe

class ArticleDetailsFragment : BaseFragment() {
    lateinit var mViewBinding: FragmentArticleDetailsBinding

    private val viewModel: ArticleDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            val article = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(ARG_ARTICLE, Article::class.java)
            } else {
                it.getParcelable(ARG_ARTICLE)
            }
            viewModel.initBundleData(article)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mViewBinding = FragmentArticleDetailsBinding.inflate(inflater, container, false)
        return mViewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addObservers()
    }

    private fun initializeView(article: Article) {
        with(mViewBinding) {
            tvTitle.text = article.title
            tvDescription.text = article.description
            tvCreatedBy.text = article.by
            tvSource.text = article.source

            Glide.with(requireContext()).load(article.largeImageUrl).into(ivImage)
        }
    }

    private fun addObservers() {
        observe(viewModel.article, ::initializeView)
    }


    companion object {
        const val ARG_ARTICLE = "arg_article"
    }
}