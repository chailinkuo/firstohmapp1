package co.ubunifu.kotlinhttpsample

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import co.ubunifu.kotlinhttpsample.Lib.ListViewModelAdapter
import com.example.firstohm_produce_kotlin.R
import java.util.*


public class ListViewModelAdapterPlating_exclude
(context: Context, listModelArrayList: ArrayList<ListViewModelAdapterPlating_exclude1>)
    : ListViewModelAdapter<ListViewModelAdapterPlating_exclude1>(
        context, listModelArrayList
) {
    private var selectedEditTextPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolderWh

        // TODO Auto-generated method stub

        val ArrayPosition = position;
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.plating_exclude_list_view, parent, false)
            vh = ViewHolderWh(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolderWh
        }
        vh.splitWeight.tag = position;
        convertView?.setTag(R.layout.plating_exclude_list_view, position); // 应该在这里让convertView绑定position
        vh.MASTER_MFO_ID.text = listModelArrayList[position].MASTER_MFO_ID
        vh.batchNo.text = listModelArrayList[position].batchNo
        vh.size.text = listModelArrayList[position].size
        vh.RTYPE.text = listModelArrayList[position].RTYPE
        vh.VAL.text = listModelArrayList[position].VAL
        vh.Tol.text = listModelArrayList[position].Tol
        vh.Vals.text = listModelArrayList[position].Vals

        val tag_position = vh.splitWeight.getTag() as Int
        vh.splitWeight.setId(tag_position)
        vh.splitWeight.setText(listModelArrayList[position].splitWeight)
        vh.plattingType.text = listModelArrayList[position].plattingType
        try{
            vh.splitWeight.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                }

                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                    //do nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val Caption = vh.splitWeight as EditText
                    val position2: Int = Caption.getId()
                    if (Caption.text.toString().length > 0) {
                        listModelArrayList[position2].splitWeight = Caption.text.toString()
                    }
                }
            })

        }catch (ex: Exception){
        }
        vh.cbxStatus.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
            buttonView, isChecked ->
            val Caption = vh.splitWeight as EditText
            val position2: Int = Caption.getId()
            if (vh.cbxStatus.isChecked() == true) {
                listModelArrayList[position2].cbxStatus = "1"
            } else {
                listModelArrayList[position2].cbxStatus = "0"
            }
        })
        if (listModelArrayList[position].cbxStatus == "0") {
            vh.cbxStatus.setChecked(false)
        } else {
            vh.cbxStatus.setChecked(true)
        }
        return view
    }

}



//ViewList 裡面 Row 的定義
class ViewHolderWh(view: View?) {
    val MASTER_MFO_ID: TextView = view?.findViewById<TextView>(R.id.SUBFLOWID) as TextView
    val batchNo: TextView = view?.findViewById<TextView>(R.id.batchNo) as TextView
    val size: TextView = view?.findViewById<TextView>(R.id.size) as TextView
    val RTYPE: TextView = view?.findViewById<TextView>(R.id.RTYPE) as TextView
    val VAL: TextView = view?.findViewById<TextView>(R.id.VAL) as TextView
    val Tol: TextView = view?.findViewById<TextView>(R.id.Tol) as TextView
    val Vals: TextView = view?.findViewById<TextView>(R.id.Vals) as TextView
    val plattingType: TextView = view?.findViewById<TextView>(R.id.plattingType) as TextView
    val splitWeight: EditText = view?.findViewById<TextView>(R.id.splitWeight) as EditText
    val cbxStatus: CheckBox = view?.findViewById<CheckBox>(R.id.cbxStatus) as CheckBox
}
data class ListViewModelAdapterPlating_exclude1(
        var id: Int, var MASTER_MFO_ID: String, var batchNo: String, var size: String,
        var RTYPE: String, var VAL: String, var Tol: String, var Vals: String, var plattingType: String,
        var FLOW_STEP: String, var splitWeight: String,
        var SID: String, var subflowID: String, var OutputQuan: String, var cbxStatus: String
){


}