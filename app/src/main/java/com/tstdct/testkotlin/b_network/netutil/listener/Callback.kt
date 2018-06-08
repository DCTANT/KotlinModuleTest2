package com.tstdct.testkotlin.b_network.netutil.listener

import com.tstdct.testkotlin.b_network.reponse.base.W_Base

/**
 * Created by Dechert on 2018-02-26.
 * Company: www.chisalsoft.co
 */
abstract class Callback<W:W_Base> {
    abstract fun onSuccess(w:W)
    fun onFail(info :String){

    }
}