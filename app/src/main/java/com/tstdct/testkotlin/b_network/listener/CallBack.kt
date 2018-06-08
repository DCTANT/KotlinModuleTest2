package com.tstdct.testkotlin.b_network.listener

import com.tstdct.testkotlin.b_network.reponse.base.W_Base

/**
 * Created by Dechert on 2/24/2018.
 * Company: www.chisalsoft.co
 */
abstract class CallBack<T:W_Base> {
    abstract fun onSuccess(w:T)

    fun onFail(info:String){

    }
}