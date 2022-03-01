package com.example.firstohm_produce_kotlin

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter


class custom_layout_quant_input(
        context: Context,
        attrs: AttributeSet
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.custom_layout_quant_input, this)
        val customAttributesStyle = context.obtainStyledAttributes(
                attrs,
                R.styleable.custom_layout_quant_input,
                0,
                0
        )
        btn_editinput.setOnClickListener {
            input_edit.setEnabled(true);
        }
        btn_acc.setOnClickListener {
            try {
                val DefectQuan = DefectQuan_edit.text.toString().toFloat()
                val StepLeft = StepLeft_edit.text.toString().toFloat()
                var sum = DefectQuan +StepLeft
                rootView.DefectQuan_edit.setText(sum.toString().replace(".0", ""))
                rootView.StepLeft_edit.setText("0")
            } catch (ex: Exception) {
        }
        }
        get_machins.setOnClickListener {
            try {
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                var url = MainActivity.ip + "PrdMgn/ScanOperate?command=8&UID=" +
                        MainActivity.userBar + "&MID=" + MainActivity.machineBar +
                 "&DEPT=" + MainActivity.dept
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                var json = JSONObject(jsonString)
                rootView.mainmessage.setText(json.getString("Message"))
                val toast = Toast.makeText(context,
                        json.getString("Message"), Toast.LENGTH_LONG)
                toast.show()
                MainActivity.flow_json = JSONObject(json.getString("Data"))
                try {
                var json_Data = JSONObject(json.getString("Data"))
                val SubflowInfo = CustomLayoutSubflowInfo(context, null)
                SubflowInfo.inputViewItems(json_Data, rootView)
            } catch (ex: Exception) {
                ui_Helper.mesage(json.getString("Message"), context)
            }
        } catch (ex: Exception) {
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                ui_Helper.send_mail(stacktrace)
        }
        }
        Radio_tdqty.setOnClickListener {
            rootView.input_edit.setText(MainActivity.InputQuan)
        }
        Radio_tdroll.setOnClickListener {
            try {
                val roll : Float = MainActivity.tdRollQty.toFloat();
                val input : Float = MainActivity.InputQuan.toFloat();
                val sum=input/(roll*1000)
                //rootView.input_edit.setText(sum.toString())
            } catch (ex: Exception) {
                try {
                    Log.d("1", ex.message)
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    //ui_Helper.send_mail(stacktrace)
                } catch (ex: Exception) {

                }
            }
        }
        Supervisor_check.setOnClickListener{
            MainActivity.super_check="1"
        }
        TieDaiL_check.setOnClickListener{
            try {
                if (TieDaiL_check.isChecked) {
                    shift_bnt.visibility = VISIBLE
                    DefectQuan_edit.setText("0")
                    DefectQuan_edit.setFocusable(false)
                    DefectQuan_edit.setBackgroundColor(Color.parseColor("#BFBFBF"))
                } else {
                    shift_bnt.visibility = GONE
                    DefectQuan_edit.setFocusableInTouchMode(true)
                    DefectQuan_edit.setFocusable(true)
                    DefectQuan_edit.setBackgroundColor(Color.WHITE)
                }
            } catch (ex: Exception) {
                Log.d("1", ex.message)
            }
        }
        DefectQuan_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    val input = input_edit.text.toString().toFloat()
                    val output = output_edit.text.toString().toFloat()
                    val DefectQuan = DefectQuan_edit.text.toString().toFloat()
                    var sum = input - output - DefectQuan
                    if (MainActivity.dept == "花蓮切割") {
                        try {
                            if (MainActivity.sumOfNaiBei != "null") {
                                var neb = MainActivity.sumOfNaiBei.toFloat()
                                sum = sum - neb
                            }


                        } catch (ex: Exception) {
                            Log.d("1", ex.message)
                        }
                    }
                    if (sum <= 0)
                        sum = 0F

                    if (Radio_tdroll.isChecked == true && MainActivity.dept == "花蓮貼帶") {
                        val new_roll = MainActivity.tdRollQty.toFloat()
                        sum = input - (output*new_roll*1000) - (DefectQuan*new_roll*1000)
                    }
                    val sum1 = sum.toInt()
                    StepLeft_edit.setText(sum1.toString().replace(".0", ""))
                    MainActivity.defectQuan = DefectQuan_edit.text.toString().toFloat().toString().replace(".0", "")
                } catch (ex: Exception) {
                    Log.d("1", ex.message)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        output_edit.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                try {
                    MainActivity.output = output_edit.text.toString().toFloat().toString()
                    val input = input_edit.text.toString().toFloat()
                    val output = output_edit.text.toString().toFloat()
                    val DefectQuan = DefectQuan_edit.text.toString().toFloat()
                    var sum = input - output - DefectQuan
                    if (sum <= 0)
                        sum = 0F
                    if (MainActivity.dept == "花蓮切割") {
                        try {
                            if (MainActivity.sumOfNaiBei != "null") {
                                var neb = MainActivity.sumOfNaiBei.toFloat()
                                sum = sum - neb
                            }
                        } catch (ex: Exception) {
                            Log.d("1", ex.message)
                        }
                    }
                    if (Radio_tdroll.isChecked == true && MainActivity.dept == "花蓮貼帶") {
                        val new_roll = MainActivity.tdRollQty.toFloat()
                        sum = input - (output*new_roll*1000) - (DefectQuan*new_roll*1000)
                    }
                    val sum1 = sum.toInt()
                    MainActivity.OutputQuan = output_edit.text.toString()
                    StepLeft_edit.setText(sum1.toString().replace(".0", ""))
                } catch (ex: Exception) {
                    Log.d("1", ex.message)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }
    public fun get_m(view: View){
        mid_text.text=MainActivity.machineBar
    }
}