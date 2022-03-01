package com.example.firstohm_produce_kotlin

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_setting_layout.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter

class setting: AppCompatActivity() {
    private var context: Context? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_setting_layout)
            this.supportActionBar?.hide()  //hide title bar
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            context = getApplicationContext();
            MainActivity.setting_flag="1"
            restmid.text="將"+MainActivity.machineBar+"設為待機"

            if(MainActivity.ip=="http://172.168.1.33:1111/firstohmWebApi/"){
                radioButton_33.isChecked=true
            }else if(MainActivity.ip=="http://192.168.1.33:1119/firstohmWebApi/"){
                radioButton_19233.isChecked=true
            }else{
                radioButton_151.isChecked=true
            }
            cancel.setOnClickListener {
                finish()
            }
            lasturl_btn.setOnClickListener {
                lasturl_t.setText(MainActivity.URL_array.toString())
            }
            table_custom_name_Edit.setText(MainActivity.table_custom_name)
            table_name.setText("平板ID "+MainActivity.table_name_value)
            mac_t.setText(MainActivity.macAddress)
            restmid.setOnClickListener {
                var webapiClient = WebapiClient()
                var url=MainActivity.ip+
                        "PrdMgn/ScanOperate?command=10&UID=" + MainActivity.userBar + "&flowBar=" + MainActivity.flowbar +
                        "&DEPT=" + MainActivity.dept + "&MID=" + MainActivity.machineBar + "&mStatus=0"
                var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
                val jsonStr = JSONObject(jsonString)
            }
            bnt_Mail.setOnClickListener {
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(it)
                ui_Helper.send_mail("手動發送")
                Toast.makeText(this, "已發送", Toast.LENGTH_LONG).show()

            }
            if (MainActivity.mail.indexOf("1")>-1){
                cb_mail_c004.isChecked=true
            }
            if (MainActivity.mail.indexOf("2")>-1){
                cb_mail_c002.isChecked=true
            }
            cb_mail_clkuo.isChecked=true
            cb_mail_clkuo.setClickable(false);
            save.setOnClickListener {
                MainActivity.table_custom_name=table_custom_name_Edit.text.toString()

                if(facroryNo_1.isChecked==true){
                    MainActivity.facrory="1"
                }else{
                    MainActivity.facrory="2"
                }
                if(input_edit.isChecked==true){
                    MainActivity.input_edit="1"
                }else{
                    MainActivity.input_edit="0"
                }
                if(radio_color_rigth.isChecked==true){
                    MainActivity.color_direction="rigth"
                }else{
                    MainActivity.color_direction="left"
                }
                if(radioButton_33.isChecked==true){
                    MainActivity.ip="http://172.168.1.33:1111/firstohmWebApi/"
                }else if(radioButton_19233.isChecked==true){
                    MainActivity.ip="http://192.168.1.33:1119/firstohmWebApi/"
                }else{
                    MainActivity.ip="http://172.168.1.151:1119/firstohmWebApi/"
                }
                MainActivity.dept=dept_spinner.getSelectedItem().toString()
                var mail =JSONObject()
                //email map
                if(cb_mail_c004.isChecked==true){
                    mail.put("1", "hualien")
                }
                if(cb_mail_c002.isChecked==true){
                    mail.put("2", "HL-WS")
                }
                if(cb_mail_clkuo.isChecked==true){
                    mail.put("3", "chailin.kuo")
                }
                MainActivity.mail=mail.toString()
                try{
                    var sharedPref = this.getPreferences(MODE_PRIVATE);
                    sharedPref.edit().putString("ip", MainActivity.ip).commit()
                    sharedPref.edit().putString("table_custom_name", MainActivity.table_custom_name).commit()
                    sharedPref.edit().putString("mail", mail.toString()).commit()
                    sharedPref.edit().putString("dept", MainActivity.dept).commit()
                    sharedPref.edit().apply()
                }catch (ex: Exception){}
                try{
                    MainActivity.machineBar =""
                    finish()
                }catch (ex: Exception){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            ArrayAdapter.createFromResource(
                    this,
                    R.array.dept,
                    R.layout.obj_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(R.layout.obj_spinner_item)
                dept_spinner.adapter = adapter
            }
            dept_spinner.setSelection((dept_spinner.getAdapter() as ArrayAdapter<String?>).getPosition(MainActivity.dept))
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@setting)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }
}