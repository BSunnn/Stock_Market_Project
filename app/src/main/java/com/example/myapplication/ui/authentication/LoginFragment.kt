package com.example.myapplication.ui.authentication

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.myapplication.MainActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import kotlin.math.log

class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var _binding: FragmentLoginBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as MainActivity).supportActionBar!!.hide()

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val loginBinding: TextView = binding.textViewSignUp
        val button: Button = binding.btnlogin

        loginBinding.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_navigation_registration)
        }

        button.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_login_to_navigation_home)
        }
        return binding.root
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}