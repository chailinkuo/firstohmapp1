package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import co.ubunifu.kotlinhttpsample.ListViewModelAdapterPlating_sendList
import co.ubunifu.kotlinhttpsample.ListViewModelAdapterPlating_sendList1
import kotlinx.android.synthetic.main.plating_manual.*
import kotlinx.android.synthetic.main.plating_send.*
import org.json.JSONArray
import org.json.JSONObject

class plating_send: AppCompatActivity() {//送出電鍍
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plating_send)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@plating_send)

    try {

        button8.setOnClickListener {
            finish()
        }
        var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_sendList>()
        var listViewAdapter = ListViewModelAdapterPlating_sendList(
                this,
                listViewModelArrayList as ArrayList<ListViewModelAdapterPlating_sendList1>
        )
        val mList: List<Map<String, Any>> = java.util.ArrayList()
        val json = JSONObject()
        var webapiClient = WebapiClient()
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        var jsonString: String? = webapiClient.requestPOST(MainActivity.ip + "PrdMgn/plattingTobeSent", json)
        val jsonStr = JSONObject(jsonString)
        val data = StringBuilder()
        val obj = JSONObject(jsonString)
        val array = JSONArray(obj.optString("Data"))
        for (i in 0 until array.length()) {
            //"Data": "[{\"supplier\":\"\",\"編號\":\"14-17, 14-18\",\"尺寸\":\"1x3.15, 1x3.15\",
            // \"packQuant\":\"52, 49\",\"送貨日期\":\"08-04\",\"回電日期\":\"12-11\"}]"
            val jsonObject = array.getJSONObject(i)
            val PackID = jsonObject.getString("supplier")
            val dataSource = jsonObject.getString("編號")
            val SRCSIGNID = jsonObject.getString("尺寸")
            val packQuant = jsonObject.getString("packQuant")
            val status = jsonObject.getString("送貨日期")
            val MASTER_MFO_ID = jsonObject.getString("回電日期")
            listViewModelArrayList.add(
                    ListViewModelAdapterPlating_sendList1(
                            0, PackID, dataSource, SRCSIGNID, packQuant, status, MASTER_MFO_ID
                    )
            )
            listViewAdapter.notifyDataSetChanged()
            MatchedList_finsh.adapter = listViewAdapter
            var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_sendList1>()
        }
        btn_send.setOnClickListener {
            var jsonString: String? = webapiClient.requestPOST(MainActivity.ip + "PrdMgn/plattingTobeSent", json)
            val jsonStr = JSONObject(jsonString)
        }
    } catch (ex: Exception) {
    }
    }
}