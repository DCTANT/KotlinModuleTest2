package com.tstdct.testkotlin.a_uicontroller.activity

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.tstdct.testkotlin.z_base.AppBaseCompatActivity
import com.libs.util.FileUtil
import com.tstdct.testkotlin.R
import com.tstdct.testkotlin.a_uicontroller.dialog.Dialog_Hint
import com.tstdct.testkotlin.a_uicontroller.dialog.Dialog_SelectPic
import com.tstdct.testkotlin.c_constant.S_Extra
import com.tstdct.testkotlin.f_model.M_Catalog
import com.tstdct.testkotlin.f_model.M_NoteContent
import com.tstdct.testkotlin.f_model.M_NoteItem
import com.tstdct.testkotlin.f_model.M_NoteLine
import com.tstdct.testkotlin.z_base.RecyclerAdapter
import com.yalantis.ucrop.model.M_Img
import com.yalantis.ucrop.util.StartGalleryAndCamera
import kotlinx.android.synthetic.main.activity_note_content.*
import kotlinx.android.synthetic.main.activity_note_content.view.*
import kotlinx.android.synthetic.main.dialog_hint.view.*
import kotlinx.android.synthetic.main.dialog_select_pic.view.*
import kotlinx.android.synthetic.main.item_note_line.view.*
import java.io.File
import java.io.FileInputStream

/**
 * Created by Dechert on 2018-02-22.
 * Company: www.chisalsoft.co
 */
open class Activity_NoteContent : AppBaseCompatActivity() {
    override fun loadLayoutRes(): Int {
        return R.layout.activity_note_content
    }

    var id: String? = null
    var title: String? = null
    var index: Int? = null
    var totalData = M_NoteContent("", ArrayList())
    lateinit var dataAdapter: RecyclerAdapter<M_NoteLine>
    var isChanged = false
    var selectedList = ArrayList<M_Img>()
    var startGalleryAndCamera = StartGalleryAndCamera(this, this, selectedList)

    override fun initVariable() {
        initRv()
        id = intent.getStringExtra(S_Extra.ID)
        title = intent.getStringExtra(S_Extra.TITLE)
        index = intent.getIntExtra(S_Extra.INDEX, 0)
        log("id:$id,title:$title,index:$index")
        if (id != null) {
            readFile()
        } else {
            var line = M_NoteLine(0, "", "")
            line.type = M_NoteLine.TEXT
            line.text = ""
            totalData.contentList.add(line)
            dataAdapter.notifyDataSetChanged()
        }
        startGalleryAndCamera.setFlashMode(StartGalleryAndCamera.FLASH_MODE_AUTO)
                .setNeedCrop(false)
                .setNeedPreview(true)
                .setMaxImgs(3)
    }

    private fun readFile() {
        var file = File(app.FILE_PATH + id + "/" + id + ".txt")
        log(file.absolutePath)
        if (!file.exists()) {
            showErrorSmallSize("文件不存在！")
            delayFinish()
        } else {
            var inputStream = FileInputStream(file)
            var bytes = ByteArray(file.length().toInt())
            inputStream.read(bytes, 0, file.length().toInt())
            var content = String(bytes, Charsets.UTF_8)
            totalData = app.gson.fromJson(content, M_NoteContent::class.java)
            log(totalData.toString())
            dataAdapter.notifyDataChanged(totalData.contentList)
        }
        mainTitle.setText(title)
        mainTitle.showText()
    }

    private fun initRv() {
        dataAdapter = object : RecyclerAdapter<M_NoteLine>(context, totalData.contentList) {
            override fun loadLayoutRes(): Int {
                return R.layout.item_note_line
            }

            override fun setData(itemView: View, position: Int, m: M_NoteLine) {
                if (position == totalData.contentList.size - 1) {
                    var layout = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    itemView.rootView.layoutParams = layout
                } else {
                    var layout = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    itemView.rootView.layoutParams = layout
                }
                if (m.type == M_NoteLine.TEXT) {
                    itemView.inputPic.visibility = View.GONE
                    itemView.inputText.visibility = View.VISIBLE
                    itemView.inputText.setText(m.text)
                    onTextChange(itemView, m)

                } else {
                    itemView.inputPic.visibility = View.VISIBLE
                    itemView.inputText.visibility = View.GONE
                    Glide.with(context).load(m.filePath).skipMemoryCache(false).into(itemView.inputPic)
                }
            }

        }
        contentList.adapter = dataAdapter
        contentList.layoutManager = LinearLayoutManager(context)

    }

    private fun onTextChange(v: View, m: M_NoteLine) {
        v.inputText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                m.text = v.inputText.text.toString().trim()
                isChanged = true
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }

    override fun onResume() {
        super.onResume()
        if (id != null) {
            deleteFile.visibility = View.VISIBLE
        } else {
            deleteFile.visibility = View.INVISIBLE
        }
    }

