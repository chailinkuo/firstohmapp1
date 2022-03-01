package com.example.firstohm_produce_kotlin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_plating.*

class platingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plating)
        this.supportActionBar?.hide()  //hide title bar
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        val inflater = LayoutInflater.from(this@platingActivity)
        try {

            btn_exclude.setOnClickListener {
                startActivity(Intent(this@platingActivity, plating_exclude::class.java))
            }
            btn_auto.setOnClickListener {
                startActivity(Intent(this@platingActivity, platingautomergeActivity::class.java))
            }
            btn_Manual.setOnClickListener {
                startActivity(Intent(this@platingActivity, plating_Manual::class.java))
            }
            btn_Modifi.setOnClickListener {
                startActivity(Intent(this@platingActivity, plating_finshed::class.java))
            }
            btn_send.setOnClickListener {
                startActivity(Intent(this@platingActivity, plating_send::class.java))
            }
            btn_callback.setOnClickListener {
                startActivity(Intent(this@platingActivity, plating_callback::class.java))
            }
            back_home.setOnClickListener {
                finish()
            }
        } catch (ex: Exception) {
        }
    }
}