package com.example.zzx.messagenotification

import android.content.Intent
import android.graphics.RectF
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val TAG = "MainActivity"

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        MessageManager.register("tv_parent", window, tv)
        tv.setOnClickListener {
            MessageManager.sendMessage("tv_parent", 320)
        }
        navigatorTv.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }

}
