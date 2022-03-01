package com.example.firstohm_produce_kotlin
import androidx.fragment.app.DialogFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.example.firstohm_produce_kotlin.Lib.ListViewModelAdapterprd
import com.example.firstohm_produce_kotlin.Lib.ListViewModelRo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shift_layout.*
import kotlinx.android.synthetic.main.dialog_prd_test.*
import kotlinx.android.synthetic.main.dialog_semidef.*
import kotlinx.android.synthetic.main.dialog_semidef.btn_defware
import kotlinx.android.synthetic.main.dialog_semioutput.*
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList
class Dialogsemioutputfin : DialogFragment()   {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_semioutput, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = 1000
        val height = 1000
        output.setText(MainActivity.output)
        var defectQuan=MainActivity.defectQuan
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog!!.window?.setLayout(height, ViewGroup.LayoutParams.WRAP_CONTENT)
        try {
            button.setOnClickListener {
                MainActivity.flow_json.put("tieDaiQuant",output.text.toString())
                var sum_dept = ""
                if (MainActivity.dept == "花蓮塗裝") {
                    sum_dept = MainActivity.sw_dept
                } else {
                    sum_dept = MainActivity.dept
                }
                var url = MainActivity.ip + "PrdMgn/ScanOperate?command=37&UID=" +
                        MainActivity.userBar + "&MID=" + MainActivity.machineBar +
                        "&flowBar=" + MainActivity.flowbar + "&DEPT=" + sum_dept + "&jsonStr=" + MainActivity.flow_json
                if (MainActivity.dept == "花蓮塗裝") {
                    url = url + "&optionDept=花蓮塗裝"
                }
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                var json = JSONObject(jsonString)
                getDialog()?.dismiss()
            }
            clsbu.setOnClickListener {
                getDialog()?.dismiss()
            }
        } catch (ex: Exception) {

        }
    }
}