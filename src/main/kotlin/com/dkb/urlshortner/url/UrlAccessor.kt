package com.dkb.urlshortner.url

import com.dkb.urlshortner.url.repository.UrlRepository
import com.dkb.urlshortner.url.visitUrl.OnVisit
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Isolation
import org.springframework.transaction.annotation.Transactional

@Service
class UrlAccessor(
    private var urlRepository: UrlRepository,
    private var visitUrl: OnVisit
) {
    // This transaction should lock the index to avoid race conditions
    // but the default @Transactional Lock Mode is READ COMMITTED, But in our case we need atomicity,
    // that's what SERIALIZABLE isolation offers, it will be a nice conversation starter in our interview :D
    @Transactional(isolation= Isolation.SERIALIZABLE)
    fun handleAccessAndReturnFullUrl(code : String): String?{
        var fullUrlDB = urlRepository.findFirstByCodeOrCodeIsNull(code);
        if(fullUrlDB == null) return null;

        //Asynchonus
        visitUrl.handle(fullUrlDB)

        return fullUrlDB.fullUrl
    }
}