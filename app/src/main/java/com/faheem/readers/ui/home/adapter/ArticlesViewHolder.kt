package com.faheem.readers.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faheem.readers.databinding.LayoutItemArticleHeadlineBinding
import com.faheem.readers.domain.models.Article

class ArticlesViewHolder(private val itemBinding: LayoutItemArticleHeadlineBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(article: Article) {
        with(itemBinding) {
            tvTitle.text = article.title
            tvCreatedBy.text = article.by
            tvPublishDate.text = article.publishDate
            Glide.with(image.context)
                .load(article.imageUrl)
                .into(image)
        }
    }
}
