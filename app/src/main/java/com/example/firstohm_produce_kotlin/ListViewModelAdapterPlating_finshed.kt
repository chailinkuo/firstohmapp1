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


public class ListViewModelAdapterPlating_finshed
(context: Context, listModelArrayList: ArrayList<ListViewModelAdapterPlating_finshed1>) : ListViewModelAdapter<ListViewModelAdapterPlating_finshed1>(
        context, listModelArrayList
) {
    private var selectedEditTextPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolderWh_finshed

        // TODO Auto-generated method stub

        val ArrayPosition = position;
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.plating_finshed_list_view, parent, false)
            vh = ViewHolderWh_finshed(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolderWh_finshed
        }
        vh.AID.tag = position;
        convertView?.setTag(R.layout.plating_finshed_list_view, position); // 应该在这里让convertView绑定position
        vh.AID.text = listModelArrayList[position].AID
        vh.AMater.text = listModelArrayList[position].AMater
        vh.AVAL.text = listModelArrayList[position].AVAL
        vh.ASIZE.text = listModelArrayList[position].ASIZE
        vh.BID.text = listModelArrayList[position].BID
        vh.BMater.text = listModelArrayList[position].BMater
        vh.BVAL.text = listModelArrayList[position].BVAL
        vh.BSIZE.text = listModelArrayList[position].BSIZE
        vh.BQUANT.text = listModelArrayList[position].BQUANT
        vh.totalQuant.text = listModelArrayList[position].totalQuant
        vh.BATCHID.text = listModelArrayList[position].BATCHID
        vh.AQUANT.text = listModelArrayList[position].AQUANT
        val tag_position = vh.AID.getTag() as Int
        vh.AID.setId(tag_position)
        vh.cbxStatus.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
            buttonView, isChecked ->
            val Caption = vh.AID as TextView
            val position2: Int = Caption.getId()
            if (vh.cbxStatus.isChecked() == true) {
                listModelArrayList[position2].cbxStatus = "1"
            } else {
                listModelArrayList[position2].cbxStatus = "0"
            }
        })
        /*
        if (listModelArrayList[position].cbxStatus == "0") {
            vh.cbxStatus.setChecked(false)
        } else {
            vh.cbxStatus.setChecked(true)
        }*/
        return view
    }

}



//ViewList 裡面 Row 的定義
class ViewHolderWh_finshed(view: View?) {
    val AID: TextView = view?.findViewById<TextView>(R.id.AID) as TextView
    val AMater: TextView = view?.findViewById<TextView>(R.id.AMater) as TextView
    val AVAL: TextView = view?.findViewById<TextView>(R.id.AVAL) as TextView
    val ASIZE: TextView = view?.findViewById<TextView>(R.id.ASIZE) as TextView
    val BID: TextView = view?.findViewById<TextView>(R.id.BID) as TextView
    val BMater: TextView = view?.findViewById<TextView>(R.id.BMater) as TextView
    val BVAL: TextView = view?.findViewById<TextView>(R.id.BVAL) as TextView
    val BSIZE: TextView = view?.findViewById<TextView>(R.id.BSIZE) as TextView
    val BQUANT: TextView = view?.findViewById<TextView>(R.id.BQUANT) as TextView
    val totalQuant: TextView = view?.findViewById<TextView>(R.id.totalQuant) as TextView
    val AQUANT: TextView = view?.findViewById<TextView>(R.id.AQUANT) as TextView
    val BATCHID: TextView = view?.findViewById<TextView>(R.id.BATCHID) as TextView
    val cbxStatus: CheckBox = view?.findViewById<CheckBox>(R.id.cbxStatus) as CheckBox
}
data class ListViewModelAdapterPlating_finshed1(
        var id: Int, var MASTER_MFO_ID: String, var AID: String, var AMater: String,
        var AVAL: String, var ASIZE: String,var AQUANT: String, var BID: String, var BMater: String,
        var BVAL: String,
        var BSIZE: String, var BQUANT: String,
        var totalQuant: String, var BATCHID: String, var cbxStatus: String
){

}