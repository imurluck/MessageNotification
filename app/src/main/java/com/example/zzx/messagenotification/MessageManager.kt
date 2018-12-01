package com.example.zzx.messagenotification

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.Window
import java.lang.ref.SoftReference

class MessageManager private constructor(){

    val TAG = "MessageManager"

    val KEY_NO_PARENT = "no_parent_key"

    private val viewController = MessageViewControlor()
    private var countStrategy = DefaultCountStrategy()

    val nodeMap: HashMap<String, Node> = HashMap()
    val mainHandler: Handler = object: Handler(Looper.getMainLooper()) {

        override fun handleMessage(msg: Message?) {
            val countMessage = msg!!.obj as CountMessage
            if (!nodeMap.containsKey(countMessage.key)) {
                Log.e(TAG, "no view registered by the key")
                return
            }
            dispatchMessage(countMessage)
        }
    }

    private fun dispatchMessage(countMessage: CountMessage) {
        val node = nodeMap[countMessage.key]!!
        node.messageCount = countMessage.count
        updateNodes(node)
    }

    private fun updateNodes(node: Node) {
        if (node.childList.size > 0) {
            node.messageCount = countStrategy.calculate(node.childList)
        }
        if (node.targetView.get() != null && node.window.get() != null) {
            node.displayView = viewController.displayMessage(countStrategy.format(node.messageCount), node.window.get()!!,
                    node.targetView.get()!!, node.displayView)
        }
        node.parentNode?.let {
            updateNodes(it)
        }
    }

    private fun register(key: String, parentKey: String, wm: Window, targetView: View) {
        if (nodeMap.containsKey(key)) {
            Log.e(TAG, "register -> the key($key) has already registered! be careful!!!")
        }
        val node = Node(key, SoftReference(wm), SoftReference(targetView))
        if (!TextUtils.equals(parentKey, KEY_NO_PARENT)) {
            if (!nodeMap.containsKey(parentKey)) {
                Log.e(TAG, "register -> there is no parentkey($parentKey) registered!")
                return
            }
            val parentNode = nodeMap[parentKey]!!
            removeChildNode(key, parentNode)
            node.parentNode = parentNode
            parentNode.childList.add(node)
        }
        nodeMap[key] = node
    }

    private fun removeChildNode(childKey: String, parentNode: Node) {
        for (childNode in parentNode.childList) {
            if (TextUtils.equals(childNode.key, childKey)) {
                parentNode.childList.remove(childNode)
                break
            }
        }
    }

    private fun unregister(key: String) {
        if (!nodeMap.containsKey(key)) {
            Log.e(TAG, "this key($key) has not registered!")
            return
        }
        val node = nodeMap[key]
        node?.parentNode?.childList?.remove(node)
        Log.i(TAG, "this view with key($key) unregister complete.")
    }

    companion object {
        private val messageManager: MessageManager by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            MessageManager()
        }

        fun register(key: String, wm: Window, targetView: View) {
            messageManager.register(key, messageManager.KEY_NO_PARENT, wm, targetView)
        }

        fun register(key: String, parentKey: String, wm: Window, targetView: View) {
            messageManager.register(key, parentKey, wm, targetView)
        }

        fun unregister(key: String) {
            messageManager.unregister(key)
        }

        fun sendMessage(key: String, count: Int) {
            val countMessage = CountMessage(key, count)
            val message = Message()
            message.obj = countMessage
            messageManager.mainHandler.sendMessage(message)
        }
    }


    class CountMessage(val key: String, val count: Int)
}
