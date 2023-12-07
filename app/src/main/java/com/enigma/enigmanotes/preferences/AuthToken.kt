package com.enigma.enigmanotes.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "token_preferences")

class AuthToken (private val dataStore: DataStore<Preferences>) {

    // Key untuk menyimpan token dalam Preferences
    private val TOKEN_AUTH_KEY = stringPreferencesKey("token_auth")

    // Metode untuk menyimpan token
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN_AUTH_KEY] = token
        }
    }

    // Metode untuk mendapatkan token
    suspend fun getToken(): String {
        val preferences = dataStore.data.first()
        return preferences[TOKEN_AUTH_KEY] ?: ""
    }

    //    Buat Clear Token (bisa buat logout)
    suspend fun clearToken() {
        dataStore.edit { preferences ->
            preferences.remove(TOKEN_AUTH_KEY)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: AuthToken? = null
        fun getInstance(dataStore: DataStore<Preferences>): AuthToken {
            return INSTANCE ?: synchronized(this) {
                val instance = AuthToken(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

}