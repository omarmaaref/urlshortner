package com.dkb.urlshortner.shortening.transformers

import org.bouncycastle.crypto.util.BasicAlphabetMapper
import org.bouncycastle.jcajce.spec.FPEParameterSpec
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.security.Security
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

// Applies format-preserving encryption (FPE/FF1) to an input string using a custom character set.
// This keeps the output string length and format consistent while ensuring cryptographic uniqueness.
@Service
public class FpeTransformer(
    @Value("\${KEY}")
    private val key: String,
    @Value("\${TWEAK}")
    private val tweakString: String,
    @Value("\${CHAR_SET}")
    private var alphabet: String
) : Transformer {
    private val tweak = tweakString.toByteArray(Charsets.UTF_8)
    private val alphabetMapper = BasicAlphabetMapper(alphabet)
    private val radix = alphabetMapper.radix
    private var cipher: Cipher? = null
    private var secretKey: SecretKey? = null;

    init {
        // Ensure BC provider is registered
        Security.addProvider(BouncyCastleProvider())
        // Instantiate the FF1 cipher from Bouncy Castle
        secretKey = stringToSecretKey(key)
        cipher = Cipher.getInstance("AES/FF1/NoPadding", BouncyCastleProvider())
    }

    //big Thanks to CharGPT for these functions
    final fun stringToSecretKey(keyString: String): SecretKey {
        // Hash or pad the string to get the required length (e.g., 16 bytes for AES-128)
        val keyBytes = keyString.toByteArray(Charsets.UTF_8)

        // You must ensure the length is correct: 16, 24, or 32 bytes for AES
        val paddedKey = ByteArray(16)
        System.arraycopy(keyBytes, 0, paddedKey, 0, keyBytes.size.coerceAtMost(16))

        return SecretKeySpec(paddedKey, "AES")
    }

    /**
     * Encrypts [plainText] (which must consist only of characters in our alphabet)
     * and returns a ciphertext string of the same length, using Format-Preserving
     * Encryption (FF1) under AES.
     */
    override fun transform(plainText: String): String {
        // map chars → numerical indices

        val inputChars = plainText.toCharArray()
        val inputIndexes = alphabetMapper.convertToIndexes(inputChars)

        // set up FPE parameters: radix + tweak
        val params = FPEParameterSpec(radix, tweak)

        // init cipher for encryption
        cipher?.init(Cipher.ENCRYPT_MODE, secretKey, params)

        // run encryption
        val cipherIndexes = cipher?.doFinal(inputIndexes)

        // map numerical indices → chars
        val cipherChars = alphabetMapper.convertToChars(cipherIndexes)
        return String(cipherChars)
    }
}