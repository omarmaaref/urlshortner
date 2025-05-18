package com.dkb.urlshortner.shortening.transformers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class Base62EncodingTransformerTest {

    private val transformer = Base62EncodingTransformer()

    @Test
    fun `transform zero returns 0`() {
        assertEquals("0", transformer.transform("0"))
    }

    @Test
    fun `transform small positive numbers`() {
        assertEquals("a", transformer.transform("10"))   // 10 → 'a'
        assertEquals("Z", transformer.transform("61"))   // 61 → 'Z'
    }

    @Test
    fun `transform boundary at base value`() {
        assertEquals("10", transformer.transform("62"))  // 62 → '10'
    }

    @Test
    fun `transform larger numbers`() {
        assertEquals("100", transformer.transform("3844"))   // 62^2 → '100'
        assertEquals("3d7", transformer.transform("12345"))  // random example
    }

    @Test
    fun `transform negative number throws exception`() {
        val ex = assertThrows<IllegalArgumentException> {
            transformer.transform("-1")
        }
        assertEquals("Number must be non-negative", ex.message)
    }
}
