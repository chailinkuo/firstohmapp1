package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.CalendarView.OnDateChangeListener
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.*
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_edit_flow.*
import kotlinx.android.synthetic.main.activity_plating_exclude.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.plating_finshed.*
import kotlinx.android.synthetic.main.plating_manual.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*


class editflowActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_flow)
        this.supportActionBar?.hide()  //hide title bar
        var date=""
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@editflowActivity)
        var webapiClient = WebapiClient()
        var mfo_id=""
        var BATCH_NO=""
        var VAL=""
        var PPM=""
        var TOL=""
        var InputQuan=""
        var OutputQuan=""
        var DefectQuan=""
        var StepLeft=""
        var BATCH_QTY=""
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        calendarView1.setOnDateChangeListener(OnDateChangeListener { view, year, month, dayOfMonth ->

            try {
                var nmonth = month + 1
                var z1 = ""
                var z2 = ""
                if (nmonth < 10) z1 = "0"
                if (dayOfMonth < 10) z2 = "0"
                date = "$year-$z1$nmonth-$z2$dayOfMonth"
                var url = MainActivity.ip + "PrdMgn/getOpertorInfoByDate?signDate=" + date
                var jsonString: String? = webapiClient.requestPOST(
                        "$url", JSONObject())
                val jsonStr = JSONObject(jsonString)
                val array = JSONArray(jsonStr.getString("Data"))
                val mylist = ArrayList<String>()
                val mid_Array = JSONArray()
                val MachineID_Array = JSONArray()
                var lastuser = ""
                var lastMachineID = ""
                for (i in 0 until array.length()) {
                    val jsonObject = array.getJSONObject(i)
                    if (MainActivity.ifLeader=="1"){
                        //if(mid_Array.indexOf(jsonObject.getString("USER_ID")) > -1)
                        if (lastuser!=jsonObject.getString("USER_ID")){
                            mid_Array.put(jsonObject.getString("USER_ID"))
                        }
                        lastuser = jsonObject.getString("USER_ID")
                    }
                    if (jsonObject.getString("MachineID") != lastMachineID)
                        MachineID_Array.put(jsonObject.getString("MachineID"))
                    lastMachineID = jsonObject.getString("MachineID")
                }
                if (MainActivity.ifLeader!="1"){
                    mid_Array.put(MainActivity.userBar)
                }
                ui_Helper.set_spinner_data(user_spinner, this@editflowActivity, mid_Array, null)
                ui_Helper.set_spinner_data(MachineID_spinner, this@editflowActivity, MachineID_Array, null)

            } catch (ex: Exception) {
                var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                ui_Helper.send_mail(ex.toString())
            }
        })
        MachineID_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    var mid = MachineID_spinner.getSelectedItem().toString()
                    var url=MainActivity.ip +"PrdMgn/getSignsFromUserMachine?signDate="+date+
                        "&userID="+user_spinner.getSelectedItem().toString()+"&machineID="+mid

                    var jsonString:String?=webapiClient.requestPOST(
                        "$url", JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    val array = JSONArray(jsonStr.getString("Data"))
                    val mylist = ArrayList<String>()
                    val SIGNID_Array = JSONArray()
                    var lastuser=""
                    var lastMachineID=""
                    for (i in 0 until array.length()) {
                        val jsonObject = array.getJSONObject(i)
                        if (jsonObject.getString("SIGNID")!=lastuser)
                            SIGNID_Array.put(jsonObject.getString("SIGNID"))
                        lastuser=jsonObject.getString("SIGNID")
                    }
                    ui_Helper.set_spinner_data(SIGNID_spinner, this@editflowActivity, SIGNID_Array, null)
                }catch (ex: Exception){
                    var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.send_mail(ex.toString())
                }
            }
        }
        SIGNID_spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                var url=MainActivity.ip +"PrdMgn/getSignInfo?signID="+SIGNID_spinner.getSelectedItem().toString()


                var jsonString:String?=webapiClient.requestPOST(
                        "$url", JSONObject())
                val jsonStr = JSONObject(jsonString)
                val data2 = JSONArray(jsonStr.getString("Data"))
                val data = JSONObject(data2[0].toString())
                try {
                    mfo_id=data.getString("mfo_id")
                    BATCH_NO=data.getString("BATCH_NO")
                    VAL=data.getString("VAL")
                    PPM=data.getString("PPM")
                    TOL=data.getString("TOL")
                    InputQuan=data.getString("InputQuan")
                    OutputQuan=data.getString("OutputQuan")
                    DefectQuan=data.getString("DefectQuan")
                    StepLeft=data.getString("StepLeft")
                    BATCH_QTY=data.getString("BATCH_QTY")
                    t1.setText("流程單號 " + mfo_id)
                    t2.setText("支數 " + BATCH_NO)
                    t3.setText("VAL " + VAL)
                    t4.setText("PPM " + PPM)
                    t5.setText("TOL " + TOL)
                    edit_InputQuan.setText(InputQuan)
                    edit_Output.setText(OutputQuan)
                    edit_DefectQuan.setText(DefectQuan)
                    edit_StepLeft.setText(StepLeft)
                }catch (ex: Exception){
                    var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.send_mail(ex.toString())
                }
            }
        }
        btn_editflow.setOnClickListener {
            try{
                var url=""
                if (MainActivity.SIGNID_Edit!=""){
                    url = MainActivity.ip + "PrdMgn/reviseSignInfo?signID=" + MainActivity.SIGNID_Edit+
                            "&outputQuan=" + edit_Output.text + "&defectQuan=" + edit_DefectQuan.text + "&note=abcde&userName=" + MainActivity.userBar
                }else{
                    url = MainActivity.ip + "PrdMgn/reviseSignInfo?signID=" + SIGNID_spinner.getSelectedItem().toString() +
                            "&outputQuan=" + edit_Output.text + "&defectQuan=" + edit_DefectQuan.text + "&note=abcde&userName=" + MainActivity.userBar
                }
                var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                val jsonStr = JSONObject(jsonString)
                var msg="已修改工令單"+mfo_id+
                        "開始量"+edit_InputQuan.text+
                        "完成量"+edit_Output.text+
                        "不良量"+edit_DefectQuan.text+
                        "交接量"+edit_StepLeft.text+"製程"+MainActivity.dept+"人員："+MainActivity.userBar
                ui_Helper.send_mail(msg)
                ui_Helper.mesage("修改完成",this@editflowActivity)
            }catch (ex: Exception){
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                ui_Helper.send_mail(stacktrace)
            }
        }
        btn_back.setOnClickListener{finish()}
        if (MainActivity.SIGNID_Edit!=""){
            var url=MainActivity.ip +"PrdMgn/getSignInfo?signID="+MainActivity.SIGNID_Edit

            try {
            var jsonString:String?=webapiClient.requestPOST(
                    "$url", JSONObject())
            val jsonStr = JSONObject(jsonString)
            val data2 = JSONArray(jsonStr.getString("Data"))
            val data = JSONObject(data2[0].toString())

                mfo_id=data.getString("mfo_id")
                BATCH_NO=data.getString("BATCH_NO")
                VAL=data.getString("VAL")
                PPM=data.getString("PPM")
                TOL=data.getString("TOL")
                InputQuan=data.getString("InputQuan")
                OutputQuan=data.getString("OutputQuan")
                DefectQuan=data.getString("DefectQuan")
                StepLeft=data.getString("StepLeft")
                BATCH_QTY=data.getString("BATCH_QTY")
                t1.setText("流程單號 " + mfo_id)
                t2.setText("支數 " + BATCH_NO)
                t3.setText("VAL " + VAL)
                t4.setText("PPM " + PPM)
                t5.setText("TOL " + TOL)
                edit_InputQuan.setText(InputQuan)
                edit_Output.setText(OutputQuan)
                edit_DefectQuan.setText(DefectQuan)
                edit_StepLeft.setText(StepLeft)
            }catch (ex: Exception){
                var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                ui_Helper.send_mail(ex.toString())
            }

        }
    }
    }


