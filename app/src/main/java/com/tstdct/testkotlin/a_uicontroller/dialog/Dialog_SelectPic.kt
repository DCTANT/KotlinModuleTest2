package com.tstdct.testkotlin.a_uicontroller.dialog

import android.content.Context
import android.view.Gravity
import com.tstdct.testkotlin.R
import com.tstdct.testkotlin.z_base.BaseDialog

/**
 * Created by Dechert on 2018-02-26.
 * Company: www.chisalsoft.co
 */
abstract class Dialog_SelectPic(context: Context): BaseDialog(context) {
    override fun loadLayoutRes(): Int {
        return R.layout.dialog_select_pic
    }

    init {
        setGravity(Gravity.BOTTOM.or( Gravity.CENTER_HORIZONTAL))
    }
}