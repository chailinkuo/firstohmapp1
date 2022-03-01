package com.example.firstohm_produce_kotlin

import android.R.attr.data
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.example.firstohm_produce_kotlin.Lib.ListViewModelAdapterprd
import com.example.firstohm_produce_kotlin.Lib.ListViewModelRo
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.dialog_prd_test.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter


class DialogPrdtest: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_prd_test, container, false)
    }

    override fun onStart() {
        super.onStart()
        try {
            var listViewModelArrayList = ArrayList<ListViewModelRo>()
            for (i in 0 until 9) {
                var listViewAdapter = ListViewModelAdapterprd(
                        this.context,
                        listViewModelArrayList as ArrayList<ListViewModelRo>
                )
                listViewModelArrayList.add(
                        ListViewModelRo(
                                "",
                                "",
                                "",
                                "",
                                "",
                                ""
                        )
                )
                listViewAdapter.notifyDataSetChanged()
                whare_house_list.adapter = listViewAdapter
                var listViewModelArrayList = ArrayList<ListViewModelRo>()
            }
            val width = 1000
            val height = 1000
            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            btn_prd_submit.setOnClickListener {

                try {

                    val roAdaptor = whare_house_list.adapter as ListViewModelAdapterprd
                    val roListModelArrayList =  roAdaptor.getListViewModelList()
                    val whIdxList = ArrayList<Int>()
                    var submit_arr=ArrayList<String>()
                    for(i in 0..roListModelArrayList.size-1)
                    {
                        submit_arr.add(roListModelArrayList[i].count_1.toString())
                        submit_arr.add(roListModelArrayList[i].count_2.toString())
                        submit_arr.add(roListModelArrayList[i].count_3.toString())
                        submit_arr.add(roListModelArrayList[i].count_4.toString())
                        submit_arr.add(roListModelArrayList[i].count_5.toString())
                        submit_arr.add(roListModelArrayList[i].count_6.toString())
                    }
                    var i=0

                    for (s in submit_arr) {
                        if (s!=="null") {
                            if (s!=="") {
                                i++
                            }
                        }
                    }
                    val refinedArray = arrayOfNulls<String>(i)
                    var count = -1
                    for (s in submit_arr) {
                        if (s!=="null") {
                            if (s!=="") {
                                refinedArray[++count] = s
                            }

                        }
                    }
                    val gson = GsonBuilder().create()
                    val jsonArray = JSONArray(refinedArray)
                    //val array = Arrays.copyOf(refinedArray, count + 1)
                    var webapiClient = WebapiClient()
                    val testobj = JSONObject()
                    testobj.put("EMPID", MainActivity.userBar)
                    testobj.put("MachineID", MainActivity.machineBar)
                    testobj.put("DEPT", MainActivity.dept)
                    testobj.put("validateTop", "0")
                    testobj.put("validateBottom", "00")
                    testobj.put("SubflowID", MainActivity.flowbar)
                    testobj.put("SIGNID", MainActivity.SIGNID)
                    testobj.put("testNote", "0")
                    testobj.put("ExtraValue", "0")
                    testobj.put("ExtraInfo", "0")
                    testobj.put("TestSet", jsonArray)
                    var url=MainActivity.ip+ "PrdMgn/SubmitFlowTest?SIGNID=" + MainActivity.SIGNID +
                            "&UID=" + MainActivity.userBar + "&flowBar=" + MainActivity.flowbar +
                            "&DEPT=" + MainActivity.dept +
                            "&MID=" + MainActivity.machineBar+ "&jsonStr=" + testobj.toString();
                    var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                    val jsonStr = JSONObject(jsonString)
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage(jsonStr.getString("Message"))
                            .setPositiveButton("是") { dialog, which -> // TODO Auto-generated method stub

                            }
                    val about_dialog = builder.create()
                    about_dialog.show()
                    //val rootView = window.decorView.rootView
                    dismiss()
                } catch (ex: Exception) {
                    val inflater = LayoutInflater.from(context)
                    val v: View = inflater.inflate(R.layout.activity_main, null)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(v)
                    context?.let { ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, it) }
                }
            }
            btn_prd_cancel.setOnClickListener {
                dismiss()
            }
        } catch (ex: Exception) {

        }
    }
}
