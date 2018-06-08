package com.tstdct.testkotlin.z_base

import android.widget.ImageView
import android.widget.TextView

/**
 * Created by Dechert on 2018-02-11.
 * Company: www.chisalsoft.co
 */
interface IBase {
    fun loadLayoutRes():Int
    fun dismissDialog()
    fun showLoading(msg: String)
    fun showLoading(res: Int)
    fun showLoadingNoCancel(msg: String)
    fun showSuccess(msg: String)
    fun showErrorSmallSize(msg: String)
    fun showErrorLargeSize(msg: String)
    fun showErrorLargeSizeTwoLines(msg: String)
    fun showNoNetwork()
    fun showNetworkFail(msg: String)
    fun showDialog(msg: String)
    fun delayFinish()
    fun loading()
    fun getText(textView: TextView):String
    fun toast(msg:String)
    fun toastL(msg:String)
    fun getResColor(res: Int):Int
    fun loadPic(url :String,imageView: ImageView)

}