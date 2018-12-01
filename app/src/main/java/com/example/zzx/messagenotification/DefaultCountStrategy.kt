package com.example.zzx.messagenotification

class DefaultCountStrategy: CountStrategy {


    override fun format(count: Int): String {
        return when {
            count <= 999 -> count.toString()
            count < 10000 -> (count / 1000).toString() + "k+"
            else -> (count / 10000).toString() + "w+"
        }
    }

    override fun calculate(nodeList: MutableList<Node>): Int {
        var resultCount = 0
        for (node in nodeList) {
            resultCount += node.messageCount
        }
        return resultCount
    }

}