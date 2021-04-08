package com.example.submission2.Model

data class ResponseDetail(
    val avatar_url: String,
    val company: String,
    val followers: Int,
    val followers_url: String,
    val following: Int,
    val following_url: String,
    val location: String,
    val login: String,
    val name: String,
    val url: String
)