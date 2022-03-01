package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.size
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import co.ubunifu.kotlinhttpsample.ListViewModelAdapterPlating_exclude
import co.ubunifu.kotlinhttpsample.ListViewModelAdapterPlating_exclude1
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_plating.*
import kotlinx.android.synthetic.main.activity_plating_exclude.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class plating_exclude: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plating_exclude)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@plating_exclude)
        var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_exclude1>()
        var listViewAdapter = ListViewModelAdapterPlating_exclude(
                this,
                listViewModelArrayList as ArrayList<ListViewModelAdapterPlating_exclude1>
        )
        val mList: List<Map<String, Any>> = java.util.ArrayList()
        try {

            val json = JSONObject()
            var webapiClient = WebapiClient()
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            var jsonString:String?=webapiClient.requestPOST(MainActivity.ip + "PrdMgn/getPlattingInit", json)
            val jsonStr = JSONObject(jsonString)
            val data = StringBuilder()
            val obj = JSONObject(jsonString)
            val array = JSONArray(obj.optString("Data"))
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val MASTER_MFO_ID = jsonObject.getString("MASTER_MFO_ID")
                val batchNo = jsonObject.getString("batchNo")
                val size = jsonObject.getString("size")
                val RTYPE= jsonObject.getString("RTYPE")
                val VAL = jsonObject.getString("VAL")
                val Tol = jsonObject.getString("Tol")
                val Vals = jsonObject.getString("Vals")
                val plattingType = jsonObject.getString("plattingType")
                val FLOW_STEP = jsonObject.getString("FLOW_STEP")
                val splitWeight = " "
                val SID = jsonObject.getString("SID")
                val subflowID = jsonObject.getString("subflowID")
                val OutputQuan = jsonObject.getString("OutputQuan")
                val status="0"
                listViewModelArrayList.add(
                        ListViewModelAdapterPlating_exclude1(
                                0, MASTER_MFO_ID, batchNo, size, RTYPE, VAL, Tol, Vals, plattingType,
                                FLOW_STEP, splitWeight, SID, subflowID, OutputQuan, status
                        )
                )
                listViewAdapter.notifyDataSetChanged()
                exclude_list.adapter = listViewAdapter
                var listViewModelArrayList = ArrayList<ListViewModelAdapterPlating_exclude>()
            }
            cls_bnt.setOnClickListener {
                this.onBackPressed()
            }
            remove_item.setOnClickListener {
                //i in 0 .. list.size - 1list.indices
                for (i in 0 .. exclude_list.size) {
                    if(listViewAdapter.getItem(i).cbxStatus=="1"){
                        listViewAdapter.delItem(i)
                    }
                }
                listViewAdapter.notifyDataSetChanged()
            }
            bnt_sumit_spil.setOnClickListener {
                var error_flag = 0
                var submitjson=""
                for (i in 0 until exclude_list.size) {
                    val submitlist = HashMap<String, String>()
                    submitlist["MASTER_MFO_ID"] = listViewAdapter.getItem(i).MASTER_MFO_ID
                    submitlist["batchNo"] = listViewAdapter.getItem(i).batchNo
                    submitlist["size"] = listViewAdapter.getItem(i).size
                    submitlist["RTYPE"] = listViewAdapter.getItem(i).RTYPE
                    submitlist["VAL"] = listViewAdapter.getItem(i).VAL
                    submitlist["Tol"] = listViewAdapter.getItem(i).Tol
                    submitlist["OutputQuan"] = listViewAdapter.getItem(i).OutputQuan
                    submitlist["Vals"]= listViewAdapter.getItem(i).Vals
                    submitlist["plattingType"] = listViewAdapter.getItem(i).plattingType
                    submitlist["SID"] = listViewAdapter.getItem(i).SID
                    submitlist["subflowID"] = listViewAdapter.getItem(i).subflowID
                    submitlist["FLOW_STEP"] = listViewAdapter.getItem(i).FLOW_STEP
                    submitlist["splitWeight"]=listViewAdapter.getItem(i).splitWeight
                    val gson = Gson()
                    val json = gson.toJson(submitlist)
                    println("PackID===>" + json)
                    println("submitjson===>" + submitjson)
                    if (i>=1) submitjson =submitjson+ ","
                    submitjson += json
                }

                val todayDate = Calendar.getInstance().time
                val formatter = SimpleDateFormat("yyyy-MM-dd")
                val todayString = formatter.format(todayDate)
                val url= MainActivity.ip+"PrdMgn/InputPlattingInit?expectReceive="+todayString+"&userID="+MainActivity.userBar+
                        "&platingJson=["+submitjson+"]"
                println(url)
                var jsonString:String?=webapiClient.requestPOST(url, json)
                val jsonStr = JSONObject(jsonString)
            }
        } catch (ex: Exception) {

        }
}
}
