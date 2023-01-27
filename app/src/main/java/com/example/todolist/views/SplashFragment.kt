package com.example.todolist.views

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.todolist.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashFragment : Fragment() {

    private lateinit var auth           : FirebaseAuth
    private lateinit var navController  : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        navController = Navigation.findNavController(view)

        CoroutineScope(Dispatchers.Main).launch {
            delay(2000L)

            if (auth.currentUser != null){
                navController.navigate(R.id.action_splashFragment_to_homeFragment)
            }
            else{
                navController.navigate(R.id.action_splashFragment_to_signinFragment)

            }

        }

//        Handler(Looper.myLooper()!!).postDelayed(Runnable {
//          //Checking User is Login..
//            if (auth.currentUser != null){
//                navController.navigate(R.id.action_splashFragment_to_homeFragment)
//            }
//            else{
//                navController.navigate(R.id.action_splashFragment_to_signinFragment)
//
//            }
//        }, 2000)
    }


}