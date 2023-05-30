package com.example.skripsol.navbar

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.skripsol.FunctionHelper.Get
import com.example.skripsol.R
import com.example.skripsol.state.MyState
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textview.MaterialTextView

class Profile : Fragment() {
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_screen, container, false)
        val user = MyState.getDataUser()

        val profile_user_name: MaterialTextView = view.findViewById(R.id.profile_user_name)
        val profile_user_email: MaterialTextView = view.findViewById(R.id.profile_user_email)
        val profile_user_status: MaterialTextView = view.findViewById(R.id.profile_user_status)
        val EditText_profile_nama_lengkap: TextInputEditText =
            view.findViewById(R.id.EditText_profile_nama_lengkap)
        val EditText_profile_nim: TextInputEditText = view.findViewById(R.id.EditText_profile_nim)
        val EditText_profile_no_hp: TextInputEditText =
            view.findViewById(R.id.EditText_profile_no_hp)
        val EditText_profile_status_skripsi: TextInputEditText =
            view.findViewById(R.id.EditText_profile_status_skripsi)

        val EditText_profile_dosen_pembimbing: TextInputEditText =
            view.findViewById(R.id.EditText_profile_dosen_pembimbing)
        val EditText_profile_dosen_penguji: TextInputEditText =
            view.findViewById(R.id.EditText_profile_dosen_penguji)

        val btnLogout: MaterialButton = view.findViewById(R.id.btn_logout_profile)

        profile_user_name.text = user?.get("name").toString()
        profile_user_email.text = user?.get("email").toString()
        EditText_profile_nama_lengkap.setText(user?.get("name").toString())
        EditText_profile_nim.setText(user?.get("nim").toString())



        btnLogout.setOnClickListener {
            Get.dialogWithImage(
                context,
                "",
                "Apakah anda yakin akan keluar aplikasi?",
                context?.let { ContextCompat.getDrawable(it, R.drawable.img_alert_logout) },
                "Ya Keluar",
                "Batal",
                onClickPositive = {

                },
                onCLickNegative = {

                },
            )
        }


        return view
    }

}