package com.example.firstohm_produce_kotlin

import android.R.attr.name
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_inquire.*
import kotlinx.android.synthetic.main.activity_inquire_views.*
import kotlinx.android.synthetic.main.activity_plating.*
import kotlinx.android.synthetic.main.activity_plating_callback.*
import kotlinx.android.synthetic.main.activity_plating_exclude.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*


class InquireviewsActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inquire_views)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@InquireviewsActivity)
        val json = JSONObject()
        var webapiClient = WebapiClient()
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        btn_back.setOnClickListener {
            finish()
        }
        try {
        if (MainActivity.seach=="1"){
            var jsonString:String?=webapiClient.requestPOST(
                    MainActivity.ip + "PrdMgn/myPrdRecords?userBar=" + MainActivity.userBar, json)
            try {
                val jsonStr = JSONObject(jsonString)
                val data = StringBuilder()
                val obj = JSONObject(jsonString)
                val d: String = obj.getString("Data")
                val separated = d.split("；".toRegex()).toTypedArray()
                val array = JSONArray(separated[1])
                val mylist = ArrayList<String>()
                ////////////////
                for (i in 0 until array.length()) {
                    val jsonObject = array.getJSONObject(i)
                    val myPrd_MASTER_MFO_ID = jsonObject.getString("MASTER_MFO_ID")
                    val myPrd_batch = jsonObject.getString("batch")
                    val myPrd_MachineID = jsonObject.getString("MachineID")
                    val myPrd_InputQuan = jsonObject.getString("InputQuan")
                    val myPrd_OutputQuan = jsonObject.getString("OutputQuan")
                    val myPrd_Tol = if (jsonObject.getString("Tol") == "null") "" else jsonObject.getString("Tol")
                    val myPrd_Val = if (jsonObject.getString("VAL") == "null") "" else jsonObject.getString("VAL")
                    val myPrd_Rtype = if (jsonObject.getString("RTYPE") == "null") "" else jsonObject.getString("RTYPE")
                    val myPrd_StepLeft = jsonObject.getString("StepLeft")
                    var my = """工令單號:$myPrd_MASTER_MFO_ID			流程單號:$myPrd_batch		機台:$myPrd_MachineID
            $myPrd_Val		$myPrd_Tol%		開始量:$myPrd_InputQuan		完成量:$myPrd_OutputQuan		交接量:$myPrd_StepLeft"""
                    mylist.add(my)
                    if (i == array.length() - 1) {
                        mylist.add("  ")
                        mylist.add("  ")
                        mylist.add("  ")
                    }
                }
                val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist)
                list.adapter = itemsAdapter
            } catch (ex: Exception) {
                var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                ui_Helper.send_mail(ex.toString())
            }

        }else if (MainActivity.seach=="2"){
            var jsonString:String?=webapiClient.requestPOST(
                    MainActivity.ip + "PrdMgn/apiGetMachinsByDept?dept=" + MainActivity.dept + "&factoryNo=1", json)
            try {
                val jsonStr = JSONObject(jsonString)
                val data = StringBuilder()
                val obj = JSONObject(jsonString)
                val array = JSONArray(obj.getString("Data"))
                val mylist = ArrayList<String>()
                ////////////////
                for (i in 0 until array.length()) {
                    val jsonObject = array.getJSONObject(i)
                    val myPrd_InputQuan: String = jsonObject.getString("機台號碼")
                    val myPrd_VAL: String = jsonObject.getString("狀態")
                    val myPrd_user: String = jsonObject.getString("操作人")
                    val myPrd_flow: String = jsonObject.getString("工令單號")
                    val myPrd_starttime: String = jsonObject.getString("最後記錄時間")
                    val myPrd_no: String = jsonObject.getString("單號")
                    if (myPrd_VAL == "使用中") {
                        mylist.add("""機台號碼:$myPrd_InputQuan			使用者:$myPrd_user			工令單:$myPrd_flow			流程單號:$myPrd_no
			開始時間:$myPrd_starttime			狀態$myPrd_VAL""")
                        //
                    }
                }
                val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist)
                list.adapter = itemsAdapter
            } catch (ex: Exception) {
            }

        }else if (MainActivity.seach=="3"){
            var jsonString:String?=webapiClient.requestPOST(
                    MainActivity.ip + "PrdMgn/GetFlowTestByStep?flowBar=" + MainActivity.flowbar, json)
            try {
                val jsonStr = JSONObject(jsonString)
                val data = StringBuilder()
                val obj = JSONObject(jsonString)
                val array = JSONArray(obj.getString("Data"))
                val mylist = ArrayList<String>()
                ////////////////
                for (i in 0 until array.length()) {
                    val jsonObject = array.getJSONObject(i)
                    val EMPNAME = jsonObject.getString("EMPNAME")
                    val MachineID = jsonObject.getString("MachineID")
                    val Testset = jsonObject.getString("Testset")
                    val Test_Dept = jsonObject.getString("Dept")
                    val validat_bottom = jsonObject.getString("validat_bottom")
                    val validat_top = jsonObject.getString("validat_top")
                    val testCnt1 = jsonObject.getString("testCnt")
                    val inValidCnt = jsonObject.getString("inValidCnt")
                    val Created = jsonObject.getString("Created")
                    var ExtraInfo = jsonObject.getString("ExtraInfo")
                    var ExtraValue = jsonObject.getString("ExtraValue")
                    var testNote_value = jsonObject.getString("testNote")
                    if (ExtraInfo != "null") {
                        if (ExtraInfo.indexOf("%E9%90%B5%E5%B8%BD") > -1 || ExtraInfo.indexOf("%25E9%2590%25B5") > -1) {
                            ExtraInfo = " 鐵帽尺寸 "
                        } else {
                            ExtraInfo = ""
                            ExtraValue = ""
                        }
                    }
                    testNote_value = if (testNote_value != "null") {
                        if (testNote_value.indexOf("%E5%8A%A0%E5%A3%93%E5%89%8D") > -1) "加壓前" else if (testNote_value.indexOf("%E5%8A%A0%E5%A3%93%E5%BE%8C") > -1) "加壓後" else ""
                    } else {
                        ""
                    }

                    mylist.add("""姓名:$EMPNAME			$Test_Dept:$MachineID			測試次數:$testCnt1
                下限:$validat_bottom				上限:$validat_top			不合格數:$inValidCnt
                測試時間$Created	$ExtraInfo$ExtraValue$testNote_value
                測試數據:$Testset""")

                }
                val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist)
                list.adapter = itemsAdapter

            } catch (ex: Exception) {
                var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                ui_Helper.send_mail(ex.toString())
            }

        }else if (MainActivity.seach=="4"){
            var jsonString:String?=webapiClient.requestPOST(
                    MainActivity.ip + "PrdMgn/myUnfinishedRecords?userBar=" + MainActivity.userBar, json)
            try {
                val jsonStr = JSONObject(jsonString)
                            val data = StringBuilder()
                            val obj = JSONObject(jsonString)
                            val array = JSONArray(obj.getString("Data"))
                            val mylist = ArrayList<String>()
                            ////////////////
                            for (i in 0 until array.length()) {
                                val jsonObject = array.getJSONObject(i)
                                val myPrd_MASTER_MFO_ID = jsonObject.getString("工令單號")
                                val myPrd_MID = jsonObject.getString("機台號碼")
                                val myPrd_ID = jsonObject.getString("型號")
                                val myPrd_VAL = jsonObject.getString("阻值")
                                val myPrd_VOL = jsonObject.getString("阻值範圍")
                                val myPrd_SIZR = jsonObject.getString("尺寸")
                                val myPrd_INPUT = jsonObject.getString("起始量")
                                val myPrd_TIME = jsonObject.getString("製程開始時間")
                                val myPrd_SIGNOD = jsonObject.getString("SIGNID")
                                mylist.add("""工令單號:$myPrd_MASTER_MFO_ID			機台號碼:$myPrd_MID			型號:$myPrd_ID
            阻值:$myPrd_VAL			阻值範圍:$myPrd_VOL			尺寸:$myPrd_SIZR			開始量:$myPrd_INPUT
            開始時間:$myPrd_TIME	ID:$myPrd_SIGNOD""")


                            }
                            val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist)
                            list.adapter = itemsAdapter
            } catch (ex: Exception) {
            }

        }else if (MainActivity.seach=="5"){
            var jsonString:String?=webapiClient.requestPOST(
                    MainActivity.ip + "PrdMgn/prdStepRecords?signID=" + MainActivity.SIGNID, json)
            val jsonStr = JSONObject(jsonString)
            val data = StringBuilder()
            val obj = JSONObject(jsonString)
            val array = JSONArray(obj.getString("Data"))
            val mylist = ArrayList<String>()
            ////////////////
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val myPrd_Start_TIME = jsonObject.getString("Start_TIME")
                val myPrd_Finish_Time = jsonObject.getString("Finish_Time")
                val myPrd_InputQuan = jsonObject.getString("InputQuan")
                val myPrd_EMPNAME = jsonObject.getString("EMPNAME")
                val myPrd_MachineID = jsonObject.getString("MachineID")
                val myPrd_OutputQuan = jsonObject.getString("OutputQuan")
                val myPrd_SIGNID = jsonObject.getString("SIGNID")
                var naiBeiComplete = jsonObject.getString("naiBeiComplete")
                //20200818 flow step
                //20200818 flow step
                val myPrd_flowstep = if (jsonObject.getString("Flow_Step") == "null") "" else jsonObject.getString("Flow_Step")

                var edit_text=""

                if (myPrd_EMPNAME==MainActivity.EMPNAME)
                {
                    edit_text="\t<可修改>"
                }
                if (naiBeiComplete == "null") naiBeiComplete = "0"
                var DefectQuan1 = jsonObject.getString("DefectQuan1")
                if (DefectQuan1 == "null") DefectQuan1 = "0"
                mylist.add("""姓名:$myPrd_EMPNAME			$myPrd_flowstep			$myPrd_MachineID	$edit_text
開始:$myPrd_Start_TIME			完成:$myPrd_Finish_Time
開始量:$myPrd_InputQuan			完成量:$myPrd_OutputQuan			
小批完成量:$naiBeiComplete			ID:$myPrd_SIGNID"""
                )

            }
            val itemsAdapter: ArrayAdapter<String> = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mylist)
            list.adapter = itemsAdapter
        }
            list.setOnItemClickListener(OnItemClickListener { arg0, arg1, position, arg3 ->
                try {
                    val itemValue = list.getItemAtPosition(position) as String
                    val nurl = itemValue.split("ID:".toRegex()).toTypedArray()
                    MainActivity.SIGNID_Edit = nurl[1]
                    Log.d("MainActivity.SIGNID",MainActivity.SIGNID_Edit)

                    if (itemValue.indexOf(MainActivity.EMPNAME)>-1 || MainActivity.ifLeader=="1" )
                    {
                        startActivity(Intent(this@InquireviewsActivity, editflowActivity::class.java))
                    }
                }catch (ex: Exception){
                    Log.d("1", ex.message)
                }
            //Toast.makeText(SuggestionActivity.this, "" + position, Toast.LENGTH_SHORT).show();
            })
    } catch (ex: Exception) {
            var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.send_mail(ex.toString())
    }
    }
}