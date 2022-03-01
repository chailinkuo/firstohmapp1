package com.example.firstohm_produce_kotlin

import androidx.appcompat.app.AppCompatActivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.DialogFragment
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.example.firstohm_produce_kotlin.MainActivity.Companion.NG_SUM
import com.example.firstohm_produce_kotlin.MainActivity.Companion.check_out_input
import com.example.firstohm_produce_kotlin.MainActivity.Companion.check_userid
import com.example.firstohm_produce_kotlin.MainActivity.Companion.select_user
import kotlinx.android.synthetic.main.activity_machins_layout.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.dialog_outcheck_1_user.*
import kotlinx.android.synthetic.main.dialog_outcheck_ng.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
class DialogChkdef : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_outcheck_ng, container, false)
    }

    override fun onStart() {
        super.onStart()
        try {

            val width = 1000
            val height = 1000
            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            var check=""
            if(MainActivity.outcheck_step == "花蓮底漆"){
                check="1"
            }else{
                check="2"
            }
            ng_submit.setOnClickListener {
                var ng1=Integer.valueOf(ng1_text.getText().toString());
                var ng2=Integer.valueOf(ng2_text.getText().toString());
                var ng3=Integer.valueOf(ng3_text.getText().toString());
                NG_SUM= (ng1+ng2+ng3).toString()
                MainActivity.NG_SUM=NG_SUM
                var webapiClient = WebapiClient()
                var url=MainActivity.ip+"PrdMgn/updOutCheckLog?"+
                        "flowBar="+MainActivity.flowbar+
                        "&NG1="+ng1+"&NG2="+ng2 +"&NG3="+ng3+
                        "&checkourSeq="+check+"&signID="+MainActivity.SIGNID+
                        "&preOperatorID="+MainActivity.check_userid+"&jsonStr="+MainActivity.flow_json
                var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                val jsonStr = JSONObject(jsonString)
                val ng1_d =ng1.toFloat()
                val ng2_d =ng2.toFloat()
                val ng3_d = ng3.toFloat()
                var sum=ng1_d+ng2_d+ng3_d
                ng1=0
                ng2=0
                ng3=0
                (activity as MainActivity).setdef(sum.toString())
                dismiss()
            }
        } catch (ex: Exception) {

        }
    }
}
