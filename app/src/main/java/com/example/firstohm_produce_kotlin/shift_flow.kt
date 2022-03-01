package com.example.firstohm_produce_kotlin

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_shift_layout.*
import kotlinx.android.synthetic.main.dialog_outcheck_ng.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter

class shift_flow : AppCompatActivity() {
    var sourceBar=MainActivity.flowbar
    var targetBar=""
    var L_MASTER_MFO_ID=""
    var R_MASTER_MFO_ID=""
    var sance_flag=1
    var t_flow=JSONObject()
    var jsonStrD=JSONObject()
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_shift_layout)
            this.supportActionBar?.hide()
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            sance_source.setOnClickListener{
                sance_flag=2
                val integrator = IntentIntegrator(this@shift_flow)
                integrator.initiateScan()
            }
            sance_tage.setOnClickListener{
                sance_flag=2
                val integrator = IntentIntegrator(this@shift_flow)
                integrator.initiateScan()
            }
            btn_back.setOnClickListener{
                finish()
            }
            sance1.setOnClickListener {//左邊LAYOUT
                try {
                    var webapiClient = WebapiClient()
                    var url=MainActivity.ip+"PrdMgn/getTiedaiLeftInfo?flowBar="+sourceBar
                    var jsonString:String?=webapiClient.requestPOST( "$url", JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    val rtnData =  JSONArray(jsonStr.getString("Data"))
                    jsonStrD = JSONObject(rtnData[0]?.toString())
                    t_flow= jsonStrD
                    val MASTER_MFO_ID = jsonStrD?.getString("MASTER_MFO_ID")
                    val RTYPE = jsonStrD?.getString("RTYPE")
                    val VAL = jsonStrD?.getString("VAL")
                    val TOL = jsonStrD?.getString("TOL")
                    val SIZE = jsonStrD?.getString("SIZE")
                    val tieDaiQuant = jsonStrD?.getString("tieDaiQuant")
                    val leftQty = jsonStrD?.getString("leftQty")
                    Text_L1.setText("\t\t工令單號$MASTER_MFO_ID".trimIndent())
                    Text_L2.setText("\t\t型號\t\t\t\t\t\t$RTYPE".trimIndent())
                    Text_L3.setText("\t\t阻值\t\t\t\t\t\t$VAL".trimIndent())
                    Text_L4.setText("\t\tTOL\t\t\t\t\t\t\t$TOL".trimIndent())
                    Text_L5.setText("\t\t尺寸\t\t\t\t\t\t$SIZE".trimIndent())
                    Text_L6.setText("\t\t已貼數量\t\t\t\t\t\t$tieDaiQuant".trimIndent())
                    Text_L7.setText("\t\t餘料數量\t\t\t\t\t\t$leftQty".trimIndent())
                }catch (ex: Exception){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@shift_flow)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            sance2.setOnClickListener {  //右邊LAYOUT
                try {
                    var webapiClient = WebapiClient()
                    var url=MainActivity.ip+"PrdMgn/getTiedaiInfo?flowBar="+targetBar
                    var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    val rtnData =  JSONArray(jsonStr.getString("Data"))
                    jsonStrD = JSONObject(rtnData[0]?.toString())
                    val MASTER_MFO_ID = jsonStrD?.getString("MASTER_MFO_ID")
                    R_MASTER_MFO_ID=MASTER_MFO_ID
                    val RTYPE = jsonStrD?.getString("RTYPE")
                    val VAL = jsonStrD?.getString("VAL")
                    val TOL = jsonStrD?.getString("Tol")
                    val SIZE = jsonStrD?.getString("SIZE")
                    val tieDaiQuant = jsonStrD?.getString("tieDaiQuant")
                    val leftQty = jsonStrD?.getString("leftQty")
                    text_R1.setText("\t\t工令單號$MASTER_MFO_ID".trimIndent())
                    text_R2.setText("\t\t型號\t\t\t\t\t\t$RTYPE".trimIndent())
                    text_R3.setText("\t\t阻值\t\t\t\t\t\t$VAL".trimIndent())
                    text_R4.setText("\t\tTOL\t\t\t\t\t\t\t$TOL".trimIndent())
                    text_R5.setText("\t\t尺寸\t\t\t\t\t\t$SIZE".trimIndent())
                    text_R6.setText("\t\t貼帶數量\t\t\t\t\t\t$tieDaiQuant".trimIndent())
                    text_R7.setText("\t\t餘料數量\t\t\t\t\t\t$leftQty".trimIndent())
                }catch (ex: Exception){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@shift_flow)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            btn_Finished_Warehousing.setOnClickListener{
                try {
                    t_flow.put("tieDaiQuant",submit_text1.text.toString())
                    var webapiClient = WebapiClient()
                    var url=MainActivity.ip+"PrdMgn/ScanOperate?" +
                            "command=37&UID="+MainActivity.userBar+
                            "&flowBar="+MainActivity.flowbar+"&DEPT="+ MainActivity.dept+
                            "&jsonStr=" +t_flow.toString()
                    var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    ui_Helper.mesage(jsonStr.getString("Message"), this@shift_flow)
                    //val rtnData =  JSONObject(jsonStr.getString("Data"))
                }catch (ex: Exception){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@shift_flow)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            shiftbnt.setOnClickListener{
                try {
                    val builder = AlertDialog.Builder(this@shift_flow)
                    val count=submit_text1.text.toString()
                    builder.setMessage("是否確認將$L_MASTER_MFO_ID 挪用數量 $count 至$R_MASTER_MFO_ID  ?")
                            .setPositiveButton("是") { dialog, which -> // TODO Auto-generated method stub
                                var webapiClient = WebapiClient()
                                var url=MainActivity.ip+
                                        "PrdMgn/" +
                                        "shiftTiedai?sourceBar=" + sourceBar +
                                        "&targetBar=" + targetBar +
                                        "&shifQuant=" + count + "&empID=" + MainActivity.userBar
                                var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                                val jsonStr = JSONObject(jsonString)
                                ui_Helper.mesage(jsonStr.getString("Message"), this@shift_flow)
                            }
                            .setNegativeButton("否") { dialog, which -> // TODO Auto-generated method stub
                            }
                    val about_dialog = builder.create()
                    about_dialog.show()

                }catch (ex: Exception){
                }
            }
            shiftbnt_out.setOnClickListener{
                try {

                    val builder = AlertDialog.Builder(this@shift_flow)
                    val count=submit_text1.text.toString()
                    builder.setMessage("是否確認將 $R_MASTER_MFO_ID 挪用數量 $count 至$L_MASTER_MFO_ID  ?")
                            .setPositiveButton("是") { dialog, which -> // TODO Auto-generated method stub
                                var webapiClient = WebapiClient()
                                var url=MainActivity.ip+
                                        "PrdMgn/" +
                                        "shiftTiedai?sourceBar=" + targetBar +
                                        "&targetBar=" + sourceBar +
                                        "&shifQuant=" + count + "&empID=" + MainActivity.userBar
                                var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                                val jsonStr = JSONObject(jsonString)
                                ui_Helper.mesage(jsonStr.getString("Message"), this@shift_flow)
                            }
                            .setNegativeButton("否") { dialog, which -> // TODO Auto-generated method stub
                            }
                    val about_dialog = builder.create()
                    about_dialog.show()
                }catch (ex: Exception){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            if (MainActivity.flowbar!=""){
                try {
                    var webapiClient = WebapiClient()
                    var url=MainActivity.ip+"PrdMgn/getTiedaiLeftInfo?flowBar="+MainActivity.flowbar
                    var jsonString:String?=webapiClient.requestPOST(
                            "$url", JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    val Message =  jsonStr.getString("Message")
                    if (Message.indexOf("**")>-1){
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                        ui_Helper.mesage(Message, this@shift_flow)
                    }else{
                        val rtnData =  JSONArray(jsonStr.getString("Data"))
                        val jsonStrD = JSONObject(rtnData[0]?.toString())
                        t_flow= jsonStrD
                        val MASTER_MFO_ID = jsonStrD?.getString("MASTER_MFO_ID")
                        L_MASTER_MFO_ID=MASTER_MFO_ID
                        val RTYPE = jsonStrD?.getString("RTYPE")
                        val VAL = jsonStrD?.getString("VAL")
                        val TOL = jsonStrD?.getString("TOL")
                        val SIZE = jsonStrD?.getString("SIZE")
                        val leftQty = jsonStrD?.getString("leftQty")
                        Text_L1.setText("工令單號\t\t\t\t\t\t$MASTER_MFO_ID".trimIndent())
                        Text_L2.setText("型號\t\t\t\t\t\t$RTYPE".trimIndent())
                        Text_L3.setText("阻值\t\t\t\t\t\t$VAL".trimIndent())
                        Text_L4.setText("TOL\t\t\t\t\t\t\t$TOL".trimIndent())
                        Text_L5.setText("尺寸\t\t\t\t\t\t$SIZE".trimIndent())
                        Text_L6.setText("".trimIndent())
                        submit_text1.setText(leftQty)
                    }

                }catch (ex: Exception){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@shift_flow)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)}
            }
        } catch (ex: Exception) {
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@shift_flow)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                } else {
                    try {
                        val obj = result.getContents()
                        if (obj.indexOf("%") >= -1) {
                            if (sance_flag==1){
                                sourceBar=obj
                                sance1.performClick();
                            }else{
                                targetBar=obj
                                sance2.performClick();
                            }
                        }
                    } catch (e: JSONException) {

                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                        ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@shift_flow)
                        val stacktrace = StringWriter().also { e.printStackTrace(PrintWriter(it)) }.toString().trim()
                        ui_Helper.send_mail(stacktrace)
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }catch (ex: Exception){

            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@shift_flow)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }
}