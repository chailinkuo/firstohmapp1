package com.example.firstohm_produce_kotlin

import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_machins_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class select_Machins : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_machins_layout)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@select_Machins)
        val v: View = inflater.inflate(R.layout.custom_layout_quant_input, null)
        val myButton = arrayOfNulls<Button>(200)
        val myButtonund = arrayOfNulls<Button>(200)
        val bnt_layout = arrayOfNulls<LinearLayout>(20)
        try {
            upmid()
        } catch (ex: Exception) {

        }
        btn_back.setOnClickListener {
            finish()
        }
        bnt_layout[0] = buttonlayout
        bnt_layout[1] = buttonlayout2
        bnt_layout[2] = buttonlayout3
        bnt_layout[3] = buttonlayout4
        bnt_layout[4] = buttonlayout5
        bnt_layout[5] = buttonlayout6
        bnt_layout[6] = buttonlayout7
        bnt_layout[7] = buttonlayout8
        bnt_layout[8] = buttonlayout9
        bnt_layout[9] = buttonlayout10
        bnt_layout[10] = buttonlayout10
        val lp = ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT)
        var j = 0
        var machineBar=""
        var subi=0
        var midv = ""
        val midlist = MainActivity.midlist
try{
    for (i in midlist.indices) {
        if (midlist.get(i) == "new") {
            j++
        } else {
            myButton[i] = Button(this)
            myButton[i]!!.textSize = 16f
            myButton[i]!!.height = 120

            //myButton[i]!!.height = WRAP_CONTENT;
            if (MainActivity.dept.indexOf("全") > -1) {
                myButton[i]!!.width = 150
            } else {
                myButton[i]!!.width = 90
            }
            myButton[i]!!.visibility=View.VISIBLE
            midv = midlist.get(i).toString()
            midv = midv.replace("status0".toRegex(), "")
            midv = midv.replace("status-1".toRegex(), "")
            midv = midv.replace("status1".toRegex(), "")
            midv = midv.replace("status2".toRegex(), "")
            midv = midv.replace("status3".toRegex(), "")
            midv = midv.replace("status4".toRegex(), "")
            midv = midv.replace("statusN".toRegex(), "")
            //machine_status : -1:無機, 0:待機 1:別人生產中  +2:故障  N:NA(不能於本製程使用的機台)
            ////machine_status+2 本人操作, +3:正常運作本人操作本人\ , 4:故障
            if (midlist.get(i).indexOf("status-1") > -1) {
                myButton[i]!!.setBackgroundColor(Color.WHITE)
            } else if (midlist.get(i).indexOf("status0") > -1) {
                //myButton[i].setBackgroundColor(Color.WHITE);
            } else if (midlist.get(i).indexOf("status1") > -1) {
                myButton[i]!!.setBackgroundColor(Color.YELLOW) //非本人使用生產中=1
            } else if (midlist.get(i).indexOf("status2") > -1) { //非本人故障
                myButton[i]!!.setBackgroundColor(Color.RED)
            } else if (midlist.get(i).indexOf("status3") > -1) {
                myButton[i]!!.setBackgroundColor(Color.GREEN) //本人使用=3
                //myButton[i].setEnabled(false);
            } else if (midlist.get(i).indexOf("status4") > -1) { //本人故障
                myButton[i]!!.setBackgroundColor(Color.parseColor("#FF8000"))
            } else if (midlist.get(i).indexOf("statusN") > -1) { //全檢不可使用機台
                myButton[i]!!.setBackgroundColor(Color.parseColor("#a8a7a5"))
                //myButton[i].setEnabled(false);
            } else if (midlist.get(i).indexOf("NULL") > -1) {
                ///myButton[i].setBackgroundColor(Color.WHITE);
                myButton[i]!!.text = "null"
            } else if (midlist.get(i).indexOf("null") > -1) {
                myButton[i]!!.setBackgroundColor(Color.WHITE)
                myButton[i]!!.text = ""
                myButton[i]!!.isEnabled = false
            }

            myButton[i]!!.setPadding(15,15,15,15);
            if (midv != "null") {
                myButton[i]!!.text = midv
            } else {}
            if (midv != "") {
                bnt_layout[j]!!.addView(myButton[i], lp)
            }

            val finalSubi: Int = subi
            myButton[i]!!.setOnClickListener {
                machineBar = myButton[finalSubi]!!.text.toString()
                val separated: Array<String> = machineBar.split("\\s+".toRegex()).toTypedArray()
                //mid 文字處理  切割 ==> o x o - 1
                //              外檢 ==> o o o x
                //              全檢 ==> o x
                if (MainActivity.dept == "花蓮切割") {
                    if (separated[1].indexOf("-") > -1) {
                        machineBar = separated[1]
                    } else if (separated[1].indexOf("-") > -1) {
                        machineBar = separated[1]
                    } else {
                        machineBar = separated[2]
                    }
                } else if (MainActivity.dept == "花蓮外檢") {
                    machineBar = separated[1]
                } else if (MainActivity.dept == "花蓮全檢") {
                    machineBar = separated[1]
                } else {
                    machineBar = separated[1]
                }
                //val midtext = findViewById(R.id.midtext) as TextView
                v.mid_text.visibility = View.VISIBLE
                v.mid_text.text = "\t" + machineBar
                v.mid_spinner.visibility= View.GONE
                MainActivity.machineBar=machineBar
                val v: View = inflater.inflate(R.layout.custom_layout_quant_input, null)
                v.mid_text.text=MainActivity.machineBar
                finish();
            }
        }
        subi++
    }
    }catch (ex: Exception){
    var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
    ui_Helper.send_mail(ex.toString())
    }

    }
    fun upmid() {//更新機台狀態

        var webapiClient = WebapiClient()
        var url=MainActivity.ip+
                "PrdMgn/apiGetMachinList?dept=" + MainActivity.dept+"&factoryNo=1&empID=" +MainActivity.userBar
        var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
        val jsonStr = JSONObject(jsonString)
        val array = JSONArray(jsonStr.getString("Data"))
        val midlist = ArrayList<String>()
        for (j in 0 until array.length()) {
            val Jasonobject: String = array.getString(j) //O
            val array2 = JSONArray(Jasonobject) //o
            for (k in 0 until array2.length()) {
                val ob2 = array2.getJSONObject(k)
                val mid = ob2.getString("mid")
                var attr2 = ob2.getString("attr2")
                val status = ob2.getString("status")
                if (mid == "null") {
                    midlist.add("null")
                } else {
                    //midlist.add(attr2 +"\n"+mid+"status"+status);
                    attr2 = attr2.replace("\\\\".toRegex(), "")
                    attr2 = attr2.replace("n".toRegex(), "  ")
                    midlist.add(attr2 + "  " + mid + "status" + status)
                }
            }
            midlist.add("new")
            MainActivity.midlist=midlist
        }
    }
}