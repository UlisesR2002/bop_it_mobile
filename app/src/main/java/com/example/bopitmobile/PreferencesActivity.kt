package com.example.bopitmobile

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.EditTextPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager


class PreferencesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.settings, SettingsFragment())
                .commit()
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey)
            findPreference<Preference>("reset_key")?.setOnPreferenceClickListener {
                resetPreferences()
                true
            }
        }

        private fun resetPreferences() {

            val sharedPreferences = preferenceManager.sharedPreferences
            val editor: SharedPreferences.Editor = sharedPreferences!!.edit()

            editor.putString("Username", "")
            editor.putString("HighScore", "0")
            editor.putString("HighScoreSolitary", "0")
            editor.putString("dificulty", "7000")
            editor.putString("theme", "DarkRed")
            editor.putString("volume", "50")

            editor.apply()

            findPreference<EditTextPreference>("HighScore")?.text = "0"
            findPreference<EditTextPreference>("HighScoreSolitary")?.text = "0"
        }
    }
}