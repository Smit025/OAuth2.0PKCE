package com.example.oauth20pkce

import android.util.Base64
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom

object PKCEUtil {
    const val CODE_VERIFIER_LENGTH = 64

    fun generateRandomCodeVerifier(): String {
        val secureRandom = SecureRandom()
        val codeVerifier = ByteArray(CODE_VERIFIER_LENGTH)
        secureRandom.nextBytes(codeVerifier)
        return Base64.encodeToString(codeVerifier, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }

    fun createCodeChallenge(codeVerifier: String): String {
        val bytes = codeVerifier.toByteArray(Charsets.UTF_8)
        val messageDigest = try {
            MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("SHA-256 algorithm not found", e)
        }
        val hashedBytes = messageDigest.digest(bytes)
        return Base64.encodeToString(hashedBytes, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
    }

    fun generatePKCEData(): PKCEData {
        val codeVerifier = generateRandomCodeVerifier()
        val codeChallenge = createCodeChallenge(codeVerifier)
        val codeChallengeMethod = "S256"
        return PKCEData(codeVerifier, codeChallenge, codeChallengeMethod)
    }

    data class PKCEData(
        val codeVerifier: String,
        val codeChallenge: String,
        val codeChallengeMethod: String
    )
}

