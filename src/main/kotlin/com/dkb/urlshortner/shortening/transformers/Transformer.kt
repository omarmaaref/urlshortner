package com.dkb.urlshortner.shortening.transformers

// Contract for transforming a URL string or numeric ID into a unique representation.
interface Transformer {
    fun transform(urlString : String): String
}