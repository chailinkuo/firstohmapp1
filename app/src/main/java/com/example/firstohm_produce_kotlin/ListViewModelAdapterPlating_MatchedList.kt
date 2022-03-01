
package co.ubunifu.kotlinhttpsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import co.ubunifu.kotlinhttpsample.Lib.ListViewModelAdapter
import com.example.firstohm_produce_kotlin.R
import kotlin.collections.ArrayList


public class ListViewModelAdapterPlating_MatchedList(context: Context,
        listModelArrayList: ArrayList<ListViewModelAdapterPlating_MatchedList1>) : ListViewModelAdapter<ListViewModelAdapterPlating_MatchedList1>(

        context, listModelArrayList
) {
    private var selectedEditTextPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolderWh_MatchedList

        // TODO Auto-generated method stub

        val ArrayPosition = position;
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.plating_manual_listview, parent, false)
            vh = ViewHolderWh_MatchedList(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolderWh_MatchedList
        }

        vh.MASTER_MFO_ID.tag = position;
        convertView?.setTag(R.layout.plating_manual_listview, position); // 应该在这里让convertView绑定position

        vh.MASTER_MFO_ID.text = listModelArrayList[position].MASTER_MFO_ID
        vh.PackID.text = listModelArrayList[position].PackID
        vh.dataSource.text = listModelArrayList[position].dataSource
        vh.RTYPE.text = listModelArrayList[position].RTYPE
        vh.packQuant.text = listModelArrayList[position].packQuant
        vh.Tol.text = listModelArrayList[position].Tol
        vh.Vals.text = listModelArrayList[position].Vals

        val tag_position = vh.MASTER_MFO_ID.getTag() as Int
        vh.PackIDc.setId(tag_position)

        vh.PackIDc.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener {
            buttonView, isChecked ->
            val Caption = vh.Vals as EditText
            val position2: Int = Caption.getId()
            if (vh.PackIDc.isChecked() == true) {
                listModelArrayList[position2].PackIDc = "1"
            } else {
                listModelArrayList[position2].PackIDc = "0"
            }
        })
        if (listModelArrayList[position].PackIDc == "0") {
            vh.PackIDc.setChecked(false)
        } else {
            vh.PackIDc.setChecked(true)
        }
        return view
    }
}
//ViewList 裡面 Row 的定義
class ViewHolderWh_MatchedList(view: View?) {
    val PackIDc: CheckBox = view?.findViewById<CheckBox>(R.id.PackIDc) as CheckBox
    val PackID: TextView = view?.findViewById<TextView>(R.id.PackIDc) as TextView
    val dataSource: TextView = view?.findViewById<TextView>(R.id.dataSource) as TextView
    val RTYPE: TextView = view?.findViewById<TextView>(R.id.RTYPE) as TextView
    val packQuant: TextView = view?.findViewById<TextView>(R.id.packQuant) as TextView
    val Tol: TextView = view?.findViewById<TextView>(R.id.Tol) as TextView
    val Vals: TextView = view?.findViewById<TextView>(R.id.Vals) as TextView
    val MASTER_MFO_ID: TextView = view?.findViewById<TextView>(R.id.SUBFLOWID) as TextView

}
data class ListViewModelAdapterPlating_MatchedList1(
        var id: Int, var PackIDc: String, var PackID: String, var dataSource: String, var SRCSIGNID: String,
        var RTYPE: String, var packQuant: String, var Tol: String,
        var Vals: String, var MASTER_MFO_ID: String,
        var Val: String, var Size: String
        //0, PackID, dataSource, SRCSIGNID, RTYPE, packQuant, Tol, Vals, MASTER_MFO_ID,
        //Val, Size
){


}