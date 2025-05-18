package com.dkb.urlshortner.url.builder

import com.dkb.urlshortner.shortening.dtos.UrlGenerateRequest
import com.dkb.urlshortner.url.entity.DetailedUrlDB

// Builds and persists a shortened-URL entry.
interface UrlBuilder {
    var urlObject: DetailedUrlDB
    fun init(urlToProcessObject: UrlGenerateRequest): UrlBuilder
    fun save()
    fun setShortCode(shortCode: String): UrlBuilder

    //in the next version we should be able to set deadline , or some metadata
    //fun deadline( ...)...
    //fun metadata(...)...
}