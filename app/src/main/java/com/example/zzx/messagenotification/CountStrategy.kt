package com.example.zzx.messagenotification

interface CountStrategy {

    fun calculate(nodeList: MutableList<Node>): Int

    fun format(count: Int): String
}