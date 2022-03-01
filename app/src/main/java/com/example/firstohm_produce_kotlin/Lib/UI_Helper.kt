package co.ubunifu.kotlinhttpsample.Lib

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.firstohm_produce_kotlin.MainActivity
import com.example.firstohm_produce_kotlin.R
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.custom_layout_main_button.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.PrintWriter
import java.io.StringWriter
import java.net.NetworkInterface
import java.text.SimpleDateFormat
import java.util.*


class UI_Helper(view: View){
    public fun check_version(context: Context, view: View, ver: String) {
        try {
            var new_ver = ver.replace("8安裝APP版本:", "")
            new_ver = new_ver.replace("安裝APP", "")
            new_ver = new_ver.replace("安裝APP版本:", "")
            new_ver = new_ver.replace(",:", "")
            new_ver = new_ver.replace("PopMsg:", "")
            new_ver = new_ver.replace(";", "")
            new_ver = new_ver.replace(",8,一般製程,1", "")
            Log.d("1version==>", new_ver)
            Log.d("1version==>", MainActivity.version)
            Log.d("1version==>", MainActivity.shiftName)
            val new: Array<String> = new_ver.split("\\.".toRegex()).toTypedArray()
            val now: Array<String> = MainActivity.version.split("\\.".toRegex()).toTypedArray()
            Log.d("1version==>", new[1])
            Log.d("1version==>", now[1])
            if (MainActivity.shiftName =="早班"  || MainActivity.shiftName =="辦公室"){
                if (//new[0].toInt()>now[0].toInt()&&
                        new[1].toInt()>now[1].toInt()){

                    val inflater = LayoutInflater.from(context)
                    val v: View = inflater.inflate(R.layout.activity_main, null)
                    view.btn_update.performClick()//getupdate
                    mesage("下載更新中請稍後...", context)
            }

            }
        } catch (ex: java.lang.Exception) {
        }
    }
    public fun check_version_logout(context: Context, view: View, ver: String, url: String, logouttype: String) {
        try {
            var new_ver = ver.replace("0安裝APP版本:", "")
            new_ver = new_ver.replace("安裝APP版本:", "")
            new_ver = new_ver.replace("PopMsg:", "")
            new_ver = new_ver.replace(";", "")
            Log.d("version==>", new_ver)
            MainActivity.new_ver=new_ver
            MainActivity.PopMsg=new_ver
            Log.d("version==>", MainActivity.version)
            val new: Array<String> = new_ver.split("\\.".toRegex()).toTypedArray()
            val now: Array<String> = MainActivity.version.split("\\.".toRegex()).toTypedArray()
            if (//new[0].toInt()>now[0].toInt()&&
                    new[1].toInt()>now[1].toInt()){
                mesage_nobtn("下載更新中請稍後...", context)
                val inflater = LayoutInflater.from(context)
                val v: View = inflater.inflate(R.layout.activity_main, null)
                v.btn_update.performClick()//getupdate
            }else
            {
                MainActivity.insted="1"
            }
        } catch (ex: java.lang.Exception) {
        }
    }
    public fun spil_json(jsonString: String):String{ //訊息分離 by ;;;
        val str=jsonString
        if(str.indexOf(";;;") > -1){
            val separated: Array<String> = str.split(";;;".toRegex()).toTypedArray()
            return separated[0]
        }else{
            return str
        }
    }
    fun getMacAddr(): String? {
        try {
            val all: List<NetworkInterface> = Collections.list(NetworkInterface.getNetworkInterfaces())
            for (nif in all) {
                if (!nif.getName().equals("wlan0")) continue
                val macBytes: ByteArray = nif.getHardwareAddress() ?: return ""
                val res1 = StringBuilder()
                for (b in macBytes) {
                    res1.append(String.format("%02X:", b))
                }
                if (res1.length > 0) {
                    res1.deleteCharAt(res1.length - 1)
                }
                return res1.toString()
            }
        } catch (ex: java.lang.Exception) {
        }
        return "02:00:00:00:00:00"
    }

