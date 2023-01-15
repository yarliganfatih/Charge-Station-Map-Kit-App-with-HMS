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
import com.example.hmsmapkitapp.data.models.ChargeStation
import com.example.hmsmapkitapp.data.repository.Repository
import com.example.hmsmapkitapp.data.viewmodel.MainViewModel
import com.example.hmsmapkitapp.databinding.FragmentDetailBinding
import android.widget.Toast


/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private lateinit var chargeStations: Array<ChargeStation>
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
        val chargeStationId: Int
        chargeStationId = if (arguments != null) {
            arguments!!.getInt("chargeStationId")
        } else {
            1
        }
        viewModel.getChargeStationById(chargeStationId)
        viewModel.chargeStationById.observe(requireActivity(), Observer { response ->
            Log.d("Code", " ${response.code()}")
            if (response.isSuccessful) {
                Log.d("Body", " ${response.body()}")
                response.body()?.forEach {
                    updatePageDatas(it)
                }
            } else {
                Log.d("Error", " ${response.errorBody()}")
            }
        })

        return binding.root
    }

    fun updatePageDatas(data: ChargeStation){
        binding.bodyTextView7.setText("WebSite: ${data.AddressInfo.RelatedURL}")
        binding.bodyTextView6.setText("E-mail: ${data.AddressInfo.ContactEmail}")
        binding.bodyTextView5.setText("Phone: ${data.AddressInfo.ContactTelephone1}")
        binding.bodyTextView4.setText("Location: ${data.AddressInfo.Latitude}, ${data.AddressInfo.Longitude}")
        binding.bodyTextView3.setText("Address: ${data.AddressInfo.AddressLine1}")
        var connectionsData : String = ""
        data.Connections.forEach {
            connectionsData += "${it.Quantity}x #${it.ID} : ${it.PowerKW}kW \n"
        }
        binding.bodyTextView8.setText(connectionsData)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageView2.setOnClickListener {
            /*
            // Initialize the ML Kit SDK.
            AGConnectInstance.getInstance().init(this);
            // Obtain the TTS service.
            tts = SpeechSynthesizer.getInstance();
            tts.setContext(this);
            tts.setAppId("your app id");
            // Start the TTS session.
            tts.start();
            tts.speak(chargeStations.toString());
             */
            Toast.makeText(context,"Error : implementation 'com.huawei.hms:ml-computer-voice-tts:3.3.0.305'", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}