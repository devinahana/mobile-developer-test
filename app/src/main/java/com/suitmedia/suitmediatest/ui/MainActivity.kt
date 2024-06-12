package com.suitmedia.suitmediatest.ui

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.suitmedia.suitmediatest.R
import com.suitmedia.suitmediatest.databinding.ActivityMainBinding
import com.suitmedia.suitmediatest.ui.user.UserActivity
import com.suitmedia.suitmediatest.utils.ViewModelFactory


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpView()

        setUpPalindromeButton()
        setUpNextButton()
    }

    private fun setUpPalindromeButton() {
        fieldValidation(binding.palindromeEditTextLayout, binding.palindromeEditText)
        binding.checkButton.setOnClickListener {
            hideKeyboard()

            val palindromeText = binding.palindromeEditText.text.toString()
            var isValidInput = true

            // Field validation
            if (palindromeText.isEmpty()) {
                binding.palindromeEditTextLayout.error = getString(R.string.empty_field)
                isValidInput = false
            }

            if (isValidInput) {
                // Check if the text is a palindrome
                val cleanedText = palindromeText.replace("\\s".toRegex(), "").lowercase()
                val reversedText = cleanedText.reversed()
                val isPalindrome = cleanedText == reversedText

                if (isPalindrome) {
                    showPalindromeDialog(palindromeText, true)
                } else {
                    showPalindromeDialog(palindromeText, false)
                }

                // clear input
                binding.palindromeEditText.apply {
                    clearFocus()
                    setText("")
                }
            }
        }
    }

    private fun setUpNextButton() {
        fieldValidation(binding.nameEditTextLayout, binding.nameEditText)
        binding.nextButton.setOnClickListener {
            hideKeyboard()

            val name = binding.nameEditText.text.toString()
            var isValidInput = true

            // Field validation
            if (name.isEmpty()) {
                binding.nameEditTextLayout.error = getString(R.string.empty_field)
                isValidInput = false
            }

            if (isValidInput) {
                // save name to session
                viewModel.saveName(name = name)

                // move to UserActivity
                val intent = Intent(this, UserActivity::class.java)
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
            }
        }
    }

    private fun fieldValidation(editTextLayout: TextInputLayout, editText: TextInputEditText) {
        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                // Remove error when user starts typing
               removeTextInputError(editTextLayout)
            }
            override fun afterTextChanged(s: Editable) {}
        }
        editText.addTextChangedListener(textWatcher)
    }

    private fun showPalindromeDialog(palindromeText: String, isPalindrome: Boolean) {
        val message = if (isPalindrome) {
            "\"$palindromeText\" is palindrome"
        } else {
            "\"$palindromeText\" is not palindrome"
        }

        val dialog = MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.palindrome))
            .setMessage(message)
            .setPositiveButton(getString(R.string.ok), null)
            .show()

        if (!isPalindrome) {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
        }
    }

    private fun removeTextInputError(editTextLayout: TextInputLayout) {
        if (editTextLayout.error != null) {
            editTextLayout.error = null
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusedView = this.currentFocus
        if (currentFocusedView != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocusedView.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    private fun setUpView() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );
    }

}