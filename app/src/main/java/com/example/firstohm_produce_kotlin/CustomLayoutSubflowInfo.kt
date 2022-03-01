package com.example.firstohm_produce_kotlin

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_info.view.*
import kotlinx.android.synthetic.main.custom_layout_main_button.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.Float.parseFloat


class CustomLayoutSubflowInfo(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.custom_layout_info, this)
        val customAttributesStyle = context.obtainStyledAttributes(
                attrs,
                R.styleable.custom_layout_info,
                0,
                0
        )
        val textView_mfo_id = findViewById<TextView>(R.id.textView_mfo_id_value)
    }
    //write layout
    public fun inputViewItems(jsonObj: JSONObject, view: View){

        try {
            MainActivity.FLOW_STEP_CURR=""
            MainActivity.SIGNID=""
            MainActivity.InputQuan=""
            MainActivity.OutputQuan=""
            MainActivity.mfo_id=""
            MainActivity.flowbar=""
            MainActivity.VAL=""
            MainActivity.FLOW_STEP=""
            MainActivity.TOL=""
            MainActivity.PPM=""
            MainActivity.AccQuan=""
            MainActivity.StepLeft=""
            if (MainActivity.dept=="花蓮切割"){
                MainActivity.sumOfNaiBei=""
            }
            MainActivity.per_USER_ID=""
            MainActivity.BATSEQ=""
            MainActivity.BATCH_NO=""
            MainActivity.SUBFLOWID=""
            MainActivity.flowbar=jsonObj.getString("barCode")

            view.textView_mfo_id_value.setText(jsonObj.getString("mfo_id"))
            var BATCH_NO=jsonObj.getString("BATCH_NO")
            var BATSEQ: Int = Integer.valueOf(jsonObj.getString("BATSEQ"))
            MainActivity.FLOW_STEP_CURR=jsonObj.getString("FLOW_STEP_CURR")
            MainActivity.SIGNID=jsonObj.getString("SIGNID")
            MainActivity.InputQuan=jsonObj.getString("InputQuan")
            MainActivity.OutputQuan=jsonObj.getString("OutputQuan")
            MainActivity.mfo_id=jsonObj.getString("mfo_id")
            MainActivity.flowbar=jsonObj.getString("barCode")
            MainActivity.VAL=jsonObj.getString("VAL")
            MainActivity.ACCDefect=jsonObj.getString("ACCDefect")
            MainActivity.SUBFLOWID=jsonObj.getString("SUBFLOWID")
            MainActivity.FLOW_STEP=jsonObj.getString("FLOW_STEP")
            MainActivity.TOL=jsonObj.getString("TOL")
            MainActivity.PPM=jsonObj.getString("PPM")
            MainActivity.AccQuan=jsonObj.getString("AccQuan")
            MainActivity.StepLeft=jsonObj.getString("StepLeft")
            MainActivity.BATSEQ=BATSEQ.toString()
            MainActivity.BATCH_NO=BATCH_NO.toString()

            if (MainActivity.dept=="花蓮切割"){
                MainActivity.sumOfNaiBei=jsonObj.getString("sumOfNaiBei")
            }
            var USER_ID_Befor=jsonObj.getString("USER_ID")
            MainActivity.per_USER_ID=jsonObj.getString("USER_ID")
            BATSEQ = BATSEQ + 1
            view.BATCH_NO_text_value.setText("  $BATSEQ / $BATCH_NO")
            view.BATCH_QTY_text_value.setText(jsonObj.getString("BATCH_QTY"))
            view.RTYPE_text_value.setText(jsonObj.getString("RTYPE"))
            view.ppm_value.setText(jsonObj.getString("PPM"))
            view.TOL_text_value.setText("  ±" + jsonObj.getString("TOL") + "%")
            view.AccQuan_text_value.setText(jsonObj.getString("AccQuan"))
            view.WAREHouse_QTY_value.setText(jsonObj.getString("WAREHouse_QTY"))
            view.val_text_value.setText(jsonObj.getString("VAL"))
            view.input_edit.setEnabled(true);
            if (MainActivity.dept!="花蓮外檢"){
                view.input_edit.setText(jsonObj.getString("InputQuan"))
            }
            view.input_edit.setEnabled(false)
            view.DefectQuan_edit.setText("0")

            view.SUBFLOWID_value.setText(jsonObj.getString("SUBFLOWID"))
            if (MainActivity.dept=="花蓮貼帶"){
                view.tdRollQty_text.visibility=View.VISIBLE
                MainActivity.tdRollQty=jsonObj.getString("tdRollQty")
                view.tdRollQty_text.setText("捲(" + jsonObj.getString("tdRollQty") + "千/捲)")
            }else{
                view.tdRollQty_text.visibility=View.GONE
            }
            var START_TIME=jsonObj.getString("Start_TIME")
            START_TIME = START_TIME.replace("\\/Date\\(".toRegex(), "")
            START_TIME = START_TIME.replace("\\)/".toRegex(), "")
            var Finish_Time=jsonObj.getString("Finish_Time")
            Finish_Time = Finish_Time.replace("\\/Date\\(".toRegex(), "")
            Finish_Time = Finish_Time.replace("\\)/".toRegex(), "")
            if (parseFloat(START_TIME)>0 &&parseFloat(Finish_Time)<0
                    &&MainActivity.userBar==MainActivity.per_USER_ID){
                //開始中
                view.partlayout.visibility=View.INVISIBLE//拆單
                view.btn_wherehouse_semi.visibility=View.INVISIBLE
                view.btn_wherehouse_def.visibility=View.INVISIBLE
                view.flow_finsh__btn.visibility=View.VISIBLE
                view.flow_start_btn.visibility=View.INVISIBLE
                if (MainActivity.dept.indexOf("切")>-1){
                    view.part_finsh_btn.visibility=View.VISIBLE
                    view.test_cnt_btn.visibility=View.VISIBLE
                }else
                    view.part_finsh_btn.visibility=View.INVISIBLE
                if (MainActivity.dept == "花蓮外檢") {
                    view.NG_select.setEnabled(false);
                    view.out_chk_btn2.setEnabled(false)
                    view.out_chk_btn.setEnabled(false)
                }
            }else{
                //已完成
                if (MainActivity.dept == "花蓮外檢") {
                    view.out_chk_btn2.setText("色碼人員")
                    view.out_chk_btn.setText("底漆人員")
                    view.out_chk_btn2.setEnabled(true)
                    view.out_chk_btn.setEnabled(true)
                    view.NG_select.setEnabled(true);
                }
                view.partlayout.visibility=View.VISIBLE//拆單
                view.btn_wherehouse_semi.visibility=View.VISIBLE
                view.btn_wherehouse_def.visibility=View.VISIBLE
                view.test_cnt_btn.visibility=View.INVISIBLE
                view.flow_finsh__btn.visibility=View.INVISIBLE
                view.part_finsh_btn.visibility=View.INVISIBLE
                view.flow_start_btn.visibility=View.VISIBLE
            }
            if (MainActivity.dept != "花蓮外檢") {
                view.NG_select.visibility=View.GONE
                view.out_chk_btn2.visibility=View.GONE
                view.out_chk_btn.visibility=View.GONE
            }
            if (USER_ID_Befor !=MainActivity.userBar) {  //單與操作不同人直接給開始
                view.btn_wherehouse_semi.visibility=View.INVISIBLE
                view.btn_wherehouse_def.visibility=View.INVISIBLE
                view.test_cnt_btn.visibility=View.INVISIBLE
                view.flow_finsh__btn.visibility=View.INVISIBLE
                view.part_finsh_btn.visibility=View.INVISIBLE
                view.flow_start_btn.visibility=View.VISIBLE
            }
            MainActivity.START_TIME=jsonObj.getString("Start_TIME")
            MainActivity.Finish_Time=jsonObj.getString("Start_TIME")
            if(jsonObj.getString("COLOR_NUM")!=""){
                try{
                    val color = custom_layout_color(context, null)
                    color.draw_color(jsonObj.getString("COLOR_NUM"), view)
                } catch (t: Throwable) {
                }
            }
            MainActivity.WAREHouse_QTY=jsonObj.getString("WAREHouse_QTY")
            if (MainActivity.WAREHouse_QTY == "null") MainActivity.WAREHouse_QTY = "0"
            if (MainActivity.WAREHouse_QTY!="0"&&MainActivity.InputQuan=="0") {
                view.input_edit.setText(MainActivity.BATCH_QTY.toString())
            }else if (MainActivity.dept!="花蓮外檢"){
                view.input_edit.setText(jsonObj.getString("InputQuan"))
            }
            if (MainActivity.dept=="花蓮外檢"&&MainActivity.ng!=""){
                view.input_edit.setText(jsonObj.getString("InputQuan"))
            }

            if (MainActivity.dept=="花蓮切割"){
                view.sumOfNaiBei_value.visibility=View.VISIBLE
                view.sumOfNaiBei_title.visibility=View.VISIBLE
                if(jsonObj.getString("sumOfNaiBei")!="null"){
                    view.sumOfNaiBei_value.setText(jsonObj.getString("sumOfNaiBei"))
                }else{
                    view.sumOfNaiBei_value.setText("")
                }
            }else{
                view.sumOfNaiBei_value.visibility=View.GONE
                view.sumOfNaiBei_title.visibility=View.GONE
            }

            if(jsonObj.getString("popMsg")!="null" && MainActivity.msg=="1"){
                MainActivity.msg="0"
                try{
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    //ui_Helper.mesage(jsonObj.getString("popMsg"), context)
                    ui_Helper.POPmsgAction(jsonObj.getString("popMsg"),"" ,context)
                    MainActivity.flow_json.put("popMsg","")
                } catch (t: Throwable) {}
            }
        } catch (ex: Exception) {

        }
        try{
            if(jsonObj.getString("currNaiBei")!="null" ){
                val Data_currNaiBei = jsonObj?.getString("currNaiBei")
                val array = JSONArray(Data_currNaiBei)
                //val arr = JSONArray()
                val arr = JSONObject()
                val requiredDataArray = JSONArray()

                for (j in 0 until array.length()) {
                    val Jasonobject: String = array.getString(j)
                    val jsonStr = JSONObject(Jasonobject)
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    val newSTART_TIME=ui_Helper.TimestampToDatetime(jsonStr.getString("START_TIME"))
                    val newFINISH_TIME=ui_Helper.TimestampToDatetime(jsonStr.getString("FINISH_TIME"))
                    val InputQuan=jsonStr.getString("InputQuan")
                    val DefectQuan=jsonStr.getString("DefectQuan")
                    val OutputQuan=jsonStr.getString("OutputQuan")
                    var jsonObjSend = JSONObject()
                    jsonObjSend.put("START_TIME", newSTART_TIME)
                    jsonObjSend.put("FINISH_TIME", newFINISH_TIME)
                    jsonObjSend.put("InputQuan", InputQuan)
                    jsonObjSend.put("DefectQuan", DefectQuan)
                    jsonObjSend.put("OutputQuan", OutputQuan)
                    requiredDataArray.put(jsonObjSend);
                }
                Log.d("arr", requiredDataArray.toString())
                MainActivity.flow_json.put("currNaiBei", requiredDataArray.toString())

            }

            } catch (t: Throwable) {}
    }
}