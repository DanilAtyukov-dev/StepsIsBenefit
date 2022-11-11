package com.danilatyukov.linkedmoney

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.FragmentContainerView
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.danilatyukov.linkedmoney.databinding.ActivityMainBinding
import com.danilatyukov.linkedmoney.ui.InfoDialogFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.my.target.ads.InterstitialAd
import java.util.*


class MainActivity : BaseActivity() {

    private lateinit var amb: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        App.it().mainActivity = this


        amb = ActivityMainBinding.inflate(layoutInflater)
        setContentView(amb.root)

        val navView: BottomNavigationView = amb.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)


        navView.setupWithNavController(navController)
        showToast("${appComponent.appPreferences.userDetails.email}, ")

        if(!appComponent.rememberMePreference.policyIsShown){
            App.it().vibratePhone()
            appComponent.rememberMePreference.policyShown()
            InfoDialogFragment(
                "Уведомление",
                getText(R.string.privacy).toString(),
                R.drawable.policy, policy = true
            ).show(supportFragmentManager, "info")
        } else (
                initAd()
        )
    }
    val tag = "MAIN ACTIVITY"

    private var ad: InterstitialAd? = null

    override fun onResume() {
        super.onResume()

        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    showAdMaybe()
                }
            },
            1000
        )
    }
    fun showAdMaybe(){
        if((1..3).shuffled().last() == 3){
            showAd()
        }
    }

    fun showAd(){
        ad!!.show()
        ad!!.load()
    }

    private fun initAd() {
        // Создаем экземпляр InterstitialAd
        ad = InterstitialAd(1141728, this)
        // Устанавливаем слушатель событий
        ad!!.setListener(object : InterstitialAd.InterstitialAdListener {
            override fun onLoad(ad: InterstitialAd) {
                // Запускаем показ
                // в отдельном Activity
                showAdMaybe()
                App.it().appComponent.appPreferences.changeAdLoaded(true)

            }
            override fun onNoAd(reason: String, ad: InterstitialAd) {
                Log.i(tag, "onNoAd")
            }
            override fun onClick(ad: InterstitialAd) {
                Log.i(tag, "onClick")
            }
            override fun onDisplay(ad: InterstitialAd) {
                Log.i(tag, "onDisplay")
                appComponent.appPreferences.incCurrentAds(1)
                appComponent.statisticInteractor.create(App.it().appComponent.appPreferences.currentSteps)
            }
            override fun onDismiss(ad: InterstitialAd) {
                Log.i(tag, "onDismiss")
            }
            override fun onVideoCompleted(ad: InterstitialAd) {
                Log.i(tag, "onVideoCompleted")
            }
        })

        // Запускаем загрузку данных
        ad!!.load()
    }

    fun lockInterface(){
        val ll = findViewById<LinearLayout>(R.id.pleaseUpdate)
        ll.visibility = View.VISIBLE

        val nav = findViewById<BottomNavigationView>(R.id.nav_view)
        nav.visibility = View.GONE

        val fr = findViewById<FragmentContainerView>(R.id.nav_host_fragment_activity_main)
        fr.visibility = View.GONE

        val updateButton = findViewById<TextView>(R.id.updateTv)
        updateButton.setOnClickListener{
            val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.danilatyukov.linkedmoney"))
            startActivity(i)
        }
    }

}