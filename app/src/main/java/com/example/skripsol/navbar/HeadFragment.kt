package com.example.skripsol.navbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.fragment.app.Fragment
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.databinding.HeadFragmentBinding
import nl.joery.animatedbottombar.AnimatedBottomBar


class HeadFragment : AppCompatActivity() {
    private lateinit var binding: HeadFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_judul_ta_screen)
        binding = HeadFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        binding.navigation.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newTab.id) {
                    R.id.bottom_home -> replaceFragment(Home())
                    R.id.botom_chat -> replaceFragment(Chat())
                    R.id.bottom_riwayat_judul -> replaceFragment(RiwayatJudul())
                    R.id.bottom_profile -> replaceFragment(Profile())
                }
            }
        })

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                onWillPopScope()
            }
            
        }
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }

    private fun onWillPopScope() {
        Get.dialog(
            activity = this,
            Title = "",
            Subtitle = "Apakah anda yakin akan keluar aplikasi ?",
            onClickPositive = {
                              finish();
            },

            )
    }


}