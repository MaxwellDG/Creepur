package com.portfolio.creepur.viewmodels.listeners

import android.util.Log
import android.view.MotionEvent

import android.view.View

class GestureDetector(private val listener: AnimationPromptListener) : View.OnTouchListener {

    private var currentPointer: Int? = null


    override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
        // maybe add a scaleDetector in here too? Apparently it listens to this motionEvent and gauges whether it should also start doing its job //
        var lastTouchX: Float?
        var lastTouchY: Float?
        when (motionEvent?.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPointer = motionEvent.getPointerId(0)
                motionEvent.actionIndex.also { pointer ->
                    // Starting variables for where the touch is at the moment
                    lastTouchX = motionEvent.rawX
                    lastTouchY = motionEvent.rawY
                    Log.d("TAG", "Pointer went down at X: $lastTouchX and Y: $lastTouchY")
                }
            }
            MotionEvent.ACTION_MOVE -> {
                // the new spot
                val (x: Float, y: Float) =
                    motionEvent.findPointerIndex(currentPointer!!).let { _ ->
                        motionEvent.rawX to motionEvent.rawY
                    }

                if(p0 != null && y < p0.y){
                    //listener.callback()
                    Log.d("TAG", "Gone above")
                } else if(p0 != null && y > p0.y + p0.height){
                    //TODO: the above line is probably wrong since things will be changing
                    //listener.callBack()
                    Log.d("TAG", "Gone below")
                }

                //TODO: everything is gucci except this part.

                // mPosX += x - lastTouchX
                // mPosY += y - lastTouchY


                //invalidate()  -> this cancels animations going on I think?

                // updating where the pointer is at the moment
                lastTouchX = x
                lastTouchY = y
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                currentPointer = null
            }
            MotionEvent.ACTION_POINTER_UP -> {
                // This is if your first finger raises but other fingers continue to move around. Probably don't need to even include this section?
                motionEvent.actionIndex.also { pointerIndex ->
                    motionEvent.getPointerId(pointerIndex)
                        .takeIf { it == currentPointer }
                        ?.run {
                            // This was our active pointer going up. Choose a new
                            // active pointer and adjust accordingly.
                            val newPointerIndex = if (pointerIndex == 0) 1 else 0
                            lastTouchX = motionEvent.rawX
                            lastTouchY = motionEvent.rawY
                            currentPointer = motionEvent.getPointerId(pointerIndex)
                        }
                }
            }
        }
        return true
    }
}
