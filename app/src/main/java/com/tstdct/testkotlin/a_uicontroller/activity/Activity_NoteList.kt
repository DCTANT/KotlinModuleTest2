package com.tstdct.testkotlin.a_uicontroller.activity

import android.Manifest
import android.app.Activity
import android.content.ClipboardManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.tstdct.testkotlin.z_base.AppBaseCompatActivity
import com.libs.util.FileUtil
import com.libs.util.TimeUtil
import com.tstdct.testkotlin.R
import com.tstdct.testkotlin.b_network.api.AnnouncementApi
import com.tstdct.testkotlin.b_network.netutil.ApiManager
import com.tstdct.testkotlin.b_network.netutil.listener.Callback
import com.tstdct.testkotlin.b_network.reponse.W_AnnouncementList
import com.tstdct.testkotlin.c_constant.S_Extra
import com.tstdct.testkotlin.f_model.M_Catalog
import com.tstdct.testkotlin.f_model.M_NoteContent
import com.tstdct.testkotlin.f_model.M_NoteItem
import com.tstdct.testkotlin.f_model.M_NoteLine
import com.tstdct.testkotlin.z_base.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_note_list.*
import kotlinx.android.synthetic.main.activity_note_list.view.*
import kotlinx.android.synthetic.main.item_note_item.view.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Exception


/**
 * Created by Dechert on 2018-02-22.
 * Company: www.chisalsoft.co
 */
class Activity_NoteList : AppBaseCompatActivity() {
    override fun loadLayoutRes(): Int {
        return R.layout.activity_note_list
    }


    var noteAdapter: RecyclerAdapter<M_NoteItem>? = null
    var dataList: ArrayList<M_NoteItem>? = null
    lateinit var catelogFile: File
    var maxIndex = 0


    override fun initVariable() {
        catelogFile = File(app.CATALOG)
        initRv()
        createFileDirs()
        testNetwork()
//        testCrash()
//        jump()
//        jump2()
    }

    private fun testCrash() {
        var crash = IntArray(5)
        log("crash:" + crash[9])
    }

    private fun testNetwork() {
        ApiManager.transfer((ApiManager.form(AnnouncementApi::class.java).getRegularBusDetail(5, 6)), object : Callback<W_AnnouncementList>() {
            override fun onSuccess(w: W_AnnouncementList) {
                log(w.toString())
            }

        }, this)
    }

    private fun jump2() {
        val tvCopy = activity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        tvCopy.text = "https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MjM5MDA2NjM5OA==&scene=124#wechat_redirect"
        try {
            var intent = Intent(Intent.ACTION_MAIN)
            val cmp = ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI")
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setData(Uri.parse("https://mp.weixin.qq.com/mp/profile_ext?action=home&__biz=MjM5MDA2NjM5OA==&scene=124#wechat_redirect"))
            intent.component = cmp
            startActivity(intent)

        } catch (e: Exception) {
            e.printStackTrace()
            activity.showErrorSmallSize("请检查您的手机有没有安装微信")
        }

    }

