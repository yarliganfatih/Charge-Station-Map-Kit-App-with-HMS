package com.example.hmsmapkitapp.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.hmsmapkitapp.MainViewModelFactory
import com.example.hmsmapkitapp.databinding.FragmentMapBinding
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.*
import com.example.hmsmapkitapp.R
import com.example.hmsmapkitapp.data.models.ChargeStation
import com.example.hmsmapkitapp.data.repository.Repository
import com.example.hmsmapkitapp.data.viewmodel.MainViewModel
import com.google.gson.Gson


class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var huaweiMap: HuaweiMap
    private lateinit var marker: Marker
    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var cameraPosition: CameraPosition

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(
            MainViewModel::
            class.java
        )
        if(arguments!!.getFloat("latitude") != null){
            val countrycode: String = arguments!!.getString("countrycode").toString()
            val distance: Int = arguments!!.getInt("distance")
            LATITUDE = arguments!!.getFloat("latitude").toFloat()
            LONGITUDE = arguments!!.getFloat("longitude").toFloat()
            Log.d("countrycode", " ${countrycode}")

            viewModel.getChargeStations(countrycode, distance, LATITUDE, LONGITUDE)
            viewModel.chargeStations.observe(requireActivity(), Observer { response ->
                Log.d("Code", " ${response.code()}")
                if (response.isSuccessful) {
                    val body = response?.body()?.toString()
                    val gson = Gson()

                    try {
                        val name2 = gson.fromJson(body, Array<ChargeStation>::class.java)
                        chargeStations = name2
                        //Updating value of name2
                    }
                    catch (e: Exception){
                        print("Error roroorirorr")
                        print(e)
                    }
                    //chargeStations = response.body()
                } else {
                    Log.d("Error", " ${response.errorBody()}")
                }
            })
        }

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var mapViewBundle: Bundle? = null
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAP_BUNDLE_KEY)
        }

        binding.huaweiMapView.apply {
            onCreate(mapViewBundle)
            getMapAsync(this@MapFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: HuaweiMap) {

        // Mapping
        huaweiMap = map

        chargeStations?.forEach {
            Log.d("xx", " ${it}")
        }
        // Marker add
        marker = huaweiMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker())
                .title(getString(R.string.location_name))
                .position(LatLng(LATITUDE.toDouble(), LONGITUDE.toDouble()))

        )
        // Camera position settings
        cameraPosition = CameraPosition.builder()
            .target(LatLng(LATITUDE.toDouble(), LONGITUDE.toDouble()))
            .zoom(ZOOM)
            .bearing(BEARING)
            .tilt(TILT).build()
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        huaweiMap.moveCamera(cameraUpdate)
    }

    companion object {
        private lateinit var chargeStations: Array<ChargeStation>
        private val MAP_BUNDLE_KEY = "MapBundleKey"
        private var LATITUDE: Float = 41.042165f
        private var LONGITUDE: Float = 28.0092591f
        private val ZOOM: Float = 15f
        private val BEARING: Float = 2.0f
        private val TILT: Float = 2.5f
    }
}