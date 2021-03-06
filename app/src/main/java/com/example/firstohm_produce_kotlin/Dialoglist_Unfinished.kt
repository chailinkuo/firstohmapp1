package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.DialogFragment
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import co.ubunifu.kotlinhttpsample.ListViewModelAdapterPlating_exclude1
import kotlinx.android.synthetic.main.dialog_spilflow_list.*
import kotlinx.android.synthetic.main.dialog_spilflow_list.flow_list
import kotlinx.android.synthetic.main.dialog_subflow_list.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.ArrayList

class Dialoglist_Unfinished: DialogFragment()  {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_spilflow_list, container, false)
    }
    var listViewModelArrayList = ArrayList<ListViewModelAdapterflowspile1>()

    override fun onStart() {
        super.onStart()
        button11.setOnClickListener {
            dialog?.dismiss()
        }
        try{
            var listViewAdapter = context?.let {
                ListViewModelAdapterflowspile(
                        it,
                        listViewModelArrayList as ArrayList<ListViewModelAdapterflowspile1>
                )
            }
            val inflater = LayoutInflater.from(context)
            val v: View = inflater.inflate(R.layout.activity_main, null)
            var webapiClient = WebapiClient()
            //flow bar list
            var url=MainActivity.ip+"PrdMgn/myUnfinishedRecords?userBar=" +MainActivity.userBar
            var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
            val obj = JSONObject(jsonString)
            val array = JSONArray(obj.optString("Data"))
            listViewModelArrayList.add(
                    ListViewModelAdapterPlating_exclude1(
                            0, "????????????", "????????????", "??????", "????????????", "????????????", "DONE_QTY",
                            "splitStep", "splitStatus",
                            "1", "1", "1", "SUBFLOWID",
                            "1", "1"
                    )
            )

            listViewModelArrayList.add(
                    ListViewModelAdapterflowspile1(
                            0, "??????????????????", "", "", "", "", "",
                            ""
                    )
            )
            for (i in 0 until array.length()) {
                val jsonObject = array.getJSONObject(i)
                val SUBFLOWID = jsonObject.getString("????????????")
                val cap_type = jsonObject.getString("????????????")
                val BATCH_QTY = jsonObject.getString("??????")
                val cat_cate = jsonObject.getString("??????")
                val CFMF = jsonObject.getString("????????????")
                val DONE_QTY = jsonObject.getString("??????")
                val splitStep = jsonObject.getString("?????????")
                val splitStatus = jsonObject.getString("SIGNID")
                listViewModelArrayList.add(
                        ListViewModelAdapterflowspile1(
                                0, SUBFLOWID, cap_type, BATCH_QTY, cat_cate, CFMF, DONE_QTY,
                                splitStatus
                        )
                )
                listViewAdapter?.notifyDataSetChanged()
                flow_list.adapter = listViewAdapter
            }
            flow_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                flow_list.adapter = listViewAdapter
                var id =listViewAdapter?.getItem(position)?.short.toString()
                if (id!=""){
                    try{
                        MainActivity.str_shortSubFlow=id
                        (activity as MainActivity).get_flow_SIGNID()
                    }catch (ex: Exception){}
                    dismiss()
                }
            }
        }catch (ex: Exception){
        }
    }
}