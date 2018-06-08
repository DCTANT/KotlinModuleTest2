package com.tstdct.testkotlin.f_model

/**
 * Created by Dechert on 2018-02-22.
 * Company: www.chisalsoft.co
 */
data class M_Catalog(
        var installTime: Long ?= 0,
        var lastOpenTime: Long ?= 0,
        var catalogList: ArrayList<M_NoteItem>?=ArrayList() ,
        var userName: String ?= "",
        var userId: Long ?= 0
)