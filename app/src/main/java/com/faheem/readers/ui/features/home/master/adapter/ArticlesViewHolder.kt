package com.faheem.readers.ui.features.home.master.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.faheem.readers.databinding.LayoutItemArticleHeadlineBinding
import com.faheem.readers.domain.models.Article
import com.faheem.readers.ui.core.OnItemClickListener

class ArticlesViewHolder(private val itemBinding: LayoutItemArticleHeadlineBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(article: Article, onItemClickListener: OnItemClickListener) {
        with(itemBinding) {
            tvTitle.text = article.title
            tvCreatedBy.text = article.by
            tvPublishDate.text = article.publishDate
            Glide.with(image.context)
                .load(article.imageUrl)
                .into(image)

            itemBinding.root.setOnClickListener {
                onItemClickListener.onItemClick(it, article)
            }
        }
    }
}
