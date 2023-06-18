package com.example.skripsol.FunctionHelper.GetMaterial


import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.skripsol.R

class GetButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = androidx.appcompat.R.attr.borderlessButtonStyle
) : AppCompatButton(context, attrs, defStyleAttr) {
    private var dashWidth = dpToPx(5)
    private var dashGap = dpToPx(5)
    private val dashPathEffect: DashPathEffect
    private val strokeColor: Int
    private val strokeWidth: Float
    private val cornerRadius: Float

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs, R.styleable.GetButton, defStyleAttr, 0
        )
        strokeColor = typedArray.getColor(
            R.styleable.GetButton_strokeColor, ContextCompat.getColor(context, R.color.red_colorButton)
        )
        strokeWidth = typedArray.getDimension(
            R.styleable.GetButton_strokeWidth, dpToPx(1).toFloat()
        )
        cornerRadius = typedArray.getDimension(
            R.styleable.GetButton_cornerRadius, dpToPx(12).toFloat()
        )
        typedArray.recycle()

        val typeface = ResourcesCompat.getFont(context, R.font.golos_text_regular)
        setTypeface(typeface)

        // Set background and text color
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent))
        setTextColor(ContextCompat.getColor(context, R.color.font_primary_color))

        // Set outline provider and clip to outline
        outlineProvider = DashedOutlineProvider()
        clipToOutline = true

        dashPathEffect = DashPathEffect(floatArrayOf(dashWidth.toFloat(), dashGap.toFloat()), 0f)
    }

    override fun onDraw(canvas: Canvas) {
        drawDashOutline(canvas)
        super.onDraw(canvas)
    }

    private fun drawDashOutline(canvas: Canvas) {
        val bounds = RectF(
            strokeWidth,
            strokeWidth,
            this.width - strokeWidth,
            this.height - strokeWidth
        )
        val paint = Paint().apply {
            style = Paint.Style.STROKE
            color = strokeColor
            strokeWidth = this@GetButton.strokeWidth
            pathEffect = dashPathEffect
        }
        canvas.drawRoundRect(bounds, cornerRadius, cornerRadius, paint)
    }

    private inner class DashedOutlineProvider : ViewOutlineProvider() {
        override fun getOutline(view: View, outline: Outline) {
            val bounds = Rect()
            view.background?.getPadding(bounds)
            val rect = Rect(
                bounds.left,
                bounds.top,
                view.width - bounds.right,
                view.height - bounds.bottom
            )
            outline.setRoundRect(rect, cornerRadius)
        }
    }

    private fun dpToPx(dp: Int): Int {
        val density = resources.displayMetrics.density
        return (dp * density).toInt()
    }
}






