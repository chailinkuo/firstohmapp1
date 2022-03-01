package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import co.ubunifu.kotlinhttpsample.*
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_plating_exclude.*
import kotlinx.android.synthetic.main.plating_manual.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class plating_Manual: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plating_manual)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@plating_Manual)
        try {

            button5.setOnClickListener {
                finish()
            }

            var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_MatchedList>()
            var listViewAdapter = ListViewModelAdapterPlating_MatchedList(
                    this,
                    listViewModelArrayList as ArrayList<ListViewModelAdapterPlating_MatchedList1>
            )
            val mList: List<Map<String, Any>> = java.util.ArrayList()
            val json = JSONObject()
            var webapiClient = WebapiClient()
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            //未完成配對
            var jsonString: String? = webapiClient.requestPOST(MainActivity.ip + "PrdMgn/plattingMatchedList?searchFlag=1", json)
            val jsonStr = JSONObject(jsonString)
            val data = StringBuilder()
            val obj = JSONObject(jsonString)
            val array = JSONArray(obj.optString("Data"))
            for (i in 0 until array.length()) {
                //{\"PackID\":\"20201214-20\",\"dataSource\":\"花蓮色碼\",
                // \"SRCSIGNID\":17130,\"combineTo\":null,\"packQuant\":12.8,
                // \"status\":0,\"MASTER_MFO_ID\":\"F-6-MAF1MD\",\"RTYPE\":\"MM204\",
                // \"Val\":\"56K2\",\"Tol\":\"1\",\"Vals\":56200,\"Size\":\"1x3.15\"}
                val jsonObject = array.getJSONObject(i)
                val PackID = jsonObject.getString("PackID")
                val dataSource = jsonObject.getString("dataSource")
                val SRCSIGNID = jsonObject.getString("SRCSIGNID")
                val packQuant = jsonObject.getString("packQuant")
                val status = jsonObject.getString("status")
                val MASTER_MFO_ID = jsonObject.getString("MASTER_MFO_ID")
                val RTYPE = jsonObject.getString("RTYPE")
                val Val = jsonObject.getString("Val")
                val Tol = jsonObject.getString("Tol")
                val Vals = jsonObject.getString("Vals")
                val Size = jsonObject.getString("Size")
                listViewModelArrayList.add(
                        ListViewModelAdapterPlating_MatchedList1(
                                0,"0", PackID, dataSource, SRCSIGNID, RTYPE, packQuant, Tol, Vals, MASTER_MFO_ID,
                                Val, Size
                        )
                )
                listViewAdapter.notifyDataSetChanged()
                MatchedList_list.adapter = listViewAdapter
                var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_MatchedList>()
            }
            btn_pair.setOnClickListener {
                var submitjson=""
                var packAID=""
                var packBID=""
                var check=0
                for (i in 0 until MatchedList_list.size) {
                    val submitlist = HashMap<String, String>()
                    if(listViewAdapter.getItem(i).PackIDc=="1"){
                        submitlist["MASTER_MFO_ID"] = listViewAdapter.getItem(i).MASTER_MFO_ID
                        submitlist["RTYPE"] = listViewAdapter.getItem(i).RTYPE
                        submitlist["Tol"] = listViewAdapter.getItem(i).Tol
                        submitlist["Vals"]= listViewAdapter.getItem(i).Vals
                        submitlist["PackIDc"]= listViewAdapter.getItem(i).PackIDc
                        submitlist["PackID"]= listViewAdapter.getItem(i).PackID
                        check++
                        if (check==1)packAID=listViewAdapter.getItem(i).PackID
                        if (check==2)packBID=listViewAdapter.getItem(i).PackID
                        val gson = Gson()
                        val json = gson.toJson(submitlist)
                        println("PackID===>" + json)
                        println("submitjson===>" + submitjson)
                        if (submitjson!="") submitjson =submitjson+ ","
                        submitjson += json
                    }
                }
                if (check!=2){
                    val builder =      androidx.appcompat.app.AlertDialog.Builder(this@plating_Manual)
                    builder.setMessage("需選擇2個配對")
                    builder.setCancelable(false)
                    builder.setPositiveButton(
                            "確認"
                    ) { dialog, id -> }
                    builder.show()
                }else{
                    val json = JSONObject()
                    var webapiClient = WebapiClient()
                    val rootView = window.decorView.rootView
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    //完成配對
                    var jsonString: String? = webapiClient.requestPOST(MainActivity.ip
                            + "PrdMgn/plattingMenualMatching?" +
                            "packAID="+packAID+"&packBID="+packBID, json)
                    val jsonStr = JSONObject(jsonString)
                }
            }
        } catch (ex: Exception) {
        }
    }
}