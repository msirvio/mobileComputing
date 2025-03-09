package com.example.composetutorial

import android.content.Context
import android.net.Uri
import org.json.JSONArray

object DataSaving {

    // Saves the shops with SharedPreferences
    fun saveShops(shops: JSONArray, context: Context) {
        val shopString = shops.toString()
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.savedShops),
            Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("shops", shopString)
        editor.apply()
    }

    // Gets the shops with SharedPreferences
    fun getShops(context: Context): JSONArray {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.savedShops),
            Context.MODE_PRIVATE)
        return JSONArray(sharedPref.getString("shops", "[]").toString())
    }

    // Saves the list with SharedPreferences
    fun saveList(list: MutableList<String>, context: Context) {
        val listString = list.joinToString("#")
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.listOfItems),
            Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("list", listString)
        editor.apply()
    }

    // Gets the list with SharedPreferences
    fun getList(context: Context): MutableList<String> {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.listOfItems),
            Context.MODE_PRIVATE)
        return sharedPref.getString("list", "").toString().split("#").toMutableList()
    }

    // Saves the user name with SharedPreferences
    fun saveName(name: String, context: Context) {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.profileName),
            Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("name", name)
        editor.apply()
    }

    // Gets the user name with SharedPreferences
    fun getName(context: Context): String {
        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.profileName),
            Context.MODE_PRIVATE)
        return sharedPref.getString("name", "Miro").toString()
    }

    // Saves the imageUri with SharedPreferences
    fun saveImageUri(imageUri: Uri, context: Context) {

        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.imageUri),
            Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("imageUri", imageUri.toString())
        editor.apply()
    }

    // Gets the imageUri with SharedPreferences - Does not work properly after restarting the app.
    fun getImageUri(context: Context): Uri {

        val sharedPref = context.getSharedPreferences(
            context.getString(R.string.imageUri),
            Context.MODE_PRIVATE)
        val imageUriString = sharedPref.getString("imageUri", Uri.EMPTY.toString())
        return Uri.parse(imageUriString)
    }
}