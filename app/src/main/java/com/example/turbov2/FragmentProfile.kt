package com.example.turbov2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth


class FragmentProfile : Fragment() {

    private lateinit var tvUserNumber: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvUserNumber = view.findViewById(R.id.tv_user_number)
        firebaseAuth = FirebaseAuth.getInstance()

        // Mövcud istifadəçinin nömrəsini al
        val currentUser = firebaseAuth.currentUser
        val phoneNumber = currentUser?.phoneNumber

        // Nömrəni göstər
        if (phoneNumber != null) {
            tvUserNumber.text = "Nömrə: $phoneNumber"
            phoneNumber.replace("+994", "0")

        } else {
            tvUserNumber.text = "Nömrə tapılmadı"
        }

        return view
    }
}
