package com.example.firstohm_produce_kotlin

import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_more_option_layout.*
import kotlinx.android.synthetic.main.activity_more_option_layout.btn_cow
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter

class more_option: AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_more_option_layout)
            this.supportActionBar?.hide()
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            btn_retun.setOnClickListener {
                finish()
            }
            btn_edit.setOnClickListener {
                startActivity(Intent(this@more_option, editflowActivity::class.java))
            }
            btn_cow.setOnClickListener {
                val builder = androidx.appcompat.app.AlertDialog.Builder(this@more_option)
                builder.setMessage("合作")
                builder.setCancelable(false)
                builder.setPositiveButton("確認") { dialog, id ->
                    MainActivity.cow ="1"
                    val scanIntegrator = IntentIntegrator(this)
                    scanIntegrator.initiateScan()
                }
                builder.setNegativeButton("取消") { dialog, which -> }
                builder.show()
            }
            testwifi.setOnClickListener {

                val rootView = window.decorView.rootView
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                try {
                    var SubflowInfo = CustomLayoutSubflowInfo(this@more_option, null)
                    var webapiClient = WebapiClient()
                    var url = "http://172.168.1.33:1111/firstohmWebApi/ProdFlow/TestConnection"
                    var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    MainActivity.flow_message =jsonStr.getString("Message")
                    if (MainActivity.flow_message.equals("連線成功")){
                        ui_Helper.mesage("連線成功", this@more_option);
                    }else{
                        ui_Helper.mesage("連線失敗請重試", this@more_option);
                    }
                }catch (ex: Exception){
                    ui_Helper.mesage("連線失敗請重試", this@more_option);
                }
            }
            button4.setOnClickListener {
                if(MainActivity.mStatus =="0"){
                    MainActivity.mStatus ="2"
                }else{
                    MainActivity.mStatus ="0"
                }
                if(MainActivity.machineBar ==""){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    ui_Helper.mesage("未選擇機台", this@more_option)
                }else {
                    var webapiClient = WebapiClient()
                    var url = MainActivity.ip +
                            "PrdMgn/ScanOperate?command=10&UID=" + MainActivity.userBar + "&flowBar=" + MainActivity.flowbar +
                            "&DEPT=" + MainActivity.dept + "&MID=" + MainActivity.machineBar + "&mStatus=" + MainActivity.mStatus
                    var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    if (MainActivity.mStatus == "2") {
                        ui_Helper.mesage("已變更${MainActivity.machineBar} 機台為故障中", this@more_option)
                    } else {
                        ui_Helper.mesage("已變更${MainActivity.machineBar} 機台為待機中", this@more_option)
                    }
                }
            }
            btn_line.setOnClickListener {
                Dialogline().show(supportFragmentManager, "MyCustomFragment")
            }
            restartwifi.setOnClickListener {
                wifi_test()
            }
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@more_option)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)}
    }

    private fun wifi_test() {
        try {
            val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
            wifiManager.setWifiEnabled(false);
            Thread.sleep(1000);
            wifiManager.setWifiEnabled(true);
            var count = 0
            while (!wifiManager.isWifiEnabled) {
                if (count >= 10) {
                    Log.i("TAG", "Took too long to enable wi-fi, quitting")
                    return
                }
                Log.i("TAG", "Still waiting for wi-fi to enable...")
                try {
                    Thread.sleep(1000L)
                } catch (ie: InterruptedException) {
                    // continue
                }
                count++
            }
            wifi_test2()
        }catch (ex: Exception){
            Log.d("1", ex.message)
        }
    }
    private fun wifi_test2() {
        Thread.sleep(5000);
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        try {
            var SubflowInfo = CustomLayoutSubflowInfo(this@more_option, null)
            var webapiClient = WebapiClient()
            var url = "http://172.168.1.33:1111/firstohmWebApi/ProdFlow/TestConnection"
            var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
            val jsonStr = JSONObject(jsonString)
            MainActivity.flow_message =jsonStr.getString("Message")
            if (MainActivity.flow_message.equals("連線成功")){
                ui_Helper.mesage("連線成功", this@more_option);
            }else{
                ui_Helper.mesage("連線失敗請重試", this@more_option);
            }
        }catch (ex: Exception){
            ui_Helper.mesage("連線失敗請重試", this@more_option);
        }
    }
}
