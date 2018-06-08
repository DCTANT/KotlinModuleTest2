package com.tstdct.testkotlin.b_network.netutil

import com.tstdct.testkotlin.b_network.reponse.base.W_Base
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Created by Dechert on 2/24/2018.
 * Company: www.chisalsoft.co
 */
open class BaseObserver<T : W_Base> : Observer<T> {
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(w: T) {
        if (w.result == "success") {
            onSuccess(w)
        } else {
            onFail(w.info)
        }

    }

    open internal fun onFail(info: String) {

    }

    open internal fun onSuccess(w: T) {

    }

    override fun onError(e: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}