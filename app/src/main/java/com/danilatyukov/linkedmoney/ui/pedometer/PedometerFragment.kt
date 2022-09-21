package com.danilatyukov.linkedmoney.ui.pedometer


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.danilatyukov.linkedmoney.databinding.FragmentPedometerBinding

class PedometerFragment : Fragment() {

   /* companion object {
        fun newInstance() = PedometerFragment()
    }*/

    private lateinit var _binding: FragmentPedometerBinding
    private val binding get() = _binding

    private lateinit var viewModel: PedometerViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPedometerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val pedometerViewModel = ViewModelProvider(this)[PedometerViewModel::class.java]

        val textView: TextView = binding.tvPedometer
        pedometerViewModel.counter.observe(viewLifecycleOwner) {
            textView.text = it
        }

        _binding.playImgBtn.setOnClickListener { viewModel.play() }

        _binding.stopImgBtn.setOnClickListener { viewModel.stop() }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this)[PedometerViewModel::class.java]
    }
}