package com.example.turbov2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.turbov2.databinding.FragmentLikedBinding

class LikedFragment : Fragment() {

    private var _binding: FragmentLikedBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLikedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Burada başlıq və ya bəyənilənlərin siyahısı göstərilə bilər

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}