package com.pluu.sample.codeforreadability.model

import java.util.*

// FIXED 14. add data type
class TriggerTreeSet<T>(
    private val treeSet: TreeSet<T>
) {
    private var updater: (() -> Unit)? = null

    fun add(item: T): Boolean {
        val result = treeSet.add(item)
        if (result) {
            invokeTrigger()
        }
        return result
    }

    fun clear() {
        treeSet.clear()
        invokeTrigger()
    }

    fun toList(): List<T> = treeSet.toList()

    fun setListener(listener: (() -> Unit)?) {
        this.updater = listener
    }

    private fun invokeTrigger() {
        updater?.invoke()
    }
}