    public fun mesage(rore: String, context: Context) {
        val builder =
                androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setMessage(rore)
        builder.setCancelable(false)
        builder.setPositiveButton(
                "確認"
        ) { dialog, id -> }
        builder.show()
    }
    public fun mesage_nobtn(rore: String, context: Context) {
        val builder =
                androidx.appcompat.app.AlertDialog.Builder(context)
        builder.setMessage(rore)
        builder.setCancelable(false)
        builder.show()
    }
    public fun retriveFromJsonStrn(jsonString: String, retriveItem: String? = null): Any? {
        var jsonObj = JSONObject(JSONObject(jsonString).getString("Data"))
        if(retriveItem==null)
            return jsonObj
        else
            return jsonObj.opt(retriveItem)
    }
    public fun jsonStrToObject(jsonString: String, elementName: String? = null): JSONObject? {
        return JSONObject(JSONObject(jsonString).getString(elementName ?: "Data"))
    }
    public fun get_subflowInfo(flow_bar: String, dept: String, user_bar: String, view: View, context: Context): JSONObject {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.custom_layout_info, null)
        var webapiClient = WebapiClient()
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(view)
        var cmd=ui_Helper.get_sance_cmd(MainActivity.dept)
        var url=MainActivity.ip+"PrdMgn/ScanOperate?" +
                "command=$cmd&UID="+MainActivity.userBar+"&flowBar="+MainActivity.flowbar +
        "&DEPT="+ MainActivity.dept
        var jsonString:String?=webapiClient.requestPOST(
                "$url", JSONObject())
        val jsonStr = JSONObject(jsonString)
        MainActivity.flow_message=jsonStr.getString("Message")
        val rtnData =  JSONObject(jsonStr.getString("Data"))
        MainActivity.flow_json= JSONObject(jsonStr.getString("Data"))
        return rtnData
    }
    public fun get_subflowInfo_by_short(short: String, processName: String, user_bar: String, view: View, context: Context): JSONObject {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.custom_layout_info, null)
        var webapiClient = WebapiClient()
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(v)
        var cmd=0
        if (MainActivity.dept.indexOf("貼") > -1) {
            cmd=30
        }
        var url = MainActivity.ip +
                "PrdMgn/ScanOperate?command=$cmd&UID=" + MainActivity.userBar +
                "&DEPT=" + MainActivity.dept + "&shortSubFlow=" + short
        var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
        val jsonStr = JSONObject(jsonString)
        MainActivity.flow_message=jsonStr.getString("Message")
        val rtnData =  JSONObject(jsonStr.getString("Data"))
        MainActivity.flow_json= JSONObject(jsonStr.getString("Data"))
        return rtnData
    }
    public fun confirmed(jsonStr: String, flag: String, context: Context): String {
        val array = JSONArray(jsonStr) //o
        var msg=""
        for (k in 0 until array.length()) {
            if (!(array.get(k).toString().indexOf("msgAction")>-1))
                msg=msg+array.get(k).toString()+"\n"
        }
        if (flag=="停止"){
            msg="已停止\n"+msg
        }
        return msg
    }
    public fun POPmsgAction(jsonStr: String, flag: String, context: Context){
        val array = JSONArray(jsonStr) //o
        var msg=""
        for (k in 0 until array.length()) {
            if (!(array.get(k).toString().indexOf("msgAction")>-1))
                msg=msg+array.get(k).toString()+"\n"
        }
        if (flag=="停止"){
            msg="已停止\n"+msg
        }
        mesage(msg, context)
    }
    public fun get_subflowInfo_by_SIGNID(short: String, processName: String, user_bar: String, view: View, context: Context): JSONObject {
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.custom_layout_info, null)
        var webapiClient = WebapiClient()
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(v)
        var cmd=8
        if (MainActivity.dept.indexOf("貼") > -1) {
            cmd=38
        }
        var url = MainActivity.ip +
                "PrdMgn/ScanOperate?command=$cmd&UID=" + MainActivity.userBar +
                "&DEPT=" + MainActivity.dept + "&signID=" + short
        var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
        val jsonStr = JSONObject(jsonString)
        MainActivity.flow_message=jsonStr.getString("Message")
        val rtnData =  JSONObject(jsonStr.getString("Data"))
        MainActivity.flow_json= JSONObject(jsonStr.getString("Data"))
        return rtnData
    }
    public fun login(jsonStr: JSONObject, view: View, context: Context){
        try {
            MainActivity.flow_message=jsonStr.getString("Message").toString()
            val rtnData =  JSONArray(jsonStr.getString("Data"))
            val jsonStrD = JSONObject(rtnData[0]?.toString())
            val Data_mid = jsonStrD?.getString("Machins")
            val DEPT = jsonStrD?.getString("DEPT")
            val EMPNAME = jsonStrD?.getString("EMPNAME")
            MainActivity.ifLeader= jsonStrD?.getString("ifLeader")
            MainActivity.DEPT = jsonStrD?.getString("DEPT")
            MainActivity.EMPNAME = jsonStrD?.getString("EMPNAME")
            MainActivity.shiftName = jsonStrD?.getString("shiftName")
            /**/try {
                MainActivity.PopMsg = jsonStrD?.getString("PopMsg")
            }catch (ex: Exception) {            }

            if(MainActivity.dept=="花蓮切割" || MainActivity.dept=="花蓮加壓"
               || MainActivity.dept=="花蓮外檢"|| MainActivity.dept=="花蓮全檢"){
                view.bnt_selectMachins.visibility=View.VISIBLE
                view.mid_spinner.visibility=View.GONE
                view.mid_text.visibility=View.VISIBLE
                val array = JSONArray(Data_mid)
                val midlist = ArrayList<String>()
                for (j in 0 until array.length()) {
                    val Jasonobject: String = array.getString(j)
                    val array2 = JSONArray(Jasonobject)
                    for (k in 0 until array2.length()) {
                        val ob2 = array2.getJSONObject(k)
                        val mid = ob2.getString("mid")
                        var attr2 = ob2.getString("attr2")
                        val status = ob2.getString("status")
                        if (mid == "null") {
                            midlist.add("null")
                        } else {
                            //midlist.add(attr2 +"\n"+mid+"status"+status);
                            attr2 = attr2.replace("\\\\".toRegex(), "")
                            attr2 = attr2.replace("n".toRegex(), "  ")
                            midlist.add(attr2 + "  " + mid + "status" + status)
                        }
                    }
                    midlist.add("new")
                    MainActivity.midlist=midlist
                }
            }else{
                val Data_mid0 = jsonStrD?.getJSONArray("Machins")
                val mid_Array = JSONArray()
                for (j in 0 until Data_mid0.length()) {
                    val jsonObjectDates: JSONObject = Data_mid0.getJSONObject(j)
                    val mid = jsonObjectDates.getString("MachineID")
                    mid_Array.put(mid)
                }
                view.mid_spinner.visibility=View.VISIBLE
                view.bnt_selectMachins.visibility=View.GONE
                view.mid_text.visibility=View.GONE
                set_spinner_data(view.mid_spinner, context, mid_Array, null)
                view.mid_spinner.setOnItemSelectedListener(object : OnItemSelectedListener {
                    override fun onItemSelected(adapterView: AdapterView<*>?, view1: View, i: Int, l: Long) {
                        MainActivity.machineBar = view.mid_spinner.getSelectedItem().toString()
                    }

                    override fun onNothingSelected(adapterView: AdapterView<*>?) {
                        return
                    }
                })
            }
            val sdf = SimpleDateFormat("dd/M/yyyy hh:mm:ss")
            val today = sdf.format(Date())
            view.info_textView.setText("部門:$DEPT\t\t\t員工:$EMPNAME\t\t\t  目前製程 : " + MainActivity.dept)
            ///////
            val ng_array = arrayOf(
                    "非重複性檢測", "NG1", "NG2", "NG3", "NG1+2", "NG1+3", "NG2+3", "NG1+2+3"
            )
            val ng_Array = JSONArray()
            for (j in 0 until ng_array.size) {
                ng_Array.put(ng_array[j])
            }
            set_spinner_data(view.NG_select, context, ng_Array, null)
            view.NG_select.setOnItemSelectedListener(object : OnItemSelectedListener {
                override fun onItemSelected(adapterView: AdapterView<*>?, view1: View, i: Int, l: Long) {
                    MainActivity.ng = i.toString()
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    return
                }
            })
            /////////////翻車
            val terrible__array = arrayOf(
                    "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10次以上"
            )
            val terrible_Array = JSONArray()
            for (j in 0 until terrible__array.size) {
                terrible_Array.put(terrible__array[j])
            }
            set_spinner_data(view.terrible_spinner, context, terrible_Array, null)
            /////////////
            if (MainActivity.dept=="花蓮底漆"){
                view.terrible_layout.visibility=View.VISIBLE
                view.part_finsh_btn.visibility=View.GONE
            }else{
                view.terrible_layout.visibility=View.GONE
                view.part_finsh_btn.visibility=View.GONE
            }
            if (MainActivity.dept=="花蓮塗裝"){
                view.btn_dept_sw.visibility=View.VISIBLE
            }else{
                view.btn_dept_sw.visibility=View.GONE
            }
            if (MainActivity.dept=="花蓮貼帶"){
                view.tedai_layout.visibility=View.VISIBLE
                view.shift_bnt.visibility=View.VISIBLE
            }else{
                view.tedai_layout.visibility=View.GONE
                view.shift_bnt.visibility=View.GONE
            }
            if (MainActivity.dept=="花蓮色碼"){
                view.custom_layout_color.visibility=View.VISIBLE
            }else{
                view.custom_layout_color.visibility=View.GONE
            }
            view.Supervisor_check.visibility=View.GONE
            if (MainActivity.dept=="花蓮外檢"){
                view.ng_layout.visibility=View.VISIBLE
                view.out_chk_btn.visibility=View.VISIBLE
                view.out_chk_btn2.visibility=View.VISIBLE
                view.out_chk_def_btn.visibility=View.VISIBLE
                view.textView123.visibility=View.GONE
                view.NG_select.visibility=View.VISIBLE
                view.Supervisor_check.visibility=View.GONE
                view.btn_material_issue.visibility=View.GONE
                view.btn_acc.visibility=View.GONE//無餘料
            }else{
                view.ng_layout.visibility=View.GONE
                view.out_chk_btn.visibility=View.GONE
                view.out_chk_btn2.visibility=View.GONE
                view.textView123.visibility=View.VISIBLE
                view.out_chk_def_btn.visibility=View.GONE
                view.NG_select.visibility=View.GONE
                view.btn_acc.visibility=View.VISIBLE//無餘料
            }
            if (MainActivity.dept=="花蓮加壓"||MainActivity.dept=="花蓮切割"){
                view.speed_layout.visibility=View.VISIBLE
                if (MainActivity.dept=="花蓮加壓"){
                    view.speed_text.text="電壓"
                }
                if (MainActivity.dept=="花蓮切割"){
                    view.speed_text.text="速度"
                }
            }else{
                view.speed_layout.visibility=View.GONE
            }
            if(MainActivity.ifLeader=="1"){
                view.btn_setting.visibility=View.VISIBLE
            }else{
                view.btn_setting.visibility=View.GONE
            }
            try{
            //check ver
               val ver = jsonStrD?.getString("PopMsg")
              check_version(context, view, ver)
            }catch (ex: Exception) {
            }
        }catch (ex: Exception) {
            val stacktrace = StringWriter().also { ex.printStackTrace(PrintWriter(it)) }.toString().trim()
            var ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(view)
            ui_Helper.send_mail(stacktrace)
        }
    }
    public fun set_spinner_data(targetSpiner: Spinner, context: Context, itemsList: JSONArray, defaulyVal: String?) {
        try {
            val list = Array(itemsList.length()) {
                itemsList.getString(it)
            }
            val arrayAdapter = ArrayAdapter(
                    context,
                    R.layout.simple_spinner_dropdown_item,
                    list
            )
            arrayAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
            targetSpiner.adapter = arrayAdapter
            if(defaulyVal!=null) {
                //Todo 设定预设值
                targetSpiner.setSelection((targetSpiner.getAdapter() as ArrayAdapter<String?>).getPosition(defaulyVal))
            }
        } catch (ex: Exception) {
        }
    }
    fun get_midinfo_cmd(start_text: String): String {
        var cmd="8"
        if (MainActivity.dept == "花蓮貼帶") {
            cmd="38"
        }
        if (MainActivity.dept == "花蓮外檢") {
            cmd="48"
        }
        return cmd
    }
    fun TimestampToDatetime(timeStamp: String): String {// input Date(1644163200000)  output yyyy-MM-dd HH:mm:ss
        try{
            var timeStamp1 = timeStamp.replace("\\/Date\\(".toRegex(), "")
            var timeStamp2 = timeStamp1.replace("\\)/".toRegex(), "")
            val timeStamp: Long = timeStamp2.toLong()
            ////////////
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN)
            return simpleDateFormat.format(Date(timeStamp))
        }catch (ex: Exception) {
            return "null"
        }
    }
    fun get_finsh_cmd(): String {
        var cmd="11"
        if (MainActivity.dept == "花蓮貼帶") {
            cmd="36"
        }
        if (MainActivity.input_edit=="1"){
            cmd="11"
        }
        return cmd
    }
    fun get_size(start_text: String): String? {
        val cmd_map = HashMap<String, String>()
        cmd_map["1x3.15"]="52000"
        cmd_map["1.7x5.4"]="12300"
        cmd_map["2.5x8"]="4400"
        cmd_map["3.5x10"]="1700"
        cmd_map["4x12"]="1300"
        cmd_map["4.5x14"]="1000"
        return cmd_map[start_text]
    }
    fun get_start_cmd(start_text: String): String {
        val cmd_map = HashMap<String, String>()
        if (MainActivity.dept == "花蓮貼帶") {
            cmd_map["開始新製程 Bắt đầu quy trình mới"]="31"
            cmd_map["開始新製程"]="31"
            cmd_map["接續前班"]="33"
            cmd_map["延續前班製程Tiếp tục ca trước"]="33"
            cmd_map["更換機台"]="34"
            cmd_map["更換機台作業thay đơi máy"]="34"
        }else{
            cmd_map["開始新製程 Bắt đầu quy trình mới"]="1"
            cmd_map["開始新製程"]="1"
            cmd_map["接續前班"]="3"
            cmd_map["延續前班製程Tiếp tục ca trước"]="3"
            cmd_map["更換機台作業thay đơi máy"]="4"
            cmd_map["更換機台"]="4"
        }
        cmd_map["換機開始"]="7"
        cmd_map["全檢1"]="15"
        cmd_map["全檢2"]="15"
        cmd_map["外檢驗底漆"]="21"  //外檢1
        cmd_map["外檢驗色碼"]="23"  //外檢2
        cmd_map["塗前加壓"]="24"
        cmd_map["底漆半成品"]="25"
        cmd_map["底漆完成品"]="27"
        cmd_map["底漆1"]="1"
        cmd_map["底漆2"]="1"
        cmd_map["外檢1"]="21"
        cmd_map["外檢2"]="23"
        cmd_map["全檢良品重測"]="22"
        cmd_map["全檢重測"]="22"
        cmd_map["重測"]="22"
        cmd_map["全檢不良品重測"]="22"
        cmd_map["良品重測"]="22"
        cmd_map["不良品重測"]="22"
        cmd_map["全檢分類"]="1"
        cmd_map["分類"]="1"
        cmd_map["外檢1重測"]="26"
        if(start_text=="外檢1重測"){
            MainActivity.flow_json.put("FLOW_STEP_CURR", "外檢1")
        }
        cmd_map["外檢2重測"]="28"
        if(start_text=="外檢2重測"){
            MainActivity.flow_json.put("FLOW_STEP_CURR", "外檢2")
        }
        cmd_map["外檢底漆重測"]="26"
        if(start_text=="外檢底漆重測"){
            MainActivity.flow_json.put("FLOW_STEP_CURR", "外檢1")
        }
        cmd_map["外檢色碼重測"]="28"
        if(start_text=="外檢色碼重測"){
            MainActivity.flow_json.put("FLOW_STEP_CURR", "外檢2")
        }
        cmd_map["外檢重驗底漆"]="29"
        if(start_text=="外檢重驗底漆"){
            MainActivity.flow_json.put("FLOW_STEP_CURR", "外檢1")
        }
        return cmd_map[start_text].toString()
    }
    fun get_sance_cmd(start_text: String): String {
        try {
            var cmd="0"
            if (MainActivity.dept.indexOf("外")>-1){
                cmd="13"
                if (MainActivity.ifLeader_forinput.indexOf("1")>-1){
                    cmd="13A"
                }
            }else if (MainActivity.dept.indexOf("貼")>-1){
                cmd="30"
            }
            return cmd.toString()
        } catch (ex: Exception) {
            return "0"
        }
    }
    fun get_Exception(body: String,message: String, context: Context){
        send_mail(body)
        if (message!=""){
            mesage(message, context)
        }else{
            mesage("錯誤已發生，請將操作流程告知資訊部，謝謝。", context)
        }
        MainActivity.flow_message=""
    }
    fun send_mail(body: String): String{
        var webapiClient = WebapiClient()
        var sendurl=""
        try {
            sendurl= MainActivity.r_url.toString()
            var body=body
            //body = body.replace("/".toRegex(), "[scott]")
            //body = body.replace(":".toRegex(), "[scott]")
            //body = body.replace("&".toRegex(), "[scott]")
            //sendurl = sendurl.replace("/".toRegex(), "[scott]")
            //sendurl = sendurl.replace(":".toRegex(), "")
            sendurl = sendurl.replace("&".toRegex(), "&amp;")
            if (body.indexOf("org.json")>-1){
                MainActivity.last_Exception=body;
            }
            body=body+MainActivity.version+"<br>"
            var ti="APP通知"
            if(MainActivity.ip.indexOf("1.33:1111")>-1){
                ti="APP通知33"
            }
            var url=MainActivity.ip+"/FirstohmAD/" +
                    "SendNotification?subject=$ti&toUserLoginName=admin"
            //+"admin" MainActivity.table_custom_name
            //"chailin.kuo" MainActivity.Currjson
            val hashMap:HashMap<String?,String?> = HashMap<String?,String?>(1)
            hashMap.put("messagebody","$body<br> url：" +sendurl
                    +"<br> tablet_name："+MainActivity.table_custom_name
                    +"<br> flowbar："+MainActivity.flowbar
                    +"<br> userBar："+MainActivity.userBar+"        "
                    +"<br> EMPNAME："+MainActivity.EMPNAME+"        "
                    +"<br> dept："+MainActivity.dept+"        "
                    +"<br> json："+MainActivity.Currjson)
            var jsonString:String?=webapiClient.httpConnectionPost("$url", hashMap)
            Log.d("m", url)
            Log.d("m", jsonString)
            Log.d("m", "$body ；；；；url；；；；" +sendurl
                    +"；；；；flowbar"+MainActivity.flowbar
                    +"；；；；userBar"+MainActivity.userBar+"        "
                    +"；；；；EMPNAME"+java.net.URLEncoder.encode(MainActivity.EMPNAME, "utf-8")+"        "
                    +"；；；；dept"+java.net.URLEncoder.encode(MainActivity.dept, "utf-8")
                    +"；；；；Currjson"+MainActivity.Currjson)
            MainActivity.Currjson=""
        }catch (ex: Exception){}

        try {
            val jsonArray = JSONObject(MainActivity.mail)
            try {
                val name1 = jsonArray?.getString("1");
                var url=MainActivity.ip+"/FirstohmAD/" +
                        "SendNotification?subject=APP通知&messagebody=$body&toUserLoginName=$name1"
                //var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                Log.d("m", url)
            }catch (ex: Exception){}
            try {
                val name1 = jsonArray?.getString("2");
                var url=MainActivity.ip+"/FirstohmAD/" +
                        "SendNotification?subject=APP通知&messagebody=$body&toUserLoginName=$name1"
                //var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                Log.d("m", url)
            }catch (ex: Exception){}
            try {
                val name1 = jsonArray?.getString("3");
                var url=MainActivity.ip+"/FirstohmAD/" +
                        "SendNotification?subject=APP通知&messagebody=$body&toUserLoginName=$name1"
                //var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
                Log.d("m", url)
            }catch (ex: Exception){}
        }catch (ex: Exception){}
        return body.toString()
    }
    fun send_last_Exception(body: String): String{
        var webapiClient = WebapiClient()
        var sendurl=""
        try {
            sendurl= MainActivity.r_url.toString()
            //sendurl = sendurl.replace("/".toRegex(), "[scott]")
            //sendurl = sendurl.replace(":".toRegex(), "[scott]")
            //sendurl = sendurl.replace("&".toRegex(), "[scott]")
            var body=MainActivity.last_Exception;
            var url=MainActivity.ip+"/firstohmWebApi/FirstohmAD/" +
                    "SendNotification?subject=APP通知手動發送Exception&messagebody=$body" +
                    "&toUserLoginName=admin"
            //+"admin"
            //"chailin.kuo"
            var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
            Log.d("m", url)
            Log.d("m", jsonString)
        }catch (ex: Exception){}
        return body.toString()
    }
}

