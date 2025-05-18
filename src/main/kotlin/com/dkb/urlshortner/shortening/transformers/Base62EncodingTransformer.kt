package com.dkb.urlshortner.shortening.transformers

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service;

// Encodes numeric strings into a compact Base62 format
// using digits and alphanumeric characters.
@Service
class Base62EncodingTransformer(
    @Value("\${CHAR_SET}")
    private var charSet: String
): Transformer {
    private val BASE = 62

    override fun transform(indexInStringFormat: String): String {
        var index= indexInStringFormat.toLong()
        val result = StringBuilder()

        while (index > 0) {
            val remainder = (index % BASE).toInt()
            result.append(charSet[remainder])
            index /= BASE
        }
        return result.reverse().toString()
    }
}