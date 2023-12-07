package com.bimdata.bimdataviewer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject


class ViewerActivity : AppCompatActivity() {
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        val webViewer= findViewById<WebView>(R.id.viewer)
        webViewer.settings.javaScriptEnabled = true;

        class JsObject {
            @JavascriptInterface
            fun postMessage(message: String) {
                Log.w("WTF", "Got message" + message)
            }
            @JavascriptInterface
            fun getViewerParam() : String {
                val viewerParams = JSONObject()
                viewerParams.put("projectId", 237466)
                viewerParams.put("cloudId", 10344)

                val modelIds = JSONArray()
                modelIds.put(15097)
                viewerParams.put("modelIds", modelIds)
                viewerParams.put("accessToken", "TAbdyPzoQeYgVSMe4GUKoCEfYctVhcwJ")
                return viewerParams.toString()
            }
        }

        // And when initializing the webview...
        webViewer.addJavascriptInterface(JsObject(), "ioDevice")

        webViewer.loadUrl("file:///android_asset/viewer.html");

        //webViewer.evaluateJavascript("loadViewer(" + viewerParams.toString() + ")", null);

    }
}

