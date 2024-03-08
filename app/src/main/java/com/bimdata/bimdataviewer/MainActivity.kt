package com.bimdata.bimdataviewer

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val launchButton = findViewById<Button>(R.id.launchButton)
        val downloadButton = findViewById<Button>(R.id.downloadButton)

        launchButton.setOnClickListener {
            startActivity(Intent(this, ViewerActivity::class.java))
        }

        downloadButton.setOnClickListener {
            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse("https://archive.bimdata.io/cloud/10344/project/237466/offline-package?modelId=15097")
            val request = DownloadManager.Request(uri)
            request.addRequestHeader("Authorization", "Bearer TAbdyPzoQeYgVSMe4GUKoCEfYctVhcwJ")
            request.setDestinationInExternalFilesDir(this, "Viewer", "offline-package.zip")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val ref = downloadManager.enqueue(request)
        }
    }
}