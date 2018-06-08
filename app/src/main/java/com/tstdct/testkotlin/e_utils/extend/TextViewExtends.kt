package com.tstdct.testkotlin.e_utils.extend

import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.tstdct.testkotlin.z_base.AppBaseCompatActivity

/**
 * Created by Dechert on 2018-06-05 18:30:25.
 * Company: www.chisalsoft.com
 * Usage:
 */
val TextView.inChinese: CharRange
    get() {
        return '一'..'龥'
    }
val TextView.inLowerCase: CharRange
    get() {
        return 'a'..'z'
    }
val TextView.inUpperCase: CharRange
    get() {
        return 'A'..'Z'
    }

fun TextView.getTrimText(): String {
    return text.toString().trim()
}

fun TextView.isEmpty(): Boolean {
    return getTrimText().length == 0
}

/**
 * 限制字数
 */
fun TextView.limitWord(limitWord: Int, action: () -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                if (s.length > limitWord) {
                    s.delete(selectionStart - 1, selectionStart)
                    action()
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

/**
 * 限制字数 具体实现
 */
fun TextView.limitWord(limitWord: Int, activity: AppBaseCompatActivity, tipString: String) {
    limitWord(limitWord, {
        tip(activity, tipString)
    })
}

/**
 * 限制小数位数
 */
fun TextView.limitDecimal(limit: Int, action: () -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                val input = s.toString().trim()
                val dot = input.indexOf(".")
                if (dot != -1) {
                    if ((input.length - dot - 1) > limit) {
                        s.delete(selectionStart - 1, selectionStart)
                        action()
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

/**
 * 限制小数位数 具体实现
 */
fun TextView.limitDecimal(limit: Int, activity: AppBaseCompatActivity, tipString: String) {
    limitDecimal(limit, {
        tip(activity, tipString)
    })
}


fun TextView.onTextChange(action: (length: Int, content: String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                val content = s.toString().trim()
                val length = s.toString().trim().length
                action(length, content)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

fun TextView.limitInputContent(action: () -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (s != null) {
                val content = s.toString()
                for (c in content) {
                    if (c in inChinese || c in inLowerCase || c in inUpperCase) {

                    } else {
                        s.delete(selectionStart - 1, selectionStart)
                        action()
                    }
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    })
}

fun TextView.limitInputContent(activity: AppBaseCompatActivity, tipString: String) {
    limitInputContent {
        tip(activity, tipString)
    }
}


private fun tip(activity: AppBaseCompatActivity, tipString: String) {
    var isTip = false
    if (!isTip) {
        isTip = true
        activity.showErrorSmallSize(tipString)
        Handler().postDelayed(Runnable { isTip = false }, 2000)
    }
}
