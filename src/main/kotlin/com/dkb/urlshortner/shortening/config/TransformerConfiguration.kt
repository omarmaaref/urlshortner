package com.dkb.urlshortner.shortening.config

import com.dkb.urlshortner.shortening.transformers.Base62EncodingTransformer
import com.dkb.urlshortner.shortening.transformers.CompositeTransformer
import com.dkb.urlshortner.shortening.transformers.FpeTransformer
import com.dkb.urlshortner.shortening.transformers.Transformer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary

// Configures and wires the chain of transformers (sorted).
// unique index => base62 => FPE =====> shortCode
@Configuration
class TransformerConfiguration(
    private val base62EncodingTransformer: Base62EncodingTransformer,
    private val fpeTransformer: FpeTransformer
    ) {

    @Bean
    @Primary
    fun compositeTransformer(): Transformer {
        val transformers = listOf(
            base62EncodingTransformer,
            fpeTransformer
        )
        return CompositeTransformer(transformers)
    }
}