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
import com.example.todolist.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth
import org.w3c.dom.Text

class SignupFragment : Fragment() {

    // Parameter
    private lateinit var fauth: FirebaseAuth
    private lateinit var navController: NavController //Navigation for different container
    private lateinit var binding: FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        registerEvents()
    }

    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        fauth = FirebaseAuth.getInstance()
    }


    private fun registerEvents() {

        binding.txtAlreadyacc.setOnClickListener {
            navController.navigate(R.id.action_signupFragment_to_signinFragment)
        }

        binding.btnSignup.setOnClickListener {
            val username = binding.edtxtUsername.text.toString().trim()
            val email = binding.edtxtemail.text.toString().trim()
            val password = binding.edtxtPass.text.toString().trim()
            val confPass = binding.edtxtconfpass.text.toString().trim()

            if (TextUtils.isEmpty(username)) {
                binding.edtxtUsername.error = "Username is Required"
            } else if (TextUtils.isEmpty(email)) {
                binding.edtxtemail.error = "Email is Required"
            } else if (TextUtils.isEmpty(password)) {
                binding.edtxtPass.error = "Password is Required"
            } else if (password.length < 6) {
                binding.edtxtPass.error = "Password must be greater than 6 characters"
            } else if (password != confPass) {
                binding.edtxtconfpass.error = "Password is not Matched"
            } else {
                binding.progressBar.visibility = View.VISIBLE
                createUserAccount(email, password)
            }
        }

    }

    private fun createUserAccount(email: String, password: String) {

        fauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "User Registered Successfully", Toast.LENGTH_LONG).show()
//                binding.progressBar.visibility=View.GONE
                navController.navigate(R.id.action_signupFragment_to_signinFragment)
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_LONG).show()
            }
            binding.progressBar.visibility = View.GONE
        }
    }


}