package com.example.composetutorial

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

object DataSaving {

    @Composable
    fun saveName(name: String) {
        val sharedPref = LocalContext.current.getSharedPreferences(
            LocalContext.current.getString(R.string.profileName),
            Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("name", name)
        editor.apply()
    }

    @Composable
    fun getName(): String {
        val sharedPref = LocalContext.current.getSharedPreferences(
            LocalContext.current.getString(R.string.profileName),
            Context.MODE_PRIVATE)
        return sharedPref.getString("name", "Miro").toString()
    }

    @Composable
    fun saveImageUri(imageUri: Uri) {
        val sharedPref = LocalContext.current.getSharedPreferences(
            LocalContext.current.getString(R.string.imageUri),
            Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("imageUri", imageUri.toString())
        editor.apply()
    }

    @Composable
    fun getImageUri(): Uri {
        val sharedPref = LocalContext.current.getSharedPreferences(
            LocalContext.current.getString(R.string.imageUri),
            Context.MODE_PRIVATE)
        val imageUriString = sharedPref.getString("imageUri", Uri.EMPTY.toString())
        return Uri.parse(imageUriString)
    }
}