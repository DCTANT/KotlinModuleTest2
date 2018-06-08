package com.tstdct.testkotlin.e_utils.extend

import java.io.*
import java.nio.charset.Charset
import java.util.*


/**
 * Created by Dechert on 2018-06-05 15:23:10.
 * Company: www.chisalsoft.com
 * Usage:
 */
fun File.readContent(): String {
    val readSize = this.length().toInt()
    if (!this.exists()) {
        fileNotFound()
        return ""
    }
    val fileInputStream = FileInputStream(this)
    val byteArray = ByteArray(readSize)
    val stringBuffer = StringBuffer()
    val bufferedInputStream = BufferedInputStream(fileInputStream)
    bufferedInputStream.buffered(64 * 1024)
    while (true) {
        val read = bufferedInputStream.read(byteArray)
        println("读取的长度为$read")
        if (read == -1) {
            break
        }
        stringBuffer.append(String(byteArray, Charset.forName("UTF-8")))
    }
    bufferedInputStream.close()
    fileInputStream.close()
    return stringBuffer.toString()
}

fun File.readToByteArray(): ByteArray {
    val readSize = this.length().toInt()
    if (!this.exists()) {
        fileNotFound()
        return ByteArray(0)
    }
    val fileInputStream = FileInputStream(this)
    val byteArray = ByteArray(readSize)
    val bufferedInputStream = BufferedInputStream(fileInputStream)
    bufferedInputStream.buffered(64 * 1024)
    while (true) {
        val read = bufferedInputStream.read(byteArray)
        println("读取的长度为$read")
        if (read == -1) {
            break
        }
    }
    bufferedInputStream.close()
    fileInputStream.close()
    return byteArray
}

private fun fileNotFound() {
    println("文件没有找到！！！！！")
}

fun File.outPutTextFile(path: String, content: String) {
    val outPutString = String(content.toByteArray(Charset.forName("UTF-8")))
    createPath(path)
    val outPutFile = File(path)
    if (!outPutFile.exists()) {
        outPutFile.createNewFile()
    }
    val fileOutputStream = FileOutputStream(outPutFile)
    val bufferedOutputStream = BufferedOutputStream(fileOutputStream)
    val toByteArray = outPutString.toByteArray(Charset.defaultCharset())
    bufferedOutputStream.write(toByteArray, 0, toByteArray.size)
    bufferedOutputStream.flush()
    bufferedOutputStream.close()
    fileOutputStream.close()
}

fun File.outPutByteFile(path: String, content: ByteArray) {
    createPath(path)
    val outPutFile = File(path)
    if (!outPutFile.exists()) {
        outPutFile.createNewFile()
    }
    val fileOutputStream = FileOutputStream(outPutFile)
    val bufferedOutputStream = BufferedOutputStream(fileOutputStream)
    bufferedOutputStream.write(content, 0, content.size)
    bufferedOutputStream.flush()
    bufferedOutputStream.close()
    fileOutputStream.close()
}

fun File.createPath(path: String) {
    val index = path.lastIndexOf("/")
    val dirPath = path.substring(0, index + 1)
    val dirs = File(dirPath)
    if (!dirs.exists()) {
        dirs.mkdirs()
    }
}

fun File.deleteFile() {
    if (!this.exists()) {
        fileNotFound()
        return
    }
    if (this.isFile) {
        this.delete()
    } else {
        val list = this.list()
        if (list.size == 0) {
            this.delete()
        } else {
            for (filePath in list) {
                val file = File(filePath)
//                deleteFile(file.absolutePath)
                file.deleteFile()
            }
        }
    }

}

/**
 * 为了彻底删除文件夹准备的，单纯使用deleteFile无法删除文件夹，只能删光文件夹中所有文件
 *
 * @param path
 */
fun File.deleteAllFiles() {
    if (!this.exists()) {
        println("deleteFile：文件不存在")
    } else {
        if (this.isFile) {
            this.delete()
        } else if (this.isDirectory) {
            val listFiles = this.listFiles()
            if(listFiles.size==0){
                this.delete()
                return
            }else{
                for (file in listFiles) {
                    if (file.isFile) {
                        file.delete()
                    }else{
                        if(file==null || file.list().size==0){
                            file.delete()
                        }else{
                            file.deleteAllFiles()
                        }
                    }
                }
                this.deleteAllFiles()
            }

        }

    }
}


fun File.copyFile(desPath: String) {
    val desFile = File(desPath)
    if (!this.exists()) {
        println("源文件不存在！！")
        return
    }
    createPath(desPath)
    if (!desFile.exists()) {
        desFile.createNewFile()
    }
    val readContent = readToByteArray()
    outPutByteFile(desPath, readContent)
}

fun File.batchRename(prefix: String, suffix1: String, suffix2: String) {
    if (this.isDirectory) {
        var directoryPath = this.absolutePath
        if (directoryPath.substring(directoryPath.length - 1, directoryPath.length) == "\\") {

        } else {
            directoryPath += "\\"
        }
        var listFiles = this.listFiles()
        var index = 0
        for (i in listFiles.indices) {
            val file = listFiles.get(i)
            if (file.isFile) {
                println("读取的文件的完整路径为：${file.absolutePath}，文件名为：${file.name}")
                var fileName = file.name
                val format = fileName.substring(fileName.lastIndexOf("."), fileName.length)
                file.copyFile(directoryPath + prefix + suffix1 + index + suffix2 + "temp" + Random().nextInt(Int.MAX_VALUE) + format)
                file.delete()
            }
        }
        listFiles = this.listFiles()
        for (i in listFiles.indices) {
            val file = listFiles.get(i)
            if (file.isFile) {
                index++
                println("读取的文件的完整路径为：${file.absolutePath}，文件名为：${file.name}")
                var fileName = file.name
                val format = fileName.substring(fileName.lastIndexOf("."), fileName.length)
                file.copyFile(directoryPath + prefix + suffix1 + index + suffix2 + format)
                file.delete()
            }
        }
    } else {
        println("不接受非文件夹！")
    }

}