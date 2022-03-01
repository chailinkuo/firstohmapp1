package co.ubunifu.kotlinhttpsample.Lib

import android.util.Log
import android.widget.Toast
import com.example.firstohm_produce_kotlin.MainActivity
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import javax.net.ssl.HttpsURLConnection

class WebapiClient {
    val GET : String = "GET"
    val POST : String = "POST"
    fun httpConnectionPost(apiUrl: String?, params: Map<String?, String?>): String? {
        var conn: HttpURLConnection? = null
        val response = java.lang.StringBuilder()
        try {
            MainActivity.flow_message="";
            val url = URL(apiUrl)
            conn = url.openConnection() as HttpURLConnection
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8")
            conn!!.setRequestProperty("Accept", "application/json")
            conn.setRequestProperty("charset", "utf-8")
            conn.requestMethod = "POST"
            conn.connectTimeout = 10000
            conn.readTimeout = 10000
            conn.doInput = true //允許輸入流，即允許下載
            conn.doOutput = true //允許輸出流，即允許上傳
            conn.useCaches = false //設置是否使用緩存
            /////////////////
            val jsonString: String = getJSONString(params)
            val wr = DataOutputStream(conn.outputStream)
            val writer = BufferedWriter(OutputStreamWriter(wr, "UTF-8"))
            writer.write(jsonString)
            writer.close()
            wr.close()
            /////////////////
            //Get Response
            val `is` = conn.inputStream
            val reader = BufferedReader(InputStreamReader(`is`))
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
                response.append('\r')
            }
            reader.close()

            var json = JSONObject(response.toString())
        } catch (ex: java.lang.Exception) {
            ex.printStackTrace()
        } finally {
            conn?.disconnect()
        }
        return response.toString()
    }

     fun getJSONString(params: Map<String?, String?>): String {
        val json = JSONObject()
        for (key in params.keys) {
            try {
                json.put(key, params[key])
            } catch (ex: java.lang.Exception) {
                ex.printStackTrace()
            }
        }
        return json.toString()
    }
    fun requestPOST(r_url: String?, postDataParams: JSONObject): String? {
        try {
            MainActivity.flow_message=""
            MainActivity.r_url=r_url.toString()
            Log.d("url", r_url)
            val url = URL(r_url)
            Log.d("url", r_url)
            try {
                MainActivity.URL_array.add(r_url.toString())
                if (MainActivity.URL_array.size>3){
                    MainActivity.URL_array.removeAt(0)
                }
            }catch (ex: Exception){

            }
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection
            conn.readTimeout = 13000
            conn.connectTimeout = 13000
            conn.requestMethod = POST
            conn.doInput = true
            conn.doOutput = true
            val os: OutputStream = conn.outputStream
            val writer = BufferedWriter(OutputStreamWriter(os, "UTF-8"))
            writer.write(encodeParams(postDataParams))
            writer.flush()
            writer.close()
            os.close()
            val responseCode: Int = conn.responseCode // To Check for 200
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                val `in` = BufferedReader(InputStreamReader(conn.inputStream))
                val sb = StringBuffer("")
                var line: String? = ""
                while (`in`.readLine().also { line = it } != null) {
                    sb.append(line)
                    break
                }
                `in`.close()
                Log.d("url_response", sb.toString())
                return sb.toString()
            }
            return null
        }catch (ex: Exception){
            return null
        }
    }
    private fun encodeParams(params: JSONObject): String? {
        val result = StringBuilder()
        var first = true
        val itr = params.keys()
        while (itr.hasNext()) {
            val key = itr.next()
            val value = params[key]
            if (first) first = false else result.append("&")
            result.append(URLEncoder.encode(key, "UTF-8"))
            result.append("=")
            result.append(URLEncoder.encode(value.toString(), "UTF-8"))
        }
        return result.toString()
    }
}