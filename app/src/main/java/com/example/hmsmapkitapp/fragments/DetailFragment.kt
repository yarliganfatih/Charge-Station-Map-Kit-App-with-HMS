package com.example.hmsmapkitapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.hmsmapkitapp.databinding.FragmentDetailBinding
import androidx.lifecycle.Observer
import com.example.hmsmapkitapp.MainViewModelFactory
import com.example.hmsmapkitapp.data.repository.Repository
import com.example.hmsmapkitapp.data.viewmodel.MainViewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentDetailBinding.inflate(inflater, container, false)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            MainViewModel::
            class.java
        )
        viewModel.getChargeStationById(211219)
        viewModel.chargeStationById.observe(requireActivity(), Observer { response ->
            Log.d("Code", " ${response.code()}")
            if (response.isSuccessful) {
                Log.d("Body", " ${response.body()}")
            } else {
                Log.d("Error", " ${response.errorBody()}")
            }
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}