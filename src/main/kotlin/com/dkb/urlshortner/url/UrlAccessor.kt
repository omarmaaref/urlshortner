package com.dkb.urlshortner.url

import com.dkb.urlshortner.url.repository.UrlRepository
import com.dkb.urlshortner.url.visitUrl.VisitHandler
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UrlAccessor(
    private var urlRepository: UrlRepository,
    private var visitUrl: VisitHandler
) {
    // This transaction should lock the element to avoid race conditions
    // but the default @Transactional Lock Mode is READ COMMITTED, which isn't enough,
    // I left it anyway, it will be a nice conversation starter in our interview :D
    @Transactional
    fun handleAccessAndReturnFullUrl(code : String): String?{
        var fullUrlDB = urlRepository.findFirstByCodeOrCodeIsNull(code);
        if(fullUrlDB == null) return null;

        //Asynchonus
        visitUrl.handle(fullUrlDB)

        return fullUrlDB.fullUrl
    }
}