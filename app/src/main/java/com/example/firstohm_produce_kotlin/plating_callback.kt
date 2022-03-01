package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_edit_flow.*
import kotlinx.android.synthetic.main.activity_plating.*
import kotlinx.android.synthetic.main.activity_plating_callback.*
import kotlinx.android.synthetic.main.activity_plating_callback.calendarView1
import kotlinx.android.synthetic.main.activity_plating_callback.view.*
import kotlinx.android.synthetic.main.activity_plating_exclude.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*

class plating_callback : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plating_callback)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@plating_callback)
        val v: View = inflater.inflate(R.layout.activity_plating_callback, null)
        val options_MEGA = JSONArray()
        options_MEGA.put("廠別")
        options_MEGA.put("CS")
        options_MEGA.put("EG")
        options_MEGA.put("JK")
        var date= ""
        btn_back3.setOnClickListener {
            finish()
        }
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        ui_Helper.set_spinner_data(spinnermid, this@plating_callback, options_MEGA, null)
        calendarView1.setOnDateChangeListener(CalendarView.OnDateChangeListener { view, year, month, dayOfMonth ->
            var nmonth = month + 1
            var z1 = ""
            var z2 = ""
            if (nmonth < 10) z1 = "0"
            if (dayOfMonth < 10) z2 = "0"
            date = "$year-$z1$nmonth-$z2$dayOfMonth"
        })
        btn_call_back.setOnClickListener {
            try{
                var mid= spinnermid.getSelectedItem().toString()
                var url_stting = MainActivity.ip + "PrdMgn/plattingCommand?command=2&supplier=" +
                        mid + "&actionDate=" + date
                var webapiClient = WebapiClient()
                var jsonString:String?=webapiClient.requestPOST(url_stting,  JSONObject())

            }catch (ex: Exception){
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@plating_callback)
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                ui_Helper.send_mail(stacktrace)
            }
        }
    }
    }