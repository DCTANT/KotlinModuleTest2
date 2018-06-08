package com.tstdct.testkotlin.e_utils.extend

import android.util.Log


/**
 * Created by Dechert on 2018-06-06 11:44:59.
 * Company: www.chisalsoft.com
 * Usage:
 */
val Any.TAG:String
get() = this::class.java.simpleName

fun Any.log(){
    Log.i(TAG,this.toString())
}

fun Any.log(tag:String){
    Log.i(tag,"${this::class.java}:${this}")
}

inline fun <reified T>T.logT():T{
    Log.i("${this!!.TAG}","${this}")
//    println("${this!!.TAG} : ${this}")
    return this
}

inline fun <reified T>T.logT(tag:String):T{
    Log.i(tag,"${this}")
//    println("${this!!.TAG} : ${this}")
    return this
}

inline fun <reified T>T.logT(tag:String,msg:String):T{
    Log.i(tag,"$msg ,${this}")
    return this
}