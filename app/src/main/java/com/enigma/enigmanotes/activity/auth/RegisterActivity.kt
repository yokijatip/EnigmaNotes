package com.enigma.enigmanotes.activity.auth

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.appcompat.app.AppCompatActivity
import com.enigma.enigmanotes.databinding.ActivityRegisterBinding
import com.enigma.enigmanotes.utils.CommonUtils
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.apply {
            btnLogin.setOnClickListener {
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                finish()
            }
            btnRegister.setOnClickListener {
                initRegister()
            }
        }
    }

    private fun initRegister() {
        val email = binding.edtEmail.text.toString().trim()
        val password = binding.edtPassword.text.toString().trim()

//        Validasi Username, email dan password jika kosong akan error dan menampilkan Alert
        if (email.isEmpty() || password.isEmpty()) {
            onFailureRegister("Username, email dan password harus semuanya di isi")
            binding.apply {
                edtEmail.requestFocus()
                edtPassword.requestFocus()
            }

//            Validasi untuk password kalau kurang dari 6 maka akan error
        } else if (password.length < 6) {
            onFailureRegister("Password harus lebih dari 6")
            binding.edtPassword.requestFocus()

//            Validasi untuk email kalau email format nya tidak sesuai format email maka aakan error
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            onFailureRegister("Register Failed, format email tidak sesuai")
            binding.edtEmail.requestFocus()

//            Melaksanakan tugas authentication dari firebase untuk proses register
        } else {
            authRegister(email, password)
        }
    }

    //    Fungsi authentication untuk register
    private fun authRegister(email: String, password: String) {
        CommonUtils.loading(binding.loading, true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    CommonUtils.loading(binding.loading, false)
                    onSuccessRegister("Terima Kasih sudah membuat akun, sekarang silahkan login")
                } else {
                    CommonUtils.loading(binding.loading, false)
                    onFailureRegister("Error : ${it.exception?.message}")
                }
            }
    }

    private fun onFailureRegister(message: String) {
        CommonUtils.alertError(this@RegisterActivity, message)
    }

    private fun onSuccessRegister(message: String) {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        CommonUtils.alertSuccess(this@RegisterActivity, message, this, intent)
    }
}