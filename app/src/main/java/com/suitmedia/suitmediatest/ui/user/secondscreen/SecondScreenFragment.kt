package com.suitmedia.suitmediatest.ui.user.secondscreen

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.suitmedia.suitmediatest.R
import com.suitmedia.suitmediatest.databinding.FragmentSecondScreenBinding
import com.suitmedia.suitmediatest.ui.MainActivity
import com.suitmedia.suitmediatest.ui.user.UserActivity
import com.suitmedia.suitmediatest.utils.ViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SecondScreenFragment : Fragment() {
    private var _binding: FragmentSecondScreenBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<SecondScreenViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.name.observe(viewLifecycleOwner) {name ->
            binding?.nameTextView?.text = name
        }

        viewModel.selectedName.observe(viewLifecycleOwner) {name ->
            if (name.isNotEmpty()) {
                binding?.selectedUserNameTextView?.text = name
            }
        }

        binding?.chooseUserButton?.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_2_to_navigation_3)
        }

        CoroutineScope(Dispatchers.Main).launch {
            (requireActivity() as UserActivity).apply {
                setToolbarTitle(getString(R.string.second_screen))
                geToolbarBackButton().setOnClickListener {
                    viewModel.logout()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        const val USERNAME = "username"
    }

}