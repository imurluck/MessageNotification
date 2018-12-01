package com.example.zzx.messagenotification

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.TextView
import java.lang.ref.SoftReference

class MessageViewControlor  {

    private val TAG = "MessageViewControlor"

    private fun getViewRect(view: View, parent: ViewGroup): Rect {
        val rect = Rect()
        DescendantOffsetUtils.getDescendantRect(parent as ViewGroup, view, rect)
        Log.e(TAG, rect.toString())
        return rect
    }

    private fun createColorState(normal: Int): ColorStateList {
        val colors = intArrayOf(0, 0, normal, 0, 0, normal)
        val states = arrayOfNulls<IntArray>(6)
        states[0] = intArrayOf(android.R.attr.state_pressed, android.R.attr.state_enabled)
        states[1] = intArrayOf(android.R.attr.state_enabled, android.R.attr.state_focused)
        states[2] = intArrayOf(android.R.attr.state_enabled)
        states[3] = intArrayOf(android.R.attr.state_focused)
        states[4] = intArrayOf(android.R.attr.state_window_focused)
        states[5] = intArrayOf()
        return ColorStateList(states, colors)
    }

    fun displayMessage(messageString: String, window: Window, targetView: View, displayView: SoftReference<TextView>?): SoftReference<TextView> {
        if (displayView?.get() == null) {
            return SoftReference(createMessageViewAndDiaplay(messageString, window, targetView))
        }
        displayView.get()!!.text = messageString
        return displayView
    }

    private fun createMessageViewAndDiaplay(messageString: String, window: Window, targetView: View): TextView {
        val addTv = TextView(targetView.context)
        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        addTv.text = messageString
        addTv.textSize = 12.0F
        addTv.setTextColor(Color.parseColor("#FFFFFF"))
        addTv.layoutParams = layoutParams
        addTv.setPadding(5, 0, 5, 0)
        addTv.visibility = View.GONE
        (window.decorView as ViewGroup).addView(addTv)
        addTv.post {
            val rect = getViewRect(targetView, window.decorView as ViewGroup)
            val drawable = GradientDrawable()
            drawable.cornerRadius = addTv.height / 2f
            drawable.colors = intArrayOf(ContextCompat.getColor(targetView.context, R.color.colorAccent), 0)
//            drawable.color = createColorState(ContextCompat.getColor(targetView.context, R.color.colorAccent))
//            drawable.c = ContextCompat.getColor(this, R.color.colorAccent)
//        layoutParams.gravity = Gravity.CENTER
            addTv.background = drawable
            Log.e(TAG, "width = ${addTv.width}")
            Log.e(TAG, "height = ${addTv.height}")
            layoutParams.leftMargin = rect.right - addTv.width / 2
            layoutParams.topMargin = rect.top - addTv.height / 2
            addTv.layoutParams = layoutParams
            addTv.visibility = View.VISIBLE
        }
        return addTv
    }

}