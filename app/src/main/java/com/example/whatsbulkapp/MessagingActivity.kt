package com.example.whatsbulkapp

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MessagingActivity : AppCompatActivity() {

    private lateinit var messageInput: TextInputEditText
    private lateinit var delaySeekBar: SeekBar
    private lateinit var delayValue: TextView
    private lateinit var btnSelectContacts: MaterialButton
    private lateinit var btnSendMessage: MaterialButton
    private lateinit var adView: AdView

    private var delayInSeconds = 0
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_messaging)

        // Initialize views
        initializeViews()
        
        // Set up ads
        setupAds()
        
        // Set up UI interactions
        setupUIInteractions()
    }

    private fun initializeViews() {
        messageInput = findViewById(R.id.messageInput)
        delaySeekBar = findViewById(R.id.delaySeekBar)
        delayValue = findViewById(R.id.delayValue)
        btnSelectContacts = findViewById(R.id.btnSelectContacts)
        btnSendMessage = findViewById(R.id.btnSendMessage)
        adView = findViewById(R.id.adView)
    }

    private fun setupAds() {
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)
    }

    private fun setupUIInteractions() {
        // Set up delay seek bar
        delaySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                delayInSeconds = progress
                delayValue.text = "Delay: $progress seconds"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Set up contact selection button
        btnSelectContacts.setOnClickListener {
            requestContactPermission()
        }

        // Set up send message button
        btnSendMessage.setOnClickListener {
            validateAndSendMessage()
        }
    }

    private fun requestContactPermission() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_CONTACTS)
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS)
        } else {
            // Permission already granted, proceed with contact selection
            selectContacts()
        }
    }

    private fun selectContacts() {
        // For this example, we'll just show a toast
        // In a full implementation, you would launch a contact picker
        Toast.makeText(this, "Contact selection to be implemented", Toast.LENGTH_SHORT).show()
    }

    private fun validateAndSendMessage() {
        val message = messageInput.text?.toString()?.trim()
        
        if (message.isNullOrEmpty()) {
            messageInput.error = "Please enter a message"
            return
        }

        showConfirmationDialog(message)
    }

    private fun showConfirmationDialog(message: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Confirm Send")
            .setMessage("Are you sure you want to send this message? Remember that automated bulk messaging is not allowed.")
            .setPositiveButton("Send") { dialog, _ ->
                dialog.dismiss()
                sendMessage(message)
            }
            .setNegativeButton("Cancel") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun sendMessage(message: String) {
        // Create WhatsApp intent
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
            setPackage("com.whatsapp")
        }

        try {
            if (delayInSeconds > 0) {
                Handler(Looper.getMainLooper()).postDelayed({
                    startActivity(sendIntent)
                }, delayInSeconds * 1000L)
                
                Toast.makeText(
                    this,
                    "Message will be sent after $delayInSeconds seconds",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                startActivity(sendIntent)
            }
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "Error: Unable to send message via WhatsApp",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    selectContacts()
                } else {
                    Toast.makeText(
                        this,
                        "Contact permission is required to select contacts",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
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
