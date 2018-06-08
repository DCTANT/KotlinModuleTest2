package com.tstdct.testkotlin.e_utils.extend

import java.math.BigDecimal


/**
 * Created by Dechert on 2018-06-05 14:35:30.
 * Company: www.chisalsoft.com
 * Usage:
 */

fun Double.noDecimal(): String {
    return customDecimal(0)
}

fun Double.oneDecimal(): String {
    return customDecimal(1)
}

fun Double.twoDecimal(): String {
    return customDecimal(2)
}

fun Double.customDecimal(scale: Int): String {
    val bigDecimal = BigDecimal(this)
    val scale1 = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP)
    return scale1.toString()
}

fun Float.noDecimal(): String {
    return customDecimal(0)
}

fun Float.oneDecimal(): String {
    return customDecimal(1)
}

fun Float.twoDecimal(): String {
    return customDecimal(2)
}

fun Float.customDecimal(scale: Int): String {
    val bigDecimal = BigDecimal(this.toString())
    val scale1 = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP)
    return scale1.toString()
}