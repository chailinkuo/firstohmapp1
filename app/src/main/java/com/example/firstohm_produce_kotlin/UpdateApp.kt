package com.example.firstohm_produce_kotlin

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.util.Log
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


abstract class UpdateApp : AsyncTask<String?, Void?, Void?>() {
    private var context: Context? = null
    fun setContext(contextf: Context?) {
        context = contextf
    }

     fun doInBackground(vararg arg0: String): Void? {
        try {
            val url = URL(arg0[0])
            val c: HttpURLConnection = url.openConnection() as HttpURLConnection
            c.setRequestMethod("GET")
            c.setDoOutput(true)
            c.connect()
            val PATH = "/mnt/sdcard/Download/"
            val file = File(PATH)
            file.mkdirs()
            val outputFile = File(file, "update.apk")
            if (outputFile.exists()) {
                outputFile.delete()
            }
            val fos = FileOutputStream(outputFile)
            val `is`: InputStream = c.getInputStream()
            val buffer = ByteArray(1024)
            var len1 = 0
            while (`is`.read(buffer).also { len1 = it } != -1) {
                fos.write(buffer, 0, len1)
            }
            fos.close()
            `is`.close()
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(File("/mnt/sdcard/Download/update.apk")), "application/vnd.android.package-archive")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK // without this flag android returned a intent error!
            context?.startActivity(intent)
        } catch (e: Exception) {
            Log.e("UpdateAPP", "Update error! " + e.message)
        }
        return null
    }
}