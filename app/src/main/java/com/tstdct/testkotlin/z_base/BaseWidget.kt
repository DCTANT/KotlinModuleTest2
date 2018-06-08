package com.tstdct.androidtokengame.z_base

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View

/**
 * Created by Dechert on 5/9/2018.
 * Company: www.chisalsoft.co
 */
open abstract class BaseWidget : View {
    open lateinit var paint: Paint
    val TAG=javaClass.simpleName

    constructor(context: Context) : super(context) {

    }

    constructor(context: Context, attributeSet: AttributeSet?) : super(context, attributeSet) {
        paint = Paint()
        initVaribles()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {

    }


    abstract fun initVaribles()

    override fun onDraw(canvas: Canvas?) {
        if (canvas != null) {
            val canvasNoNull = canvas!!
//            Log.i("BaseWidget", "提交了AppBaseCompatActivity")
            onDraws(canvasNoNull)
        } else {
            Log.e("BaseWidget", "Canvas is null!!")
        }
        super.onDraw(canvas)
    }

    abstract fun onDraws(canvas: Canvas)

    fun getResColor(resColor:Int): Int {
        return context.resources.getColor(resColor)
    }


}