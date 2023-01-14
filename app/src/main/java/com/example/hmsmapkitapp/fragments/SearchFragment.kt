package com.example.hmsmapkitapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.hmsmapkitapp.MainViewModelFactory
import com.example.hmsmapkitapp.R
import com.example.hmsmapkitapp.data.repository.Repository
import com.example.hmsmapkitapp.data.viewmodel.MainViewModel
import com.example.hmsmapkitapp.databinding.FragmentSearchBinding
import androidx.lifecycle.Observer

/**
 * A simple [Fragment] subclass as the Search destination in the navigation.
 */
class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

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
        binding.buttonSearch.setOnClickListener {
            bundle.putString("countrycode", "TR")
            bundle.putInt("distance", 1)
            bundle.putFloat("latitude", 41.042165.toFloat())
            bundle.putFloat("longitude", 29.0092591.toFloat())
            findNavController().navigate(R.id.action_SearchFragment_to_MapFragment, bundle)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}