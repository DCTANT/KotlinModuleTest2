package com.tstdct.testkotlin.b_network.netutil

import android.util.Log
import com.tstdct.testkotlin.z_base.AppBaseCompatActivity
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.experimental.CoroutineCallAdapterFactory
import com.tstdct.testkotlin.App
import com.tstdct.testkotlin.BuildConfig
import com.tstdct.testkotlin.b_network.netutil.listener.Callback
import com.tstdct.testkotlin.b_network.reponse.base.W_Base
import com.tstdct.testkotlin.c_constant.S_WebInfo
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Dechert on 2/24/2018.
 * Company: www.chisalsoft.co
 */
object ApiManager {
    private val loading = "请稍候……"
    private val app = App()
    private val TAG = ApiManager::class.java.simpleName
    private val MAX_LOG_LENGTH = 4000
    private val retrofit: Retrofit by lazy {
        initRetrofit()
    }
    private val headerInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain?): Response {
            var builder = chain!!.request().headers().newBuilder()
            setInterfaceVersion(builder)
            setSkey(builder)
            var request = chain.request()
                    .newBuilder()
                    .headers(builder.build())
                    .build()
            return chain.proceed(request)
        }

    }

    private fun setSkey(builder: Headers.Builder?) {
        if (builder != null) {
            if (builder.get("skey") == null) {
                builder.set("skey", S_WebInfo.skey)
            }
        } else {
            Log.e(TAG, "builder is null!!")
        }

    }

    private fun setInterfaceVersion(builder: Headers.Builder?) {
        if (builder != null) {
            if (builder.get("interfaceVersion") == null) {
                builder.set("interfaceVersion", "19000101")
            }
        } else {
            Log.e(TAG, "builder is null!!")
        }

    }

    private fun initRetrofit(): Retrofit {
        var okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.addInterceptor(headerInterceptor)
        if (BuildConfig.DEBUG) {
            var httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
                override fun log(message: String?) {
//                    Log.i(TAG,message)
                    if (message != null) {
                        var start=0
                        while(start<message.length){
                            var newline = message.indexOf('\n', start)
                            newline = if (newline != -1) newline else message.length
                            do {
                                var end = Math.min(newline, start + MAX_LOG_LENGTH)
                                Log.i("OkHttp", message.substring(start, end))
                                start = end
                            } while (start < newline)
                        }

                    } else {
                        Log.e(TAG, "Message is null!!")
                    }
                }

            })
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            okHttpClientBuilder.addInterceptor(httpLoggingInterceptor)
        } else {
            Log.i(TAG, "This is release package")
        }
        return Retrofit.Builder().client(okHttpClientBuilder.build())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create(app.gson))
                .baseUrl(S_WebInfo.WebRootUrl)
                .build()
    }


    fun <W> form(api: Class<W>): W {
        return retrofit.create(api)
    }

    fun <W : W_Base> transfer(observable: Observable<W>, callback: Callback<W>, appBaseCompatActivity: AppBaseCompatActivity?) {
        transfer(observable, callback, AndroidSchedulers.mainThread(), appBaseCompatActivity)
    }

    fun <W : W_Base> transfer(observable: Observable<W>, callback: Callback<W>) {
        transfer(observable, callback, null)
    }

    fun <W : W_Base> transfer(observable: Observable<W>, callback: Callback<W>, scheduler: Scheduler?, appBaseCompatActivity: AppBaseCompatActivity?) {
        if (appBaseCompatActivity != null) {
            appBaseCompatActivity.showLoading(loading)
        }
        if (scheduler != null) {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(scheduler)
                    .subscribe(object : BaseObserver<W>() {
                        override fun onSuccess(w: W) {
                            super.onSuccess(w)
                            if (appBaseCompatActivity != null) {
                                appBaseCompatActivity.showSuccess(w.info)
                            }
                            callback.onSuccess(w)
                        }

                        override fun onFail(info: String) {
                            super.onFail(info)
                            if (appBaseCompatActivity != null) {
                                appBaseCompatActivity.showErrorSmallSize(info)
                            }
                            callback.onFail(info)
                        }

                    })
        } else {
            observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(object : BaseObserver<W>() {
                        override fun onSuccess(w: W) {
                            super.onSuccess(w)
                            if (appBaseCompatActivity != null) {
                                appBaseCompatActivity.showSuccess(w.info)
                            }
                            callback.onSuccess(w)
                        }

                        override fun onFail(info: String) {
                            super.onFail(info)
                            if (appBaseCompatActivity != null) {
                                appBaseCompatActivity.showErrorSmallSize(info)
                            }
                            callback.onFail(info)
                        }

                    })
        }

    }
}