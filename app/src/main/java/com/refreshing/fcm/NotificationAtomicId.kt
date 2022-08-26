package com.refreshing.fcm

import java.util.concurrent.atomic.AtomicInteger

object NotificationAtomicId {
    private val c = AtomicInteger(0)
    fun getAtomicId(): Int {
        return c.incrementAndGet()
    }
}