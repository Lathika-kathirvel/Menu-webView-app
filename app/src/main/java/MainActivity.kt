
package com.example.menuwebviewapp

import android.os.Bundle
import android.view.MenuItem
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var menuButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webView)
        menuButton = findViewById(R.id.menuButton)

        // Configure WebView
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        // Show the welcome screen
        showHome()

        // Open the options menu when the button is tapped
        menuButton.setOnClickListener { view ->
            showOptionsMenu(view)
        }
    }

    private fun showHome() {
        webView.loadDataWithBaseURL(
            null,
            """
            <!DOCTYPE html>
            <html>
            <head>
                <meta name="viewport"
                      content="width=device-width, initial-scale=1">
            </head>
            <body style="text-align:center; padding:30px;
                         font-family:sans-serif;">
                <h2>Welcome!</h2>
                <p>Use the three-dot menu to open a website.</p>
            </body>
            </html>
            """.trimIndent(),
            "text/html",
            "UTF-8",
            null
        )
    }

    private fun showOptionsMenu(view: android.view.View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.main_menu, popup.menu)

        popup.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {

                R.id.menu_home -> {
                    showHome()
                    true
                }

                R.id.menu_website -> {
                    webView.loadUrl("https://www.google.com")
                    true
                }

                R.id.menu_about -> {
                    Toast.makeText(
                        this,
                        "Menus and WebView Demo",
                        Toast.LENGTH_LONG
                    ).show()
                    true
                }

                else -> false
            }
        }

        popup.show()
    }

    override fun onDestroy() {
        webView.stopLoading()
        webView.destroy()
        super.onDestroy()
    }
}
