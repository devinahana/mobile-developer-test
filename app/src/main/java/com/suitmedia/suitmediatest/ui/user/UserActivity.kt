package com.suitmedia.suitmediatest.ui.user

import android.os.Bundle
import android.view.WindowManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.suitmedia.suitmediatest.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()
        setStatusBarTextColor()
    }

    fun setToolbarTitle(title: String) {
        binding.toolbarTitleTextView.text = title
    }

    fun geToolbarBackButton(): ImageView {
        return binding.toolbarBackButton
    }

    private fun setUpView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

    private fun setStatusBarTextColor() {
        val window = window
        val decorView = window.decorView

        val windowInsetsController = WindowCompat.getInsetsController(window, decorView)
        windowInsetsController.isAppearanceLightStatusBars = true
    }

}