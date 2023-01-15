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
import android.widget.Toast
import androidx.navigation.fragment.findNavController


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
        try {
            var countrycode: String = arguments!!.getString("countrycode").toString()
            if (arguments!!.getInt("distance") != 0) {
                distance = arguments!!.getInt("distance")
            }
            if (countrycode.isEmpty()) {
                LATITUDE = arguments!!.getDouble("latitude")
                LONGITUDE = arguments!!.getDouble("longitude")
            }
            if (arguments!!.getString("chargeStations") != null) {
                var chargeStationsARG: String = arguments!!.getString("chargeStations").toString()
                val gson = Gson()
                chargeStations = gson.fromJson(chargeStationsARG, Array<ChargeStation>::class.java)
            }
        }catch (e: Exception){
            Log.d("Error", e.toString())
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

        val stationIcon1 = BitmapDescriptorFactory.fromResource(R.drawable.chargingstation1)
        val stationIcon2 = BitmapDescriptorFactory.fromResource(R.drawable.chargingstation2)
        val stationIcon3 = BitmapDescriptorFactory.fromResource(R.drawable.chargingstation3)
        val stationIcon4 = BitmapDescriptorFactory.fromResource(R.drawable.chargingstation4)
        val stationIcon5 = BitmapDescriptorFactory.fromResource(R.drawable.chargingstation5)
        val stationIcon6 = BitmapDescriptorFactory.fromResource(R.drawable.chargingstation6)
        chargeStations?.forEach {
            var stationIcon = stationIcon1
            when (it.UsageTypeID) {
                1 -> stationIcon = stationIcon1
                2 -> stationIcon = stationIcon2
                3 -> stationIcon = stationIcon3
                4 -> stationIcon = stationIcon4
                5 -> stationIcon = stationIcon5
                6 -> stationIcon = stationIcon6
            }
            marker = huaweiMap.addMarker(
                MarkerOptions()
                    .icon(stationIcon)
                    .title(it.AddressInfo.Title)
                    .snippet(it.ID.toString())
                    .position(LatLng(it.AddressInfo.Latitude.toDouble(), it.AddressInfo.Longitude.toDouble()))
            )
        }
        huaweiMap.setOnMarkerClickListener { marker ->
            getStationDetail(marker.snippet.toInt())
            false
        }
        // Marker add
        marker = huaweiMap.addMarker(
            MarkerOptions()
                .icon(BitmapDescriptorFactory.defaultMarker())
                .title(getString(R.string.location_name))
                .position(LatLng(LATITUDE, LONGITUDE))
        )
        // Camera position settings
        cameraPosition = CameraPosition.builder()
            .target(LatLng(LATITUDE, LONGITUDE))
            .zoom((15.0-distance/5).toFloat())
            .bearing(BEARING)
            .tilt(TILT).build()
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        huaweiMap.moveCamera(cameraUpdate)
    }

    fun getStationDetail(stationId: Int){
        val bundle = Bundle()
        bundle.putInt("chargeStationId", stationId)
        findNavController().navigate(R.id.action_MapFragment_to_DetailFragment, bundle)
    }

    companion object {
        private lateinit var chargeStations: Array<ChargeStation>
        private val MAP_BUNDLE_KEY = "MapBundleKey"
        private var LATITUDE: Double = 41.042165.toDouble()
        private var LONGITUDE: Double = 29.0092591.toDouble()
        private var distance: Int = 10
        private val ZOOM: Float = 15f
        private val BEARING: Float = 2.0f
        private val TILT: Float = 2.5f
    }
}