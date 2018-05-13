package com.example.mycustomview

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.widget.TextView
import com.zzhoujay.richtext.RichText
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    var tv:WebView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var progressView = findViewById<MyProgressView>(R.id.main_MyProgressView)
        progressView!!.setProgressChangeListener(object :MyProgressView.MyProgressListener {
            override fun moveEnd(progress: MyProgressView?) {

            }

            override fun moveStart(progress: MyProgressView?) {

            }

            override fun moveing(progress: MyProgressView?) {

            }
        })
    }
}
