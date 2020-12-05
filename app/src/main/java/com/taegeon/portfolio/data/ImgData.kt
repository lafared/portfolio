package com.taegeon.portfolio.data

data class ImgData(
    val meta: Meta,
    val documents: List<Documents>
)

data class Meta(
    val total_count: Int,
    val pageable_count: Int,
    val is_end: Boolean
)

data class Documents(
    val collection: String,
    val thumbnail_url: String,
    val image_url: String,
    val width: Int,
    val height: Int,
    val display_sitename: String,
    val doc_url : String,
    val datetime: String
)