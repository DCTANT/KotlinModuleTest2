package com.tstdct.testkotlin.z_base
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tstdct.testkotlin.App
import com.tstdct.testkotlin.e_utils.extend.TAG

/**
 * Created by Dechert on 2018-02-13.
 * Company: www.chisalsoft.co
 */
abstract class BaseFragment : Fragment(), IBase {
    val defaultActivity: AppBaseCompatActivity by lazy {
        getActivity() as AppBaseCompatActivity
    }
//    val TAG = javaClass.simpleName
    val rootView: View by lazy {
        LayoutInflater.from(context).inflate(loadLayoutRes(), null, false)
    }
    val app: App by lazy {
        defaultActivity.app
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

    }
    var defaultHandler: Handler = Handler()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initVariable()
        loadListener()
        return rootView
    }

    @LayoutRes
    abstract override fun loadLayoutRes(): Int

    abstract fun loadListener()

    abstract fun initVariable()

    override fun onDestroyView() {
        super.onDestroyView()
    }

    fun log(log: String) {
        Log.i(TAG, log)
    }

    override fun dismissDialog() {
        defaultActivity.dismissDialog()
    }

    override fun showLoading(msg: String) {
        defaultActivity.showLoading(msg)
    }

    override fun showSuccess(msg: String) {
        defaultActivity.showSuccess(msg)
    }

    override fun showErrorSmallSize(msg: String) {
        defaultActivity.showErrorSmallSize(msg)
    }

    override fun showErrorLargeSize(msg: String) {
        defaultActivity.showErrorLargeSize(msg)
    }

    override fun showErrorLargeSizeTwoLines(msg: String) {

    }

    override fun showNoNetwork() {
        defaultActivity.showNoNetwork()
    }

    override fun showNetworkFail(msg: String) {
        defaultActivity.showNetworkFail(msg)
    }

    override fun showDialog(msg: String) {
        defaultActivity.showLoading(msg)
    }

    override fun delayFinish() {
        defaultActivity.delayFinish()
    }

    override fun loading() {
        defaultActivity.loading()
    }

    override fun showLoading(res: Int) {
        defaultActivity.showLoading(res)
    }

    override fun showLoadingNoCancel(msg: String) {
        defaultActivity.showLoadingNoCancel(msg)
    }

    override fun getText(textView: TextView): String {
        return textView.text.toString().trim()
    }

    override fun toast(msg: String) {
        Toast.makeText(context,msg, Toast.LENGTH_SHORT).show()
    }

    override fun toastL(msg: String) {
        Toast.makeText(context,msg, Toast.LENGTH_LONG).show()
    }

    override fun getResColor(res: Int): Int {
        return resources.getColor(res)
    }

    fun runOnUiThread(runnable: Runnable) {
        activity.runOnUiThread(runnable)
    }

    fun runOnUiThread(action: () -> Unit) {
        activity.runOnUiThread {
            action()
        }
    }

    override fun loadPic(url: String, imageView: ImageView) {
        Glide.with(context).load(url).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }
}