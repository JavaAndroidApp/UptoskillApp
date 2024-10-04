package com.example.uptoskills

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.uptoskills.R.layout.activity_main

class FunApp : AppCompatActivity() {
    private var navController: NavController? = null
    var context: Context? = null
    private var googleSignInViewModel: GoogleSignInViewModel<*>? = null
        get() {
            val signInViewModel = field
            return signInViewModel
        }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(/* layoutResID = */ activity_main)

        navController = findNavController(this, R.id.nav_host_fragment)
        context = this
        googleSignInViewModel = GoogleSignInViewModel<Any?>()

        findNavController(this, R.id.nav_host_fragment).navigate<Any>(R.id.action_home)
    }


    fun HomeScreen(view: View?) {
        googleSignInViewModel?.handleGoogleSignIn(context, navController)
    }

    fun ProfileScreen(view: View?) {
        googleSignInViewModel.ProfileScreen(googleSignInViewModel)
    }
}

private fun <T> NavController.navigate(actionHome: Any) {
    TODO("Not yet implemented")
}



