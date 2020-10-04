package ru.skillbranch.devintensive.extensions

import java.lang.IllegalStateException
import java.lang.Math.abs
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

enum class TimeUnits {
    SECOND {
        override fun plural(value: Int): String {
            return if (value % 10 == 1 && value != 11) "$value секунда"
            else if (value % 10 == 2 || value % 10 == 3 || value % 10 == 4) "$value секунды"
            else "$value секунд"
        }
    },
    MINUTE {
        override fun plural(value: Int): String {
            return if (value % 10 == 1 && value != 11) "$value минута"
            else if (value % 10 == 2 || value % 10 == 3 || value % 10 == 4) "$value минуты"
            else "$value минут"
        }
    },
    HOUR {
        override fun plural(value: Int): String {
            return if (value % 10 == 1 && value != 11) "$value час"
            else if (value % 10 == 2 || value % 10 == 3 || value % 10 == 4) "$value часа"
            else "$value часов"
        }
    },
    DAY {
        override fun plural(value: Int): String {
            return if ((value % 100 != 11) && (value % 10 == 1)) "$value день"
            else if (value % 10 == 2 || value % 10 == 3 || value % 10 == 4) "$value дня"
            else "$value дней"
        }
    };

    abstract fun plural(value: Int): String
}

fun Date.format(pattern: String = "HH:mm:ss dd.MM.yy"): String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND): Date {
    var time = this.time

    time += when (units) {
        TimeUnits.SECOND -> value * SECOND
        TimeUnits.MINUTE -> value * MINUTE
        TimeUnits.HOUR -> value * HOUR
        TimeUnits.DAY -> value * DAY
        else -> throw IllegalStateException("invalid unit")
    }
    this.time = time
    return this
}

fun Date.humanizeDiff(date: Date = Date()): String {
    var time = this.time - date.time
    return when {
        abs(time) in 0L..1005L -> "только что"
        abs(time) > 1000L && abs(time) < 45L * SECOND -> if (time > 0) "через несколько секунд" else "несколько секунд назад"
        abs(time) > 45L * SECOND && abs(time) < 75L * SECOND -> if (time > 0) "через минуту" else "минуту назад"
        abs(time) > 75L * SECOND && abs(time) < 45L * MINUTE ->  if (time > 0) "через ${TimeUnits.MINUTE.plural((time / MINUTE).toInt())}" else "${TimeUnits.MINUTE.plural((abs(time) / MINUTE).toInt())} назад"
        abs(time) > 45L * MINUTE && abs(time) < 75L * MINUTE -> if (time > 0) "через час" else "час назад"
        abs(time) > 75L * MINUTE && abs(time) < 22L * HOUR -> if (time > 0) "через ${TimeUnits.HOUR.plural((time / HOUR).toInt())}" else "${TimeUnits.HOUR.plural((abs(time) / HOUR).toInt())} назад"
        abs(time) > 22L * SECOND && abs(time) < 26L * HOUR -> if (time > 0) "через день" else "день назад"
        abs(time) > 26L * HOUR && abs(time) < 360L * DAY -> if (time > 0) "через ${TimeUnits.DAY.plural((time / DAY).toInt())}" else "${TimeUnits.DAY.plural((abs(time) / DAY).toInt())} назад"
        abs(time) > 360L * DAY -> if (time > 0) "более чем через год" else "более года назад"
        else -> "ERROR"
    }
}