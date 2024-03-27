package com.bimdata.bimdataviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.webkit.WebViewAssetLoader
import androidx.webkit.WebViewClientCompat
import org.json.JSONArray
import org.json.JSONObject


class ViewerActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        val webView = findViewById<WebView>(R.id.viewer)
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true // Enable localStorage

        // An asset loader is needed because JS can't be loaded from file:// because of CORS
        val assetLoader: WebViewAssetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this))
            .build()

        webView.webViewClient = object : WebViewClientCompat() {
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest
            ): WebResourceResponse? {
                return assetLoader.shouldInterceptRequest(request.url)
            }
        }

        class JsObject {
            @JavascriptInterface
            fun getViewerApiConfig(): String {
                val apiConfig = JSONObject()
                apiConfig.put("cloudId", 10344)
                apiConfig.put("projectId", 237466)
                apiConfig.put("accessToken", "TAbdyPzoQeYgVSMe4GUKoCEfYctVhcwJ")

                val modelIds = JSONArray()
                modelIds.put(15097)
                apiConfig.put("modelIds", modelIds)

                return apiConfig.toString()
            }

            @JavascriptInterface
            fun postMessage(message: String) {
                Log.d("WebViewMessage", "Got message from webview: $message")
            }
        }

        webView.addJavascriptInterface(JsObject(), "jsInterface")

        webView.loadUrl("https://appassets.androidplatform.net/assets/viewer.html")
    }
}

