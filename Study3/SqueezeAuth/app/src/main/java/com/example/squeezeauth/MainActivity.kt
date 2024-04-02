package com.example.squeezeauth

import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.GestureDetectorCompat
import com.example.squeezeauth.ui.theme.SqueezeAuthTheme
import java.time.LocalDate
import kotlin.properties.Delegates

private const val DEBUG_TAG = "Gestures"

class MainActivity : ComponentActivity() {
    private val valuesForFinger = arrayListOf<Float>()
    private var timePressed by Delegates.notNull<Long>();
    private var numbOfFingers = 0
    private val averagePressure = 0;
    private var startTime = 0.toLong();
    private var xVal by Delegates.notNull<Float>();
    private var yVal by Delegates.notNull<Float>();
    private lateinit var mDetector: GestureDetectorCompat

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mDetector = GestureDetectorCompat(this, MyGestureListener())
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if(event.action == MotionEvent.ACTION_DOWN && startTime == 0.toLong()){
            Log.d(DEBUG_TAG, "------------------------------------")
            Log.d(DEBUG_TAG, "Registered button pressed")
            startTime = System.currentTimeMillis();
            valuesForFinger.clear();
            xVal = event.x
            yVal = event.y
        }
        valuesForFinger.add(event.pressure)
        numbOfFingers = numbOfFingers.coerceAtLeast(event.pointerCount)



        if(event.action == MotionEvent.ACTION_UP){
            timePressed = System.currentTimeMillis() - startTime
            startTime = 0.toLong()

            var averagePressure = 0.0
            for(value in valuesForFinger){
                averagePressure+= value
            }
            averagePressure /= valuesForFinger.size

            Log.d(DEBUG_TAG, "Time pressed: $timePressed")
            Log.d(DEBUG_TAG, "number of fingers: $numbOfFingers")
            Log.d(DEBUG_TAG, "Average Pressure: $averagePressure")
            Log.d(DEBUG_TAG, "($timePressed, $averagePressure)")
            Log.d(DEBUG_TAG, "------------------------------------")
            numbOfFingers = 0
        }

//        mDetector.onTouchEvent(event)
        return super.onTouchEvent(event)
    }

    private class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        override fun onDown(e: MotionEvent): Boolean {
            Log.d(DEBUG_TAG, "--------------------------")
            Log.d(DEBUG_TAG, "********"+e.pressure)
            Log.d(DEBUG_TAG, "********"+e.getPressure(e.getPointerId(0)))
            Log.d(DEBUG_TAG, "*****"+e.getPressure(e.getPointerId(e.actionIndex)))
            Log.d(DEBUG_TAG, "x position: "+e.x)
            Log.d(DEBUG_TAG, "y position: "+e.y)
            Log.d(DEBUG_TAG, "--------------------------")
            return true
        }

//        override fun onShowPress(e: MotionEvent) {
//            Log.d(DEBUG_TAG, "--------------------------")
//            Log.d(DEBUG_TAG, "********"+e.pressure)
//            Log.d(DEBUG_TAG, "********"+e.getPressure(e.getPointerId(0)))
//            Log.d(DEBUG_TAG, "*****"+e.getPressure(e.getPointerId(e.actionIndex)))
//            Log.d(DEBUG_TAG, "x position: "+e.x)
//            Log.d(DEBUG_TAG, "y position: "+e.y)
//            Log.d(DEBUG_TAG, "--------------------------")
//            return super.onShowPress(e)
//        }

//        override fun onFling(
//            e1: MotionEvent?,
//            event1: MotionEvent,
//            velocityX: Float,
//            velocityY: Float
//        ): Boolean {
//            Log.d(DEBUG_TAG, "onFling: $e1 $event1")
//            return true
//        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SqueezeAuthTheme {
        Greeting("Android")
    }
}