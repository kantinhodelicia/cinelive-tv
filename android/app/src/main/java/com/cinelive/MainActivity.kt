package com.cinelive.tv

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private val cineliveUrl = "https://cinelive.djuntemon.com" // Production URL

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Enable immersive fullscreen mode
        enableImmersiveMode()
        
        // Create and configure WebView
        webView = WebView(this).apply {
            layoutParams = android.view.ViewGroup.LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT
            )
            
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                mediaPlaybackRequiresUserGesture = false
                allowFileAccess = true
                allowContentAccess = true
                
                // Enable hardware acceleration for smooth video
                setRenderPriority(WebSettings.RenderPriority.HIGH)
                
                // Enable zoom for accessibility
                setSupportZoom(false)
                builtInZoomControls = false
            }
            
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                    view.loadUrl(url)
                    return true
                }
            }
            
            webChromeClient = WebChromeClient()
            
            // Load CineLive
            loadUrl(cineliveUrl)
        }
        
        setContentView(webView)
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
        // Handle D-Pad navigation
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_UP -> {
                webView.evaluateJavascript("document.activeElement?.dispatchEvent(new KeyboardEvent('keydown', {key: 'ArrowUp', keyCode: 38}))", null)
                return true
            }
            KeyEvent.KEYCODE_DPAD_DOWN -> {
                webView.evaluateJavascript("document.activeElement?.dispatchEvent(new KeyboardEvent('keydown', {key: 'ArrowDown', keyCode: 40}))", null)
                return true
            }
            KeyEvent.KEYCODE_DPAD_LEFT -> {
                webView.evaluateJavascript("document.activeElement?.dispatchEvent(new KeyboardEvent('keydown', {key: 'ArrowLeft', keyCode: 37}))", null)
                return true
            }
            KeyEvent.KEYCODE_DPAD_RIGHT -> {
                webView.evaluateJavascript("document.activeElement?.dispatchEvent(new KeyboardEvent('keydown', {key: 'ArrowRight', keyCode: 39}))", null)
                return true
            }
            KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
                webView.evaluateJavascript("document.activeElement?.dispatchEvent(new KeyboardEvent('keydown', {key: 'Enter', keyCode: 13}))", null)
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
