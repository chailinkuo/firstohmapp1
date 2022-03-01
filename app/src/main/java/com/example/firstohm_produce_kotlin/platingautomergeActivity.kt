package com.example.firstohm_produce_kotlin
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import co.ubunifu.kotlinhttpsample.Lib.WebapiClient
import kotlinx.android.synthetic.main.activity_plating.*
import kotlinx.android.synthetic.main.plating_automerge.*
import org.json.JSONObject

class platingautomergeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.plating_automerge)
    this.supportActionBar?.hide()  //hide title bar
    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    val inflater = LayoutInflater.from(this@platingautomergeActivity)

        try {

            Auto_cl.setOnClickListener {
                this.onBackPressed()
            }
            auto_ok.setOnClickListener {
                val Str_diffVal=  diffVal_texted.text.toString()
                val Str_diffWeight=diffWeight_texted.text.toString()
                val Str_stdWeight=stdWeight_texted.text.toString()
                var Str_ifSameSize=""
                when (size_rd.checkedRadioButtonId) {
                    R.id.size_rd1 -> Str_ifSameSize = "0"
                    R.id.size_rd2 -> Str_ifSameSize = "1"
                }
                var webapiClient = WebapiClient()
                var url = MainActivity.ip + "PrdMgn/plattingAutoMatching?" +
                        "stdWeight=" + Str_diffVal +
                        "&diffWeight=" + Str_diffWeight+
                        "&diffVal=" + Str_stdWeight +
                        "&ifSameSize=" + Str_ifSameSize

                var jsonString:String?=webapiClient.requestPOST(url, JSONObject())
                val jsonStr = JSONObject(jsonString)
            }
        } catch (ex: Exception) {
        }
}
}