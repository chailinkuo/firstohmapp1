package com.example.firstohm_produce_kotlin.Lib

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.ubunifu.kotlinhttpsample.Lib.ListViewModelAdapter
import com.example.firstohm_produce_kotlin.R
import java.util.ArrayList


public class ListViewModelAdapterprd(context: Context?
                                      , listModelArrayList: ArrayList<ListViewModelRo>)
    : ListViewModelAdapter<ListViewModelRo>(
        context, listModelArrayList
) {
    fun listOfObjToJstr(idxList: List<Int>) {

    }

    override fun getView(position: Int, convertView: View?,
                         parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolderRo

        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.dialog_prd_test_row,
                    parent,
                    false)
            vh = ViewHolderRo(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolderRo
        }
        //當允許修改 List 內容時, 需修改 所有會被修改欄位的 Listener
        vh.prd_editText1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listModelArrayList[position].count_1=s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })
        vh.prd_editText2.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listModelArrayList[position].count_2=s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })
        vh.prd_editText3.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listModelArrayList[position].count_3=s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })
        vh.prd_editText4.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listModelArrayList[position].count_4=s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })
        vh.prd_editText5.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listModelArrayList[position].count_5=s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })
        vh.prd_editText6.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                listModelArrayList[position].count_6=s.toString()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //do nothing
            }
        })
        return view
    }
    //ViewList 裡面 Row 的定義
    public class ViewHolderRo(view: View?) {
        val prd_editText1: TextView = view?.findViewById<TextView>(R.id.prd_editText1) as TextView
        val prd_editText2: TextView = view?.findViewById<TextView>(R.id.prd_editText2) as TextView
        val prd_editText3: TextView = view?.findViewById<TextView>(R.id.prd_editText3) as TextView
        val prd_editText4: TextView = view?.findViewById<TextView>(R.id.prd_editText4) as TextView
        val prd_editText5: TextView = view?.findViewById<TextView>(R.id.prd_editText5) as TextView
        val prd_editText6: TextView = view?.findViewById<TextView>(R.id.prd_editText6) as TextView
    }
}


data class ListViewModelRo(var count_1: String, var count_2: String, var count_3: String,  var count_4: String,
                           var count_5: String, var count_6: String)
{}