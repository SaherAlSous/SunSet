package com.bignerdranch.android.sunset

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationSet
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import kotlin.time.times

class MainActivity : AppCompatActivity() {

    private lateinit var sceneView: View
    private lateinit var sunView: View
    private lateinit var skyView: View

    private val blueSkyColor : Int by lazy { //page 632
        ContextCompat.getColor(this, R.color.blue_sky)
    }
    private val sunSetSkyColor : Int by lazy {
        ContextCompat.getColor(this, R.color.sunset_sky)
    }
    private val nightSkyColor : Int by lazy {
        ContextCompat.getColor(this, R.color.night_sky)
    }

    private val heatSun : Int by lazy {
        ContextCompat.getColor(this, R.color.hot_sun)
    }

    private val normalSun : Int by lazy {
        ContextCompat.getColor(this, R.color.bright_sun)
    }

        var animated = false


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sceneView = findViewById(R.id.scene)
        sunView = findViewById(R.id.sun)
        skyView = findViewById(R.id.sky)


        sceneView.setOnClickListener{
            if (!animated) startAnimation() else reverse()
        }

    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun startAnimation(){ //page. 626
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(sunView, "y", sunYStart, sunYEnd)
            .setDuration(3000)

        val sunHeat = ObjectAnimator
            .ofInt(sunView, "Color",normalSun, heatSun)
            .setDuration(1000)



        val sunSetSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", blueSkyColor, sunSetSkyColor)
            .setDuration(3000)

        val nightSkyAnimator =ObjectAnimator
            .ofInt(skyView,"backgroundColor", sunSetSkyColor, nightSkyColor)
            .setDuration(1500)


        heightAnimator.interpolator = AccelerateInterpolator() //page. 631
        sunSetSkyAnimator.setEvaluator(ArgbEvaluator()) //page. 633
        nightSkyAnimator.setEvaluator(ArgbEvaluator()) //page. 634

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(sunHeat)
            .with(sunSetSkyAnimator)
            .before(nightSkyAnimator)
        animatorSet.start()
        animated = true
     }

    @SuppressLint("ObjectAnimatorBinding")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun reverse(){
        val sunYStart = sunView.top.toFloat()
        val sunYEnd = skyView.height.toFloat()

        val heightAnimator = ObjectAnimator
            .ofFloat(sunView, "y", sunYEnd,sunYStart)
            .setDuration(3000)

        val sunHeat = ObjectAnimator
            .ofInt(sunView, "color",normalSun ,heatSun)

        val sunSetSkyAnimator = ObjectAnimator
            .ofInt(skyView, "backgroundColor", sunSetSkyColor, blueSkyColor)
            .setDuration(3000)

        val nightSkyAnimator =ObjectAnimator
            .ofInt(skyView,"backgroundColor", nightSkyColor, sunSetSkyColor)
            .setDuration(1500)


        heightAnimator.interpolator = AccelerateInterpolator() //page. 631
        sunSetSkyAnimator.setEvaluator(ArgbEvaluator()) //page. 633
        nightSkyAnimator.setEvaluator(ArgbEvaluator()) //page. 634

        val animatorSet = AnimatorSet()
        animatorSet.play(heightAnimator)
            .with(sunSetSkyAnimator)
            .with(sunHeat)
            .after(nightSkyAnimator)


        animatorSet.start()
        animated = false
    }

}
