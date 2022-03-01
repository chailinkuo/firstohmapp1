package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.dialog_outcheck_1_user.*
import kotlinx.android.synthetic.main.dialog_spilflow_list.*
import kotlinx.android.synthetic.main.dialog_spilflow_list.flow_list
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.ArrayList

class Dialogoutcheck_2: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_outcheck_1_user, container, false)
    }
    var listViewModelArrayList = ArrayList<ListViewModelAdapterflowspile1>()

    override fun onStart() {
        super.onStart()

        val width = 1000
        val height = 1000
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        btn_user_ok.setOnClickListener {
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
            var url = MainActivity.ip +
                    "PrdMgn/getOpertorInfoByDept?flowBar=" +
                    MainActivity.flowbar + "&DEPT="+ MainActivity.outcheck_step
            var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
            val obj = JSONObject(jsonString)
            val array = JSONArray(obj.optString("Data"))
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val SUBFLOWID = jsonObject.getString("EMPNAME")
                val BATCH_QTY = jsonObject.getString("USER_ID")
                var DONE_QTY = jsonObject.getString("colorOut")
                val parentID = jsonObject.getString("outchkCnt")
                val splitStep = jsonObject.getString("FLOW_STEP")
                val splitStatus = jsonObject.getString("colorOut")
                val short = jsonObject.getString("colorOut")
                val START_TIME= jsonObject.getString("colorOut")
                listViewModelArrayList.add(
                        ListViewModelAdapterflowspile1(
                                1,BATCH_QTY ,  SUBFLOWID, DONE_QTY, short,"", "", ""
                        )
                )
                listViewAdapter?.notifyDataSetChanged()
                flow_list.adapter = listViewAdapter
            }
            flow_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                flow_list.adapter = listViewAdapter
                var id =listViewAdapter?.getItem(position)?.DONE_QTY.toString()
                var id2 =listViewAdapter?.getItem(position)?.BATCH_QTY.toString()
                MainActivity.check_userid =listViewAdapter?.getItem(position)?.SUBFLOWID.toString()
                if (id!="流程單號"){
                    try{
                        (activity as MainActivity).setinput(id,id2)
                    }catch (ex: Exception){}
                    dismiss()
                }
            }
        }catch (ex: Exception){
        }
    }
}