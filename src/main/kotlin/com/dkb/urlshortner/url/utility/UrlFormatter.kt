package com.dkb.urlshortner.url.utility

import org.springframework.stereotype.Service

// TODO: create a real formatter that formats the fullUrl to make it standard ( remove extra chars ...)
@Service
class UrlFormatter {
    fun format(fullUrl: String): String {
        return fullUrl;
    }

}
