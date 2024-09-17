package com.example.mymobileapp


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mymobileapp.ui.AuthService
import com.example.mymobileapp.ui.DashboardActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {

    private lateinit var userName: EditText
    private lateinit var passWord: EditText
    private lateinit var buttonLogin: Button
    private lateinit var error: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        userName = findViewById(R.id.usernameEditText)
        passWord = findViewById(R.id.passwordEditText)
        buttonLogin = findViewById(R.id.button1)
        error = findViewById(R.id.Error)

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

            //Use the actual values of username and password
            val loginRequest = LoginRequest(username, password)


            authService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(
                    call: Call<LoginResponse>,
                    response: Response<LoginResponse>
                ) {
                    if (response.isSuccessful) {
                        val keypass = response.body()?.keypass
                        Log.d("LoginActivity", "Keypass: $keypass")

                        // Navigate to DashboardActivity and pass the keypass
                        val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                        intent.putExtra("keypass", keypass)
                        startActivity(intent)
                        finish()
                    } else {
                        Log.d("LoginActivity", "Login failed with status code: ${response.code()}")
                        error.text = "Invalid credentials, please try again"
                        error.visibility = TextView.VISIBLE
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("LoginActivity", "Login failed with error: ${t.message}")
                    error.text = "Error: ${t.message}"
                    error.visibility = TextView.VISIBLE
                }
            })

        }else {
            Log.d("LoginActivity", "Invalid credentials entered")
            error.text = "Invalid credentials, please try again"
            error.visibility = TextView.VISIBLE
    }
}
    }
