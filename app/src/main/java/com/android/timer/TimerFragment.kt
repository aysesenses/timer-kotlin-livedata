package com.android.timer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.android.timer.databinding.FragmentTimerBinding

class TimerFragment : Fragment() {

    private var binding: FragmentTimerBinding? = null
    private var viewModel: TimerViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.i("oncreate", "configurasyon değişikliği oldu")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_timer,
            container,
            false
        )

        Log.i("TimerFragment", "Called ViewModelProvider.get")
        viewModel = ViewModelProvider(this).get(TimerViewModel::class.java)

        viewModel?.currentTime?.observe(viewLifecycleOwner, Observer { newCurrentTime ->
            binding?.timerText?.text = newCurrentTime.toString()
        })

        viewModel?.buttonVisible?.observe(viewLifecycleOwner, Observer { isVisible ->
            binding?.apply {
                when {
                    isVisible -> {
                        startButton.visibility = View.VISIBLE
                        pauseButton.visibility = View.GONE
                    }
                    else -> {
                        pauseButton.visibility = View.VISIBLE
                        startButton.visibility = View.GONE
                    }
                }
            }
        })

        binding?.startButton?.setOnClickListener { viewModel?.startTimer() }
        binding?.pauseButton?.setOnClickListener { viewModel?.stopTimer() }
        return binding?.root
    }
}