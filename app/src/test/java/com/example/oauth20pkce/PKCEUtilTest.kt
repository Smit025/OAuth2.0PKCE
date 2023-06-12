package com.example.oauth20pkce

import junit.framework.Assert.*
import org.junit.Test

class PKCEUtilTest {

    @Test
    fun testgenerateRandomCodeVerifier() {
        val codeVerifier = PKCEUtil.generateRandomCodeVerifier()

        assertNotNull(codeVerifier)
        assertEquals(PKCEUtil.CODE_VERIFIER_LENGTH, codeVerifier.length)
        assertTrue(codeVerifier.matches("[A-Za-z0-9-._~]{64}".toRegex()))
    }

    @Test
    fun testcreateCodeChallenge() {
        val codeVerifier = "testCodeVerifier"

        val codeChallenge = PKCEUtil.createCodeChallenge(codeVerifier)

        assertNotNull(codeChallenge)
        assertTrue(codeChallenge.matches("[A-Za-z0-9-._~]+".toRegex()))
    }

    @Test
    fun testgeneratePKCEData() {
        val pkceData = PKCEUtil.generatePKCEData()

        assertNotNull(pkceData.codeVerifier)
        assertNotNull(pkceData.codeChallenge)
        assertEquals(PKCEUtil.CODE_VERIFIER_LENGTH, pkceData.codeVerifier.length)
        assertTrue(pkceData.codeVerifier.matches("[A-Za-z0-9-._~]{64}".toRegex()))
        assertTrue(pkceData.codeChallenge.matches("[A-Za-z0-9-._~]+".toRegex()))
        assertEquals("S256", pkceData.codeChallengeMethod)
    }
}