package com.suitmedia.suitmediatest.ui.user.thirdscreen

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.suitmedia.suitmediatest.R
import com.suitmedia.suitmediatest.databinding.FragmentThirdScreenBinding
import com.suitmedia.suitmediatest.ui.user.UserActivity
import com.suitmedia.suitmediatest.utils.ViewModelFactory

class ThirdScreenFragment : Fragment() {

    private var _binding: FragmentThirdScreenBinding? = null
    private val binding get() = _binding
    private val viewModel by viewModels<ThirdScreenViewModel> {
        ViewModelFactory.getInstance(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdScreenBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as UserActivity).apply {
            setToolbarTitle(getString(R.string.third_screen))
            geToolbarBackButton().setOnClickListener {
                findNavController().navigate(R.id.action_navigation_3_to_navigation_2)
            }
        }

        setupAdapter()
    }

    private fun setupAdapter() {
        binding?.userRecyclerView?.layoutManager = LinearLayoutManager(requireContext())

        var adapter = UserListAdapter(this)
        binding?.userRecyclerView?.adapter = adapter

        viewModel.getUsers().observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding?.progressBar?.isVisible = it
        }
    }

    fun saveSelected(selectedName: String) {
        viewModel.saveSelected(selectedName)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}