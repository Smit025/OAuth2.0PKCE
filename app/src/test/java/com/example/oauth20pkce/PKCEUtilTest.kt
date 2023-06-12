package com.example.oauth20pkce
import android.util.Base64

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class PKCEUtilTest {

    @Test
    fun `test generatePKCEData`() {
        val mockedCodeVerifier = "mockedCodeVerifier"
        val mockedCodeChallenge = "mockedCodeChallenge"

        // Mock the generateRandomCodeVerifier() function
        val mockPKCEUtil = mock(PKCEUtil::class.java)
        `when`(mockPKCEUtil.generateRandomCodeVerifier()).thenReturn(mockedCodeVerifier)

        // Mock the createCodeChallenge() function
        `when`(mockPKCEUtil.createCodeChallenge(mockedCodeVerifier)).thenReturn(mockedCodeChallenge)

        // Generate PKCEData using the mocked functions
        val pkceData = mockPKCEUtil.generatePKCEData()

        // Verify that the generated PKCEData has the expected properties
        assertNotNull(pkceData)
        assertEquals(mockedCodeVerifier, pkceData.codeVerifier)
        assertEquals(mockedCodeChallenge, pkceData.codeChallenge)
        assertEquals("S256", pkceData.codeChallengeMethod)
    }
}
