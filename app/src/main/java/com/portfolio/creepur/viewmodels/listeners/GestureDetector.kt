package com.portfolio.creepur.viewmodels.listeners

import android.util.Log
import android.view.MotionEvent

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.portfolio.creepur.viewmodels.listeners.GestureDetector.ListenerForSwipe.Companion.DIRECTION_DOWN
import com.portfolio.creepur.viewmodels.listeners.GestureDetector.ListenerForSwipe.Companion.DIRECTION_UP
import kotlinx.android.synthetic.main.activity_home_page.view.*

class GestureDetector(private val listener: ListenerForSwipe) : View.OnTouchListener {

    interface ListenerForSwipe {
        companion object{
            const val DIRECTION_UP = 0
            const val DIRECTION_DOWN = 1
        }
        fun changeUI(direction: Int){
        }
    }

    private var currentPointer: Int? = null
    private var startingPointerY: Float? = null
    private lateinit var viewGroup: ViewGroup


    override fun onTouch(p0: View?, motionEvent: MotionEvent?): Boolean {
        // maybe add a scaleDetector in here too? Apparently it listens to this motionEvent and gauges whether it should also start doing its job //
        viewGroup = p0 as ViewGroup
        val conLayoutBottom: ConstraintLayout = viewGroup.constraintLayoutBottom
        var lastTouchX: Float?
        var lastTouchY: Float?
        when (motionEvent?.action) {
            MotionEvent.ACTION_DOWN -> {
                currentPointer = motionEvent.getPointerId(0)
                motionEvent.actionIndex.also { _ ->
                    // Starting variables for where the touch is at the moment
                    lastTouchX = motionEvent.rawX
                    lastTouchY = motionEvent.rawY
                    startingPointerY = lastTouchY
                    Log.d("TAG", "Pointer went down at X: $lastTouchX and Y: $lastTouchY")
                }
            }
            MotionEvent.ACTION_MOVE -> {
                // the new spot
                val (x: Float, y: Float) =
                    motionEvent.findPointerIndex(currentPointer!!).let { _ ->
                        motionEvent.rawX to motionEvent.rawY
                    }

                // Checking for correct swipe movements and changing UI if so
                if (startingPointerY != null) {
                    if (startingPointerY!! > conLayoutBottom.y && y < conLayoutBottom.y && !isUiExpanded()) {
                        Log.d("TAG", "Triggered way up.")
                        listener.changeUI(DIRECTION_UP)
                    } else if (startingPointerY!! < conLayoutBottom.y && y > conLayoutBottom.y && isUiExpanded()){
                        Log.d("TAG", "Triggered way down.")
                        listener.changeUI(DIRECTION_DOWN)
                    }
                }

                //invalidate()  -> this cancels animations going on I think?

                // updating where the pointer is at the moment
                lastTouchX = x
                lastTouchY = y
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                currentPointer = null
                startingPointerY = null
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

    // uses creepSpinnerOptionstext's visibility as a marker for whether or not constraintLayoutBottom should be able to animate
    private fun isUiExpanded(): Boolean = viewGroup.creepSpinnerOptionsText.visibility == View.VISIBLE
}
