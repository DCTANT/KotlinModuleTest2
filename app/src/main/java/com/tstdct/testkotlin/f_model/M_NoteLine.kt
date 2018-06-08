package com.tstdct.testkotlin.f_model

/**
 * Created by Dechert on 2018-02-22.
 * Company: www.chisalsoft.co
 */
data class M_NoteLine(var type:Int=0,var text:String="",var filePath:String="") {
    companion object {
        val TEXT=0
        val PIC=1
        val AUDIO=2
        val MOVIE=3
    }
}