    override fun loadListener() {
        rootView.save.setOnClickListener(View.OnClickListener {
            saveFile()

        })
        rootView.deleteFile.setOnClickListener({
            var hint = object : Dialog_Hint(context) {
                override fun loadData(v: View) {
                    v.confirmBtn.setOnClickListener({
                        deleteFile()
                        dismiss()
                        setResult(Activity.RESULT_OK)
                        showSuccess("删除成功！")
                        delayFinish()
                    })
                }
            }
            hint.show()
        })

        rootView.insertPic.setOnClickListener(View.OnClickListener {
            var dialog = object : Dialog_SelectPic(context) {
                override fun loadListener(dialogView: View) {
                    dialogView.gallery.setOnClickListener(View.OnClickListener {
                        startGalleryAndCamera.startGallery()
                        dismiss()
                    })

                    dialogView.camera.setOnClickListener(View.OnClickListener {
                        startGalleryAndCamera.startCamera()
                        dismiss()
                    })
                }

                override fun loadData(dialogView: View) {

                }

            }
            dialog.show()
        })
    }

    private fun saveFile() {
        if (id == null) {
            var time = System.currentTimeMillis().toString()
            var fileName = time + "_" + index
            var filePath = app.FILE_PATH + fileName + "/" + fileName + ".txt"
            var content = app.gson.toJson(totalData)
            FileUtil.outputFile(filePath, content)
            log("filePath:$filePath")
            var catalog = FileUtil.readFile(File(app.CATALOG))
            var m_catalog = app.gson.fromJson<M_Catalog>(catalog, M_Catalog::class.java)
            var m_NoteItem = M_NoteItem()
            m_NoteItem.createTime = time.toLong()
            m_NoteItem.lastModiftyTime = time.toLong()
            m_NoteItem.id = time + "_" + index
            id = m_NoteItem.id
            val abstractTilte = totalData.contentList.get(0).text
            log("abstractTilte:$abstractTilte")
            if (mainTitle.text.toString().trim().length == 0) {
                m_NoteItem.mainTitle = abstractTilte
            } else {
                m_NoteItem.mainTitle = mainTitle.text.toString().trim()
            }
            m_NoteItem.abstractTitle = abstractTilte
            if (m_catalog.catalogList != null) {
                m_catalog.catalogList!!.add(m_NoteItem)
            }
            FileUtil.outputFile(app.CATALOG, app.gson.toJson(m_catalog))
        } else {
            var content = app.gson.toJson(totalData)
            FileUtil.outputFile(app.FILE_PATH + id + "/" + id + ".txt", content)
            var catalog = FileUtil.readFile(File(app.CATALOG))
            var m_catalog = app.gson.fromJson<M_Catalog>(catalog, M_Catalog::class.java)
            if (m_catalog.catalogList != null) {
                for (m in m_catalog.catalogList!!) {
                    if (m.id.equals(id)) {
                        m.mainTitle = mainTitle.text.toString().trim()
                        m.abstractTitle = totalData.contentList.get(0).text
                        m.lastModiftyTime = System.currentTimeMillis()
                        FileUtil.outputFile(app.CATALOG, app.gson.toJson(m_catalog))
                        break
                    }
                }
            }

        }
        showSuccess("保存成功！")
        isChanged = false
        setResult(Activity.RESULT_OK)
    }

    private fun deleteFile() {
        var catalog = FileUtil.readFile(app.CATALOG)
        var m_catalog = app.gson.fromJson<M_Catalog>(catalog, M_Catalog::class.java)
        if (m_catalog.catalogList != null) {
            for (i in m_catalog.catalogList!!) {
                if (i.id.equals(id)) {
                    m_catalog.catalogList!!.remove(i)
                    FileUtil.outputFile(app.CATALOG, app.gson.toJson(m_catalog))
                    break
                }
            }
        }

        FileUtil.deleteAllFiles(app.FILE_PATH + id + "/")
    }

    override fun onBackPressed() {
        if (isChanged) {
            var dialog = object : Dialog_Hint(context) {
                override fun loadData(dialogView: View) {
                    dialogView.dialogTitle.text = "提示"
                    dialogView.dialogContent.text = "您尚未保存该文件\n确定要退出吗？"
                    dialogView.confirmBtn.setOnClickListener({
                        finish()
                    })
                }
            }
            dialog.show()
        } else {
            super.onBackPressed()
        }
    }

    fun TextView.showText() {
        Log.i("TextView", "这是showText:${text.toString().trim()}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        startGalleryAndCamera.onActivityResult(requestCode, resultCode, data, null)
        log("选择的list的长度为：$selectedList.size")
        refreshList()
    }

    private fun refreshList() {
        for (i in selectedList.indices) {
            var m=selectedList.get(i)
            var desPath = app.FILE_PATH + id + "/" + m.imgName+".jpg"
            log("srcPath:${m.path},desPath:$desPath")
            FileUtil.copyFile(m.path, desPath)
            var noteLine = M_NoteLine()
            noteLine.type = M_NoteLine.PIC
            noteLine.filePath =desPath
            totalData.contentList.add(noteLine)
            var noteLine2=M_NoteLine()
            noteLine2.type=M_NoteLine.TEXT
            noteLine2.text=""
            totalData.contentList.add(noteLine2)
            dataAdapter.notifyDataSetChanged()

        }
        selectedList.clear()
    }
}