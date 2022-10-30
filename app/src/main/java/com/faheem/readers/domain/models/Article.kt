package com.faheem.readers.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Article(
    val title: String,
    val by: String,
    val publishDate: String,
    val description: String = "",
    val source: String = "",
    val imageUrl: String = "",
    val largeImageUrl: String = ""
) : Parcelable
