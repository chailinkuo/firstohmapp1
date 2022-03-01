package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_info.*
import kotlinx.android.synthetic.main.custom_layout_info.view.*
import kotlinx.android.synthetic.main.dialog_semidef.*
import kotlinx.android.synthetic.main.dialog_spilflow_list.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class Dialoglist: DialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_spilflow_list, container, false)
    }
    var listViewModelArrayList = ArrayList<ListViewModelAdapterflowspile1>()
    override fun onStart() {
        super.onStart()
        button11.setOnClickListener {
            getDialog()?.dismiss()
        }
        try{
            var listViewAdapter = context?.let {
                ListViewModelAdapterflowspile(
                        it,
                        listViewModelArrayList as ArrayList<ListViewModelAdapterflowspile1>
                )
            }
            var webapiClient = WebapiClient()
            var url = MainActivity.ip+"PrdMgn/ScanOperate?command=32&UID=B_004&flowBar="+MainActivity.flowbar+"&DEPT="+MainActivity.dept
            var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
            val obj = JSONObject(jsonString)
            val array = JSONArray(obj.optString("Data"))
            listViewModelArrayList.add(
                    ListViewModelAdapterflowspile1(
                            1, "流程單號", "支數", "開始時間", "", "拆單部門", "", "短碼"
                    )
            )
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val SUBFLOWID = jsonObject.getString("SUBFLOWID")
                val BATCH_QTY = jsonObject.getString("BATCH_QTY")
                var DONE_QTY = jsonObject.getString("START_TIME")
                val parentID = jsonObject.getString("parentID")
                val splitStep = jsonObject.getString("splitStep")
                val splitStatus = jsonObject.getString("splitStatus")
                val short = jsonObject.getString("短碼")
                DONE_QTY = DONE_QTY.replace("\\/Date\\(".toRegex(), "")
                DONE_QTY = DONE_QTY.replace("\\)/".toRegex(), "")
                val formatter = SimpleDateFormat("yyyy-M-d")
                val START_TIME= formatter.format(DONE_QTY.toLong())
                listViewModelArrayList.add(
                        ListViewModelAdapterflowspile1(
                                1, SUBFLOWID, BATCH_QTY, START_TIME, parentID, splitStep, splitStatus, short
                        )
                )
                listViewAdapter?.notifyDataSetChanged()
                flow_list.adapter = listViewAdapter
            }
            flow_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                flow_list.adapter = listViewAdapter
                var id =listViewAdapter?.getItem(position)?.SUBFLOWID.toString()
                if (id!="流程單號"){
                    try{
                        MainActivity.str_shortSubFlow=id
                        (activity as MainActivity).get_flow_forselect()
                    }catch (ex: Exception){}
                    dismiss()
                }
            }
        }catch (ex: Exception){
        }
    }

}

