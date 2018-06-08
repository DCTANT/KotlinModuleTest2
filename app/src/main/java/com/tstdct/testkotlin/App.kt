package com.tstdct.testkotlin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.Environment
import android.support.multidex.MultiDexApplication
import android.util.Log
import com.google.gson.GsonBuilder
import com.libs.util.crash.CrashHandler
import java.util.*

/**
 * Created by Dechert on 2018-02-22.
 * Company: www.chisalsoft.co
 */
class App : MultiDexApplication(), Application.ActivityLifecycleCallbacks {
    private val activityList = LinkedList<Activity>()
    val FILE_PATH = Environment.getExternalStorageDirectory().path + "/ClearNotepad/"
    val CATALOG = FILE_PATH + "Catalog.txt"
    val gson = GsonBuilder().create()

    override fun onCreate() {
        super.onCreate()
        registerActivityLifecycleCallbacks(this)
        Log.i("app", "FILE_PATH:" + FILE_PATH)
        var file = externalCacheDir
        if (!file.exists()) {
            file.mkdirs()
        }
        CrashHandler.getInstance(gson,this).init(this)
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityResumed(activity: Activity?) {
    }

    override fun onActivityStarted(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activityList.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        if (activity != null) {
            activityList.add(activity)
        }
    }

    override fun onTerminate() {
        if (activityList.size > 0) {
            for (activity in activityList) {
                activity.finish()
            }
            activityList.clear()
        }
        super.onTerminate()
    }

    fun<T:Activity> getActivity(cls: Class<T>): T? {
        for (activity in activityList) {
            if (cls.simpleName==activity::class.java.simpleName ) {
                return activity as T
            }
        }
        return null
    }

    fun getActivityList(): LinkedList<Activity> {
        return activityList
    }

}