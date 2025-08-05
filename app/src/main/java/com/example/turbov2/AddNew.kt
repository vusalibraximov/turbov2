package com.example.turbov2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.turbov2.data.CarAd
import com.example.turbov2.databinding.FragmentAddNewBinding
import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore


class NewAdFragment : Fragment() {

    private var _binding: FragmentAddNewBinding? = null
    private val binding get() = _binding!!

    private val modelList = listOf("BMW", "Mercedes", "Toyota", "Hyundai", "LADA","Chevrolet","BYD","Ford","Kia")
    private val yearList = (1990..2025).map { it.toString() }.reversed()
    private val gearboxList = listOf("Avtomat", "Robot", "Mexanika", "Variator","Reduktor")
    private val fuelList = listOf("Benzin", "Dizel", "Elektrik", "Hibrid", "LPG","Plug-in Hybrid","Dizel-Hibrid")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddNewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.spinnerModel.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, modelList).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerYear.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, yearList).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerGearbox.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, gearboxList).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        binding.spinnerFuel.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, fuelList).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        binding.btnSubmit.setOnClickListener {
            val selectedModel = binding.spinnerModel.selectedItem.toString()
            val selectedYear = binding.spinnerYear.selectedItem.toString()
            val selectedGearbox = binding.spinnerGearbox.selectedItem.toString()
            val selectedFuel = binding.spinnerFuel.selectedItem.toString()
            val description = binding.editTextDescription.text.toString()

            if (description.isBlank()) {
                binding.editTextDescription.error = "Zəhmət olmasa, əlavə məlumat yazın"
                return@setOnClickListener
            }

            val newAd = CarAd(
                model = selectedModel,
                year = selectedYear,
                gearbox = selectedGearbox,
                fuel = selectedFuel,
                description = description
            )

            val database = FirebaseFirestore.getInstance()
            val adsRef = database.collection("cars").add(CarAd())
                .addOnSuccessListener {
                    Toast.makeText(requireContext(), "Elan uğurla əlavə olundu", Toast.LENGTH_SHORT).show()
                    clearForm()
                }
                .addOnFailureListener {
                    Toast.makeText(requireContext(), "Xəta baş verdi: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun clearForm() {
        binding.spinnerModel.setSelection(0)
        binding.spinnerYear.setSelection(0)
        binding.spinnerGearbox.setSelection(0)
        binding.spinnerFuel.setSelection(0)
        binding.editTextDescription.text?.clear()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


