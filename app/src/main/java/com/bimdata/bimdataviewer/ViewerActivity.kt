package com.bimdata.bimdataviewer

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.widget.Button
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.bimdata.bimdataviewer.databinding.ActivityViewerBinding

class ViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        val webViewer= findViewById<WebView>(R.id.viewer)
        webViewer.settings.javaScriptEnabled = true;
        webViewer.loadUrl("file:///android_asset/viewer.html");

    }
}