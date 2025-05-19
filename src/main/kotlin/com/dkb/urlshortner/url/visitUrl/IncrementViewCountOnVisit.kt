package com.dkb.urlshortner.url.visitUrl

import com.dkb.urlshortner.url.entity.DetailedUrlDB
import com.dkb.urlshortner.url.repository.UrlRepository
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service

@Service
class IncrementViewCountOnVisit(
    private var urlRepository: UrlRepository
): OnVisit {

    // Asynchronously increments the view count and saves it back.
    @Async
    override fun handle(detailedUrlDB: DetailedUrlDB){
        detailedUrlDB.viewCount+=1;
        urlRepository.save(detailedUrlDB)
    }
}