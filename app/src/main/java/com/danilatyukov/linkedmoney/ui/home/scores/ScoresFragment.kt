package com.danilatyukov.linkedmoney.ui.home.scores

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.danilatyukov.linkedmoney.App
import com.danilatyukov.linkedmoney.R
import com.danilatyukov.linkedmoney.appComponent
import com.danilatyukov.linkedmoney.databinding.FragmentPedometerBinding
import com.danilatyukov.linkedmoney.databinding.FragmentScoresBinding
import com.danilatyukov.linkedmoney.ui.InfoDialogFragment
import kotlinx.android.synthetic.main.fragment_scores.*
import kotlinx.android.synthetic.main.fragment_scores.view.*

class ScoresFragment : Fragment() {


    private lateinit var _binding: FragmentScoresBinding
    private val binding get() = _binding

    companion object {
        fun newInstance() = ScoresFragment()
    }

    private lateinit var viewModel: ScoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentScoresBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ScoresViewModel::class.java)
        // TODO: Use the ViewModel




        viewModel.stepPrice.observe(viewLifecycleOwner) {
            _binding.stepPriceTv.text = App.roundFloat(it.toFloat(), "#.#####").plus("р")
        }
        viewModel.confirmSteps.observe(viewLifecycleOwner) {
            _binding.confirmStepsTv.text = it
        }

        viewModel.allMoney.observe(viewLifecycleOwner) {
            _binding.balanceTv.text = App.roundFloat(it.toFloat()/*+RetrievedPreference.getRefBonus() TODO */, "#.#####").plus("р")
        }

        viewModel.allGpsDistance.observe(viewLifecycleOwner) {
            _binding.allGpsDistanceTv.text = it.plus(" км")
        }
        viewModel.adLoaded.observe(viewLifecycleOwner){
            _binding.showAdbtn.visibility = View.VISIBLE
        }

        showAdbtn.setOnClickListener{
            App.it().mainActivity!!.showAd()
            App.it().appComponent.appPreferences.changeAdLoaded(false)
            /*App.it().appComponent.appPreferences.incCurrentAds(1)
            App.it().appComponent.statisticInteractor.create(App.it().appComponent.appPreferences.currentSteps)*/
        }

        _binding.priceMyStepImg.setOnClickListener {
            App.it().vibratePhone()
            InfoDialogFragment(
                "Стоимость шага",
                "Этот параметр отражает текущий уникальный курс шага, который меняется в зависимости от активности пользователя в системе.",
                R.drawable.ic_steps_green_two
            ).show(parentFragmentManager.beginTransaction(), "info")
        }

        _binding.barterInfoImg.setOnClickListener {
            App.it().vibratePhone()

            InfoDialogFragment(
                "Стоимость всех шагов",
                "Сумма, накопленная с предыдущего вывода.",
                R.drawable.bartericon
            ).show(parentFragmentManager.beginTransaction(), "info")
        }

        _binding.cashOut.setOnClickListener {

            App.it().vibratePhone()

            val krv = 10 /*RetrievedPreference.getKrv()*/ //TODO если всё-таки меняем параметр numberDefaultVip
            InfoDialogFragment(
                "Информация",
                "Вывод средств производится в соотношении $krv к 1, где ${krv}р - это Ваши средства, а 1р - это накопленный Реферальный бонус. Минимальная сумма вывода - 50р.",
                R.drawable.bartericon
            ).show(parentFragmentManager.beginTransaction(), "info")
        }
    }
}