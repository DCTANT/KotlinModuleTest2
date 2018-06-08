package com.tstdct.testkotlin.z_base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.libs.util.dialog.ProgressDialog
import com.tstdct.testkotlin.e_utils.extend.TAG

/**
 * Created by Dechert on 2018-02-11.
 * Company: www.chisalsoft.co
 */
abstract class BaseActivity : AppCompatActivity(), IBase {
    private val DELAY_FINISH = 2000
    lateinit protected var context: Context
    protected var progressDialog: ProgressDialog? = null
//    protected val TAG = javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context=this
        progressDialog = ProgressDialog(context)
    }

    override fun dismissDialog() {
        if (!isFinishing) {
            progressDialog?.dismiss()
        }
    }

    override fun showLoading(msg: String) {
        if (!isFinishing) {
            progressDialog?.showLoading(msg)
        }
    }

    override fun showLoading(stringRes: Int) {
        showLoading(getString(stringRes))
    }

    override fun showLoadingNoCancel(msg: String) {
        progressDialog?.showLoadingNoCancel(msg)
    }

    override fun showSuccess(msg: String) {
        if (!isFinishing) {
            progressDialog?.showSuccess(msg)
        }
    }

    override fun showErrorSmallSize(msg: String) {
        if (!isFinishing) {
            progressDialog?.showErrorSmallSize(msg)
        }
    }

    override fun showErrorLargeSize(msg: String) {
        progressDialog?.showErrorLargeSize(msg)
    }

    override fun showErrorLargeSizeTwoLines(msg: String) {
        progressDialog?.showErrorLargeSize(msg)
    }

    fun setMesLoading(msg: String) {
        progressDialog?.mes = msg
    }

    override fun showNoNetwork() {
        if (!isFinishing) {
            progressDialog?.showNoNetwork()
        }
    }

    override fun showNetworkFail(msg: String) {
        if (!isFinishing) {
            progressDialog?.showNetworkFail()
        }
    }

    override fun showDialog(msg: String) {
        if (!isFinishing) {
            progressDialog?.show(msg)
        }
    }

//    override fun delayFinish() {
//        delayFinish(null)
//    }

    override  fun delayFinish() {
        Handler().postDelayed(Runnable {
            if(progressDialog!=null){
                if(progressDialog!!.isShowing){
                    dismissDialog()
                }
            }
            finish()
        }, DELAY_FINISH.toLong())
//        Handler().postDelayed({
//            runnable?.run()
//            if (progressDialog?.isShowing!!) {
//                dismissDialog()
//            }
//            finish()
//        }, DELAY_FINISH.toLong())
    }

    override fun onDestroy() {
        if (!isFinishing) {
            progressDialog?.dismiss()
        }
        super.onDestroy()
    }

    fun back(view: View) {
        onBackPressed()
    }

    override fun getText(textView: TextView): String {
        return textView.text.toString().trim()
    }

    override fun getResColor(resId: Int): Int {
        return resources.getColor(resId)
    }

    override fun loading() {
        showLoading("请稍候")
    }

    fun log(log: String) {
        Log.i(TAG, log)
    }

    override fun toast(msg: String) {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }

    override fun toastL(msg: String) {
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
    }

    override fun loadPic(url:String,imageView: ImageView){
        Glide.with(context).load(url).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }
}