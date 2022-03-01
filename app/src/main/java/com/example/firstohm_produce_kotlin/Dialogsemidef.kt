package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.example.firstohm_produce_kotlin.Lib.ListViewModelAdapterprd
import com.example.firstohm_produce_kotlin.Lib.ListViewModelRo
import com.google.gson.Gson
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.dialog_prd_test.*
import kotlinx.android.synthetic.main.dialog_semidef.*
import org.json.JSONObject
import java.net.URLEncoder
import java.util.*
import kotlin.collections.ArrayList

class Dialogsemidef: DialogFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_semidef, container, false)
    }

    override fun onStart() {
        super.onStart()
        val width = 1000
        val height = 1000
        var VAL=MainActivity.VAL
        var TOL=MainActivity.TOL
        var PPM=MainActivity.PPM
        var OutputQuan=MainActivity.output
        var defectQuan=MainActivity.defectQuan
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        try {
            textView_OutputQuan.setText(MainActivity.output.replace(".0", ""))
        } catch (ex: Exception) {
            textView_OutputQuan.setText("0")
        }
        try {
            textView_VAL.setText(MainActivity.VAL)
            textView_TOL.setText(MainActivity.TOL)
            textView_PPM.setText(MainActivity.PPM)
        } catch (ex: Exception) {

        }
        btn_defware.setOnClickListener {
            try {
                if (editText_VAL.text.toString()!="")VAL=editText_VAL.text.toString()
                if (textView_TOL.text.toString()!="")TOL=editText_TOL.text.toString()
                if (editText_PPM.text.toString()!="")PPM=editText_PPM.text.toString()
                if (editText_OutputQuan.text.toString()!="")OutputQuan=editText_OutputQuan.text.toString()
                MainActivity.flow_json.put("OutputQuan", OutputQuan)
                val url=MainActivity.ip +"PrdMgn/ScanOperate?command=41&UID=" + MainActivity.userBar +
                        "&DEPT=" + MainActivity.dept +
                        "&flowBar=" +MainActivity.flowbar+
                        "&jsonStr=" + MainActivity.flow_json+ "&ifBad=1"+
                        "&extraJson={\"val\":\"$VAL\",\"tol\":\"$TOL\",\"ppm\":\"$PPM\",\"OutputQuan\":\"$OutputQuan\",\"DefectQuan\":\"$defectQuan\"}"
                Log.d("url",url)
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                var json = JSONObject(jsonString)
                (activity as MainActivity).get_flow()
                getDialog()?.dismiss()
            } catch (ex: Exception) {

            }
        }
        cls.setOnClickListener {
            try {
                getDialog()?.dismiss()
            } catch (ex: Exception) {

            }
        }

    }

}