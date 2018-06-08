package com.tstdct.testkotlin.e_utils.extend
import java.text.SimpleDateFormat
import java.util.*



/**
 * Created by Dechert on 2018-06-05.
 * Company: www.chisalsoft.co
 */
val Long.detailedFormat: SimpleDateFormat
    get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

val String.detailedFormat: SimpleDateFormat
    get() = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

val Long.onlyDayFormat: SimpleDateFormat
    get() = SimpleDateFormat("yyyy-MM-dd")

val String.onlyDayFormat: SimpleDateFormat
    get() = SimpleDateFormat("yyyy-MM-dd")

private val Long.calendar: Calendar
    get() = Calendar.getInstance()

fun Long.timeStamp2Date(): String {
    return detailedFormat.format(Date(this))
}

fun Long.timeStamp2DayDate(): String {
    return onlyDayFormat.format(Date(this))
}

fun Long.timeStamp2Date(dateFormat: SimpleDateFormat): String {
    return dateFormat.format(Date(this))
}

fun Long.getYear(): Int {
    calendar.time=Date(this)
    return calendar.get(Calendar.YEAR)
}

fun Long.getMonth(): Int {
    calendar.time=Date(this)
    return calendar.get(Calendar.MONTH)+1
}

fun Long.getDay(): Int {
    calendar.time=Date(this)
    return calendar.get(Calendar.DAY_OF_MONTH)
}

fun Long.getHour(): Int {
    calendar.time=Date(this)
    return calendar.get(Calendar.HOUR)
}

fun Long.getMinute(): Int {
    calendar.time=Date(this)
    return calendar.get(Calendar.MINUTE)
}

fun Long.getSecond(): Int {
    calendar.time=Date(this)
    return calendar.get(Calendar.SECOND)
}

fun Long.getAge():Int{
    val birthday = Date(this)
    if(calendar.before(birthday)){
        println("The birthDay is before now.It's unbelievable!")
        return -1
    }
    val nowTimeStamp = System.currentTimeMillis()
    val yearNow = nowTimeStamp.getYear()
    val monthNow = nowTimeStamp.getMonth()
    val dayNow = nowTimeStamp.getDay()
    calendar.time=birthday
    val yearBirth = calendar.get(Calendar.YEAR)
    val monthBirth = calendar.get(Calendar.MONTH)
    val dayOfMonthBirth = calendar.get(Calendar.DAY_OF_MONTH)
    var age = yearNow - yearBirth

    if (monthNow <= monthBirth) {
        if (monthNow === monthBirth) {
            if (dayNow < dayOfMonthBirth) age--
        } else {
            age--
        }
    }
    return age
}

fun String.dateDetailString2TimeStamp(): Long {
    val parse = detailedFormat.parse(this)
    return parse.time
}

fun String.dateString2TimeStamp(simpleDateFormat: SimpleDateFormat): Long {
    val parse = simpleDateFormat.parse(this)
    return parse.time
}

fun Long.dayTimeStamp(): Long {
    val dayString = System.currentTimeMillis().timeStamp2DayDate()
    return dayString.dateString2TimeStamp(onlyDayFormat)
}

fun String.dayTimeStamp(simpleDateFormat: SimpleDateFormat): Long {
    val dateString2TimeStamp = dateString2TimeStamp(simpleDateFormat)
    val dayString = dateString2TimeStamp.timeStamp2DayDate()
    return dayString.dateString2TimeStamp(onlyDayFormat)
}

