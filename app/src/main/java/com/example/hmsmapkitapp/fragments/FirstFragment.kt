package com.example.hmsmapkitapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hmsmapkitapp.R
import com.example.hmsmapkitapp.databinding.FragmentFirstBinding
import com.example.hmsmapkitapp.MainViewModelFactory
import com.example.hmsmapkitapp.data.repository.Repository
import com.example.hmsmapkitapp.data.viewmodel.MainViewModel
import android.widget.Toast

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            MainViewModel::
            class.java
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = Bundle()
        binding.hwidButton.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SearchFragment, bundle)
            Toast.makeText(context,"Authentication step skipped", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}