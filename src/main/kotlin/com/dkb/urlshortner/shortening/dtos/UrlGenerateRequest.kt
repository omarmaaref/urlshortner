package com.dkb.urlshortner.shortening.dtos

// Represents the input for generating a short URL.
data class UrlGenerateRequest(
    val longUrl: String,
    //val deadline: String,
    //val configuration: UrlConfiguration
)
