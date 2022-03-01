package com.example.firstohm_produce_kotlin

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.dialog_line.*
import org.json.JSONObject

class Dialogline: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_line, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = 800
        val height = 800
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
        send_line_exit.setOnClickListener {
            dismiss()
        }
        send_line_click_login.setOnClickListener {
            send_msg("無法登入", it)
        }
        send_line_click_logout.setOnClickListener {
            send_msg("無法登出", it)
        }
        send_line_click_start.setOnClickListener {
            send_msg("無法開始", it)
        }
        send_line_click_finsh.setOnClickListener {
            send_msg("無法完成", it)
        }
        send_line_click_report.setOnClickListener {
            send_msg("無法列印工作日報", it)
        }
        send_last_Exception.setOnClickListener {
            try{
                send_msg("其他錯誤Exception已傳送至E-mail", it)
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(it)
                context?.let { ui_Helper.mesage("已傳送", it) };
                context?.let { ui_Helper.send_last_Exception("已傳送") };
            }catch (ex: Exception){

            }
        }
    }

    private fun send_msg(s: String, views: View) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage("是否確認發送")
                .setPositiveButton("是") { dialog, which -> // TODO Auto-generated method stub
                    try {
                    val about_dialog = builder.create()
                    about_dialog.show()
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(views)
                    context?.let { ui_Helper.mesage("已傳送", it) };
                    var t =s+"新版製程"+MainActivity.version+" MAC "+MainActivity.macAddress+
                            " 平板名稱 "+MainActivity.table_custom_name
                    //MainActivity.table_custom_name
                        var webapiClient = WebapiClient()
                        if (MainActivity.userBar == null || MainActivity.userBar == "") MainActivity.userBar = "-"
                        if (MainActivity.EMPNAME == null || MainActivity.EMPNAME == "") MainActivity.EMPNAME = "-"
                        if (MainActivity.dept == null || MainActivity.dept == "") MainActivity.dept = "-"
                        var jsonString: String? = webapiClient.requestPOST(
                                MainActivity.ip + "/PrdMgn/emergencyLine?" +
                                        "UserID=" + MainActivity.userBar +
                                        "&empName=" + MainActivity.EMPNAME +
                                        "&factory=1" +
                                        "&flowStep=" + MainActivity.dept +
                                        "&issue=" + t, JSONObject()
                        )
                        var jsonStr = JSONObject(jsonString)
                    }catch (ex: Exception){
                        Log.d("1", ex.message)
                    }
                }
                .setNegativeButton("否") { dialog, which -> // TODO Auto-generated method stub
                }
        builder.show()
    }
}