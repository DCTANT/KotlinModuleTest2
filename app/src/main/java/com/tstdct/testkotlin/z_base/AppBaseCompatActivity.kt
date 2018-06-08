package com.tstdct.testkotlin.z_base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tstdct.testkotlin.App


/**
 * Created by Dechert on 2018-02-11.
 * Company: www.chisalsoft.co
 */
abstract class AppBaseCompatActivity : BaseActivity() {
    var defaultHandler: Handler = Handler()
    val app: App by lazy {
        application as App
    }
    val activity by lazy {
        this
    }
    val rootView: View by lazy {
        LayoutInflater.from(context).inflate(loadLayoutRes(), null, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(rootView)
        requestedOrientation = loadOrientation()
//        StatusBarUtil.transparencyBar(this)//设置为沉浸式页面
        if (!isSoftInputShown()) {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
        initVariable()
        loadListener()
    }

    @LayoutRes
    abstract override fun loadLayoutRes(): Int

    /**
     * 设置activity的方向，例如横向，竖向显示。
     * @return activity方向
     */
    open fun loadOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    }

    open fun setOrientation(orientation: Int) {
        requestedOrientation = orientation
    }

    /**
     * 进入时，当有输入框存在，设置是否需要显示键盘。
     * @return 返回true or false, 默认false
     */
    open fun isSoftInputShown(): Boolean {
        return false;
    }

    abstract fun initVariable()
    abstract fun loadListener()

    override fun loadPic(url: String, imageView: ImageView) {
        Glide.with(context).load(url).skipMemoryCache(false).diskCacheStrategy(DiskCacheStrategy.RESULT).into(imageView)
    }

}