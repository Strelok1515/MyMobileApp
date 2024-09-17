package com.example.mymobileapp.ui

import com.example.mymobileapp.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import com.example.mymobileapp.LoginRequest
import com.example.mymobileapp.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginFragment : Fragment() {

    private lateinit var userName: EditText
    private lateinit var passWord: EditText
    private lateinit var buttonLogin: Button
    private lateinit var error: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.login_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userName = view.findViewById(R.id.usernameEditText)
        passWord = view.findViewById(R.id.passwordEditText)
        buttonLogin = view.findViewById(R.id.button1)
        error = view.findViewById(R.id.Error)

        buttonLogin.setOnClickListener {
            val username = userName.text.toString()
            val password = passWord.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                performLogin(username, password)
            } else {
                error.text = "Please fill in all fields"
                error.visibility = TextView.VISIBLE
            }
        }
    }

    private fun performLogin(username: String, password: String) {
        if (username == "Aleksandar" && password == "s8090291") {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://vu-nit3213-api.onrender.com/") // Base URL of your API
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val authService = retrofit.create(AuthService::class.java)
            val loginRequest = LoginRequest(username, password)

            authService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val keypass = response.body()?.keypass
                        Log.d("LoginFragment", "Keypass: $keypass")

                        keypass?.let {
                            // Navigate to DashboardFragment and pass the keypass
                            val action = LoginFragmentDirections.actionLoginFragmentToDashboardFragment(it)
                            findNavController().navigate(action)
                        } ?: run {
                            Log.e("LoginFragment", "Keypass is null")
                            error.text = "Error retrieving authentication details"
                            error.visibility = TextView.VISIBLE
                        }
                    } else {
                        Log.e("LoginFragment", "Login failed with status code: ${response.code()}")
                        error.text = "Invalid credentials, please try again"
                        error.visibility = TextView.VISIBLE
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.e("LoginFragment", "Login failed with error: ${t.message}", t)
                    error.text = "Error: ${t.message}"
                    error.visibility = TextView.VISIBLE
                }
            })
        } else {
            Log.e("LoginFragment", "Invalid credentials entered")
            error.text = "Invalid credentials, please try again"
            error.visibility = TextView.VISIBLE
        }
    }
}

private fun Any.actionLoginFragmentToDashboardFragment(it: String): NavDirections {

    return TODO("Provide the return value")
}