    private fun jump() {
        var intent = Intent(Intent.ACTION_VIEW)
        var url = "https://www.baidu.com"
        intent.setData(Uri.parse(url))
        intent.setPackage("com.tencent.mm")
        intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent)

    }

    private fun initList() {
        if (!catelogFile.exists()) {
            createBaseDirs()
        }
        var fileInputStream = FileInputStream(catelogFile)
//        var fileBytes= arrayOfNulls<Byte>(catelogFile.length().toInt())
        var fileBytes = ByteArray(catelogFile.length().toInt())
        fileInputStream.read(fileBytes, 0, catelogFile.length().toInt())
        fileInputStream.close()
        var gsonString = String(fileBytes, Charsets.UTF_8)
        log(gsonString)
        var catelog = app.gson.fromJson(gsonString, M_Catalog::class.javaObjectType)
        catelog.lastOpenTime = System.currentTimeMillis()
        FileUtil.outputFile(app.CATALOG, app.gson.toJson(catelog))
        if (catelog.catalogList != null) {
            dataList = catelog.catalogList!!
        } else {
            dataList = ArrayList()
        }
        if (dataList != null && noteAdapter != null) {
            noteAdapter!!.notifyDataChanged(dataList)
            for (m in dataList!!) {
                var temp = m.id.split("_")[1].toInt()
                if (temp >= maxIndex) {
                    maxIndex = temp + 1
                }
            }
        }
    }

    private fun initRv() {
        noteAdapter = object : RecyclerAdapter<M_NoteItem>(context, dataList) {
            override fun loadLayoutRes(): Int {
                return R.layout.item_note_item
            }

            override fun setData(itemView: View, position: Int, m: M_NoteItem) {
                itemView.itemMainTitle.setText(m.mainTitle)
                itemView.itemAbstractTitle.setText(m.abstractTitle)
                itemView.itemCreateTime.setText("创建时间:" + TimeUtil.timeStampToDate(m.createTime))
                itemView.itemLastModifyTime.setText("修改时间:" + TimeUtil.timeStampToDate(m.lastModiftyTime))
                itemView.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        startActivityForResult(Intent(context, Activity_NoteContent::class.java).putExtra(S_Extra.ID, m.id).putExtra(S_Extra.TITLE, m.mainTitle), 102)
                    }

                })
            }
        }
        noteList.adapter = noteAdapter
        noteList.layoutManager = LinearLayoutManager(context)
    }

    override fun loadListener() {
        rootView.add.setOnClickListener({
            startActivityForResult(Intent(context, Activity_NoteContent::class.java).putExtra(S_Extra.INDEX, maxIndex), 103)
        })
        rootView.login.setOnClickListener(View.OnClickListener {
            testCrash()
        })
    }

    private fun createFileDirs() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    101);
        } else {
            initList()
        }
    }

    private fun writeNewFile() {
        var fileOutputStream = FileOutputStream(catelogFile)
        var catelog = M_Catalog()
        catelog.installTime = System.currentTimeMillis()
        catelog.lastOpenTime = System.currentTimeMillis()
//        var catelogList=ArrayList<M_NoteItem>()
        var noteItem = M_NoteItem()
        noteItem.id = "1519282926000_0"
        noteItem.abstractTitle = "欢迎使用简明记事本，这是一个你从未使用过的全新版本"
        noteItem.mainTitle = "欢迎使用"
        noteItem.createTime = 1519282926000
        noteItem.lastModiftyTime = System.currentTimeMillis()
        catelog.catalogList?.add(noteItem)

        var result = String(app.gson.toJson(catelog).toByteArray(Charsets.UTF_8), Charsets.UTF_8)
        fileOutputStream.write(result.toByteArray(Charsets.UTF_8))
        fileOutputStream.flush()
        fileOutputStream.close()

        var detailFileDirs = File(app.FILE_PATH + "1519282926000_0/")
        if (!detailFileDirs.exists()) {
            detailFileDirs.mkdirs()
        }
        var detailFile = File(app.FILE_PATH + "1519282926000_0/1519282926000_0.txt")
        if (!detailFile.exists()) {
            detailFile.createNewFile()
        }
        var noteContent = M_NoteContent("", ArrayList<M_NoteLine>())
        noteContent.id = "1519282926000_0"
        var line = M_NoteLine(0, "", "")
        line.type = M_NoteLine.TEXT
        line.text = "欢迎使用简明记事本，这是一个你从未使用过的全新版本"
        noteContent.contentList.add(line)
        result = String(app.gson.toJson(noteContent).toByteArray(Charsets.UTF_8), Charsets.UTF_8)
        fileOutputStream = FileOutputStream(detailFile)
        fileOutputStream.write(result.toByteArray(Charsets.UTF_8))
        fileOutputStream.flush()
        fileOutputStream.close()

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                createBaseDirs()
                initList()
            } else {
                showErrorSmallSize("该APP无法使用！")
                delayFinish()
            }
        }
    }

    private fun createBaseDirs() {
        var dirs = File(app.FILE_PATH)
        if (!dirs.exists()) {
            dirs.mkdirs()
        }
        if (!catelogFile.exists()) {
            catelogFile.createNewFile()
            writeNewFile()

        } else {

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            initList()
        }
    }
}