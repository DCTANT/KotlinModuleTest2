package com.tstdct.testkotlin.z_base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import com.tstdct.testkotlin.R


/**
 * Created by Dechert on 2018-02-12.
 * Company: www.chisalsoft.co
 */
abstract class BaseDialog(context: Context) : Dialog(context, R.style.AppDialog) {
    var dialogView = LayoutInflater.from(context).inflate(loadLayoutRes(), null, false)

    init {
        setGravity(Gravity.CENTER)
        setCanceledOnTouchOutside(true)
        setContentView(dialogView)
    }

    @LayoutRes
    abstract fun loadLayoutRes(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadData(dialogView)
        loadListener(dialogView)
    }

    abstract fun loadListener(dialogView: View)

    abstract fun loadData(dialogView: View)

    fun setGravity(gravity: Int) {
        window.setGravity(gravity)
    }

}