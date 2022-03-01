
package co.ubunifu.kotlinhttpsample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.ubunifu.kotlinhttpsample.Lib.ListViewModelAdapter
import com.example.firstohm_produce_kotlin.R
import kotlin.collections.ArrayList


public class ListViewModelAdapterPlating_sendList(context: Context,
                                                     listModelArrayList: ArrayList<ListViewModelAdapterPlating_sendList1>) : ListViewModelAdapter<ListViewModelAdapterPlating_sendList1>(

        context, listModelArrayList
) {
    private var selectedEditTextPosition = -1

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View?
        val vh: ViewHolderWh_sendList

        // TODO Auto-generated method stub

        val ArrayPosition = position;
        if (convertView == null) {
            val layoutInflater = LayoutInflater.from(context)
            view = layoutInflater.inflate(R.layout.plating_send_list, parent, false)
            vh = ViewHolderWh_sendList(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ViewHolderWh_sendList
        }

        convertView?.setTag(R.layout.plating_send_list, position); // 应该在这里让convertView绑定position


        vh.PackID.text = listModelArrayList[position].PackID
        vh.dataSource.text = listModelArrayList[position].dataSource
        vh.RTYPE.text = listModelArrayList[position].RTYPE
        vh.packQuant.text = listModelArrayList[position].packQuant
        vh.Tol.text = listModelArrayList[position].Tol

        return view
    }
}
//ViewList 裡面 Row 的定義
class ViewHolderWh_sendList(view: View?) {
    val PackID: TextView = view?.findViewById<TextView>(R.id.PackID) as TextView
    val dataSource: TextView = view?.findViewById<TextView>(R.id.dataSource) as TextView
    val RTYPE: TextView = view?.findViewById<TextView>(R.id.RTYPE) as TextView
    val packQuant: TextView = view?.findViewById<TextView>(R.id.packQuant) as TextView
    val Tol: TextView = view?.findViewById<TextView>(R.id.Tol) as TextView

}
data class ListViewModelAdapterPlating_sendList1(
        var id: Int, var PackID: String, var dataSource: String, var SRCSIGNID: String,
        var RTYPE: String, var packQuant: String, var Tol: String
        //0, PackID, dataSource, SRCSIGNID, RTYPE, packQuant, Tol, Vals, MASTER_MFO_ID,
        //Val, Size
){


}