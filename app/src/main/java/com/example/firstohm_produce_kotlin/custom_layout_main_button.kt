package com.example.firstohm_produce_kotlin

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import co.ubunifu.kotlinhttpsample.Lib.UI_Helper
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_main_button.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.DefectQuan_edit
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.StepLeft_edit
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.input_edit
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.output_edit
import kotlinx.android.synthetic.main.dialog_exit.view.*
import kotlinx.android.synthetic.main.dialog_last_finsh.view.*
import kotlinx.android.synthetic.main.dialog_start_flow.view.*
import kotlinx.android.synthetic.main.dialog_start_flow.view.btn_back
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter


class custom_layout_main_button(
        context: Context,
        attrs: AttributeSet?
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.custom_layout_main_button, this)
        val customAttributesStyle = context.obtainStyledAttributes(
                attrs,
                R.styleable.custom_layout_main_button,
                0,
                0
        )
        try{
            val UI_Helper = UI_Helper(rootView)
            flow_finsh__btn.setOnClickListener {
                flow_finsh(rootView)
            }
            part_finsh_btn.setOnClickListener {
                flow_part_finsh(rootView)
            }
            flow_start_btn.setOnClickListener {
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                if (MainActivity.flowbar == "") {
                    ui_Helper.mesage("??????????????????", context)
                } else if (MainActivity.machineBar == "") {
                    ui_Helper.mesage("????????????", context)
                } else {
                    if (MainActivity.dept=="????????????"&&MainActivity.FLOW_STEP=="null") {
                        val builder = AlertDialog.Builder(context)
                        builder.setMessage("??????????????? ????????????????")
                                .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub
                                    showwindows(rootView)
                                }
                                .setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                                }
                        val about_dialog = builder.create()
                        about_dialog.show()
                    } else if (MainActivity.dept=="????????????"&& MainActivity.FLOW_STEP!="????????????") {
                        //callapi????????????
                        ///flowstepHistory?subflowID=2064510&checkStep=????????????

                        var url = MainActivity.ip + "PrdMgn/flowstepHistory?subflowID="+MainActivity.SUBFLOWID+
                                "&checkStep=????????????"
                        var webapiClient = WebapiClient()
                        var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                        var json = JSONObject(jsonString)
                        if (!(json.getString("Data").indexOf("??????")>-1)){
                            val builder = AlertDialog.Builder(context)
                            builder.setMessage("??????????????? ????????????????")
                                    .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub
                                        showwindows(rootView)
                                    }
                                    .setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                                    }
                            val about_dialog = builder.create()
                            about_dialog.show()
                        }else{
                            showwindows(rootView)
                        }
                        /*val builder = AlertDialog.Builder(context)
                        builder.setMessage("??????????????? ????????????????")
                                .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub
                                    showwindows(rootView)
                                }
                                .setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                                }
                        val about_dialog = builder.create()
                        about_dialog.show()*/
                    }else if (MainActivity.dept=="????????????"&& MainActivity.check_userid=="") {
                        val builder = AlertDialog.Builder(context)
                        builder.setMessage("???????????????????????????????????????????")
                                .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub
                                    showwindows(rootView)
                                }
                                .setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                                }
                        val about_dialog = builder.create()
                        about_dialog.show()
                    } else {
                        showwindows(rootView)
                    }
                }
            }
            logout_btn.setOnClickListener {
                exit(rootView)
            }
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.mesage("???????????????????????????????????????????????????????????????", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }

    private fun showwindows(v: View){
        try {

        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_start_flow, null)
        val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
        val title = TextView(context)
        title.text = "??????"
        title.gravity = Gravity.CENTER
        title.textSize = 24f
        dialog.setCustomTitle(title)
        dialog.setView(v)
        button_views(v)
        val alertDialog = dialog.create()
        val dlg: AlertDialog = dialog.show()
        val params: WindowManager.LayoutParams = dlg.window!!.getAttributes()

            v.btn_back.setOnClickListener {
                dlg.dismiss();
            }
        v.btn_new.setOnClickListener {
            flow_new_start(rootView, v.btn_new.text as String)
            dlg.dismiss();
        }
        v.btn_continue.setOnClickListener {
            flow_new_start(rootView, v.btn_continue.text as String)
            dlg.dismiss();
        }
        v.btn_OCHeck_re.setOnClickListener {//??????????????????
            flow_new_start(rootView, v.btn_OCHeck_re.text as String)
            dlg.dismiss();
        }
        v.btn_D2.setOnClickListener {
            flow_new_start(rootView, v.btn_D2.text as String)
            dlg.dismiss();
        }
        v.btn_D1.setOnClickListener {
            flow_new_start(rootView, v.btn_D1.text as String)
            dlg.dismiss();
        }
        v.btn_CHeck_re1.setOnClickListener {
            flow_new_start(rootView, v.btn_CHeck_re1.text as String)
            dlg.dismiss();
        }
        v.btn_CHeck_re2.setOnClickListener {
            flow_new_start(rootView, v.btn_CHeck_re2.text as String)
            dlg.dismiss();
        }
        v.btn_changmid.setOnClickListener {
            flow_new_start(rootView, v.btn_changmid.text as String)
            dlg.dismiss();
        }
        v.btn_changcolor.setOnClickListener {
            flow_new_start(rootView, v.btn_changcolor.text as String)
            dlg.dismiss();
        }
        v.btn_retest.setOnClickListener {
            flow_new_start(rootView, v.btn_retest.text as String)
            dlg.dismiss();
        }
        v.btn_cut_befor.setOnClickListener {
            flow_new_start(rootView, v.btn_cut_befor.text as String)
            dlg.dismiss();
        }
        v.btn_cut_after.setOnClickListener {
            flow_new_start(rootView, v.btn_cut_after.text as String)
            dlg.dismiss();
        }
        v.btn_color_start.setOnClickListener {
            flow_new_start(rootView, v.btn_color_start.text as String)
            dlg.dismiss();
        }
        v.btn_cut_test.setOnClickListener {
            flow_new_start(rootView, v.btn_cut_test.text as String)
            dlg.dismiss();
        }
        v.btn_OCHeck_2.setOnClickListener {
            flow_new_start(rootView, v.btn_OCHeck_2.text as String)
            dlg.dismiss();
        }
        v.btn_OCHeck_1.setOnClickListener {
            flow_new_start(rootView, v.btn_OCHeck_1.text as String)
            dlg.dismiss();
        }
        v.btn_allchk_1.setOnClickListener {
            flow_new_start(rootView, v.btn_allchk_1.text as String)
            dlg.dismiss();
        }
        v.btn_allchk_2.setOnClickListener {
            flow_new_start(rootView, v.btn_allchk_2.text as String)
            dlg.dismiss();
        }
        v.btn_allchk_Remarksb.setOnClickListener {
            flow_new_start(rootView, v.btn_allchk_Remarksb.text as String)
            dlg.dismiss();
        }
        v.def_reset.setOnClickListener {
            flow_new_start(rootView, v.def_reset.text as String)
            dlg.dismiss();
        }
        v.output_reset1.setOnClickListener {
            flow_new_start(rootView, v.output_reset1.text as String)
            dlg.dismiss();
        }
        dialog.setPositiveButton("??????") { dialog, which ->
            val id = v.start_group.checkedRadioButtonId
            //
            when (id) {
                R.id.radio_new -> {
                    flow_new_start(rootView, v.radio_new.text as String)
                }
                R.id.radio_continue -> {
                    flow_new_start(rootView, v.radio_continue.text as String)
                }
                R.id.radio_change_machine -> {
                    flow_new_start(rootView, v.radio_change_machine.text as String)
                }
                R.id.D1 -> {//??????1
                    flow_new_start(rootView, v.D1.text as String)
                }
                R.id.D2 -> {//??????2
                    flow_new_start(rootView, v.D2.text as String)
                }
                R.id.check_out_1_retest -> {//??????1??????
                    flow_new_start(rootView, v.check_out_1_retest.text as String)
                }
                R.id.check_out_2 -> {//??????1??????
                    flow_new_start(rootView, v.check_out_1_retest.text as String)
                }
                R.id.check_all_1 -> {//??????1
                    flow_new_start(rootView, v.check_all_1.text as String)
                }
                R.id.check_all_2 -> {//??????2
                    flow_new_start(rootView, v.check_all_2.text as String)
                }
                R.id.check_all_re_1 -> {//??????????????????
                    flow_new_start(rootView, v.check_all_re_1.text as String)
                }
                R.id.check_all_re_2 -> {//?????????????????????
                    flow_new_start(rootView, v.check_all_re_2.text as String)
                }
                R.id.check_all_remark -> {//?????????????????????
                    flow_new_start(rootView, v.check_all_remark.text as String)
                }
            }
            dialog.cancel()
        }
        dialog.setNeutralButton("??????") { dialog, which ->
            //mothing
        }
        params.width = 1200
        params.height = 700
        dlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f)
        dlg.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f)
        dlg.getWindow()!!.setAttributes(params)
    } catch (ex: Exception) {

    }
    }
    private fun button_views(v: View) {
        try {

        button_views_standard(v)
        if (MainActivity.dept.indexOf("???")>-1){
            button_views_cut(v)
        }
        if (MainActivity.dept.indexOf("???")>-1){
            button_views_D(v)
        }
        if (MainActivity.dept.indexOf("???")>-1){
            button_views_Painting(v)
        }
        if (MainActivity.dept.indexOf("???")>-1){
            button_views_all_check(v)
        }
        if (MainActivity.dept.indexOf("???")>-1){
            button_views_out_check(v)
        }
    } catch (ex: Exception) {

    }
    }
    private fun button_views_out_check(v: View) {
        try {
            v.btn_new.visibility= GONE
            v.btn_continue.visibility= VISIBLE
            v.btn_changmid.visibility= VISIBLE
            if (MainActivity.FLOW_STEP_CURR.indexOf("??????1") > -1&&
                    MainActivity.FLOW_STEP.indexOf("??????1") > -1) {
                v.btn_CHeck_re1.setVisibility(VISIBLE);//??????1??????
            }
            if (MainActivity.FLOW_STEP_CURR.indexOf("??????2") > -1&&
                    MainActivity.FLOW_STEP.indexOf("??????2") > -1) {
                v.btn_CHeck_re2.setVisibility(VISIBLE);//??????2??????
            }
        if (!MainActivity.dept.equals(MainActivity.FLOW_STEP)
                && !MainActivity.FLOW_STEP.equals("??????2")
                && ((MainActivity.dept.indexOf("???") >1)))
        {
            v.btn_continue.setVisibility(GONE);//???????????????????????????
        }
        if (MainActivity.FLOW_STEP_CURR.indexOf("??????1") > -1
                &&MainActivity.dept.indexOf("???") > -1) {
            v.btn_OCHeck_1.setVisibility(VISIBLE);//??????=?????????
            v.btn_OCHeck_2.setVisibility(GONE);//?????????=?????????
        }
        if(MainActivity.FLOW_STEP_CURR.indexOf("??????")>-1
                &&MainActivity.colorUser.equals("")) {
            v.btn_OCHeck_1.setVisibility(VISIBLE);//??????=?????????
            v.btn_OCHeck_2.setVisibility(VISIBLE);//?????????=?????????
        }
        if (MainActivity.dept.indexOf("???")>-1&&MainActivity.super_check=="1")
        {
            v.btn_OCHeck_1.setVisibility(VISIBLE);//??????=?????????
            v.btn_OCHeck_2.setVisibility(VISIBLE);//?????????=?????????
        }
        if (MainActivity.FLOW_STEP_CURR.indexOf("??????2") > -1
                &&MainActivity.dept.indexOf("???") > -1) {
            v.btn_OCHeck_1.setVisibility(GONE);//??????=?????????
            v.btn_OCHeck_2.setVisibility(VISIBLE);//?????????=?????????
            v.btn_OCHeck_re.setVisibility(VISIBLE);//?????????????????????
        }
            try {
                if (MainActivity.WAREHouse_QTY.toInt() > 1) {//?????????
                    v.btn_OCHeck_1.setVisibility(VISIBLE);//??????=?????????
                    v.btn_OCHeck_2.setVisibility(VISIBLE);//?????????=?????????
                    v.btn_OCHeck_re.setVisibility(GONE);//?????????????????????
                }

                v.btn_OCHeck_1.setVisibility(VISIBLE);//??????=?????????
                v.btn_OCHeck_2.setVisibility(VISIBLE);//?????????=?????????
            } catch (ex: Exception) {

            }

            v.btn_OCHeck_1.setVisibility(VISIBLE);//??????=?????????
            v.btn_OCHeck_2.setVisibility(VISIBLE);//?????????=?????????
    } catch (ex: Exception) {

    }
    }
    private fun button_views_standard(v: View) {
        try {

        v.btn_new.visibility= VISIBLE
        v.btn_continue.visibility= VISIBLE
        v.btn_changmid.visibility= VISIBLE
        if (MainActivity.dept==MainActivity.FLOW_STEP)
            v.btn_new.visibility= GONE
        else{
            v.btn_continue.visibility= GONE
            v.btn_changmid.visibility= GONE
        }
    } catch (ex: Exception) {

    }
    }
    //??????
    private fun button_views_D(v: View) {
        try {

        v.btn_changcolor.visibility= VISIBLE//??????
            v.btn_D2.visibility= VISIBLE//??????2
            v.btn_D1.visibility= VISIBLE//??????1
            if (MainActivity.dept.equals(MainActivity.FLOW_STEP) && !MainActivity.AccQuan.equals("0")){
                v.btn_new.visibility= GONE
            }
            if (MainActivity.FLOW_STEP.equals("????????????") ){
                v.btn_D1.visibility= GONE
            }
    } catch (ex: Exception) {

    }
    }
    //??????
    public fun button_views_cut(v: View) {

    }
    //??????
    private fun button_views_all_check(v: View) {
        try {
            v.btn_new.visibility= GONE
        if (!MainActivity.StepLeft.equals("0")
            &&MainActivity.FLOW_STEP.indexOf("??????1") > -1) {
            v.btn_changmid.setVisibility(VISIBLE);//?????????
            v.radio_continue.setVisibility(VISIBLE);//??????
            v.btn_allchk_2.setVisibility(VISIBLE);//??????2
            v.btn_retest.setVisibility(VISIBLE);//??????
        }
        if (MainActivity.StepLeft.equals("0")
                &&MainActivity.FLOW_STEP.equals("??????2") ) {
            v.def_reset.setVisibility(VISIBLE);//???????????????
            v.output_reset1.setVisibility(VISIBLE);//????????????
        }

            ///////

            if((MainActivity.FLOW_STEP.indexOf("??????2") > -1)
                    &&MainActivity.ACCDefect.equals("0")){//2?????????2
                v.output_reset1.setVisibility(VISIBLE);//??????2????????? ???
                v.def_reset.setVisibility(VISIBLE);//??????2????????? ??????
            }else{
                v.output_reset1.setVisibility(GONE);//??????2????????? ???
                v.def_reset.setVisibility(GONE);//??????2????????? ??????
            }

            if (!MainActivity.ACCDefect.equals("0")&&
                    ((MainActivity.FLOW_STEP.indexOf("??????1") > -1) ||
                            (MainActivity.FLOW_STEP.indexOf("??????2") > -1))) {
                v.radio_new.setVisibility(GONE);//?????????
                v.btn_allchk_1.setVisibility(GONE);//??????1
                v.btn_changmid.setVisibility(VISIBLE);//?????????
                v.radio_continue.setVisibility(VISIBLE);//??????
                v.btn_allchk_2.setVisibility(GONE);//??????2
                v.def_reset.setVisibility(GONE);//???????????????
                v.btn_retest.setVisibility(GONE);//??????
                v.output_reset1.setVisibility(GONE);//????????????
                v.btn_allchk_Remarksb.setVisibility(GONE);//????????????
            }
            if (MainActivity.StepLeft.equals("0")&&
                    ((MainActivity.FLOW_STEP.indexOf("??????2") > -1)) ) {
                v.radio_new.setVisibility(GONE);//?????????
                v.btn_allchk_1.setVisibility(GONE);//??????1
                v.btn_changmid.setVisibility(GONE);//?????????
                v.radio_continue.setVisibility(GONE);//??????
                v.btn_allchk_2.setVisibility(GONE);//??????2
                v.def_reset.setVisibility(VISIBLE);//???????????????
                v.btn_retest.setVisibility(GONE);//??????
                v.output_reset1.setVisibility(VISIBLE);//????????????
                v.btn_allchk_Remarksb.setVisibility(GONE);//????????????
            }
            if (MainActivity.StepLeft.equals("0")&&MainActivity.FLOW_STEP.equals("??????2") ) {
                v.btn_allchk_Remarksb.setVisibility(GONE);//????????????
                v.btn_allchk_2.setVisibility(GONE);//??????2
                v.btn_allchk_1.setVisibility(GONE);//??????1
                v.def_reset.setVisibility(VISIBLE);//???????????????
                v.output_reset1.setVisibility(VISIBLE);//????????????
                v.btn_retest.setVisibility(GONE);//??????
            }

            if (!MainActivity.StepLeft.equals("0")&&
                    ((MainActivity.FLOW_STEP.indexOf("??????1") > -1) || (MainActivity.FLOW_STEP.indexOf("??????2") > -1))) {
                v.radio_new.setVisibility(GONE);//?????????
                v.btn_allchk_1.setVisibility(GONE);//??????1
                v.btn_changmid.setVisibility(VISIBLE);//?????????
                v.radio_continue.setVisibility(VISIBLE);//??????
                v.btn_allchk_2.setVisibility(GONE);//??????2
                v.def_reset.setVisibility(GONE);//???????????????
                v.btn_retest.setVisibility(GONE);//??????
                v.output_reset1.setVisibility(GONE);//????????????
                v.btn_allchk_Remarksb.setVisibility(GONE);//????????????
            }
            if (MainActivity.StepLeft.equals("0")&&( (MainActivity.FLOW_STEP.indexOf("??????2") > -1))) {
                v.radio_new.setVisibility(GONE);//?????????
                v.btn_allchk_1.setVisibility(GONE);//??????1
                v.btn_changmid.setVisibility(GONE);//?????????
                v.radio_continue.setVisibility(GONE);//??????
                v.btn_allchk_2.setVisibility(GONE);//??????2
                v.def_reset.setVisibility(VISIBLE);//???????????????
                v.btn_retest.setVisibility(GONE);//??????
                v.output_reset1.setVisibility(VISIBLE);//????????????
                v.btn_allchk_Remarksb.setVisibility(GONE);//????????????
            }
            //////////
        if ((MainActivity.machineBar.equals("No.31")
             || MainActivity.machineBar.equals("No.32")
             || MainActivity.machineBar.equals("No.33"))) {
            v.btn_allchk_Remarksb.setVisibility(VISIBLE);//????????????
            v.btn_allchk_2.setVisibility(VISIBLE);//??????2
            v.btn_allchk_1.setVisibility(GONE);//??????1
            v.def_reset.setVisibility(GONE);//???????????????
            v.output_reset1.setVisibility(GONE);//????????????
            v.btn_retest.setVisibility(GONE);//??????
            if (MainActivity.FLOW_STEP.indexOf("??????") > -1){
                v.btn_allchk_Remarksb.setVisibility(GONE);//????????????
                v.btn_allchk_2.setVisibility(GONE);//??????2
                if (MainActivity.StepLeft.equals("0")){
                    v.def_reset.setVisibility(VISIBLE);//???????????????
                    v.output_reset1.setVisibility(VISIBLE);//????????????
                    v.btn_changmid.setVisibility(GONE);//??????
                }else if (!MainActivity.StepLeft.equals("0")){
                    v.radio_continue.setVisibility(VISIBLE);//??????
                    v.btn_changmid.setVisibility(VISIBLE);//??????
                }
            }else{
                v.btn_allchk_2.setVisibility(VISIBLE);//??????2
            }
        }else{ //??????31.32.33
            if (MainActivity.dept.indexOf("???") > -1
                    && !(MainActivity.FLOW_STEP.indexOf("??????")>-1)){
                v.btn_allchk_Remarksb.setVisibility(VISIBLE);//????????????
                v.btn_allchk_1.setVisibility(VISIBLE);//??????1
                v.btn_allchk_2.setVisibility(GONE);//??????2
                v.def_reset.setVisibility(GONE);//???????????????
                v.output_reset1.setVisibility(GONE);//????????????
                v.btn_allchk_2.setVisibility(GONE);//??????
                v.btn_changmid.setVisibility(GONE);//??????2
            }
        }
            if (!MainActivity.StepLeft.equals("0")&&
                    ((MainActivity.FLOW_STEP.indexOf("??????1") > -1)
                            || (MainActivity.FLOW_STEP.indexOf("??????2") > -1))
            ) {
                v.radio_new.setVisibility(GONE);//?????????
                v.btn_allchk_1.setVisibility(GONE);//??????1
                v.btn_changmid.setVisibility(VISIBLE);//?????????
                v.btn_continue.setVisibility(VISIBLE);//??????
            }

            if (MainActivity.StepLeft.equals("0")&&(MainActivity.FLOW_STEP.indexOf("??????1") > -1)) {

                v.btn_changmid.setVisibility(VISIBLE);//?????????
                v.btn_allchk_2.setVisibility(VISIBLE);//??????2
            }
    } catch (ex: Exception) {

    }
    }
    //??????
    private fun button_views_Painting(v: View) {
        try {
            v.btn_cut_after.setVisibility(VISIBLE) //???????????? ???????????????
            v.btn_cut_befor.setVisibility(VISIBLE) //???????????? ???????????????
            v.btn_color_start.setVisibility(GONE) //????????????
            if (MainActivity.dept.indexOf("???") > -1) {
                v.btn_new.text = "???????????????"
            }
        } catch (ex: Exception) {}
    }
    private fun exit(view: View) {
        try{
            val inflater = LayoutInflater.from(context)
            val v: View = inflater.inflate(R.layout.dialog_exit, null)
            val dialog: AlertDialog.Builder = AlertDialog.Builder(context)
            val title = TextView(context)
            title.text = "??????"
            title.gravity = Gravity.CENTER
            title.textSize = 24f
            dialog.setCustomTitle(title)
            dialog.setView(v)
            val dlg: AlertDialog = dialog.show()
            var webapiClient = WebapiClient()
            v.logout_type1.setOnClickListener {
                var url = MainActivity.ip +
                        "PrdMgn/Logout?userBar=" + MainActivity.userBar +
                        "&Dept=" + MainActivity.dept + "&shiftName=??????"
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                Log.d("Logout", jsonString)
                val jsonStr = JSONObject(jsonString)
                MainActivity.Currjson= jsonString!!
                if (jsonStr.getString("Message").indexOf("??????")>-1){
                    view.btn_Unfinished.performClick()
                    dlg.dismiss()
                }else if (jsonStr.getString("Data").indexOf("??????")>-1){
                    try{
                        print_report(url, "??????", "1", jsonStr, view)
                    }catch (ex: Exception){}
                }else{
                    print_report(url, "??????", "0", jsonStr, view)
                }
            }
            v.logout_type2.setOnClickListener {
                var url = MainActivity.ip +
                        "PrdMgn/Logout?userBar=" + MainActivity.userBar +
                        "&Dept=" + MainActivity.dept + "&shiftName=??????"
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                Log.d("Logout", jsonString)
                val jsonStr = JSONObject(jsonString)
                MainActivity.Currjson= jsonString!!
                if (jsonStr.getString("Message").indexOf("??????")>-1){
                    view.btn_Unfinished.performClick()
                    dlg.dismiss()
                }else if (jsonStr.getString("Data").indexOf("??????")>-1){
                    try{
                        print_report(url, "??????", "1", jsonStr, view)
                    }catch (ex: Exception){}
                }else{
                    print_report(url, "??????", "0", jsonStr, view)
                }
            }
            v.logout_type3.setOnClickListener {
                var url = MainActivity.ip +
                        "PrdMgn/Logout?userBar=" + MainActivity.userBar +
                        "&Dept=" + MainActivity.dept + "&shiftName=??????"
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                val jsonStr = JSONObject(jsonString)
                Log.d("Logout", jsonString)
                MainActivity.Currjson= jsonString!!
                if (jsonStr.getString("Message").indexOf("??????")>-1){
                    view.btn_Unfinished.performClick()
                    dlg.dismiss()
                }else if (jsonStr.getString("Data").indexOf("??????")>-1){
                    try{
                        print_report(url, "??????", "1", jsonStr, view)
                    }catch (ex: Exception){}
                }else{
                    print_report(url, "??????", "0", jsonStr, view)
                }
            }
            val params: WindowManager.LayoutParams = dlg.window!!.getAttributes()
            params.width = 1200
            params.height = 700
            dlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f)
            dlg.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f)
            dlg.getWindow()!!.setAttributes(params)
            fun diss(){
                dlg.dismiss()
            }
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.mesage("???????????????????????????????????????????????????????????????", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }
    private fun print_report(url: String, logouttype: String, intall: String, jsonStr: JSONObject, view: View) {
        try{
            val builder = AlertDialog.Builder(context)
            builder.setMessage("???????????????????")
                    .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub
                        var purl = MainActivity.ip +
                                "PrdMgn/printDailyReport?flowStep=" +
                                MainActivity.dept + "&empID=" + MainActivity.userBar +
                                "&shiftName=" + logouttype
                        var webapiClient = WebapiClient()
                        Log.d("purl", purl)
                        try{
                            var jsonString: String? = webapiClient.requestPOST(purl, JSONObject())
                            Log.d("Logout", jsonString)
                            MainActivity.Currjson= jsonString!!
                        }catch (ex: Exception){
                        }
                        if (intall=="1"){
                            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(view)
                            ui_Helper.check_version_logout(context, view, jsonStr.getString("Data"), "3", url)
                            if (MainActivity.insted=="1"){
                                call_exit(url)
                            }
                        }else{
                            call_exit(url)
                        }
                    }
                    .setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                        if (intall=="1"){
                            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(view)
                            ui_Helper.check_version_logout(context, view, jsonStr.getString("Data"), "3", url)
                            if (MainActivity.insted=="1"){
                                call_exit(url)
                            }
                        }else{
                            call_exit(url)
                        }
                    }
            val about_dialog = builder.create()
            about_dialog.show()
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.mesage("???????????????????????????????????????????????????????????????", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }
    private fun call_exit(url: String) {
        try{
            /*var webapiClient = WebapiClient()
            var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
            val jsonStr = JSONObject(jsonString)
            val data = StringBuilder()
            val obj = JSONObject(jsonString)*/
            try{
                /*restart app in other activity for kotlin*/
                val packageManager = context.packageManager
                val intent = packageManager.getLaunchIntentForPackage(context.packageName)
                val componentName = intent.component
                val mainIntent = Intent.makeRestartActivityTask(componentName)
                context.startActivity(mainIntent)
                Runtime.getRuntime().exit(0)
            }catch (ex: Exception){
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                ui_Helper.mesage("???????????????????????????????????????????????????????????????", context)
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                ui_Helper.send_mail(stacktrace)
            }
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.mesage("???????????????????????????????????????????????????????????????", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }


    private fun flow_new_start(view: View, Start: String) {
        try {
            MainActivity.flow_json.put("popMsg", "")
            MainActivity.flow_json.put("currNaiBei", "")
            var sum_dept = ""
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            var start_cmd = ui_Helper.get_start_cmd(Start)
            //??????.??????1???2 ??? FLOW_STEP_CURR
            if (MainActivity.WAREHouse_QTY != "0"&&MainActivity.InputQuan=="0") { //?????????FLOW_STEP???????????????
                MainActivity.flow_json.put("FLOW_STEP", MainActivity.dept)
            }
            if (MainActivity.dept == "????????????") {
                if (MainActivity.check_userid!= "") {//????????????USER
                    MainActivity.flow_json.put("colorUser", MainActivity.check_userid)
                }
                if (Start.indexOf("??????1") > -1 || Start.indexOf("???????????????") > -1) {
                    MainActivity.flow_json.put("FLOW_STEP_CURR", "??????1")
                    MainActivity.flow_json.put("FLOW_STEP", "??????1")
                }
                if (Start.indexOf("??????2") > -1 || Start.indexOf("???????????????") > -1) {
                    MainActivity.flow_json.put("FLOW_STEP_CURR", "??????2")
                    MainActivity.flow_json.put("FLOW_STEP", "??????2")
                }
            }
            if (MainActivity.dept == "????????????") {
                if (Start.indexOf("??????1") > -1) {
                    val Start = "??????1"
                    MainActivity.flow_json.put("FLOW_STEP_CURR", Start)
                }
                if (Start.indexOf("??????2") > -1) {
                    val Start = "??????2"
                    MainActivity.flow_json.put("FLOW_STEP_CURR", Start)
                }
            }
            if (MainActivity.dept == "????????????") {
                sum_dept = MainActivity.sw_dept
            } else {
                sum_dept = MainActivity.dept
            }
            if (MainActivity.dept == "????????????"&&Start=="??????1") {
                sum_dept = "??????1"
            } else if (MainActivity.dept == "????????????"&&Start=="??????2") {
                sum_dept = "??????2"
            }
            //??????????????????
            if (MainActivity.dept == "????????????"&& !(start_cmd=="29")) {//???????????????????????????1,2
                if(MainActivity.ng!="0"){//???ng
                    if (MainActivity.FLOW_STEP_CURR == "??????1"){
                        start_cmd="26"
                    }else{
                        start_cmd="28"
                    }
                }else if (MainActivity.FLOW_STEP_CURR == "??????1"){
                    start_cmd="21"
                }else if (MainActivity.FLOW_STEP_CURR == "??????2"){
                    start_cmd="23"
                }
            }
            var url = MainActivity.ip + "PrdMgn/ScanOperate?command=" + start_cmd + "&UID=" +
                    MainActivity.userBar + "&MID=" + MainActivity.machineBar +
                    "&flowBar=" + MainActivity.flowbar + "&DEPT=" + sum_dept + "&jsonStr=" + MainActivity.flow_json.toString()

            if (Start.indexOf("?????????") > -1){
                if (Start.indexOf("???") > -1){
                    url = url + "&NGOptions=2"
                }else{
                    url = url + "&NGOptions=1"
                }
            }
            if (start_cmd=="22") {
                url = url + "&NGOptions=1"
            }
            //??????????????????
            if (MainActivity.dept == "????????????") {
                if (MainActivity.check_userid != "") {
                    url = url + "&preOperator=" + MainActivity.check_userid
                }
                if (MainActivity.ng != ""||MainActivity.ng != "??????????????????") {
                    url = url + "&subCommand=1&confirmed=2&NGOptions=" + MainActivity.ng
                }
            }
            //??????????????????
            if (MainActivity.dept == "????????????") {
                url = url + "&optionDept=????????????"
            }
            if (MainActivity.confirmed != "") {
                url = url + MainActivity.confirmed
            }
            MainActivity.confirmed=""
            sum_dept = MainActivity.dept
            //????????????????????????
            if (MainActivity.dept == "????????????") {
                if (Start.indexOf("???????????????") > -1) {
                    url = url + "&NGOptions=2"
                }else if (Start.indexOf("????????????") > -1) {
                    url = url + "&NGOptions=1"
                }
                if (Start.indexOf("??????") > -1) {
                    url = url + "&subCommand=1"
                }
            }
            try {
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                var json = JSONObject(jsonString)
                MainActivity.flow_message=json.getString("Message")
                if (json.getString("Message")=="??????"){
                    val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                    val msg=ui_Helper.confirmed(json.getString("Data"), "", context)
                    builder.setMessage(msg + "??????????????????????")
                    builder.setCancelable(false)
                    builder.setPositiveButton("????????????") { dialog, id ->
                        try {
                            url = url + "&confirmed=1"   //&confirmed=1 User ?????????????????? ????????????????????? Client ????????????
                            var webapiClient = WebapiClient()
                            var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                            var json = JSONObject(jsonString)
                            MainActivity.flow_json = JSONObject(json.getString("Data"))
                            var SubflowInfo = CustomLayoutSubflowInfo(context, null)
                            SubflowInfo.inputViewItems(MainActivity.flow_json, rootView)
                        }catch (ex: Exception){
                            ui_Helper.mesage("???????????????????????????????????????????????????????????????", context)
                            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                            ui_Helper.send_mail(stacktrace)
                        }
                    }
                    builder.setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                    }
                    builder.show()
                }
                else if (json.getString("Message")=="??????"){
                    ui_Helper.POPmsgAction(json.getString("Data"), "??????", context)
                }else{
                    if (json.getString("Message").indexOf("3-8") > -1
                            || json.getString("Message").indexOf("2-17") > -1
                            || json.getString("Message").indexOf("3-7") > -1
                    // ||Message2.indexOf("1-14") > -1
                    ) { //????????????
                        val inflater = LayoutInflater.from(context)
                        val v: View = inflater.inflate(com.example.firstohm_produce_kotlin.R.layout.dialog_last_finsh, null)
                        val dialog = AlertDialog.Builder(context)
                        dialog.setView(v)
                        v.title_info.setText("????????????????????????(" + MainActivity.per_USER_ID + ")???????????????????????????????????????")
                        v.input_edit.setText(MainActivity.InputQuan)
                        v.input_edit.setEnabled(false);
                        dialog.setPositiveButton("??????") { dialog, which ->
                            if(v.output_edit.text.toString()==""){v.output_edit.setText("0")}
                            if(v.DefectQuan_edit.text.toString()==""){v.DefectQuan_edit.setText("0")}
                            if(v.StepLeft_edit.text.toString()==""){v.StepLeft_edit.setText("0")}
                            MainActivity.flow_json.put("OutputQuan", v.output_edit.text.toString())
                            MainActivity.flow_json.put("DefectQuan", v.DefectQuan_edit.text.toString())
                            MainActivity.flow_json.put("StepLeft", v.StepLeft_edit.text.toString())
                            var url = MainActivity.ip + "PrdMgn/ScanOperate?command=50&UID="+
                                    MainActivity.userBar + "&flowBar=" + MainActivity.flowbar + "&DEPT=" + MainActivity.dept +
                                    "&MID=" + MainActivity.machineBar+"&jsonStr=" + MainActivity.flow_json.toString()
                            var webapiClient = WebapiClient()
                            var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                            var json = JSONObject(jsonString)
                            MainActivity.flow_message=json.getString("Message")
                            try {
                                var json_Data = JSONObject(json.getString("Data"))
                                val SubflowInfo = CustomLayoutSubflowInfo(context, null)
                                SubflowInfo.inputViewItems(json_Data, rootView)
                            } catch (ex: Exception) {
                                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                                    ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, context)
                            }
                        }
                        dialog.setNeutralButton("??????") { dialog, which ->
                            //mothing
                        }
                        val dlg = dialog.show()
                        val params = dlg.window.attributes
                        params.width = 1200
                        params.height = 700
                        dlg.getButton(AlertDialog.BUTTON_POSITIVE).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f)
                        dlg.getButton(AlertDialog.BUTTON_NEUTRAL).setTextSize(TypedValue.COMPLEX_UNIT_SP, 25.0f)
                        dlg.window.attributes = params
                    }else{
                        try {
                            if (json.getString("Message").indexOf("??????")>-1){
                                val separated: Array<String> = json.getString("Message").split("\\r\\n\\r\\n".toRegex()).toTypedArray()
                                val builder = AlertDialog.Builder(context)
                                builder.setMessage("?????????????????????????????????????(???????????????????????????)" + separated[1])
                                        .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub
                                            var msg="?????????"+MainActivity.mfo_id+"??????"+MainActivity.BATSEQ+"/"+MainActivity.BATCH_NO+separated[1]+
                                                    "??????"+MainActivity.dept+"?????????"+MainActivity.userBar+"???????????????"
                                            ui_Helper.send_mail(msg)
                                            MainActivity.confirmed="&confirmed=2"
                                            flow_new_start(view, Start)
                                        }
                                        .setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                                        }
                                val about_dialog = builder.create()
                                about_dialog.show()
                            }else if(json.getString("Message").indexOf("error")>-1
                                    ||json.getString("Message").indexOf("**")>-1
                                    ||json.getString("Message").indexOf("??????")>-1){
                               //??????show msg
                               ui_Helper.mesage(json.getString("Message"), context)
                            }else{
                                //????????????
                                var json_Data = JSONObject(json.getString("Data"))
                                MainActivity.flow_json = json_Data
                                view.input_edit.setText(json_Data.getString("InputQuan"))
                                val SubflowInfo = CustomLayoutSubflowInfo(context, null)
                                SubflowInfo.inputViewItems(json_Data, rootView)
                            }
                        } catch (ex: Exception) {
                            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                            ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, context)
                        }
                    }
                }
            }catch (ex: Exception){
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, context)
            }
        } catch (ex: Exception) {
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, context)
        }

    }
    private fun flow_finsh(view: View) {
        //???????????????????????????SIGN????????????????????????0??????
        MainActivity.flow_json.put("popMsg", "")
        if(MainActivity.sumOfNaiBei!="null"&&MainActivity.dept=="????????????"&&(view.output_edit.text.toString()!="0"|| view.DefectQuan_edit.text.toString()!="0")){


            val builder = AlertDialog.Builder(context)
            builder.setMessage("???????????????????????????????????????????????????")
                    .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub

                    }
            val about_dialog = builder.create()
            about_dialog.show()
        }else{
            try {
                if (view.input_edit.text.toString()!=MainActivity.InputQuan
                        &&MainActivity.WAREHouse_QTY=="0"
                        &&(MainActivity.dept != "????????????")){
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("????????????????????????????????????????????????????")
                            .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub
                                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                                MainActivity.flow_json.put("note", "????????????????????????" + MainActivity.InputQuan + "??????" + view.input_edit.text.toString() + "?????????" + MainActivity.userBar)
                                ui_Helper.send_mail(MainActivity.mfo_id + "????????????????????????" + MainActivity.InputQuan + "??????" + view.input_edit.text.toString() + "?????????" + MainActivity.userBar)
                                flow_finsh2(view)
                            }
                            .setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                                view.input_edit.setText(MainActivity.InputQuan.toString())
                            }
                    val about_dialog = builder.create()
                    about_dialog.show()
                }else if (view.output_edit.text.toString()=="" ||view.DefectQuan_edit.text.toString()==""){
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("??????????????????????????????")
                            .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub

                            }
                    val about_dialog = builder.create()
                    about_dialog.show()
                }else if (view.speed_value.text.toString()=="" &&(MainActivity.dept == "????????????")){
                    //dialog_speeddinput
                    // view.btn_sped.performClick()
                    val builder = AlertDialog.Builder(context)
                    builder.setMessage("???????????????")
                            .setPositiveButton("???") { dialog, which -> // TODO Auto-generated method stub

                            }
                    val about_dialog = builder.create()
                    about_dialog.show()
                }else{
                    flow_finsh2(view)
                }
            } catch (ex: Exception) {
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                ui_Helper.get_Exception(stacktrace,"??????????????????????????????", context)
            }
        }
    }
    public fun flow_finsh2(view: View) {
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        if (MainActivity.cow_user!=""){
            MainActivity.flow_json.put("coworker", MainActivity.cow_user)
        }
        try {
            if (view.Radio_tdroll.isChecked == true && MainActivity.dept == "????????????") {//??????
                val new_output = view.output_edit.text.toString().toFloat()
                val Defect = view.DefectQuan_edit.text.toString().toFloat()
                val StepLeft = view.StepLeft_edit.text.toString().toFloat()
                val new_roll = MainActivity.tdRollQty.toFloat()
                val OutputQuan = new_output * new_roll*1000
                val new_Defect = Defect * new_roll*1000
                val new_StepLeft = StepLeft * new_roll*1000
                MainActivity.flow_json.put("OutputQuan", OutputQuan)
                MainActivity.flow_json.put("DefectQuan", new_Defect)
                MainActivity.flow_json.put("StepLeft", StepLeft)
            } else {//??????
                if (MainActivity.dept == "????????????") {
                    var weight = ui_Helper.get_size(MainActivity.flow_json.get("Size").toString())
                    val OutputQuan = Integer.valueOf(view.output_edit.text.toString()) * Integer.valueOf(weight)
                    MainActivity.flow_json.put("OutputQuan", OutputQuan)
                } else {
                    MainActivity.flow_json.put("OutputQuan", view.output_edit.text.toString())
                }
                MainActivity.flow_json.put("DefectQuan", view.DefectQuan_edit.text.toString())
                MainActivity.flow_json.put("StepLeft", view.StepLeft_edit.text.toString())
            }
            if(view.input_edit.text.toString()!=""){
                MainActivity.flow_json.put("InputQuan", view.input_edit.text.toString())
            }
        } catch (ex: Exception) {
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.mesage("???????????????????????????????????????????????????????????????", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
        if (MainActivity.dept == "????????????"){
            MainActivity.flow_json.put("colorUser", MainActivity.check_userid)
        }
        if (MainActivity.dept == "????????????"&&!view.terrible_spinner.getSelectedItem().toString().equals("0")){
            MainActivity.flow_json.put("note", "??????X" + view.terrible_spinner.getSelectedItem().toString())
        }
        if (MainActivity.dept == "????????????" || MainActivity.dept == "????????????") {//???????????????
            if (view.speed_value.text.toString() == "") {
                view.speed_value.setText("0")
            }
            MainActivity.flow_json.put("machineSpeed", view.speed_value.text.toString())
        }
        var sum_dept = ""
        if (MainActivity.dept == "????????????") {
            sum_dept = MainActivity.sw_dept
        } else {
            sum_dept = MainActivity.dept
        }
        if (view.note_edit.text.toString()!=""){
            MainActivity.flow_json.put("note", view.note_edit.text.toString())
        }
        //????????????
        MainActivity.flow_json.put("InputQuan", view.input_edit.text)
        var finsh_cmd = ui_Helper.get_finsh_cmd()
        var url = MainActivity.ip + "PrdMgn/ScanOperate?command="+finsh_cmd+"&UID=" +
                MainActivity.userBar + "&MID=" + MainActivity.machineBar +
                "&flowBar=" + MainActivity.flowbar + "&DEPT=" + sum_dept + "&jsonStr=" + MainActivity.flow_json
        if (MainActivity.dept == "????????????") {
            url = url + "&optionDept=????????????"
        }
        var webapiClient = WebapiClient()
        var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
        var json = JSONObject(jsonString)
        MainActivity.flow_message=json.getString("Message")
        try {
                if (json.getString("Message")=="??????"){
                    val builder = androidx.appcompat.app.AlertDialog.Builder(context)
                    val msg=ui_Helper.confirmed(json.getString("Data"), "", context)
                    builder.setMessage(msg + "??????????????????????")
                    builder.setCancelable(false)
                    builder.setPositiveButton("????????????") { dialog, id ->
                        try {
                            url = url + "&confirmed=1"   //&confirmed=1 User ?????????????????? ????????????????????? Client ????????????
                            var webapiClient = WebapiClient()
                            var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                            var json = JSONObject(jsonString)
                            MainActivity.flow_json = JSONObject(json.getString("Data"))
                            MainActivity.flow_message=json.getString("Message")
                            var SubflowInfo = CustomLayoutSubflowInfo(context, null)
                            SubflowInfo.inputViewItems(MainActivity.flow_json, rootView)
                        }catch (ex: Exception){
                            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                            ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, context)
                        }
                    }
                    builder.setNegativeButton("???") { dialog, which -> // TODO Auto-generated method stub
                    }
                    builder.show()
                }
                else if (json.getString("Message")=="??????"){
                    ui_Helper.POPmsgAction(json.getString("Data"), "??????", context)
                }else{//????????????
                    var json = JSONObject(jsonString)
                    var json_Data = JSONObject(json.getString("Data"))
                    MainActivity.flow_json = json_Data
                    val SubflowInfo = CustomLayoutSubflowInfo(context, null)
                    SubflowInfo.inputViewItems(json_Data, rootView)
                }

            }catch (ex: Exception){
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, context)
                }
            view.DefectQuan_edit.setText("0")
        view.output_edit.setText("0")
        view.terrible_spinner.setSelection(0)

        view.get_machins.performClick()
        view.note_edit.setText("")
    }

    private fun flow_part_finsh(view: View) {
        try {
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            MainActivity.flow_json.put("InputQuan", view.input_edit.text)
            MainActivity.flow_json.put("OutputQuan", view.output_edit.text)
            MainActivity.flow_json.put("DefectQuan", view.DefectQuan_edit.text)
            MainActivity.flow_json.put("StepLeft_edit", view.StepLeft_edit.text)
            MainActivity.flow_json.put("SIGNID", MainActivity.SIGNID)
            var url = MainActivity.ip + "PrdMgn/ScanOperate?command=9&UID=" +
                    MainActivity.userBar + "&MID=" + MainActivity.machineBar +
                "&flowBar=" + MainActivity.flowbar + "&DEPT=" + MainActivity.dept + "&jsonStr=" + MainActivity.flow_json
                var webapiClient = WebapiClient()
                var jsonString: String? = webapiClient.requestPOST(url, JSONObject())
                var json = JSONObject(jsonString)
                var json_Data = JSONObject(json.getString("Data"))
                MainActivity.flow_message= json.getString("Message")
                val SubflowInfo = CustomLayoutSubflowInfo(context, null)
                SubflowInfo.inputViewItems(json_Data, rootView)
                ui_Helper.mesage("???????????????", context)
                view.DefectQuan_edit.setText("0")
                view.output_edit.setText("0")
        } catch (ex: Exception) {
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.get_Exception(stacktrace,MainActivity.flow_message, context)
        }
    }
}