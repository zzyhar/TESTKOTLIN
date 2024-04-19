package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignInBinding

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Initialize the fragment components
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init(view)
        registerEvents()
    }

    // Initialize the FirebaseAuth instance and NavController
    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    // Register click events for the buttons
    private fun registerEvents() {
        // Navigate to the sign-up fragment when the "Don't have an account?" text is clicked
        binding.authTextView.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        // Sign in the user when the "Next" button is clicked
        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val pass = binding.passEt.text.toString().trim()

            // Check if email and password fields are not empty
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Sign in with email and password
                auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        // Login successful, navigate to the home fragment
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                        navController.navigate(R.id.action_signInFragment_to_homeFragment)
                    } else {
                        // Login failed, display error message
                        Toast.makeText(context, signInTask.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                // Email and password fields are required
                Toast.makeText(context, "Email and Password are required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
