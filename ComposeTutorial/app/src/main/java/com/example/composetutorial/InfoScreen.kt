@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composetutorial

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import org.json.JSONArray
import java.net.URL
import java.util.concurrent.Executors
import org.json.JSONObject
import java.io.FileNotFoundException

class InfoScreen {

    private lateinit var apiDataJSONArray: JSONArray
    private var city = ""
    private var timeStamp = ""
    private var latitude = 0.0
    private var longitude = 0.0
    private var shops = JSONArray()

    private fun getClosestShops(latitude: Double, longitude: Double, context: Context) : JSONArray {

        Log.d("APITest", "$latitude, $longitude")
        Executors.newSingleThreadExecutor().execute({

            val query = """
            [out:json];
            (
            node["shop"="alcohol"](around:500, $latitude, $longitude);
            node["shop"="supermarket"](around:500, $latitude, $longitude);
            node["shop"="convenience"](around:500, $latitude, $longitude);
            );
            out body;
            """

            val url =
                "https://overpass-api.de/api/interpreter?data=${query.replace("\n", "").trim()}"

            try {
                val response = URL(url).readText()
                Log.d("APITest", response)

                val jsonResponse = JSONObject(response)
                Log.d("APITest", jsonResponse.toString())

                val newShops = jsonResponse.getJSONArray("elements")
                if (newShops.toString() != "[]") {
                    DataSaving.saveShops(newShops, context)
                    shops = newShops
                    Log.d("APITest", shops.toString())
                } else {
                    shops = DataSaving.getShops(context)
                    Log.d("APITest", "Empty array!")
                }

                Log.d("APITest", shops.toString())
            } catch (e: FileNotFoundException) {
                Log.d("APITest", "Exception caught!")
            }
        })
        return shops
    }

    @Composable
    fun ShopCards(storeUpdate: JSONArray) {
        val components = Components()

        for (i in 0 until storeUpdate.length()) {
            //Only first five
            if (i >= 5) {
                break
            }

            val shop = storeUpdate.getJSONObject(i)
            val shopName = shop.getJSONObject("tags").optString("name", "N/A")
            val branch = shop.getJSONObject("tags").optString("branch", "")
            val openingHours = shop.getJSONObject("tags").optString("opening_hours","N/A")
            val street = shop.getJSONObject("tags").optString("addr:street","N/A")
            val number = shop.getJSONObject("tags").optString("addr:housenumber","N/A")
            val postCode = shop.getJSONObject("tags").optString("addr:postcode","N/A")

            components.ShopCard(
                name = shopName,
                branch = branch,
                time = openingHours,
                street = street,
                number = number,
                postCode = postCode)

            Log.d("APITest", "Shop Name: $shopName, OH: $openingHours")
            Log.d("APITest", "$street $number, $postCode")
        }
    }

    @Composable
    fun Info(navigateToConversation: () -> Unit,
             recomposer: () -> Unit) {

        val context = LocalContext.current
        val components = Components()

        var time by remember { mutableStateOf(timeStamp) }

        shops = DataSaving.getShops(context)
        var storeUpdate by remember { mutableStateOf(shops) }

        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, CancellationTokenSource().token)
                .addOnSuccessListener { location : Location? ->
                    if (location != null) {
                        val geocoder = Geocoder(context)
                        val address = geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        if (address != null) {
                            Log.d("Location", "City: " + address[0].subAdminArea)
                            city = address[0].subAdminArea.toString()

                        } else {
                            Log.d("Location", "Address null")
                        }

                        latitude = location.latitude
                        longitude = location.longitude

                    } else {
                        Log.d("Location", "Location null")
                    }
                }
        } else {
            Log.d("Location", "No permission")
        }

        if (city == "") {
            Log.d("APIStuff", "City name empty")
        } else {
            Executors.newSingleThreadExecutor().execute({
                Log.d("APIStuff", "test")
                val url = StringBuilder(
                    "https://liukastumisvaroitus-api.beze.io/api/v1/warnings?filter=city:"
                            + city
                            + "&order=created_at:desc").toString()
                Log.d("APIStuff", url)

                apiDataJSONArray = JSONArray(
                    URL(url).readText()
                )

                if (apiDataJSONArray.toString() == "[]") {
                    Log.d("APIStuff", "Empty JSONArray")
                } else {
                    Log.d("APIStuff", apiDataJSONArray.toString())
                    val jsonObj = apiDataJSONArray.getJSONObject(0)
                    Log.d("APIStuff", jsonObj.toString())
                    city = jsonObj.get("city").toString()
                    timeStamp = jsonObj.get("created_at").toString()
                    time = timeStamp
                }
            })
        }
        //if (location.latitude != 0.0 && location.longitude != 0.0)
        storeUpdate = getClosestShops(latitude, longitude, context)

        Scaffold (
            topBar = {
                // The top bar
                TopAppBar(
                    // Top bar name and info icon
                    title = {
                        Row {
                            //Name of page
                            Text(
                                text = "Info"
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            //Info icon
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info page",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    actions = {
                        //Info button
                        IconButton(onClick =
                        {
                            storeUpdate = getClosestShops(latitude, longitude, context)

                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_refresh_24),
                                contentDescription = "Refresh"
                            )
                        }
                    },
                    // Back arrow
                    navigationIcon = {
                        IconButton(onClick = navigateToConversation) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Return"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            Column {
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                        .fillMaxWidth()
                ) {
                    item {
                        Row {
                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                modifier = Modifier.padding(innerPadding),
                                text = "Stores nearby:"
                            )
                        }
                        //getClosestShops(latitude, longitude)
                        //Spacer(modifier = Modifier.width(64.dp))
                        Log.d("APITest", "STORES: " + storeUpdate.toString())
                        ShopCards(storeUpdate)

                        Row {
                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                modifier = Modifier.padding(innerPadding),
                                text = "Latest Slippery Warning:")
                        }
                        Row {
                            Spacer(modifier = Modifier.width(16.dp))

                            if (time == "") {
                                Log.d("APIStuff", "No timestamp")
                            } else {
                                components.SlipperyWarning(SlipWarning(city, timeStamp))
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewInfo() {
        ComposeTutorialTheme {
            Info(navigateToConversation = {}, recomposer = {})
        }
    }
}