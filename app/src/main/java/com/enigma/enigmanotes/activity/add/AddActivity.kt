package com.enigma.enigmanotes.activity.add

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.enigma.enigmanotes.activity.main.MainActivity
import com.enigma.enigmanotes.data.model.Notes
import com.enigma.enigmanotes.databinding.ActivityAddBinding
import com.enigma.enigmanotes.utils.CommonUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        val uid = auth.currentUser?.uid
        databaseReference = FirebaseDatabase.getInstance()
            .getReference("Notes") // Ganti "Notes" sesuai dengan keinginan Anda

        binding.apply {
            btnBack.setOnClickListener {
                startActivity(Intent(this@AddActivity, MainActivity::class.java))
                finish()
            }
        }

        binding.fabSave.setOnClickListener {
            CommonUtils.showToast(this@AddActivity, "Click")

            val title = binding.edtContentTitle.text.toString()
            val description =
                binding.edtContentDescription.text.toString() // Ganti dengan edtContentDescription yang sesuai dengan layout Anda

            val notes = Notes(title, description)

            if (uid != null) {
                val userNotesReference = databaseReference.child("users").child(uid).child("notes")
                val newNoteReference = userNotesReference.push()
                newNoteReference.setValue(notes).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val intent = Intent(this@AddActivity, MainActivity::class.java)
                        CommonUtils.alertSuccess(
                            this@AddActivity,
                            "Upload Notes is Success",
                            this@AddActivity,
                            intent
                        )

                    } else {
                        CommonUtils.alertError(
                            this@AddActivity,
                            "Failed upload Notes, Error : ${it.exception?.message}"
                        )
                    }
                }
            }
        }
    }
}