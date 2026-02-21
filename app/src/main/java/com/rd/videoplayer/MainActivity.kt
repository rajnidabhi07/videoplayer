package com.rd.videoplayer

import android.net.Uri
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.core.net.toUri

class MainActivity : AppCompatActivity() {

    private lateinit var player: ExoPlayer
    private lateinit var playerView: PlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        playerView = findViewById(R.id.playerView)

        initializePlayer()
    }

    private fun initializePlayer() {
        val videoList = listOf(

            VideoItem(
                "https://storage.googleapis.com/shaka-demo-assets/angel-one-hls/hls.m3u8",
                "https://storage.googleapis.com/shaka-demo-assets/angel-one-text/vtt/angel-one-en.vtt"
            ),
        )

        player = ExoPlayer.Builder(this).build()
        playerView.player = player

        val mediaItems = videoList.map { item ->

            val subtitleConfig = MediaItem.SubtitleConfiguration.Builder(
                item.subtitleUrl.toUri()
            )
                .setMimeType(MimeTypes.TEXT_VTT)
                .setLanguage("en")
                .build()

            MediaItem.Builder()
                .setUri(item.videoUrl)
                .setSubtitleConfigurations(listOf(subtitleConfig))
                .build()
        }

        player.setMediaItems(mediaItems)
        player.prepare()
        player.play()
    }

    override fun onStop() {
        super.onStop()
        player.release()
    }
}

data class VideoItem(
    val videoUrl: String,
    val subtitleUrl: String
)