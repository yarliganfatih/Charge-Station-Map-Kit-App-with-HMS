package com.example.hmsmapkitapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.hmsmapkitapp.databinding.FragmentSecondBinding
import com.huawei.hms.maps.*
import com.huawei.hms.maps.model.*
import androidx.navigation.fragment.findNavController


class SecondFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    private lateinit var huaweiMap: HuaweiMap
    private lateinit var marker: Marker
    private lateinit var cameraUpdate: CameraUpdate
    private lateinit var cameraPosition: CameraPosition

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
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
            getMapAsync(this@SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(map: HuaweiMap) {

        // Mapping
        huaweiMap = map

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
            .zoom(ZOOM)
            .bearing(BEARING)
            .tilt(TILT).build()
        cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        huaweiMap.moveCamera(cameraUpdate)
    }

    companion object {
        private const val MAP_BUNDLE_KEY = "MapBundleKey"
        private const val LATITUDE: Double = 41.042165
        private const val LONGITUDE: Double = 29.0092591
        private const val ZOOM: Float = 15f
        private const val BEARING: Float = 2.0f
        private const val TILT: Float = 2.5f
    }
}