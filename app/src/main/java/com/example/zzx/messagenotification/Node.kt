package com.example.zzx.messagenotification

import android.view.View
import android.view.Window
import android.widget.TextView
import java.lang.ref.SoftReference
/**
 * parent node's key can not be the same as child node's key
 * create by zzx
 * create at 18-12-1
 */
class Node(val key: String, val window: SoftReference<Window>, val targetView: SoftReference<View>) {

    val childList: MutableList<Node> by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
        mutableListOf<Node>()
    }

    var parentNode: Node? = null

    var displayView: SoftReference<TextView>? = null

    var messageCount: Int = 0
}