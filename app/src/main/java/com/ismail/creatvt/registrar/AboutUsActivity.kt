package com.ismail.creatvt.registrar

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import kotlinx.android.synthetic.main.activity_about_us.*

class AboutUsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_us)

        send_feedback.setOnClickListener {
            openEmailApp()
        }

        app_settings.setOnClickListener {
            openAppSettings()
        }

        source_code.setOnClickListener {
            openBrowserURL()
        }
    }

    private fun openAppSettings() {
        //Step 1: Define intent and action
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)

        //Step 2: Set data to intent
        intent.data = Uri.parse("package:$packageName")

        //Step 3: call startActivity
        startActivity(intent)
    }

    private fun openBrowserURL() {
        //Step 1: Define intent and action
        val intent = Intent(ACTION_VIEW)

        //Step 2: Set data to intent
        intent.data = Uri.parse("https://github.com/ismail-creatvt/Registrar")

        //Step 3: call startActivity
        startActivity(intent)
    }

    private fun openEmailApp() {
        //Step 1: Define intent and action
        val intent = Intent(ACTION_SEND)

        //Step 2: Set data to intent
        val recipients = arrayOf("ismailshaikh31097@gmail.com")
        intent.putExtra(Intent.EXTRA_EMAIL, recipients)
        intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
        intent.type = "message/rfc822"

        //Step 3: call startActivity
        startActivity(intent)
    }
}