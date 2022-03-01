package com.example.firstohm_produce_kotlin

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.example.firstohm_produce_kotlin.MainActivity.Companion.check_out_input
import com.example.firstohm_produce_kotlin.MainActivity.Companion.select_user
import kotlinx.android.synthetic.main.activity_machins_layout.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.dialog_outcheck_1_user.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class Dialogoutcheck_1: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        //外檢下拉式選人 已無使用
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_outcheck_1_user)
        this.supportActionBar?.hide()  //hide title bar
        val name=""
        var array=JSONArray()
        try{
            val midlist = ArrayList<String>()
            var webapiClient = WebapiClient()
            var jsonString:String?=webapiClient.requestPOST(MainActivity.ip +
                    "PrdMgn/getOpertorInfoByDept?flowBar=" +
                    MainActivity.flowbar + "&DEPT=花蓮底漆", JSONObject())
            val jsonStrD = JSONObject(jsonString?.toString())
            array = JSONArray(jsonStrD?.getString("Data"))
            for (i in 0 until array.length()) {
                val itemBooks: MutableMap<String, Any> = HashMap()
                val jsonObject = array.getJSONObject(i)
                val EMPNAME = jsonObject.getString("EMPNAME")
                val USER_ID1 = jsonObject.getString("USER_ID")
                val colorIn = jsonObject.getString("colorIn")
                val outchkCnt = jsonObject.getString("outchkCnt")
                val FLOW_STEP = jsonObject.getString("FLOW_STEP")
                val colorOut = jsonObject.getString("colorOut")
                val nameout = jsonObject.getString("EMPNAME")
            }
            for (i in 0 until array.length()) {
                val jsonObject: JSONObject = array.getJSONObject(i)
                midlist.add(jsonObject.getString("EMPNAME") + " " + jsonObject.getString("colorOut"))

            }
            val arrayAdapter = ArrayAdapter(
                    this@Dialogoutcheck_1,
                    android.R.layout.simple_spinner_dropdown_item,
                    midlist
            )
            user_spinner.adapter = arrayAdapter
            user_spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view1: View, i: Int, l: Long) {
                    val name = user_spinner.getSelectedItem().toString()
                    (this@Dialogoutcheck_1 as Activity).runOnUiThread {
                        val inflater = LayoutInflater.from(this@Dialogoutcheck_1)
                        val v: View = inflater.inflate(R.layout.custom_layout_quant_input, null)
                        v.out_chk_btn.text = user_spinner.getSelectedItem().toString()
                        v.out_chk_btn.invalidate()
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    return
                }
            })
        }catch (ex: Exception){
        }
        btn_user_ok.setOnClickListener{
            try {
                MainActivity.select_user=user_spinner.getSelectedItem().toString()
                select_user=user_spinner.getSelectedItem().toString()
                check_out_input=user_spinner.getSelectedItem().toString()

                for (i in 0 until array.length()) {
                    val itemBooks: MutableMap<String, Any> = HashMap()
                    val jsonObject = array.getJSONObject(i)
                    MainActivity.check_userid = jsonObject.getString("USER_ID")
                }
                val intent = Intent(this, MainActivity::class.java).apply {
                    putExtra(AlarmClock.EXTRA_MESSAGE, select_user)
                }
                startActivity(intent)
                finish();
            }catch (ex: Exception){
                var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                ui_Helper.send_mail(ex.stackTraceToString())
            }
        }

    }
}

private operator fun <E> List<E>.invoke(): List<E> {
    TODO("Not yet implemented")
}

fun <E> List<E>.add(itemBooks: E) {

}


