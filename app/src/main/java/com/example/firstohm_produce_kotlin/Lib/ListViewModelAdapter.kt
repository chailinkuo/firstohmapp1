package co.ubunifu.kotlinhttpsample.Lib

import android.content.Context
import android.widget.BaseAdapter
//import co.ubunifu.kotlinhttpsample.ListViewModel
//import co.ubunifu.kotlinhttpsample.R

import java.util.ArrayList

abstract class ListViewModelAdapter<T>(val context: Context?, val listModelArrayList: ArrayList<T>) : BaseAdapter() {
    override fun getItem(position: Int): T {
        return listModelArrayList[position]
    }

    fun getMultipleItems(idxList: List<Int>): ArrayList<T> {
        var outputObjList: ArrayList<T> = ArrayList<T>()
        idxList?.forEach{outputObjList.add(listModelArrayList[it])}
        return outputObjList
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return listModelArrayList.size
    }

    open fun getListViewModelList(): ArrayList<T> {
        return listModelArrayList;
    }

    open fun insterItem(toBeInserted:T): Unit
    {
        listModelArrayList.add(toBeInserted)
        super.notifyDataSetChanged()
    }

    //回傳 是否 成功刪除
    open fun delItem(position:Int): Boolean
    {
        if(position >= listModelArrayList.size)
            return false
        listModelArrayList.removeAt(position)
        super.notifyDataSetChanged()
        return true
    }
}