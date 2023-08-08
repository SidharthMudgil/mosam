package com.sidharth.mosam

import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.flaviofaria.kenburnsview.RandomTransitionGenerator
import com.sidharth.mosam.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        installSplashScreen()
        setContentView(activityMainBinding.root)
        setupTransitionGenerator()
    }

    private fun setupTransitionGenerator() {
        activityMainBinding.bgImage.setTransitionGenerator(
            RandomTransitionGenerator(
                TRANSITION_DURATION,
                AccelerateDecelerateInterpolator()
            )
        )
    }

    companion object {
        private const val TRANSITION_DURATION = 30000L
    }
}