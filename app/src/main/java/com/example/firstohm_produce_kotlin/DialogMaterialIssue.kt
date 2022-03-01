
package com.example.firstohm_produce_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.firstohm_produce_kotlin.MainActivity.Companion.material_issue
import kotlinx.android.synthetic.main.custom_layout_quant_input.view.*
import kotlinx.android.synthetic.main.dialog_material_issue.*
import kotlinx.android.synthetic.main.dialog_prd_test.*
import kotlinx.android.synthetic.main.dialog_spilflow_list.*
import java.util.*


class DialogMaterialIssue: DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //getDialog()!!.getWindow()?.setBackgroundDrawableResource(R.drawable.ic_launcher_background);
        return inflater.inflate(R.layout.dialog_material_issue, container, false)
    }
    override fun onStart() {
        super.onStart()
        try {
            material_issue.clear()
            btn_issue_cancel.setOnClickListener {
                getDialog()?.dismiss()
            }
            if (MainActivity.dept == "花蓮切割") {
                issue_1.text = "線紋太粗" //過胖、過瘦、針孔、沾帽、塗膜硬度不足、測試資料
                issue_2.text = "線紋太細"
                issue_3.text = "線紋太長"
                issue_4.text = "線紋太短"
                issue_5.text = "線紋跳溝"
                issue_6.text = "線紋毛邊"
                issue_7.text = "切帽"
                issue_8.visibility = View.GONE
            } else if (MainActivity.dept == "花蓮底漆") {
                issue_1.text = "酒瓶" //過胖、過瘦、針孔、沾帽、塗膜硬度不足、測試資料
                issue_2.text = "過胖"
                issue_3.text = "過瘦"
                issue_4.text = "針孔"
                issue_5.text = "沾帽"
                issue_6.text = "塗膜硬度不足"
                issue_7.visibility = View.GONE
                issue_8.text = ""
                issue_8.visibility = View.GONE
            } else if (MainActivity.dept == "花蓮色碼" || MainActivity.dept == "花蓮塗裝") {
                issue_1.text = "色碼核對" //色碼核對、、、、、、
                issue_2.text = "斷線"
                issue_3.text = "暈開"
                issue_4.text = "粗細"
                issue_5.text = "起泡"
                issue_6.text = "底漆未乾"
                issue_7.visibility = View.GONE
                issue_8.text = ""
                issue_8.visibility = View.GONE
            } else if (MainActivity.dept == "花蓮全檢") {
                issue_1.text = "混料" //、、測試資料
                issue_2.text = "四點測試"
                issue_3.visibility = View.GONE
                issue_4.text = ""
                issue_4.visibility = View.GONE
                issue_5.text = ""
                issue_5.visibility = View.GONE
                issue_6.text = ""
                issue_6.visibility = View.GONE
                issue_7.text = ""
                issue_7.visibility = View.GONE
                issue_8.text = ""
                issue_8.visibility = View.GONE
            } else if (MainActivity.dept == "花蓮加壓") {
                issue_1.text = "不合格"
                issue_2.visibility = View.GONE
                issue_3.text = ""
                issue_3.visibility = View.GONE
                issue_4.text = ""
                issue_4.visibility = View.GONE
                issue_5.text = ""
                issue_5.visibility = View.GONE
                issue_6.text = ""
                issue_6.visibility = View.GONE
                issue_7.text = ""
                issue_7.visibility = View.GONE
                issue_8.text = ""
                issue_8.visibility = View.GONE
            } else {
                issue_1.text = "測試資料"
                issue_2.text = ""
                issue_2.visibility = View.GONE
                issue_3.text = ""
                issue_3.visibility = View.GONE
                issue_4.text = ""
                issue_4.visibility = View.GONE
                issue_5.text = ""
                issue_5.visibility = View.GONE
                issue_6.text = ""
                issue_6.visibility = View.GONE
                issue_7.text = ""
                issue_7.visibility = View.GONE
                issue_8.text = ""
                issue_8.visibility = View.GONE
            }
            btn_issue_ok.setOnClickListener {
                if (issue_1.isChecked == true) {
                    material_issue.add(issue_1.text.toString())
                }
                if (issue_2.isChecked == true) {
                    material_issue.add(issue_2.text.toString())
                }
                if (issue_3.isChecked == true) {
                    material_issue.add(issue_3.text.toString())
                }
                if (issue_4.isChecked == true) {
                    material_issue.add(issue_4.text.toString())
                }
                if (issue_5.isChecked == true) {
                    material_issue.add(issue_5.text.toString())
                }
                if (issue_6.isChecked == true) {
                    material_issue.add(issue_6.text.toString())
                }
                if (issue_7.isChecked == true) {
                    material_issue.add(issue_7.text.toString())
                }
                if (issue_8.isChecked == true) {
                    material_issue.add(issue_8.text.toString())
                }
                MainActivity.flow_json.put("material_issue", material_issue.toString())
                dismiss()
            }
        } catch (ex: Exception) {

        }
    }
}
