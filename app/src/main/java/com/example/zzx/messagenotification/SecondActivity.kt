package com.example.zzx.messagenotification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        MessageManager.register("second_btn1", "tv_parent", window, btn1)
        MessageManager.register("second_btn2", "tv_parent", window, btn2)
        btn1.setOnClickListener {
            MessageManager.sendMessage("second_btn1", 256)
        }
        btn2.setOnClickListener {
            MessageManager.sendMessage("second_btn2", 998)
        }

    }
}
