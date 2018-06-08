package com.tstdct.testkotlin.a_uicontroller.dialog

import android.content.Context
import android.view.View
import com.tstdct.testkotlin.R
import com.tstdct.testkotlin.z_base.BaseDialog
import kotlinx.android.synthetic.main.dialog_hint.view.*

/**
 * Created by Dechert on 2/23/2018.
 * Company: www.chisalsoft.co
 */
abstract class Dialog_Hint(context: Context): BaseDialog(context) {
    override fun loadLayoutRes(): Int {
        return R.layout.dialog_hint
    }

    override fun loadListener(v: View) {
       v.cancelBtn.setOnClickListener(View.OnClickListener {
            dismiss()
        })
    }
}