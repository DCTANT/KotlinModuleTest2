package com.tstdct.testkotlin.e_utils.extend

import com.libs.util.IdCardValidateUtil
import java.util.regex.Pattern

/**
 * Created by Dechert on 2018-06-05 20:04:26.
 * Company: www.chisalsoft.com
 * Usage:
 */
fun String.validateOfPassword(onSuccess: () -> Unit, onError: () -> Unit) {
    val regex = "[!@#$%\\^&*()/\\+\\[\\]\\{\\}, .?;:<>|\\w]{6,20}"
    val isMatch = Pattern.matches(regex, this)
    if (isMatch) {
        onSuccess()
    } else {
        onError()
    }
}

fun String.validateOfPhone(onSuccess: () -> Unit, onError: () -> Unit) {
    val regex = "(\\+86)?[1]\\d{2}\\d{8}"
    val isMatch = Pattern.matches(regex, this)
    if (isMatch) {
        onSuccess()
    } else {
        onError()
    }
}

fun String.validateOfQQ(onSuccess: () -> Unit, onError: () -> Unit) {
    val regex = "[1-9][0-9]{4,}"
    val isMatch = Pattern.matches(regex, this)
    if (isMatch) {
        onSuccess()
    } else {
        onError()
    }
}

fun String.validateOfEmail(onSuccess: () -> Unit, onError: () -> Unit) {
    val regex = "\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*"
    val isMatch = Pattern.matches(regex, this)
    if (isMatch) {
        onSuccess()
    } else {
        onError()
    }
}


fun String.validateOfWebUrl(onSuccess: () -> Unit, onError: () -> Unit) {
    val regex = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$"
    val isMatch = Pattern.matches(regex, this)
    if (isMatch) {
        onSuccess()
    } else {
        onError()
    }
}

fun String.validateOfIdCard(onSuccess: () -> Unit, onError: () -> Unit) {
//    val regex = "^(([hH][tT]{2}[pP][sS]?)|([fF][tT][pP]))\\:\\/\\/[wW]{3}\\.[\\w-]+\\.\\w{2,4}(\\/.*)?$"
    val isMatch = IdCardValidateUtil.getInstance().isIDCard(this);
    if (isMatch) {
        onSuccess()
    } else {
        onError()
    }
}

fun String.validateNumber(onSuccess: () -> Unit, onError: () -> Unit) {
    val regex = "\\d+"
    val isMatch = Pattern.matches(regex, this);
    if (isMatch) {
        onSuccess()
    } else {
        onError()
    }
}

