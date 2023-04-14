package com.geniouscraft.topappandroid.ui.screens.main

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore


const val sharedName = "topApps"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = sharedName)

val countryCodeKey = stringPreferencesKey("COUNTRY_CODE_KEY")
