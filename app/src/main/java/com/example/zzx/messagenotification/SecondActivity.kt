package com.example.zzx.messagenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        MessageManager.register("second_tv", "tv_parent", window, tv)
        tv.setOnClickListener {
            MessageManager.sendMessage("second_tv", 256)
        }
    }
}
