package com.example.firstohm_produce_kotlin

import android.Manifest
import android.app.DownloadManager
import android.content.*
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.media.audiofx.BassBoost
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import co.ubunifu.kotlinhttpsample.Lib.*
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_inquire.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_info.*
import kotlinx.android.synthetic.main.custom_layout_info.view.*
import kotlinx.android.synthetic.main.custom_layout_main_button.*
import kotlinx.android.synthetic.main.custom_layout_main_button.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_color.*
import kotlinx.android.synthetic.main.custom_layout_quant_color.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.plating_manual_listview.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainActivity : AppCompatActivity() {
    override fun onResume() {
        super.onResume()
        Log.d("onResume", "onResume")
        //check_version()
        try{
            val pInfo: PackageInfo = this@MainActivity.getPackageManager().getPackageInfo(this@MainActivity.getPackageName(), 0)
            var version = pInfo.versionName
            version = pInfo.versionName
            version_value.setText(version)
            if (MainActivity.ip.indexOf("33")>-1)version_value.setText(Companion.version + "\n測試模式")
            if (userBar!=""&&machineBar==""){
                login()
            }
            if (machineBar!=""){
                mid_text.text=machineBar
            }
        }catch (ex: Exception){
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        val Code_PERMISSION = 0
        ActivityCompat.requestPermissions(this, arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.REQUEST_INSTALL_PACKAGES,
                android.Manifest.permission.READ_EXTERNAL_STORAGE), 0)
        try {
            super.onCreate(savedInstanceState)
            var activity: MainActivity? = null
            setContentView(R.layout.activity_main)
            this.supportActionBar?.hide()  //hide title bar
        }catch (ex: Exception){
            Log.d("1", ex.message)
        }

        val pInfo: PackageInfo = this@MainActivity.getPackageManager().getPackageInfo(this@MainActivity.getPackageName(), 0)
        var version = pInfo.versionName
        MainActivity.version = pInfo.versionName
        val builder = VmPolicy.Builder()

        StrictMode.setVmPolicy(builder.build())

        builder.detectFileUriExposure()
        try {
            if (Build.VERSION.SDK_INT > 9) {
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            }
            MainActivity.context = getApplicationContext();
            setting_flag="0"
            val cpuInfo = readCpuInfo()
            if ((cpuInfo.contains("Intel") || cpuInfo.contains("amd"))) {
                local_test_layout.visibility= View.VISIBLE
            }
            local_test_layout.visibility= View.VISIBLE

            if (userBar==""){
                mainarea.visibility= View.GONE
            }else{
                btn_start_login.visibility= View.GONE
                relogin.visibility=View.GONE
            }
            btn_start_login.setOnClickListener{
                btn_start_login.visibility= View.GONE
                val scanIntegrator = IntentIntegrator(this)
                scanIntegrator.initiateScan()
            }
            test_button.setOnClickListener{1
                if ((cpuInfo.contains("Intel") || cpuInfo.contains("amd"))) {
                    userBar="C_004"
                }
                val pInfo: PackageInfo = this@MainActivity.getPackageManager().getPackageInfo(this@MainActivity.getPackageName(), 0)
                version = pInfo.versionName
                version_value.setText(version)
                if (MainActivity.ip.indexOf("33")>-1)version_value.setText(version + "\n測試模式")
                login()
            }
            test_flow.setOnClickListener{
                if ((cpuInfo.contains("Intel") || cpuInfo.contains("amd"))) {
                    //flowbar="21892-38-2061568-0-%"
                    flowbar="23413-4-2065508-0-%"
                }
                msg="1"
                get_flow()
            }
            btn_sendline.setOnClickListener{
                Dialogline().show(supportFragmentManager, "MyCustomFragment")
            }
            /*預設值*/
            ng=""
            color_direction="rigth"
            facrory="1"
            mStatus="0"
            cow="0"
            super_check="0"
            msg="0"
            val saveip = getPreferences(MODE_PRIVATE)
            ip = saveip.getString("ip", "http://172.168.1.151:1119/firstohmWebApi/")
            mail = saveip.getString("mail", "")
            dept = saveip.getString("dept", "花蓮切割")
            login = saveip.getString("login", "")
            table_custom_name = saveip.getString("table_custom_name", "")
            mail_list= mail
            /*預設值*/
            //login=1 閃退 =0正常
            per_user = saveip.getString("per_user", "")
            if (login=="1"){
                relogin.visibility=View.GONE
                sance_btn.visibility=View.VISIBLE
                if (userBar!="")
                    info_layout.visibility=View.VISIBLE
            }else{
                relogin.visibility=View.VISIBLE
                sance_btn.visibility=View.GONE
                info_layout.visibility=View.GONE
            }
            if (userBar==""){
                logo.visibility=View.VISIBLE
                if(per_user==""){
                    relogin.visibility=View.GONE
                    btn_start_login.visibility=View.VISIBLE

                }else{
                    btn_start_login.visibility=View.GONE
                    relogin.visibility=View.VISIBLE
                }
            }else{
                logo.visibility=View.GONE
            }
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy)
            val rootView = window.decorView.rootView
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            relogin.setOnClickListener {
                try {
                    relogin.visibility=View.GONE
                    sance_btn.visibility=View.VISIBLE
                    userBar= per_user
                    btn_start_login.visibility= View.GONE
                    login()
                } catch (ex: Exception) {
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.send_mail(stacktrace)
                }
            }
            wifi.setOnClickListener{
                wifi_test()
            }
            btn_cow.setOnClickListener {
                val builder = androidx.appcompat.app.AlertDialog.Builder(this@MainActivity)
                builder.setMessage("合作")
                builder.setCancelable(false)
                builder.setPositiveButton("確認") { dialog, id ->
                    cow="1"
                    val scanIntegrator = IntentIntegrator(this)
                    scanIntegrator.initiateScan()
                }
                builder.setNegativeButton("取消") { dialog, which -> }
                builder.show()
            }
            btn_setting.setOnClickListener {
                startActivity(Intent(this@MainActivity, setting::class.java))
            }
            btn_setting_ADMIN.setOnClickListener {
                startActivity(Intent(this@MainActivity, setting::class.java))
            }
            btn_plating.setOnClickListener {
                startActivity(Intent(this@MainActivity, platingActivity::class.java))
            }
            btn_editflow.setOnClickListener {
                startActivity(Intent(this@MainActivity, editflowActivity::class.java))
            }
            test_cnt_btn.setOnClickListener{
                DialogPrdtest().show(supportFragmentManager, "MyCustomFragment")
            }
            out_chk_btn.setOnClickListener{
                outcheck_step= "花蓮底漆"
                Dialogoutcheck_2().show(supportFragmentManager, "MyCustomFragment")
            }
            out_chk_btn2.setOnClickListener{
                outcheck_step= "花蓮色碼"
                Dialogoutcheck_2().show(supportFragmentManager, "MyCustomFragment")
            }
            btn_Inquire.setOnClickListener{
                startActivity(Intent(this@MainActivity, InquireActivity::class.java))
            }
            bnt_selectMachins.setOnClickListener{
                speed_value.setText("")
                startActivity(Intent(this@MainActivity, select_Machins::class.java))
            }
            btn_material_issue.setOnClickListener{
                DialogMaterialIssue().show(supportFragmentManager, "MyCustomFragment")
            }
            out_chk_def_btn.setOnClickListener{
                DialogChkdef().show(supportFragmentManager, "MyCustomFragment")
            }
            btn_colorsearch.setOnClickListener{
                startActivity(Intent(this@MainActivity, colorsearchActivity::class.java))
            }
            sance_btn.setOnClickListener{
                val scanIntegrator = IntentIntegrator(this)
                scanIntegrator.initiateScan()
            }
            //不良品入庫
            btn_wherehouse_def.setOnClickListener {
                try {
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    val quant=DefectQuan_edit.text.toString()
                    if(quant==""||quant=="0" ){


                        ui_Helper.mesage("請輸入入庫數量", this@MainActivity)
                    }else{
                        defectQuan=DefectQuan_edit.text.toString()
                        Dialogsemidef().show(supportFragmentManager, "MyCustomFragment")
                    }
                } catch (ex: Exception) {
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.send_mail(stacktrace)
                }
            }
            //良品入庫
            btn_wherehouse_semi.setOnClickListener {
                try {
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    val quant=DefectQuan_edit.text.toString()
                    if(quant==""){
                        ui_Helper.mesage("請輸入入庫數量", this@MainActivity)
                    }else{
                        Dialogsemioutput().show(supportFragmentManager, "MyCustomFragment")
                    }
                } catch (ex: Exception) {
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                    ui_Helper.send_mail(stacktrace)
                }
            }
            //拆單
            btn_spilt.setOnClickListener {
                val rootView = window.decorView.rootView
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                if (splitQuan.text.toString() == "") {
                    ui_Helper.mesage("無輸入拆單量", this@MainActivity)
                } else {
                    try {
                        val builder = androidx.appcompat.app.AlertDialog.Builder(this@MainActivity)
                        var BATSEQ1: Int = Integer.valueOf(MainActivity.BATSEQ)
                        BATSEQ1=BATSEQ1+1
                        builder.setMessage(mfo_id + "\t \t  " + BATSEQ1 + "/" + MainActivity.BATCH_NO + " \t\t  即將拆單，拆單數量: " + splitQuan.text.toString() + "  是否確認拆單?")
                        builder.setCancelable(false)
                        builder.setPositiveButton("確認") { dialog, id ->
                            try {
                                var floatsplitQuan : Float = splitQuan.text.toString().toFloat();
                                var floatinput : Float = InputQuan.toString().toFloat();
                                try {
                                    var webapiClient = WebapiClient()
                                    var url=MainActivity.ip+
                                            "PrdMgn/ScanOperate?command=35&UID=" + MainActivity.userBar +
                                            "&splitQuan=" +(splitQuan.text.toString())+
                                            "&flowBar=" + MainActivity.flowbar +
                                            "&DEPT=" + MainActivity.dept +
                                            "&MID=" + MainActivity.machineBar +
                                            "&jsonStr=" + MainActivity.flow_json
                                    var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
                                    val jsonStr = JSONObject(jsonString)
                                    Log.d("Message111", jsonStr.getString("Message"))
                                    if(jsonStr.getString("Message").indexOf("錯誤")>-1){
                                        ui_Helper.mesage(jsonStr.getString("Message"), this@MainActivity)
                                    }else{
                                        val rtnData =  JSONObject(jsonStr.getString("Data"))
                                        flow_message="已拆單 速碼: "+rtnData.getString("SUBFLOWID")
                                        mainmessage.text=flow_message
                                        ui_Helper.mesage(flow_message, this@MainActivity)
                                        test_flow.performClick()
                                    }
                                }catch (ex: Exception){
                                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
                                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                                    ui_Helper.send_mail(stacktrace)
                                }
                                splitQuan.setText("")
                            }catch (ex: Exception){
                                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                                ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
                                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                                ui_Helper.send_mail(stacktrace)
                            }
                        }
                        builder.setNegativeButton("取消") { dialog, which -> }
                        builder.show()
                    } catch (ex: Exception) {
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                        ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@MainActivity)
                        val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                        ui_Helper.send_mail(stacktrace)
                    }
                }
            }
            shift_bnt.setOnClickListener {
                startActivity(Intent(this@MainActivity, shift_flow::class.java))
            }
            more_option_btn.setOnClickListener {
                startActivity(Intent(this@MainActivity, more_option::class.java))
            }
            btn_getspilinfo.setOnClickListener {//選取拆單
                Dialoglist().show(supportFragmentManager, "MyCustomFragment")
            }
            change_Status.setOnClickListener {
                try {
                    if(mStatus=="0"){
                        mStatus="2"
                    }else{
                        mStatus="0"
                    }
                    if(machineBar==""){
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                        ui_Helper.mesage("未選擇機台", this@MainActivity)
                    }else
                    {
                        var webapiClient = WebapiClient()
                        var url=MainActivity.ip+
                                "PrdMgn/ScanOperate?command=10&UID=" + MainActivity.userBar + "&flowBar=" + MainActivity.flowbar +
                                "&DEPT=" + MainActivity.dept + "&MID=" + MainActivity.machineBar + "&mStatus="+ MainActivity.mStatus
                        var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
                        val jsonStr = JSONObject(jsonString)
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                        if(mStatus=="2"){
                            ui_Helper.mesage("已變更$machineBar 機台為故障中", this@MainActivity)
                        }else{
                            ui_Helper.mesage("已變更$machineBar 機台為待機中", this@MainActivity)
                        }
                    }
                }catch (ex: Exception){
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@MainActivity)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            btn_shortSubFlow.setOnClickListener {
                //速碼
                try{
                        val rootView = window.decorView.rootView
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                        if(shortSubFlow.text.toString()==""){
                            ui_Helper.mesage("無輸入速碼", this@MainActivity)
                        }else{
                            short=shortSubFlow.text.toString()
                            DefectQuan_edit.setText("0")
                            Dialoglist_flowshort().show(supportFragmentManager, "MyCustomFragment")
                        }
                    }catch (ex: Exception){
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                        ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", this@MainActivity)
                        val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                        ui_Helper.send_mail(stacktrace)
                    }

            }
            btn_sped.setOnClickListener {
                dialog_speeddinput().show(supportFragmentManager, "MyCustomFragment")
            }
            btn_Unfinished.setOnClickListener {
                Dialoglist_Unfinished().show(supportFragmentManager, "MyCustomFragment")
            }
            time_test.setOnClickListener {
                val rootView = window.decorView.rootView
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                var return_string= ui_Helper.TimestampToDatetime(MainActivity.START_TIME)
                ui_Helper.mesage(return_string, this@MainActivity)
            }
            btn_dept_sw.setOnClickListener {
                if (sw_dept=="花蓮底漆"){
                    btn_dept_sw.text="花蓮色碼"
                    sw_dept="花蓮色碼"
                }else{
                    btn_dept_sw.text="花蓮底漆"
                    sw_dept="花蓮底漆"
                }
            }
            if(!flowbar.equals("")){
                test_flow.performClick();
            }
            if(!userBar.equals("")){
                //login()
                val rootView = window.decorView.rootView
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                ui_Helper.login(User_json, rootView, this@MainActivity)
                info_textView.setText("部門:$DEPT\t\t\t員工:$EMPNAME\t\t\t  目前製程 : " + MainActivity.dept)
            }
            // Get the Intent that started this activity and extract the string
            try {
                val out_chk_btn = findViewById<Button>(R.id.out_chk_btn).apply {
                    if (select_user!=""){
                        text = select_user
                    }
                }
                val input_edit = findViewById<EditText>(R.id.input_edit).apply {
                    //外檢user input quant
                    val regEx = "[^0-9]"
                    val p: Pattern = Pattern.compile(regEx)
                    val m: Matcher = p.matcher(select_user)
                    System.out.println(m.replaceAll("").trim())
                    val t=m.replaceAll("").trim().toString()
                    input_edit.setText(t)
                }
                val DefectQuan_edit = findViewById<TextView>(R.id.DefectQuan_edit).apply {
                    text = NG_SUM
                }
                val mid = findViewById<TextView>(R.id.mid_text).apply {
                    text = machineBar
                }
            }catch (ex: Exception){}

            Supervisor_check.setOnClickListener{
                try {
                    super_check="1"
                    val scanIntegrator = IntentIntegrator(this)
                    scanIntegrator.initiateScan()
                } catch (ex: Exception) {
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            btn_del.setOnClickListener{
                try {
                    val dir = filesDir
                    val file = File(dir, "1.apk")
                    val deleted = file.delete()
                } catch (ex: Exception) {
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            btn_update.setOnClickListener{
                if (shiftName=="早班"  ||shiftName=="辦公室"){
                    download_apk()
                }
            }
            intalltest.setOnClickListener{
                installAPK()
            }
            savebtn.setOnClickListener{
                try {
                    var sharedPref : SharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
                    sharedPref.edit().putString("login", "0").commit()
                    sharedPref.edit().putString("ip", MainActivity.ip).commit()
                    sharedPref.edit().putString("dept", MainActivity.dept).commit()
                    sharedPref.edit().putString("mail", MainActivity.mail.toString()).commit()
                    sharedPref.edit().putString("per_user", "").commit()
                    sharedPref.edit().putString("table_custom_name", MainActivity.table_custom_name).commit()
                    sharedPref.edit().putString("facrory", MainActivity.facrory.toString()).commit()
                    sharedPref.edit().putString("login", "1").commit()
                    sharedPref.edit().apply();
                    logout_btn.performClick()
                } catch (ex: Exception) {
                    val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
                    ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
                    val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                    ui_Helper.send_mail(stacktrace)
                }
            }
            Log.i("Display : ", Build.DISPLAY);
            if (((android.os.Build.MANUFACTURER.indexOf("LENOVO") > -1 ) &&
                            (android.os.Build.PRODUCT.indexOf("X306F") > -1 )
                            )){ //NO 華為
                val main_ut = LinearLayout.LayoutParams(1200, 550)
                main_layout.setLayoutParams(main_ut)
            }else if (((android.os.Build.MANUFACTURER.indexOf("LENOVO") > -1 ) &&
                            (android.os.Build.PRODUCT.indexOf("X605F") > -1 )
                            )){ //NO 華為
                val main_ut = LinearLayout.LayoutParams(1800, 850)
                main_layout.setLayoutParams(main_ut)
            }else {
                val main_ut = LinearLayout.LayoutParams(1200, 500)
                main_layout.setLayoutParams(main_ut)
            }
            mainmessage.setText(android.os.Build.MANUFACTURER + Build.PRODUCT + Build.ID)
            Log.e("TAG", "MAC Build : $macAddress"+android.os.Build.MANUFACTURER + Build.PRODUCT + Build.ID)
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            val macAddress1 = ui_Helper.getMacAddr()
            macAddress=macAddress1.toString()
            var table=MainActivity.macAddress.replace(":", "")
            table_name_value = table.substring(8, 12);
            Log.e("TAG", "MAC Address : $macAddress")
            StepLeft_edit.setEnabled(false)
        } catch (ex: PackageManager.NameNotFoundException) {
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
        restart.setOnClickListener{
            val i1 = baseContext.packageManager.getLaunchIntentForPackage(
                    baseContext.packageName
            )
            i1!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i1) //restart app
        }

        //Storage Permission


        //Storage Permission
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        }
    }
    fun getAppContext(): Context? {
        return MainActivity.context
    }
    public fun get_flow_forselect(){
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        try{
            var SubflowInfo = CustomLayoutSubflowInfo(this@MainActivity, null)
            var flow_json=ui_Helper.get_subflowInfo_by_short(str_shortSubFlow, userBar, dept, rootView, this@MainActivity)
            SubflowInfo.inputViewItems(flow_json, rootView)
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
        mainmessage.text=MainActivity.flow_message
    }
    fun installAPK() {
        val PATH = Environment.getExternalStorageDirectory().toString()+
                "/Download/" + "firstohm_produce"+installv+".apk"
        val file = File(PATH)
        if (file.exists()) {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(uriFromFile(applicationContext, File(PATH)),
                    "application/vnd.android.package-archive")
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            try {
                applicationContext.startActivity(intent)
                finish()
            } catch (e: ActivityNotFoundException) {
                e.printStackTrace()
                Log.e("TAG", "Error in opening the file!")
            }
        } else {
            Toast.makeText(applicationContext, "installing", Toast.LENGTH_LONG).show()
        }
    }

    fun uriFromFile(context: Context?, file: File?): Uri? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context!!, BuildConfig.APPLICATION_ID + ".provider", file!!)
        } else {
            Uri.fromFile(file)
        }
    }
    public fun download_apk(){
        try {

            Log.d("00000000000033333333333", new_ver)
            if (PopMsg.indexOf("安裝APP版本")>-1){
                var new_ver = PopMsg.replace("8安裝APP版本:", "")
                new_ver = new_ver.replace("安裝APP版本:", "")
                new_ver = new_ver.replace("PopMsg:", "")
                new_ver = new_ver.replace(";", "")
                PopMsg=new_ver
                Log.d("version==>", new_ver)
                Log.d("000000000000", new_ver)
            }
            val separated: Array<String> = PopMsg.split(",".toRegex()).toTypedArray()
            var destination = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
            val fileName = "firstohm_produce"+separated[2]+".apk"
            destination += fileName
            Log.d("url", fileName)

            Log.d("000000000000111111111111", new_ver)
            val uri = Uri.parse("file://$destination")
            val file = File(destination)
            if (file.exists()) //file.delete() - test this, I think sometimes it doesnt work
                file.delete()
            //151 /htdocs/release/
            //val url: String = "http://172.168.1.151:8080/release/firstohm_produce$PopMsg.apk"
            val url: String = "http://172.168.1.151:8080/release/firstohm_produce"+separated[2]+".apk"
            Log.d("url", url)
            installv=separated[2]

            Log.d("00000000000002222222222222222", new_ver)
            val request = DownloadManager.Request(Uri.parse(url))
            request.setDescription("")
            request.setTitle("")
            request.setDestinationUri(uri)
            val manager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            val downloadId = manager.enqueue(request)
            try{
                val onComplete: BroadcastReceiver = object : BroadcastReceiver() {
                    override fun onReceive(ctxt: Context, intent: Intent) {
                        try {
                            var webapiClient = WebapiClient()
                            var url=MainActivity.ip+
                                    "PrdMgn/installLog?" +
                                    "version_no=" +separated[2]+
                                    "&appType=一般製程" +
                                    "&macAdd=" + macAddress+
                                    "&dept=" + dept+
                                    "&pdaName=" +table_name_value+
                                    "&empid="+MainActivity.userBar
                            var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
                        }catch (ex: Exception){}
                        installAPK()
                        /*
                        val install = Intent(Intent.ACTION_INSTALL_PACKAGE)
                        install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        install.setDataAndType(uri, manager.getMimeTypeForDownloadedFile(downloadId))
                        install.setDataAndType(uri, "application/vnd.android.package-archive");
                        startActivity(install)
                        unregisterReceiver(this)
                        finish()*/
                    }
                }
                registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
            } catch (ex: Exception) {
                val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
                val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                ui_Helper.get_Exception(stacktrace, MainActivity.flow_message, context)
            }
        } catch (ex: Exception) {
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.get_Exception(stacktrace, MainActivity.flow_message, context)
        }
    }

    public fun get_flow_SIGNID(){
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        try{
            var SubflowInfo = CustomLayoutSubflowInfo(this@MainActivity, null)
            var flow_json=ui_Helper.get_subflowInfo_by_SIGNID(str_shortSubFlow, userBar, dept, rootView, this@MainActivity)
            SubflowInfo.inputViewItems(flow_json, rootView)
        }catch (ex: Exception){
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.get_Exception(stacktrace, MainActivity.flow_message, context)
        }
        mainmessage.text=MainActivity.flow_message
    }
    private fun wifi_test() {
        try {
            val wifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
            wifiManager.setWifiEnabled(false);
            Thread.sleep(1000);
            wifiManager.setWifiEnabled(true);
            var count = 0
            while (!wifiManager.isWifiEnabled) {
                if (count >= 10) {
                    Log.i("TAG", "Took too long to enable wi-fi, quitting")
                    return
                }
                Log.i("TAG", "Still waiting for wi-fi to enable...")
                try {
                    Thread.sleep(1000L)
                } catch (ie: InterruptedException) {
                    // continue
                }
                count++
            }
            wifi_test2()
        }catch (ex: Exception){
            Log.d("1", ex.message)
        }
    }
    private fun wifi_test2() {
        Thread.sleep(5000);
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        try {
            var SubflowInfo = CustomLayoutSubflowInfo(this@MainActivity, null)
            var webapiClient = WebapiClient()
            var url = "http://172.168.1.33:1111/firstohmWebApi/ProdFlow/TestConnection"
            var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
            val jsonStr = JSONObject(jsonString)
            flow_message=jsonStr.getString("Message")
            if (flow_message.equals("連線成功")){
                ui_Helper.mesage("連線成功", this@MainActivity);
            }else{
                ui_Helper.mesage("連線失敗請重試", this@MainActivity);
            }
        }catch (ex: Exception){
            ui_Helper.mesage("連線失敗請重試", this@MainActivity);
        }
    }
    fun get_flow() {
        DefectQuan_edit.setText("0")
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        try{
            var SubflowInfo = CustomLayoutSubflowInfo(this@MainActivity, null)
            var flow_json=ui_Helper.get_subflowInfo(flowbar, userBar, dept, rootView, this@MainActivity)
            SubflowInfo.inputViewItems(flow_json, rootView)
        }catch (ex: Exception){
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.get_Exception(stacktrace, MainActivity.flow_message, context)
        }
        mainmessage.text=MainActivity.flow_message
    }
    private fun check_super(){
        try {
            var json = JSONObject()
            var webapiClient = WebapiClient()
            var rootView = window.decorView.rootView
            var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            var jsonString:String?=webapiClient.requestPOST(ip + "PrdMgn/Witness?UID=" + res_witness
                    + "&DEPT=" + MainActivity.dept + "&facroryNo=1&ifLeader=0&flowBar=" + flowbar, json)
            var jsonStr = JSONObject(jsonString)
            var Message = jsonStr.getString("Message")
            if (Message == "通過見證人驗證") {
                ifLeader = "1"
                ifLeader_forinput = "1"
            }
        }catch (ex: Exception){
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.get_Exception(stacktrace, MainActivity.flow_message, context)
        }
    }
    public fun check_version() {
        try{
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.check_version(context, window.decorView.rootView, "")
        }catch (ex: Exception){
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.get_Exception(stacktrace, MainActivity.flow_message, context)
        }
    }
    public fun login() {
        var sharedPref : SharedPreferences = this.getPreferences(Context.MODE_PRIVATE);
        try {
            login="1"
            sharedPref.edit().putString("table_custom_name", MainActivity.table_custom_name).commit()
            sharedPref.edit().putString("ip", MainActivity.ip).commit()
            sharedPref.edit().putString("dept", MainActivity.dept).commit()
            sharedPref.edit().putString("mail", mail.toString()).commit()
            sharedPref.edit().putString("per_user", userBar).commit()
            sharedPref.edit().putString("facrory", facrory.toString()).commit()
            sharedPref.edit().putString("login", "1").commit()
            sharedPref.edit().apply();
            var json = JSONObject()
            var webapiClient = WebapiClient()
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            var jsonString:String?=webapiClient.requestPOST(ip + "PrdMgn/Login?userBar=" + userBar +
                    "&Dept=" + MainActivity.dept + "&facroryNo=" + facrory, json)
            var jsonStr = JSONObject(jsonString)
            if (jsonStr.getString("Message").toString().indexOf("查無") > -1 ){
                ui_Helper.mesage(jsonStr.getString("Message").toString(), this@MainActivity)
                btn_start_login.visibility= View.VISIBLE
            }else{
                logo.visibility= View.GONE
                mainarea.visibility= View.VISIBLE
                info_layout.visibility= View.VISIBLE
                relogin.visibility= View.GONE
                flow_start_btn.visibility= View.VISIBLE
                test_cnt_btn.visibility= View.INVISIBLE
                part_finsh_btn.visibility= View.INVISIBLE
                flow_finsh__btn.visibility= View.INVISIBLE
                btn_wherehouse_def.visibility= View.INVISIBLE
                btn_wherehouse_semi.visibility= View.INVISIBLE
                User_json=jsonStr
                btn_start_login.visibility= View.GONE
                ui_Helper.login(jsonStr, rootView, this@MainActivity)
                mainmessage.text=MainActivity.flow_message
            }

            if (((android.os.Build.MANUFACTURER.indexOf("LENOVO") > -1 ) &&
                            (android.os.Build.PRODUCT.indexOf("X306F") > -1 )
                            )){ //NO 華為
                //val main_ut = LinearLayout.LayoutParams(1800, 550)
                //main_layout.setLayoutParams(main_ut)
            }else if (((android.os.Build.MANUFACTURER.indexOf("LENOVO") > -1 ) &&
                            (android.os.Build.PRODUCT.indexOf("X605F") > -1 )
                            )){ //NO 華為
                // val main_ut = LinearLayout.LayoutParams(1800, 850)
                // main_layout.setLayoutParams(main_ut)
            }else {
            }
            if (dept=="花蓮電鍍"){
                btn_plating.performClick()
            }
        }catch (ex: Exception){
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.mesage("查無員工編號", this@MainActivity)
            btn_start_login.visibility= View.VISIBLE
            ui_Helper.send_mail(stacktrace)
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        try {
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                } else {
                    try {
                        val obj = result.getContents()
                        if (obj.indexOf("*") > -1&&super_check=="0"&&cow=="0") {
                            userBar = obj
                            userBar=userBar.replace("*", "")
                            login()
                        }else if (obj.indexOf("*") >= -1&&cow=="1"&&super_check=="0"){//合作
                            cow="0"
                            cow_user=obj
                            cow_user=cow_user.replace("*", "")
                            cow_login()
                        }else if (obj.indexOf("*") > -1&&super_check=="1"&&cow=="0"){//見證
                            super_check="0"
                            res_witness=obj
                            check_super()
                        }else if (obj.indexOf("%") > -1) {
                            msg="1"
                            flowbar = obj
                            get_flow()
                        }
                    } catch (e: JSONException) {
                        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
                        ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
                        val stacktrace = StringWriter().also { e.printStackTrace(PrintWriter(it)) }.toString().trim()
                        ui_Helper.send_mail(stacktrace)
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }catch (ex: Exception){
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
        if(MainActivity.setting_flag=="1"){
            setting_flag="0"
            login()
        }
    }
    private fun cow_login(){//合作登入
        try {
            var json = JSONObject()
            var webapiClient = WebapiClient()
            val rootView = window.decorView.rootView
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
            var jsonString:String?=webapiClient.requestPOST(ip + "PrdMgn/Login?userBar=" +
                    cow_user + "&Dept=" + dept + "&facroryNo=1&coworker=" + cow_user, json)//+"&coworker=1"
            var jsonStr = JSONObject(jsonString)
            btn_cow.setBackgroundResource(R.mipmap.ic_cow_on)
            val rtnData =  JSONArray(jsonStr.getString("Data"))
            val jsonStrD = JSONObject(rtnData[0]?.toString())
            ui_Helper.mesage("   目前合作: " + jsonStrD?.getString("EMPNAME"), this@MainActivity)
            info_textView.text=info_textView.text.toString()+"目前合作: "+jsonStrD?.getString("EMPNAME")
        }catch (ex: Exception){
            cow_user=""
            val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(window.decorView.rootView)
            ui_Helper.mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            ui_Helper.send_mail(stacktrace)
        }
    }
    companion object{
        private lateinit var context: Context
        fun setContext(con: Context) {
            context=con
        }
        const val KEY = "com.example.firstohm_produce_kotlin"
        var macAddress: String = String()
        var ip: String = String()
        var Currjson: String = String()
        var installv: String = String()
        var table_custom_name: String = String()
        var confirmed: String = String()
        var outcheck_step : String = String()
        var PopMsg : String = String()
        var table_name_value : String = String()
        var setting_flag: String = String()
        var flow_message: String = String()
        var sw_dept: String = String()
        var per_user: String = String()
        var machineBar: String = String()
        var SUBFLOWID: String = String()
        var dept: String = String()
        var flow_json: JSONObject = JSONObject()
        var User_json: JSONObject = JSONObject()
        var userBar: String = String()
        var SIGNID: String = String()
        var BATCH_QTY: String = String()
        var flowbar: String = String()
        var InputQuan: String = String()
        var tdRollQty: String = String()
        var mail: String = String()
        var color_direction: String = String()
        var midlist = ArrayList<String>()
        var mail_list : String = String()
        var material_issue = ArrayList<String>()
        var URL_array = ArrayList<String>()
        var select_user: String = String()
        var check_out_input: String = String()
        var ACCDefect: String = String()

        var FLOW_STEP_CURR: String = String()
        var check_userid: String = String()
        var NG_SUM: String = String()
        var seach: String = String()
        var ng: String = String()
        var mStatus: String = String()
        var shiftName: String = String()
        var colorUser: String = String()
        var super_check: String = String()
        var FLOW_STEP: String = String()
        var res_witness: String = String()
        var ifLeader: String = String()
        var ifLeader_forinput: String = String()
        var input_edit: String = String()
        var splitID: String = String()
        var str_shortSubFlow: String = String()
        var VAL: String = String()
        var TOL: String = String()
        var PPM: String = String()
        var defectQuan: String = String()
        var output: String = String()
        var cow: String = String()
        var cow_user: String = String()
        var mfo_id: String = String()
        var BATCH_NO: String = String()
        var DEPT: String = String()
        var EMPNAME: String = String()
        var BATSEQ: String = String()
        var SIGNID_Edit: String = String()
        var facrory: String = String()
        var AccQuan: String = String()
        var StepLeft: String = String()
        var login: String = String()
        var msg: String = String()
        var short: String = String()
        var version: String = String()
        var r_url: String = String()
        var last_Exception: String = String()
        var WAREHouse_QTY: String = String()
        var OutputQuan: String = String()
        var sumOfNaiBei: String = String()
        var START_TIME: String = String()
        var Finish_Time: String = String()
        var per_USER_ID: String = String()
        var insted: String = String()
        var new_ver: String = String()

    }
    private fun readCpuInfo():String{
        val sb = StringBuffer();
        sb.append("abi: ").append(Build.CPU_ABI).append("\n");
        if (File("/proc/cpuinfo").exists()) {
            try {
                //val br =  BufferedReader(FileReader(File("/proc/cpuinfo")));
                val file = File("/proc/cpuinfo")

                file.bufferedReader().forEachLine {
                    sb.append(it + "\n");
                }

            } catch (e: IOException) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
    fun setdef(text: String) {
        DefectQuan_edit.setText(text.replace(".0", ""))
    }
    fun setinput(text: String, name: String) {
        input_edit.setText(text)
        if(outcheck_step== "花蓮底漆"){
            out_chk_btn2.setText("色碼人員")
            out_chk_btn.setText(name)
        }else{
            out_chk_btn2.setText(name)
            out_chk_btn.setText("底漆人員")
        }
    }

}
