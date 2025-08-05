package com.example.turbov2.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.turbov2.databinding.FragmentLoginBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // OTP callback-ları
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: com.google.firebase.auth.PhoneAuthCredential) {
                // Kod avtomatik gəlib təsdiqlənsə
                Toast.makeText(requireContext(), "Kod avtomatik təsdiqləndi", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(requireContext(), "Xəta: ${e.message}", Toast.LENGTH_LONG).show()
            }

            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                // OTP uğurla göndərildi – OtpFragment-ə yönləndir
                val action = LoginFragmentDirections.actionLoginFragmentToOtpFragment(verificationId)
                findNavController().navigate(action)
            }
        }

        binding.sendCodeButton.setOnClickListener {
            val phone = binding.phoneEditText.text.toString().trim()

            if (phone.isEmpty() || !phone.startsWith("+994") || phone.length < 13) {
                Toast.makeText(requireContext(), "Zəhmət olmasa düzgün nömrə daxil edin", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.loading.visibility = View.VISIBLE

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build()

            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
