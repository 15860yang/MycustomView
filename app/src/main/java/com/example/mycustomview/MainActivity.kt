package com.example.mycustomview

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.util.Log
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
//        tv = findViewById(R.id.main_TextView)
//
//        tv!!.loadUrl("https://www.jianshu.com/p/e0f7113cfc06")

    }
}
