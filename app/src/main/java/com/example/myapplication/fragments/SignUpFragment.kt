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
import com.example.myapplication.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpFragment : Fragment() {

    private lateinit var auth: FirebaseAuth
    private lateinit var navControl: NavController
    private lateinit var binding: FragmentSignUpBinding

    // Inflate the layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
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
        navControl = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
    }

    // Register click events for the buttons
    private fun registerEvents() {
        // Navigate to the sign-in fragment when the "Already have an account?" text is clicked
        binding.authTextView.setOnClickListener {
            navControl.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        // Register the user when the "Next" button is clicked
        binding.nextBtn.setOnClickListener {
            val email = binding.emailEt.text.toString().trim()
            val pass = binding.passEt.text.toString().trim()
            val verifyPass = binding.rePassEt.text.toString().trim()

            // Check if email, password, and verify password fields are not empty
            if (email.isNotEmpty() && pass.isNotEmpty() && verifyPass.isNotEmpty()) {
                // Check if the password matches the verify password
                if (pass == verifyPass) {
                    // Create a new user with email and password
                    auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // User registration successful
                            Toast.makeText(
                                context,
                                "Registered Successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                            // Navigate to the home fragment
                            navControl.navigate(R.id.action_signUpFragment_to_homeFragment)
                        } else {
                            // User registration failed
                            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            } else {
                // Email and password fields are required
                Toast.makeText(context, "Email and Password are required", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
