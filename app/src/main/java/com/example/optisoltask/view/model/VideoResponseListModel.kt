package com.example.optisoltask.view.model

data class VideoResponseListModel(
    var page: Int,
    var per_page: Int,
    var total: Int,
    var total_pages: Int,
    var data: List<Videos> = emptyList()

)

data class Videos(
    var id: Int,
    var email: String,
    var first_name: String,
    var last_name: String,
    var avatar: String,
)