package com.example.oauth20pkce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pkceData = PKCEUtil.generatePKCEData()
        println("Code Verifier: ${pkceData.codeVerifier}")
        println("Code Challenge: ${pkceData.codeChallenge}")
        println("Code Challenge Method: ${pkceData.codeChallengeMethod}")

        // Use the pkceData for OAuth 2.0 PKCE flow
    }
}
