package com.tstdct.testkotlin.e_utils.extend

import android.content.Context
import android.content.res.Resources

/**
 * Created by Dechert on 2018-06-05 20:12:52.
 * Company: www.chisalsoft.com
 * Usage:
 */
val Context.density: Float
    get() = resources.displayMetrics.density

fun Context.dip2px(dpValue: Float): Int {
    return (dpValue * density + 0.5f).toInt()
}

fun Context.px2dip(pxValue: Float): Int {
    return (pxValue / density + 0.5f).toInt()
}

fun Context.getScreenWidth(): Int {
    return (resources.displayMetrics.widthPixels + 0.5f).toInt()
}

fun Context.getScreenHeight(): Int {
    return (resources.displayMetrics.heightPixels + 0.5f).toInt()
}

val Int.dp: Int
    get() = (this / Resources.getSystem().displayMetrics.density).toInt()

val Int.px: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()
