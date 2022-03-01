package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_main_button.view.*
import kotlinx.android.synthetic.main.dialog_speeddinput.*

class dialog_speeddinput: DialogFragment()  {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_speeddinput, container, false)
    }

    override fun onStart() {
        super.onStart()
        try {
            val width = 1000
            val height = 1000
            dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
            var check = ""
            speed_submit.setOnClickListener {
                    val inflater = LayoutInflater.from(context)
                val v: View = inflater.inflate(R.layout.activity_main, null)
                val v2: View = inflater.inflate(R.layout.custom_layout_main_button, null)
                //custom_layout_main_button
                if(speed_edit.text.toString()==""){

                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(v)
                    context?.let { ui_Helper.mesage("請輸入速度", it) };
                }else{
                    v.speed_value.setText(speed_edit.text)
                    v.partlayout.visibility=View.VISIBLE//拆單
                    v.btn_wherehouse_semi.visibility=View.INVISIBLE
                    v.btn_wherehouse_def.visibility=View.INVISIBLE
                    v.test_cnt_btn.visibility=View.INVISIBLE
                    v.flow_finsh__btn.visibility=View.INVISIBLE
                    v.part_finsh_btn.visibility=View.INVISIBLE
                    v.flow_start_btn.visibility=View.VISIBLE
                    v.flow_finsh__btn.performClick()
                    dismiss()
                }

            }
        } catch (ex: Exception) {

        }
    }
}