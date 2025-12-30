package com.cinelive.tv

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar
    private lateinit var errorLayout: LinearLayout
    private lateinit var retryButton: Button
    
    // URL
    private val cineliveUrl = "https://cinelive.djuntemon.com"

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Inflate from XML
        
        // Disable Action Bar
        supportActionBar?.hide()
        
        // Initialize Views
        webView = findViewById(R.id.webView)
        progressBar = findViewById(R.id.progressBar)
        errorLayout = findViewById(R.id.errorLayout)
        retryButton = findViewById(R.id.retryButton)
        
        // Enable immersive mode
        enableImmersiveMode()
        
        // Configure WebView
        setupWebView()
        
        // Load Content
        webView.loadUrl(cineliveUrl)
        
        // Setup Retry Button
        retryButton.setOnClickListener {
            errorLayout.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            webView.reload()
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            mediaPlaybackRequiresUserGesture = false
            allowFileAccess = true
            allowContentAccess = true
            
            // Hardware Acceleration
            setRenderPriority(WebSettings.RenderPriority.HIGH)
            
            // Zoom disabled
            setSupportZoom(false)
            builtInZoomControls = false
            
            // Custom User Agent
            userAgentString = "$userAgentString CineLiveTV/1.0"
        }

        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                progressBar.visibility = View.VISIBLE
                errorLayout.visibility = View.GONE
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                progressBar.visibility = View.GONE
            }
            
            override fun onReceivedError(
                view: WebView?,
                request: WebResourceRequest?,
                error: WebResourceError?
            ) {
                super.onReceivedError(view, request, error)
                // Only show error for the main page, not for missing assets/analytics
                if (request?.isForMainFrame == true) {
                    webView.visibility = View.INVISIBLE
                    progressBar.visibility = View.GONE
                    errorLayout.visibility = View.VISIBLE
                }
            }
        }
        
        webView.webChromeClient = WebChromeClient()
    }

    private fun enableImmersiveMode() {
        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_FULLSCREEN
            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        )
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // If error view is showing, allow D-Pad to focus retry button automatically
        if (errorLayout.visibility == View.VISIBLE) {
             return super.onKeyDown(keyCode, event)
        }
    
        // Handle D-Pad navigation for WebView
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                injectKey("ArrowUp", 38)
                return true
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                injectKey("ArrowDown", 40)
                return true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                injectKey("ArrowLeft", 37)
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                injectKey("ArrowRight", 39)
                return true
            }
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                injectKey("Enter", 13)
                return true
            }
            KeyEvent.KEYCODE_BACK -> {
                if (webView.canGoBack()) {
                    webView.goBack()
                    return true
                }
            }
        }
        return super.onKeyDown(keyCode, event)
    }
    
    private fun injectKey(key: String, code: Int) {
        webView.evaluateJavascript(
            "document.activeElement?.dispatchEvent(new KeyboardEvent('keydown', {key: '$key', keyCode: $code}))",
            null
        )
    }

    override fun onResume() {
        super.onResume()
        webView.onResume()
        enableImmersiveMode()
    }

    override fun onPause() {
        super.onPause()
        webView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        webView.destroy()
    }
}
