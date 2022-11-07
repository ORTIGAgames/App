package com.example.elcochedelhormiguero.DataStore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class Store(private val context: Context) {

    companion object{
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore("hyperlinks")
        val LINK_KEY = stringPreferencesKey("hiperlink_store")
    }


    val getLink: Flow<String?> = context.dataStore.data
        .map{ preferences ->
            preferences[LINK_KEY] ?: ""
        }

    suspend fun saveLink(name: String){
        context.dataStore.edit{ preferences ->
            preferences[LINK_KEY] = name
        }
    }
}