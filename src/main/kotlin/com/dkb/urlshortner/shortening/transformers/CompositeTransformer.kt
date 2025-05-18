package com.dkb.urlshortner.shortening.transformers


// Uses Composite design pattern to chain multiple transformers together (in a sequence).
// but, I now I think a decorator pattern would have been more suitable
class CompositeTransformer(private val transformers: List<Transformer>) : Transformer{
    override fun transform(urlString: String): String {
        return transformers.fold(urlString) { acc, transformer ->
            transformer.transform(acc)
        }
    }
}