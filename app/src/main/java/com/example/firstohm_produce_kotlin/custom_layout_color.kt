package com.example.firstohm_produce_kotlin


import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.custom_layout_quant_color.*
import kotlinx.android.synthetic.main.custom_layout_quant_color.view.*

class custom_layout_color(
        context: Context,
        attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    init {
        inflate(context, R.layout.custom_layout_quant_color, this)
        val customAttributesStyle = context.obtainStyledAttributes(
                attrs,
                R.styleable.custom_layout_quant_color,
                0,
                0
        )
    }
    fun draw_color(color_str: String,view: View){
        try {

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
            color_map["MP106 粉紅"]="#FF99CC"
            color_map["MP106"]="#FF99CC"
            color_map["2070"]="#00B0F0"
            color_map["2791"]="#00FFFF"
            color_map["2791漆"]="#0DE1CD"
            color_map["7921漆"]="#73C3AA"
            color_map["Z0154"]="#FFCCFF"
            color_map["Z0360"]="#F2B300"
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
            color_map["蘋果綠不燃性漆"]="#66FF66"
            color_map["綠色不燃性漆"]="#375623"
            color_map["藍色不燃性漆"]="#3C8C4D"
            color_map["紫色不燃性漆"]="#E3A5E3"
            color_map["磚紅不燃性漆"]="#FF6600"
            color_map["金屬塗料"]="#73AD8F"
            color_map["黑"]="#000000"
            color_map["GR-2"]="#4B854B"
            val color_image = arrayOfNulls<ImageView>(7)
            val Color_text = arrayOfNulls<TextView>(6)
            if((MainActivity.color_direction=="rigth") || (MainActivity.color_direction=="") ){
                color_image[0] = view.color1
                color_image[1] = view.color2
                color_image[2] = view.color3
                color_image[3] = view.color4
                color_image[4] = view.color5
                color_image[5] = view.color6
                color_image[6] = view.color7
                Color_text[0]=view.color_text1
                Color_text[1]=view.color_text2
                Color_text[2]=view.color_text3
                Color_text[3]=view.color_text4
                Color_text[4]=view.color_text5
                Color_text[5]=view.color_text6
            }else{
                color_image[0] = view.color7
                color_image[1] = view.color6
                color_image[2] = view.color5
                color_image[3] = view.color4
                color_image[4] = view.color3
                color_image[5] = view.color2
                color_image[6] = view.color1
                Color_text[0]=view.color_text6
                Color_text[1]=view.color_text5
                Color_text[2]=view.color_text4
                Color_text[3]=view.color_text3
                Color_text[4]=view.color_text2
                Color_text[5]=view.color_text1
            }
            if (color_str!="--"){
                val parts = color_str.split("-".toRegex()).toTypedArray()
                val i = parts[0].length
                val first = parts[0]
                val reverse = StringBuffer(first).reverse().toString()
                val content = first.toCharArray()
                (context as Activity).runOnUiThread {
                    for(i in 0..content.size-1){
                        val colo=content[i].toString()
                        val co=color_map.get(colo).toString()
                        color_image[i]!!.setBackgroundColor(Color.parseColor(co))
                        Color_text[i]!!.setText(colo)
                    }
                    view.back_color.setText("底色:"+parts[2].toString())
                    val co=color_map.get(parts[2].toString()).toString()
                    view.bgcolor.setBackgroundColor(Color.parseColor(co))
                }
            }
        } catch (ex: Exception) {

        }
        ////////////////////////////////
        //COLOR_NUM="紅紅黑-黑-MP106 粉紅";

    }
}