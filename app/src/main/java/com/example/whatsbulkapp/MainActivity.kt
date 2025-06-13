package com.example.whatsbulkapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {
    
    private lateinit var adView: AdView
    private lateinit var btnStartMessaging: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize views
        adView = findViewById(R.id.adView)
        btnStartMessaging = findViewById(R.id.btnStartMessaging)

        // Initialize Mobile Ads SDK
        initializeAds()

        // Set up click listeners
        setupClickListeners()
    }

    private fun initializeAds() {
        MobileAds.initialize(this) { initializationStatus ->
            // Initialization complete
        }

        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun setupClickListeners() {
        btnStartMessaging.setOnClickListener {
            showDisclaimerDialog()
        }
    }

    private fun showDisclaimerDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Important Notice")
            .setMessage("This app uses manual message sending to comply with WhatsApp's Terms of Service. " +
                    "Automated bulk messaging is not allowed. Do you understand and wish to proceed?")
            .setPositiveButton("Yes, I Understand") { dialog, _ ->
                dialog.dismiss()
                startMessagingActivity()
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun startMessagingActivity() {
        // Check if WhatsApp is installed
        if (isWhatsAppInstalled()) {
            startActivity(Intent(this, MessagingActivity::class.java))
        } else {
            Toast.makeText(
                this,
                "WhatsApp is not installed on your device",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun isWhatsAppInstalled(): Boolean {
        return try {
            packageManager.getPackageInfo("com.whatsapp", 0)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun onResume() {
        super.onResume()
        adView.resume()
    }

    override fun onPause() {
        adView.pause()
        super.onPause()
    }

    override fun onDestroy() {
        adView.destroy()
        super.onDestroy()
    }
}
