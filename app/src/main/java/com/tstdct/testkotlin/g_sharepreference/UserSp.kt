package com.tstdct.testkotlin.g_sharepreference

import android.content.Context
import com.google.gson.GsonBuilder
import com.tstdct.testkotlin.f_model.M_User

/**
 * Created by Dechert on 3/21/2018.
 * Company: www.chisalsoft.co
 */
class UserSp {
    companion object {
        private val SP_NAME = "USER"
        private val USER_LIST = "USER_LIST"

        fun read(context: Context): M_User {
            val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            var gson=GsonBuilder().create()
            return gson.fromJson(sharedPreferences.getString(USER_LIST, "{}"), M_User::class.java)
        }

        fun write(context: Context,userList: M_User){
            val sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
            var editor=sharedPreferences.edit()
            var gson=GsonBuilder().create()
            editor.putString(USER_LIST,gson.toJson(userList))
            editor.apply()
        }
    }

}