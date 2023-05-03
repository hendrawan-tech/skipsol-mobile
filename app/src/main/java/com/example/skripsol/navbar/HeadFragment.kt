package com.example.skripsol.navbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.skripsol.R
import com.example.skripsol.databinding.HeadFragmentBinding


class HeadFragment : AppCompatActivity()  {
    private lateinit var binding: HeadFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.head_fragment)
        binding = HeadFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(Home())
        binding.navigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_home -> replaceFragment(Home())
                R.id.botom_chat -> replaceFragment(Chat())
                R.id.bottom_riwayat_judul -> replaceFragment(RiwayatJudul())
                R.id.bottom_profile -> replaceFragment(Profile())
                else -> {
                }

            }
            true

        }


    }

    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }


}