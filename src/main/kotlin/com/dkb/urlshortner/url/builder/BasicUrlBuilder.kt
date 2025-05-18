package com.dkb.urlshortner.url.builder

import com.dkb.urlshortner.shortening.dtos.UrlGenerateRequest
import com.dkb.urlshortner.url.entity.DetailedUrlDB
import com.dkb.urlshortner.url.repository.UrlRepository
import com.dkb.urlshortner.url.utility.UrlFormatter
import org.springframework.stereotype.Service
import org.springframework.web.context.annotation.RequestScope

@Service()
// UrlBuilder maintains state specific to each HTTP request.
// it is marked as @RequestScope to avoid issues with shared mutable state that would occur with a singleton.
@RequestScope
class BasicUrlBuilder(
    private var urlRepository: UrlRepository,
    private val urlFormatter: UrlFormatter,
): UrlBuilder {

    override var urlObject: DetailedUrlDB = DetailedUrlDB();

    override fun init(urlToProcessObject: UrlGenerateRequest): UrlBuilder {
        urlObject.fullUrl = urlFormatter.format(urlToProcessObject.longUrl);
        return this
    }

    override fun setShortCode(shortCode: String): UrlBuilder {
        urlObject.code = shortCode
        return this
    }

    override fun save() {
        urlRepository.save(urlObject);
    }
}
