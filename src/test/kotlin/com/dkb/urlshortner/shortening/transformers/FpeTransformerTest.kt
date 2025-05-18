package com.dkb.urlshortner.shortening.transformers

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import javax.crypto.SecretKey

class FpeTransformerTest {

    private val shortKey = "abcdef"                // 6 bytes → will be zero-padded
    private val longKey  = "0123456789abcdef0123"   // 20 bytes → will be truncated
    private val tweak    = "tweak"
    private val alphabet = "0123456789ABCDEF"       // hex-style alphabet

    private val transformer       = FpeTransformer(shortKey, tweak, alphabet)
    private val longKeyTransformer = FpeTransformer(longKey, tweak, alphabet)

    @Test
    fun `stringToSecretKey pads short keys to exactly 16 bytes`() {
        val sk: SecretKey = transformer.stringToSecretKey(shortKey)
        val encoded = sk.encoded
        assertEquals(16, encoded.size, "AES key must be exactly 16 bytes")
        // first 6 bytes should match; rest should be zero
        assertArrayEquals(shortKey.toByteArray(Charsets.UTF_8), encoded.copyOfRange(0, shortKey.length))
        assertTrue(encoded.copyOfRange(shortKey.length, 16).all { it == 0.toByte() })
    }

    @Test
    fun `stringToSecretKey truncates long keys to exactly 16 bytes`() {
        val sk: SecretKey = longKeyTransformer.stringToSecretKey(longKey)
        val encoded = sk.encoded
        assertEquals(16, encoded.size, "AES key must be exactly 16 bytes")
        // should equal the first 16 bytes of the UTF-8 longKey
        val expected = longKey.toByteArray(Charsets.UTF_8).copyOfRange(0, 16)
        assertArrayEquals(expected, encoded)
    }

    @Test
    fun `transform returns same-length cipher with only alphabet chars`() {
        val plain = "0123456789ABCDEF"
        val cipher = transformer.transform(plain)

        // 1) same length
        assertEquals(plain.length, cipher.length)

        // 2) only chars from our alphabet
        assertTrue(cipher.all { it in alphabet },
            "Cipher text contains illegal character: $cipher")
    }

    @Test
    fun `transform is deterministic for same input`() {
        val input = "0123456789"
        val c1 = transformer.transform(input)
        val c2 = transformer.transform(input)
        assertEquals(c1, c2, "FF1 encryption should be deterministic with fixed key & tweak")
    }

    @Test
    fun `transform on two different inputs yields different outputs`() {
        val c1 = transformer.transform("0000000000")
        val c2 = transformer.transform("1111111111")
        assertNotEquals(c1, c2, "Different plaintexts should not map to the same ciphertext")
    }
}
