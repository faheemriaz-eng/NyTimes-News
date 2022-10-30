package com.faheem.readers.ui.features.home.master.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.faheem.readers.databinding.LayoutItemArticleHeadlineBinding
import com.faheem.readers.domain.models.Article
import com.faheem.readers.ui.core.OnItemClickListener

class ArticlesAdapter(private val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ArticlesViewHolder>() {

    private var list: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ArticlesViewHolder {
        return ArticlesViewHolder(
            LayoutItemArticleHeadlineBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticlesViewHolder, position: Int) {
        holder.bind(list[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<Article>) {
        this.list.clear()
        this.list.addAll(list)
        notifyDataSetChanged()
    }
}