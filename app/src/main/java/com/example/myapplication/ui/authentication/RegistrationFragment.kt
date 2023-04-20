package com.example.myapplication.ui.authentication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import com.example.myapplication.databinding.FragmentRegistrationBinding
import com.google.firebase.auth.FirebaseAuth


class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).supportActionBar!!.hide()
        // Inflate the layout for this fragment
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val loginBinding: TextView = binding.alreadyHaveAccount
        val username = binding.inputUsername.text.toString()
        val email = binding.inputEmail.text.toString()
        val password = binding.inputPassword.text.toString()
        val confirmedPassword = binding.inputConformPassword.text.toString()

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && confirmedPassword.isNotEmpty()){
                if (password == confirmedPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().navigate(R.id.action_navigation_registration_to_navigation_login2)
                        } else {
                            Toast.makeText(requireContext(), "sign up failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "pass word not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "please complete the blanks", Toast.LENGTH_SHORT).show()
            }
        }



        loginBinding.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_registration_to_navigation_login2)
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val loginBinding: TextView = binding.alreadyHaveAccount

        firebaseAuth = FirebaseAuth.getInstance()

        binding.btnRegister.setOnClickListener {

            val username = binding.inputUsername.text.toString()
            val email = binding.inputEmail.text.toString()
            val password = binding.inputPassword.text.toString()
            val confirmedPassword = binding.inputConformPassword.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty() && username.isNotEmpty() && confirmedPassword.isNotEmpty()){
                if (password == confirmedPassword){
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            findNavController().navigate(R.id.action_navigation_registration_to_navigation_login2)
                            Toast.makeText(requireContext(), "sign up good", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(requireContext(), "sign up failed: ${it.exception?.message}", Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    Toast.makeText(requireContext(), "pass word not match", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(requireContext(), "please complete the blanks", Toast.LENGTH_SHORT).show()
            }
        }



        loginBinding.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_registration_to_navigation_login2)
        }
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}