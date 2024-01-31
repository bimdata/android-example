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
        // Enable localStorage
        webView.settings.domStorageEnabled = true

        // JS can't be loaded from file:// because of CORS
        //
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
            fun postMessage(message: String) {
                Log.d("WebViewMessage", "Got message from webview " + message)
            }
            @JavascriptInterface
            fun getViewerParams() : String {
                val viewerParams = JSONObject()
                viewerParams.put("cloudId", 10344)
                viewerParams.put("projectId", 237466)
                viewerParams.put("accessToken", "TAbdyPzoQeYgVSMe4GUKoCEfYctVhcwJ")

                val modelIds = JSONArray()
                modelIds.put(15097)
                viewerParams.put("modelIds", modelIds)

                val offline = JSONObject()
                offline.put("enabled", true)
                offline.put("dataFile", "https://appassets.androidplatform.net/assets/offline-package.zip")
                viewerParams.put("offline", offline)

                return viewerParams.toString()
            }
        }
        webView.addJavascriptInterface(JsObject(), "ioDevice")

        webView.loadUrl("https://appassets.androidplatform.net/assets/viewer.html")
    }
}

