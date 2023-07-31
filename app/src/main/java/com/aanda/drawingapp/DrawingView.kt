package com.aanda.drawingapp

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.TypedValue
import android.view.MotionEvent
import android.view.View

class DrawingView(context:Context,attr:AttributeSet) : View(context,attr) {
    private var mDrawPath : CustomPath? = null
    private var mCanvasBitmap:Bitmap? = null
    private var mDrawPaint:Paint? = null
    private var mCanvasPaint:Paint? = null
    private var mBrushSize:Float = 0.toFloat()
    private var color = Color.YELLOW
    private var canvas: Canvas? = null
    private val mPaths = ArrayList<CustomPath>()
    //initializer block
    init {
        setUpDrawing()
    }

    private fun setUpDrawing() {
        mDrawPaint = Paint()
        mDrawPath = CustomPath(color,mBrushSize)
        mDrawPaint!!.style = Paint.Style.STROKE
        mDrawPaint!!.color = color
        mDrawPaint!!.strokeJoin = Paint.Join.ROUND
        mDrawPaint!!.strokeCap=Paint.Cap.SQUARE
        mCanvasPaint = Paint(Paint.DITHER_FLAG)
//        mBrushSize = 20.toFloat()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCanvasBitmap = Bitmap.createBitmap(w,h,Bitmap.Config.ARGB_8888)
        canvas = Canvas(mCanvasBitmap!!)
    }

    //change canvas to canvas?
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(mCanvasBitmap!!,0f,0f,mCanvasPaint)
        for(i in mPaths){
            mDrawPaint!!.strokeWidth = i.brushThickness
            mDrawPaint!!.color = i.color
            canvas.drawPath(i,mDrawPaint!!)
        }
        if(!mDrawPath!!.isEmpty){
            mDrawPaint!!.strokeWidth=mDrawPath!!.brushThickness
            canvas.drawPath(mDrawPath!!,mDrawPaint!!)

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchx = event?.x
        val touchy = event?.y

        when(event?.action){
            MotionEvent.ACTION_DOWN  -> {
                mDrawPath!!.color = color
                mDrawPath!!.brushThickness = mBrushSize

                mDrawPath!!.reset()
                if(touchx != null){
                    if(touchy != null){
                        mDrawPath!!.moveTo(touchx,touchy)
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                if (touchx != null) {
                    if (touchy != null) {
                        mDrawPath!!.lineTo(touchx,touchy)
                    }
                }
            }

            MotionEvent.ACTION_UP -> {
                mPaths.add(mDrawPath!!)
                mDrawPath = CustomPath(color,mBrushSize)
            }
            else-> return false
        }
        invalidate()
        return true
    }

    fun setSizeForBrush(newSize:Float){
        mBrushSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
          newSize,resources.displayMetrics
            )
        mDrawPaint!!.strokeWidth = mBrushSize
    }

    fun setColor(colorHex:String){
       color = Color.parseColor(colorHex)
        mDrawPaint!!.color = color
    }
    internal inner class CustomPath(var color:Int,var brushThickness:Float):Path() {

    }
}