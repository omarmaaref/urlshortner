package com.dkb.urlshortner.redirect

import com.dkb.urlshortner.url.UrlAccessor
import org.springframework.stereotype.Service

@Service
class UrlRedirectService(
    private var urlAccessor: UrlAccessor
) {
    // Look up the full URL for code, or throw if not found.
    fun handleAccessAndReturnFullUrlOrThrow(code : String): String {
        return urlAccessor.handleAccessAndReturnFullUrl(code)?:
        throw UrlNotFoundException(
            "Url not found"
        )
    }

    class UrlNotFoundException(message: String) : RuntimeException(message)
}