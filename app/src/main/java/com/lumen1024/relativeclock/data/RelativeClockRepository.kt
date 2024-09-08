package com.lumen1024.relativeclock.data

import android.content.Context
import com.lumen1024.relativeclock.tools.durationToString
import com.lumen1024.relativeclock.tools.getFromPreferences
import com.lumen1024.relativeclock.tools.instantToStringWithPattern
import com.lumen1024.relativeclock.tools.saveToPreferences
import com.lumen1024.relativeclock.tools.stringToDuration
import com.lumen1024.relativeclock.tools.stringToInstantWithPattern
import java.time.Duration
import java.time.Instant

object RelativeClockRepository {
    private const val WAKE_TIME_KEY = "wake_time"
    private const val OFFSET_TIME_KEY = "offset_time"

    private var cachedWakeTime: Instant? = null
    private var cashedTimeOffset: Duration? = null

    fun getRelativeTime(context: Context): Duration {
        val current = Instant.now()
        val saved = getWakeTime(context) ?:
            return Duration.ZERO
        val offset = getTimeOffset(context) ?: Duration.ZERO

        val result = Duration.between(saved, current) + offset

        if (result > Duration.ofHours(24)) {
            setWakeTime(Instant.now(), context)
            return Duration.ZERO
        } else return result
    }

    fun setWakeTime(time: Instant, context: Context) {
        context.saveToPreferences(WAKE_TIME_KEY, instantToStringWithPattern(time))
        cachedWakeTime = time
    }

    fun getWakeTime(context: Context): Instant? {
        if (cachedWakeTime != null) return cachedWakeTime

        val result = context.getFromPreferences<String>(WAKE_TIME_KEY)
            ?.let { stringToInstantWithPattern(it) }

        if (result != null) cachedWakeTime = result
        return result
    }

    fun setTimeOffset(offset: Duration, context: Context) {
        context.saveToPreferences(OFFSET_TIME_KEY, durationToString(offset))
        cashedTimeOffset = offset
    }

    fun getTimeOffset(context: Context) : Duration? {
        if (cashedTimeOffset != null) return cashedTimeOffset

        val offset = context.getFromPreferences<String>(OFFSET_TIME_KEY)
            ?.let { stringToDuration(it) }

        if (offset != null) cashedTimeOffset = offset
        return offset
    }

}


