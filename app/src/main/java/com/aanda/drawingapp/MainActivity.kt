package com.aanda.drawingapp

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.slider.Slider


class MainActivity : AppCompatActivity() {
    var drawingView:DrawingView? = null
    private var brushDialog: Dialog? = null
    private var colorDialog:Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var brush:ImageButton = findViewById(R.id.brush)
        var color:ImageButton = findViewById(R.id.color)
        drawingView = findViewById(R.id.drawing_view)
        drawingView?.setSizeForBrush(20.toFloat())
        brush.setOnClickListener{
            if(colorDialog?.isShowing == true){
                colorDialog?.dismiss()
            }
            showBrushSizeChooserDialog()
        }
        color.setOnClickListener{
//            if(brushDialog?.isShowing == true){
//                brushDialog?.dismiss()
//            }
            showColorDialog()
        }
    }

    private fun showColorDialog() {
        colorDialog = Dialog(this)
        colorDialog!!.setContentView(R.layout.color_palette)
        val window:Window? = colorDialog!!.window
        val wlp : WindowManager.LayoutParams = window!!.attributes
//        wlp.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics).toInt()
        wlp.height =WindowManager.LayoutParams.WRAP_CONTENT
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT
        wlp.gravity = Gravity.TOP
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window?.attributes = wlp
        val black:ImageButton = colorDialog!!.findViewById(R.id.black)
        black.setOnClickListener {
            paintClick(it)
        }
        colorDialog!!.setTitle("Brush Size: ")
        colorDialog!!.show()
    }

    fun paintClick(view:View){
        val imageButton = view as ImageButton
        val colorTag = imageButton.tag.toString()
        drawingView?.setColor(colorTag)
        Toast.makeText(this,"Color Changed",Toast.LENGTH_SHORT).show()
    }
    private fun showBrushSizeChooserDialog(){
        brushDialog = Dialog(this)
        val view:View = findViewById(R.id.linearLayout2)
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        brushDialog!!.setContentView(R.layout.slider)
        val window:Window? = brushDialog!!.window
        val wlp : WindowManager.LayoutParams = window!!.attributes
//        wlp.height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, resources.displayMetrics).toInt()
        wlp.height =WindowManager.LayoutParams.WRAP_CONTENT
        wlp.width = WindowManager.LayoutParams.WRAP_CONTENT
        wlp.gravity = Gravity.TOP
        wlp.flags = wlp.flags and WindowManager.LayoutParams.FLAG_DIM_BEHIND.inv()
        window?.attributes = wlp

        brushDialog!!.setTitle("Brush Size: ")
        val slider:Slider = brushDialog!!.findViewById(R.id.brushSlider)
        slider.addOnSliderTouchListener(object:Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {
                val value = slider.value
                Log.i("Slider Value","value is $value")
            }

            override fun onStopTrackingTouch(slider: Slider) {
                val value = slider.value
                Toast.makeText(this@MainActivity,"Brush Size is $value",Toast.LENGTH_SHORT).show()
                drawingView?.setSizeForBrush(value)
            }
        })
//        val small = brushDialog.findViewById<ImageButton>(R.id.ib_small)
//        small.setOnClickListener{
//            drawingView?.setSizeForBrush(10.toFloat())
//            brushDialog.dismiss()
//        }
//        val med = brushDialog.findViewById<ImageButton>(R.id.ib_med)
//        med.setOnClickListener{
//            drawingView?.setSizeForBrush(20.toFloat())
//            brushDialog.dismiss()
//        }
//        val lar  = brushDialog.findViewById<ImageButton>(R.id.ib_large)
//        lar.setOnClickListener {
//            drawingView?.setSizeForBrush(30.toFloat())
//            brushDialog.dismiss()
//        }
        brushDialog!!.show()

    }
}

