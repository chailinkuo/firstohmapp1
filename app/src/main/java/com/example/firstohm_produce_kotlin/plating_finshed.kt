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
import kotlinx.android.synthetic.main.plating_finshed.*
import kotlinx.android.synthetic.main.plating_manual.*
import kotlinx.android.synthetic.main.plating_manual.MatchedList_list
import org.json.JSONArray
import org.json.JSONObject
import java.util.HashMap

class plating_finshed: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plating_finshed)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@plating_finshed)
        try {
            button6.setOnClickListener {
                finish()
            }
            var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_finshed>()
            var listViewAdapter = ListViewModelAdapterPlating_finshed(
                    this,
                    listViewModelArrayList as ArrayList<ListViewModelAdapterPlating_finshed1>
            )
            val mList: List<Map<String, Any>> = java.util.ArrayList()
            val json = JSONObject()
            var webapiClient = WebapiClient()
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            //未完成配對
            var jsonString: String? = webapiClient.requestPOST(MainActivity.ip + "PrdMgn/plattingMatchedList?searchFlag=0", json)
            val jsonStr = JSONObject(jsonString)
            val data = StringBuilder()
            val obj = JSONObject(jsonString)
            val array = JSONArray(obj.optString("Data"))
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val AID = jsonObject.getString("AID")
                val AMater = jsonObject.getString("AMater")
                val AVAL = jsonObject.getString("AVAL")
                val ASIZE = jsonObject.getString("ASIZE")
                val AQUANT = jsonObject.getString("AQUANT")
                val BID = jsonObject.getString("BID")
                val BMater = jsonObject.getString("BMater")
                val BVAL = jsonObject.getString("BVAL")
                val BSIZE = jsonObject.getString("BSIZE")
                val BQUANT = jsonObject.getString("BQUANT")
                val totalQuant = jsonObject.getString("totalQuant")
                val BATCHID = jsonObject.getString("BATCHID")
                listViewModelArrayList.add(
                        ListViewModelAdapterPlating_finshed1(
                                0, "0", AID, AMater, AVAL, ASIZE,AQUANT
                                , BID, BMater, BVAL, BSIZE,
                                BQUANT, totalQuant,BATCHID,"0"
                        )
                )
                listViewAdapter.notifyDataSetChanged()
                MatchedList_list.adapter = listViewAdapter
                var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_MatchedList>()
            }
            unpack.setOnClickListener {
                var submitjson=""
                var packAID=""
                var packBID=""
                var check=0
                for (i in 0 until MatchedList_list.size) {
                    val submitlist = HashMap<String, String>()
                    if(listViewAdapter.getItem(i).cbxStatus=="1"){
                        submitlist["AID"] = listViewAdapter.getItem(i).AID
                        submitlist["BID"] = listViewAdapter.getItem(i).BID
                    }
                    val json = JSONObject()
                    var webapiClient = WebapiClient()
                    val rootView = window.decorView.rootView
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    //完成配對
                    var jsonString: String? = webapiClient.requestPOST(MainActivity.ip
                            + "PrdMgn/plattingUnMatchingList?BatchID="+submitlist["AID"] +","+submitlist["BID"], json)
                    val jsonStr = JSONObject(jsonString)
                }
            }
        } catch (ex: Exception) {
        }

    }
}