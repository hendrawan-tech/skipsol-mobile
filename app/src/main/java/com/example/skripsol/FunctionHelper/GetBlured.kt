package com.example.skripsol.FunctionHelper

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.graphics.drawable.toDrawable

object GetBlured {
    private const val BLUR_RADIUS = 25f


    fun applyBlur(activity: Context?, enableBlur: Boolean) {

        if (activity is Activity) {
            val activity = activity as Activity
            activity.window?.let { window ->
                val rootView =
                    activity?.window!!.decorView.findViewById<ViewGroup>(android.R.id.content)
                when (enableBlur) {
                    true -> {
                        val blurredView = createBlurredView(activity)
                        rootView.addView(blurredView)
                    }
                    false -> {
                        val blurredView = rootView.findViewWithTag<View>("blurredView")
                        rootView.removeView(blurredView)
                    }
                }
            }
        }


    }


    private fun createBlurredView(activity: Activity): View {
        val rootView = activity.window.decorView.findViewById<ViewGroup>(android.R.id.content)
        val screenshot = getScreenshot(rootView)
        val blurredBitmap = blurBitmap(activity, screenshot)

        val blurredView = View(activity).apply {
            tag = "blurredView"
            background = blurredBitmap.toDrawable(activity.resources)
        }

        val params = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        blurredView.layoutParams = params
        return blurredView
    }

    private fun getScreenshot(view: View): Bitmap {
        val screenshot = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
        view.draw(canvas)
        return screenshot
    }

    private fun blurBitmap(activity: Activity, bitmap: Bitmap): Bitmap {
        val blurredBitmap =
            Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)

        val renderScript = RenderScript.create(activity)
        val blurScript = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript))

        val input = Allocation.createFromBitmap(renderScript, bitmap)
        val output = Allocation.createFromBitmap(renderScript, blurredBitmap)

        blurScript.setRadius(BLUR_RADIUS)
        blurScript.setInput(input)
        blurScript.forEach(output)

        output.copyTo(blurredBitmap)

        input.destroy()
        output.destroy()
        blurScript.destroy()
        renderScript.destroy()

        return blurredBitmap
    }
}