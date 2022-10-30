package com.faheem.readers.data.dtos


import com.faheem.readers.domain.models.Article
import com.google.gson.annotations.SerializedName

data class ArticlesDto(
    @SerializedName("results")
    val results: List<Result>
)

data class Media(
    @SerializedName("approved_for_syndication")
    val approvedForSyndication: Int? = null,
    @SerializedName("caption")
    val caption: String? = null,
    @SerializedName("copyright")
    val copyright: String? = null,
    @SerializedName("media-metadata")
    val mediaMetadata: List<MediaMetadata?>? = null,
    @SerializedName("subtype")
    val subtype: String? = null,
    @SerializedName("type")
    val type: String? = null
)

data class MediaMetadata(
    @SerializedName("format")
    val format: String? = null,
    @SerializedName("height")
    val height: Int? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("width")
    val width: Int? = null
)

data class Result(
    @SerializedName("abstract")
    val `abstract`: String? = null,
    @SerializedName("adx_keywords")
    val adxKeywords: String? = null,
    @SerializedName("asset_id")
    val assetId: Long? = null,
    @SerializedName("byline")
    val byline: String? = null,
    @SerializedName("column")
    val column: Any? = null,
    @SerializedName("des_facet")
    val desFacet: List<String?>? = null,
    @SerializedName("eta_id")
    val etaId: Int? = null,
    @SerializedName("geo_facet")
    val geoFacet: List<String?>? = null,
    @SerializedName("id")
    val id: Long? = null,
    @SerializedName("media")
    val media: List<Media?>? = null,
    @SerializedName("nytdsection")
    val nytdsection: String? = null,
    @SerializedName("org_facet")
    val orgFacet: List<String?>? = null,
    @SerializedName("per_facet")
    val perFacet: List<String?>? = null,
    @SerializedName("published_date")
    val publishedDate: String? = null,
    @SerializedName("section")
    val section: String? = null,
    @SerializedName("source")
    val source: String? = null,
    @SerializedName("subsection")
    val subsection: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("updated")
    val updated: String? = null,
    @SerializedName("uri")
    val uri: String? = null,
    @SerializedName("url")
    val url: String? = null
)

fun ArticlesDto.asDomain(): List<Article> {
    return this.results.map {
        Article(
            title = it.title ?: "",
            description = it.abstract ?: "",
            by = it.byline ?: "Anonymous",
            source = it.source ?: "",
            publishDate = it.publishedDate ?: "",
            imageUrl = it.media?.getOrNull(0)?.mediaMetadata?.getOrNull(1)?.url ?: "",
            largeImageUrl = it.media?.getOrNull(0)?.mediaMetadata?.getOrNull(2)?.url ?: ""
        )
    }
}