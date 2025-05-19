package com.dkb.urlshortner.url.visitUrl

import com.dkb.urlshortner.url.entity.DetailedUrlDB

// Strategy for handling what happens after a URL is viewed.
// e.g. incrementing counters, logging, analytics, etc.
interface OnVisit {
    fun handle(detailedUrlDB: DetailedUrlDB)
}