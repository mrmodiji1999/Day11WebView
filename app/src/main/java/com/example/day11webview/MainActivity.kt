package com.example.day11webview

import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var webView: WebView
//
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Hide the ActionBar
        supportActionBar?.hide()

        webView = findViewById(R.id.webView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)

        webViewSetUp(webView, progressBar)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun webViewSetUp(webView: WebView, progressBar: ProgressBar) {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageStarted(view: WebView?, url: String?, favicon: android.graphics.Bitmap?) {
                super.onPageStarted(view, url, favicon)
                // Show the ProgressBar when the page starts loading
                progressBar.visibility = android.view.View.VISIBLE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                // Hide the ProgressBar when the page finishes loading
                progressBar.visibility = android.view.View.GONE
            }
        }

        webView.apply {
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
            loadUrl("https://www.youtube.com/")
        }
    }

    override fun onBackPressed() {
        val specificUrl = "https://m.youtube.com/feed/library"
        if (webView.url == specificUrl) {
            showExitConfirmationDialog()
        } else if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    private fun showExitConfirmationDialog() {
        AlertDialog.Builder(this).apply {
            setTitle("Exit Confirmation")
            setMessage("Are you sure you want to exit?")
            setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                super.onBackPressed()
            }
            setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            create()//
            show()
        }
    }//
}
