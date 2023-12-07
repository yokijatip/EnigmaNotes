package com.enigma.enigmanotes.activity.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.enigma.enigmanotes.R
import com.enigma.enigmanotes.activity.add.AddActivity
import com.enigma.enigmanotes.adapter.MainAdapter
import com.enigma.enigmanotes.data.model.Notes
import com.enigma.enigmanotes.databinding.ActivityMainBinding
import com.enigma.enigmanotes.preferences.AuthToken
import com.enigma.enigmanotes.preferences.dataStore
import com.enigma.enigmanotes.utils.CommonUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            menu.setOnClickListener {
                showPopupMenu()
            }
            fabAdd.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddActivity::class.java))
                finish()
            }
        }

    }

    @SuppressLint("DiscouragedPrivateApi")
    private fun showPopupMenu() {

        val popupMenu = PopupMenu(this, binding.menu)
        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.about -> {
                    CommonUtils.showToast(this, "About di klik")
                    true
                }

                R.id.logout -> {
                    lifecycleScope.launch {
                        clearToken()
                        finish()
                    }
                    true
                }

                else -> true
            }
        }
        popupMenu.inflate(R.menu.item_menu)

        try {
            val fieldPopUp = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldPopUp.isAccessible = true
            val mPopup = fieldPopUp.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            popupMenu.show()
        }
    }

//    private fun fetchNotes(uid: String?) {
//        if (uid != null) {
//            val userNotesReference = databaseReference.child("users").child(uid).child("notes")
//
//            userNotesReference.addValueEventListener(object : ValueEventListener{
//                @SuppressLint("NotifyDataSetChanged")
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    notesList.clear()
//
//                    if (snapshot.exists()) {
//                        for (noteSnap in snapshot.children) {
//                            val note = noteSnap.getValue(Notes::class.java)
//                            if (note != null) {
//                                notesList.add(note)
//                            }
//                        }
//                        adapter.notifyDataSetChanged()
//                    }
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    CommonUtils.alertError(this@MainActivity, "Error Message: ${error.message}")
//                }
//
//            })
//        }
//    }

    private suspend fun clearToken() {
        val dataStore = AuthToken.getInstance(this.dataStore)
        return dataStore.clearToken()
    }
}