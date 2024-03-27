package com.bimdata.bimdataviewer

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewerOnlineButton = findViewById<Button>(R.id.viewerOnline)
        val viewerOffline1Button = findViewById<Button>(R.id.viewerOffline1)
        val viewerOffline2Button = findViewById<Button>(R.id.viewerOffline2)

        viewerOnlineButton.setOnClickListener {
            startActivity(Intent(this, ViewerActivity::class.java))
        }

        viewerOffline1Button.setOnClickListener {
            startActivity(Intent(this, ViewerOffline1Activity::class.java))
        }

        viewerOffline2Button.setOnClickListener {
            val mainContext = this

            // Register a receiver that will open the viewer when the offline package has been downloaded
            val onDownloadComplete = object : BroadcastReceiver() {
                override fun onReceive(ctx: Context, intent: Intent) {
                    startActivity(Intent(mainContext, ViewerOffline2Activity::class.java))
                }
            }
            registerReceiver(
                onDownloadComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
                RECEIVER_NOT_EXPORTED)

            // Download offline package from BIMData archive backend
            val downloadManager = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri = Uri.parse("https://archive.bimdata.io/cloud/10344/project/237466/offline-package?modelId=15097")
            val request = DownloadManager.Request(uri)
            request.addRequestHeader("Authorization", "Bearer TAbdyPzoQeYgVSMe4GUKoCEfYctVhcwJ")
            request.setDestinationInExternalFilesDir(this, "Viewer", "offline-package.zip")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            downloadManager.enqueue(request)
        }
    }
}