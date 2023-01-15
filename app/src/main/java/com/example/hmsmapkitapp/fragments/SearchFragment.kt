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
import com.huawei.hmf.tasks.OnSuccessListener
import com.huawei.hms.location.FusedLocationProviderClient
import com.huawei.hms.location.LocationServices
import android.widget.Toast
import com.example.hmsmapkitapp.data.models.ChargeStation
import com.google.gson.Gson
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
        mFusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(getContext())
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
            var countrycode: String = binding.spinner1.selectedItem.toString()
            var distance: Int = 10
            try {
                if(binding.editTextDistance.text.isNotEmpty()){
                    distance = binding.editTextDistance.text.toString().toInt()
                }
                if(binding.textView7.text.isNotEmpty()){
                    countrycode = "" // to focus on the country independent location
                    bundle.putDouble("latitude", binding.textView7.text.toString().toDouble())
                }
                if(binding.textView8.text.isNotEmpty()){
                    countrycode = "" // to focus on the country independent location
                    bundle.putDouble("longitude", binding.textView8.text.toString().toDouble())
                }
                bundle.putString("countrycode", countrycode)
                bundle.putInt("distance", distance)
            }catch (e: Exception){
                Log.d("Error", e.toString())
            }
            viewModel.getChargeStations(
                countrycode,
                distance,
                binding.textView7.text.toString().toFloat(),
                binding.textView8.text.toString().toFloat()
            )
            viewModel.chargeStations.observe(requireActivity(), Observer { response ->
                Log.d("Code", " ${response.code()}")
                if (response.isSuccessful) {
                    val gson = Gson()
                    val body = gson.toJson(response?.body())
                    bundle.putString("chargeStations", body)
                } else {
                    Log.d("Error", " ${response.errorBody()}")
                }
                findNavController().navigate(R.id.action_SearchFragment_to_MapFragment, bundle)
            })

        }
        binding.textView9.setOnClickListener {
            binding.textView7.setText(" ")
            binding.textView8.setText(" ")
        }
        binding.imageView.setOnClickListener {
            getLocationData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private lateinit var mFusedLocationProviderClient: FusedLocationProviderClient

    private fun getLocationData() {
        try {
            val lastLocation =
                mFusedLocationProviderClient.lastLocation
            lastLocation.addOnSuccessListener(OnSuccessListener { location ->
                if (location == null) {
                    Toast.makeText(context,"Location data is null", Toast.LENGTH_SHORT).show()
                    return@OnSuccessListener
                }
                binding.textView7.setText(location.latitude.toString())
                binding.textView8.setText(location.longitude.toString())
                Toast.makeText(context,"Location data received successfully", Toast.LENGTH_SHORT).show()
                return@OnSuccessListener
            }).addOnFailureListener { e: Exception ->
                Log.i("Location", "getLastLocation onFailure:" + e.message)
                Toast.makeText(context,"Location data could not be retrieved", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Log.i("Location", "getLastLocation exception:${e.message}")
            Toast.makeText(context,"Location data could not be retrieved", Toast.LENGTH_SHORT).show()
        }
    }
}