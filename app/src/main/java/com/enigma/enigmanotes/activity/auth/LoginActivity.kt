package com.enigma.enigmanotes.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.enigma.enigmanotes.activity.main.MainActivity
import com.enigma.enigmanotes.databinding.ActivityLoginBinding
import com.enigma.enigmanotes.utils.CommonUtils
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnLogin.setOnClickListener {
                initLogin()
            }
            btnRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
                finish()
            }
        }


    }

    private fun initLogin() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            onFailureLogin("Login Failed, email dan password harus di isi")

//            Validasi password kalau password kurang dari 6
        } else if (password.length < 6) {
            onFailureLogin("Login Failed, harus lebih dari 6")

//            Validasi email kalau format email tidak sesuai
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onFailureLogin("Register Failed, format email tidak sesuai")
            binding.edtEmail.requestFocus()
        } else {
            authLogin(email, password)
        }
    }

    private fun authLogin(email: String, password: String) {
        CommonUtils.loading(binding.loading, true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    val user = auth.currentUser
                    user?.getIdToken(true)
                        ?.addOnCompleteListener { tokenId ->
                            if (tokenId.isSuccessful) {
                                val token = tokenId.result?.token
                                Log.d("Auth Yoki", "Token nya adalah : $token")
                                CommonUtils.loading(binding.loading, false)
                                onSuccessLogin()
                            } else {
                                onFailureLogin("Failed to get ID Token : ${tokenId.exception?.message}")
                            }
                        }
                } else {
                    CommonUtils.loading(binding.loading, false)
                    onFailureLogin("Login Failed : ${it.exception?.message}")
                }
            }
    }

    private fun onFailureLogin(message: String) {
        CommonUtils.alertError(this@LoginActivity, message)
    }

    private fun onSuccessLogin() {
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
}