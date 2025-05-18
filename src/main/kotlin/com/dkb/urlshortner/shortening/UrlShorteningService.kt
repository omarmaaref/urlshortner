package com.dkb.urlshortner.shortening

import com.dkb.urlshortner.shortening.dtos.UrlGenerateRequest
import com.dkb.urlshortner.shortening.transformers.Transformer
import com.dkb.urlshortner.shortening.unicity.postgres.index.UniqueIndexGenerator
import com.dkb.urlshortner.url.builder.UrlBuilder
import org.springframework.stereotype.Service

// Handles the logic of generating a unique short URL based on an input.
@Service
class UrlShorteningService(
    private val uniqueIndexGenerator: UniqueIndexGenerator,
    private val transformer: Transformer,
    private val urlFactory: UrlBuilder
    ) {

    // Generates a new short URL code by transforming a unique index,
    // and stores the mapping using the configured UrlBuilder.
    fun shorten(urlRequest: UrlGenerateRequest): String {
        val nextId= uniqueIndexGenerator.nextIndex();
        val shortCode = transformer.transform(nextId.toString())

        urlFactory
            .init(urlRequest)
            .setShortCode(shortCode)
            .save();
        return shortCode;
    }


}
