@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composetutorial

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.composetutorial.ui.theme.ComposeTutorialTheme
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import org.json.JSONArray
import java.net.URL
import java.util.concurrent.Executors

class InfoScreen {

    private lateinit var apiDataJSONArray: JSONArray
    private var city = ""
    private var timeStamp = ""

    @Composable
    fun Info(navigateToConversation: () -> Unit) {

        var time by remember { mutableStateOf(timeStamp) }

        val context = LocalContext.current
        val components = Components()

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
                    } else {
                        Log.d("Location", "Location null")
                    }
                }
        } else {
            Log.d("Location", "No permission")
        }

        if (city.equals("")) {
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

                if (apiDataJSONArray.toString().equals("[]")) {
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
                Row {
                    Spacer(modifier = Modifier.width(16.dp))

                    Text(
                        modifier = Modifier.padding(innerPadding),
                        text = "Latest Slippery Warning:")
                }
                Row {
                    Spacer(modifier = Modifier.width(16.dp))

                    if (time.equals("")) {
                        Log.d("APIStuff", "No timestamp")
                    } else {
                        components.SlipperyWarning(SlipWarning(city, timeStamp))
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewInfo() {
        ComposeTutorialTheme {
            Info(navigateToConversation = {})
        }
    }
}