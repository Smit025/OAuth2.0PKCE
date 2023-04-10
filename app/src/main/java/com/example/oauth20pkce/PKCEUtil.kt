package com.example.oauth20pkce

import android.os.Build
import androidx.annotation.RequiresApi
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.security.SecureRandom
import java.util.Base64

object PKCEUtil {
    private const val CODE_VERIFIER_LENGTH = 64

    @RequiresApi(Build.VERSION_CODES.O)
    private fun generateRandomCodeVerifier(): String {
        val secureRandom = SecureRandom()
        val codeVerifier = ByteArray(CODE_VERIFIER_LENGTH)
        secureRandom.nextBytes(codeVerifier)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(codeVerifier)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createCodeChallenge(codeVerifier: String): String {
        val bytes = codeVerifier.toByteArray(Charsets.UTF_8)
        val messageDigest = try {
            MessageDigest.getInstance("SHA-256")
        } catch (e: NoSuchAlgorithmException) {
            throw IllegalStateException("SHA-256 algorithm not found", e)
        }
        val hashedBytes = messageDigest.digest(bytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashedBytes)
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
