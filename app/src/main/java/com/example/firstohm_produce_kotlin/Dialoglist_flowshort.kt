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
import kotlinx.android.synthetic.main.dialog_subflow_list.flow_list
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import java.util.ArrayList

class Dialoglist_flowshort : DialogFragment() {
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

                var url=MainActivity.ip+"PrdMgn/ScanOperate?" +
                        "command=0&UID=" +MainActivity.userBar+
                        "&DEPT=" +MainActivity.dept+
                        "&shortSubFlow="+MainActivity.short
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                val obj = JSONObject(jsonString)
                MainActivity.flow_message=obj.optString("Message")
            if(obj.optString("Message").indexOf("流程單錯誤")>-1){

                val v: View = inflater.inflate(R.layout.dialog_spilflow_list, null)
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(v)
                context?.let { ui_Helper.mesage("查無速碼", it) }

                dismiss()
            }else if(obj.optString("Message").indexOf("完成")>-1){
                    try{
                        dismiss()
                        val inflater = LayoutInflater.from(context)
                        val v: View = inflater.inflate(R.layout.activity_main, null)
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(v)
                        var json_Data = JSONObject(obj.getString("Data"))
                        MainActivity.flowbar =json_Data.getString("barCode")
                        (activity as MainActivity).get_flow()
                    }catch (ex: Exception){

                    }
                    //mainmessage.text=MainActivity.flow_message
                }
                else{
                    val array = JSONArray(obj.optString("Data"))
                    listViewModelArrayList.add(
                            ListViewModelAdapterPlating_exclude1(
                                    0, "流程單號", "分類方式", "支數", "鐵帽規格", "磁棒種類", "DONE_QTY",
                                    "splitStep", "splitStatus",
                                    "1", "1", "1", "SUBFLOWID",
                                    "1", "1"
                            )
                    )
                    for (i in 0 until array.length()) {
                        val jsonObject = array.getJSONObject(i)
                        val SUBFLOWID = jsonObject.getString("SUBFLOWID")
                        val cap_type = jsonObject.getString("MASTER_MFO_ID")
                        val BATCH_QTY = jsonObject.getString("RTYPE")
                        val cat_cate = jsonObject.getString("VAL")
                        val CFMF = jsonObject.getString("Tol")
                        val DONE_QTY = jsonObject.getString("batchNo")
                        val splitStep = jsonObject.getString("barCode")
                        val splitStatus = jsonObject.getString("barCode")
                        listViewModelArrayList.add(
                                ListViewModelAdapterflowspile1(
                                        0, SUBFLOWID, cap_type, BATCH_QTY, cat_cate, CFMF, DONE_QTY,
                                        splitStep
                                )
                        )
                        listViewAdapter?.notifyDataSetChanged()
                        flow_list.adapter = listViewAdapter
                    }
                    flow_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
                        flow_list.adapter = listViewAdapter
                        var id =listViewAdapter?.getItem(position)?.SUBFLOWID.toString()
                        if (id!="SUBFLOWID"){
                            try{
                                MainActivity.str_shortSubFlow=id
                                (activity as MainActivity).get_flow_forselect()
                            }catch (ex: Exception){}
                            dismiss()
                        }
                    }
                }
            }catch (ex: Exception){
                val inflater = LayoutInflater.from(context)
                val v: View = inflater.inflate(R.layout.activity_main, null)
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(v)
                context?.let { ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, it) }
            }
        }

}