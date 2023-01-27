package com.example.todolist.views

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todolist.R
import com.example.todolist.databinding.FragmentSigninBinding
import com.example.todolist.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth


class SigninFragment : Fragment() {

    // Parameter
    private lateinit var fauth          : FirebaseAuth
    private lateinit var navController  : NavController //Navigation for different container
    private lateinit var binding        : FragmentSigninBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentSigninBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        loginUser()
    }

    private fun init(view: View) {

        navController = Navigation.findNavController(view)
        fauth = FirebaseAuth.getInstance()
    }


    // function for Sign-In User
    private fun loginUser() {

        binding.edtxtsignup.setOnClickListener {
            navController.navigate(R.id.action_signinFragment_to_signupFragment)
        }
        binding.btnLogin.setOnClickListener {
            val email = binding.edtxtemail.text.toString().trim()
            val pass = binding.edtxtPass.text.toString().trim()
            if (TextUtils.isEmpty(email)) {
                binding.edtxtemail.error = "Email must not be empty"
            } else if (TextUtils.isEmpty(pass)) {
                binding.edtxtPass.error = "Password must not be empty"
            } else if (email.isNotEmpty() && pass.isNotEmpty()) {
                binding.progressBar.visibility = View.VISIBLE
                fauth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Login Successfully", Toast.LENGTH_LONG).show()
                        navController.navigate(R.id.action_signinFragment_to_homeFragment)
                    } else {
                        Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
                    }
                    binding.progressBar.visibility = View.GONE
                }
            }
        }
    }

}