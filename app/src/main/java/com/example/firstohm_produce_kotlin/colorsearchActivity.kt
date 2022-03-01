package com.example.firstohm_produce_kotlin
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_colorsearch.*
import kotlinx.android.synthetic.main.activity_plating.*
import kotlinx.android.synthetic.main.activity_plating_callback.*
import kotlinx.android.synthetic.main.activity_plating_exclude.*
import kotlinx.android.synthetic.main.activity_shift_layout.*
import kotlinx.android.synthetic.main.custom_layout_quant_color.*
import kotlinx.android.synthetic.main.custom_layout_quant_color.view.*
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class colorsearchActivity  : AppCompatActivity(){
    var sourceBar=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colorsearch)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@colorsearchActivity)
        var foot = 0
        val rootView = window.decorView.rootView
        val ui_Helper = co.ubunifu.kotlinhttpsample.Lib.UI_Helper(rootView)
        val arraySpinner = arrayOf(
                "選擇", "C3", "CSR", "EFR", "FGE", "HDR", "HVR", "IG", "M", "R", "MO", "MP", "MSD", "MVR", "NFR", "NL", "PMA", "PPR", "PSR", "PWR", "SCP", "SSR", "SWA", "WA", "SWAT", "ZOM", "SGS"
        )
        val ng_Array = JSONArray()
        for (j in 0 until arraySpinner.size) {
            ng_Array.put(arraySpinner[j])
        }
        ui_Helper.set_spinner_data(spinnerC1, this@colorsearchActivity, ng_Array, null)
        val arraySpinner2 = arrayOf(
                "選擇", "CM", "CSM", "EFP", "FM", "HFT", "HVM", "MM", "MMP", "MM(P)", "SFP", "SFPV", "SL", "SLC", "SM", "SRM", "SRMT", "SWM", "ZMM"
        )
        val ng_Array2 = JSONArray()
        for (j in 0 until arraySpinner2.size) {
            ng_Array2.put(arraySpinner2[j])
        }
        ui_Helper.set_spinner_data(spinnerC2, this@colorsearchActivity, ng_Array2, null)
        ///////////////////////////////////////////////////////
        back.setOnClickListener(View.OnClickListener {
            finish()
        })
        bnt_num_1.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "1") })
        bnt_num_2.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "2") })
        bnt_num_3.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "3") })
        bnt_num_4.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "4") })
        bnt_num_5.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "5") })
        bnt_num_6.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "6") })
        bnt_num_7.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "7") })
        bnt_num_8.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "8") })
        bnt_num_9.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "9") })
        bnt_num_0.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "0") })
        bnt_num_m.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "M") })
        bnt_num_e.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "E") })
        bnt_num_k.setOnClickListener(View.OnClickListener { tol_edit.setText(tol_edit.getText().toString() + "K") })

        bnt_tol_005.setOnClickListener { val_edit.setText("±0.05%") }
        bnt_tol_01.setOnClickListener { val_edit.setText("±0.1%") }
        bnt_tol_025.setOnClickListener { val_edit.setText("±0.25%") }
        bnt_tol_05.setOnClickListener { val_edit.setText("±0.5%") }
        bnt_tol_2.setOnClickListener { val_edit.setText("±2%") }
        bnt_tol_1.setOnClickListener { val_edit.setText("±1%") }
        bnt_tol_5.setOnClickListener { val_edit.setText("±5%") }
        bnt_tol_10.setOnClickListener { val_edit.setText("±10%") }
        rb1.setOnClickListener(first_radio_listener)
        rb2.setOnClickListener(sence_radio_listener)
        submit.setOnClickListener(button_listener)
        sance_color.setOnClickListener(sance_button_listener)
    }
    var sance_button_listener = View.OnClickListener {
        val integrator = IntentIntegrator(this@colorsearchActivity)
        integrator.initiateScan()
    }
    var first_radio_listener = View.OnClickListener {
        var foot = 1
        spinnerC2.setSelection(0)
        spinnerC2.isEnabled = false
        spinnerC1.isEnabled = true
    }
    var sence_radio_listener = View.OnClickListener {
        var foot = 0
        spinnerC1.setSelection(0)
        spinnerC1.isEnabled = false
        spinnerC2.isEnabled = true
    }
    var button_listener = View.OnClickListener {
        try {
            var rtype = spinnerC1.selectedItem.toString() + spinnerC2.selectedItem.toString()
            val tol = tol_edit.text.toString()
            var val1 = val_edit.text.toString().replace("±".toRegex(), "")
            val1 = val1.replace("±".toRegex(), "").toString()
            val1 = val1.replace("%".toRegex(), "")
            rtype = rtype.replace("選擇".toRegex(), "")
            if (rtype.indexOf("SRMT") > -1) {
                rtype = "SRM-T"
            }
            if (rtype.indexOf("MM(P)") > -1) {
                rtype = "MM-P"
            }
            if (rtype.indexOf("SFPV") > -1) {
                rtype = "SFP-V"
            }
            var webapiClient = WebapiClient()
            val url: String = MainActivity.ip + "PrdMgn/getColorPrdAttribute?rtype=" + rtype + "&tol=" + val1 + "&val=" + tol + "&ifFoot=1"
            var jsonString:String?=webapiClient.requestPOST("$url", JSONObject())
            val jsonStr = JSONObject(jsonString)
            val rtnData =  jsonStr.getString("Data")
            color(rtnData)
        }catch (ex: Exception){
        }
    }
    fun get_color(){
        try {

            var webapiClient = WebapiClient()
            val url: String = MainActivity.ip + "PrdMgn/getColorFromWareHouseBarCode?warehouseBar=" + sourceBar

            var jsonString:String?=webapiClient.requestPOST(
                    "$url", JSONObject())
            val jsonStr = JSONObject(jsonString)
            val rtnData =  jsonStr.getString("Data")
            color(rtnData)
        }catch (ex: Exception){
        }
    }
    fun color(rtnData: String){
        try {

            val letters = arrayOfNulls<ImageView>(8)
            val ct = arrayOfNulls<TextView>(8)
            ct[0] = findViewById<TextView>(R.id.textViewc_1)
            ct[1] = findViewById<TextView>(R.id.textViewc_2)
            ct[2] = findViewById<TextView>(R.id.textViewc_3)
            ct[3] = findViewById<TextView>(R.id.textViewc_4)
            ct[4] = findViewById<TextView>(R.id.textViewc_5)
            ct[5] = findViewById<TextView>(R.id.textViewc_6)
            val bg = findViewById(R.id.bg_frame) as FrameLayout
            //左至右
            letters[0] = findViewById<ImageView>(R.id.cp_c1)
            letters[1] = findViewById<ImageView>(R.id.cp_c2)
            letters[2] = findViewById<ImageView>(R.id.cp_c3)
            letters[3] = findViewById<ImageView>(R.id.cp_c4)
            letters[4] = findViewById<ImageView>(R.id.cp_c5)
            letters[5] = findViewById<ImageView>(R.id.cp_c6)
            siru.text = "→→→→"
            val color_map = HashMap<String, String>()
            color_map["白"]="#ffffff"
            color_map["7921 藍色B"]="#73C3AA"
            color_map["蘋果綠"]="#9BFFAA"
            color_map["磚紅"]="#FFBEBE"
            color_map["MO 藍色"]="#64A6CE"
            color_map["紫色"]="#CC99CC"
            color_map["MP106 粉紅"]="#FACCBA"
            color_map["金屬 墨綠"]="#73AD8F"
            color_map["白"]="#ffffff"
            color_map["7921 藍色B"]="#73C3AA"
            color_map["蘋果綠"]="#9BFFAA"
            color_map["磚紅"]="#FFBEBE"
            color_map["MO 藍色"]="#64A6CE"
            color_map["MP106 粉紅"]="#FACCBA"
            color_map["紫色"]="#CC99CC"
            color_map["金屬 墨綠"]="#73AD8F"
            color_map["磚紅"]="#FFBEBE"
            color_map["綠色"]="#4B854B"
            color_map["碳膜 乳黃"]="#E8D296"
            color_map["CM02 碳膜乳黃"]="#FFE0A0"
            color_map["粉紅A"]="#FFCCAF"
            color_map["MM02 藍色A"]="#1E82C8"
            color_map["MP106 粉紅B"]="#FACCBA"
            color_map["紅"]="#FF0000"
            color_map["橙"]="#FF6600"
            color_map["黃"]="#FFFF00"
            color_map["綠"]="#009900"
            color_map["棕"]="#F5F5DC"
            color_map["藍"]="#0000FF"
            color_map["灰"]="#999999"
            color_map["白"]="#FFFFFF"
            color_map["金"]="#FFCC00"
            color_map["銀"]="#CCCCCC"
            color_map["粉紅B"]="#FACCBA"
            color_map["藍色A"]="#1E82C8"
            color_map["7921 藍色"]="#73C3AA"
            color_map["粉紅A"]="#FFCCAF"
            color_map["碳膜乳黃"]="#FFFF00"
            color_map["7921"]="#73C3AA"
            color_map["2070"]="#00B0F0"
            color_map["Z0415"]="#FFCCFF"
            color_map["Z0360"]="#F2B300"
            color_map["GR-2"]="#F2B300"
            color_map["蘋果綠不燃性漆"]="#3C8C4D"
            color_map["綠色不燃性漆"]="#4B854B"
            color_map["藍色不燃性漆"]="#64A6CE"
            color_map["紫色不燃性漆"]="#CE99CC"
            color_map["磚紅不燃性漆"]="#FFBEBE"
            color_map["金屬塗料"]="#73AD8F"
            color_map["黑"]="#000000"
            val color_str=rtnData;
            val parts = color_str.split("-".toRegex()).toTypedArray()
            val i = parts[0].length
            val first = parts[0]
            val reverse = StringBuffer(first).reverse().toString()
            val content = first.toCharArray()
            for(i in 0..content.size-1){
                val colo=content[i].toString()
                val co=color_map.get(colo).toString()
                letters[i]!!.setBackgroundColor(Color.parseColor(co))
                ct[i]!!.setText(colo)
                bg_t.setText("底色:" + parts[2].toString())
            }
        }catch (ex: Exception){
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
                        sourceBar=obj
                        get_color()
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }catch (ex: Exception){
        }
    }
}