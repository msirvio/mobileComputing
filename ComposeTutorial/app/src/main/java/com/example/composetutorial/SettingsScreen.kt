@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composetutorial

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class SettingsScreen {
    @Composable
    fun Settings(
        navigateToConversation: () -> Unit) {

        val context = LocalContext.current
        // Get updated data
        val updatedName = DataSaving.getName(context)
        val updatedImageUri = DataSaving.getImageUri(context)

        // Remember variables
        var imageUri by remember { mutableStateOf(updatedImageUri) }
        var text by remember { mutableStateOf(updatedName) }

        //Save the updated data
        DataSaving.saveName(text, context)
        DataSaving.saveImageUri(imageUri, context)

        val pickMedia = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                imageUri = uri
            }
        }

        Scaffold (
            topBar = {
                // The top bar
                TopAppBar (
                    // Top bar name and settings icon
                    title = {
                        Row {
                            //Name of page
                            Text(
                                text = "Settings"
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            //Settings icon
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings page",
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
                    // Page content
                    Text(
                        modifier = Modifier.padding(innerPadding),
                        text = "This is a settings page."
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Spacer(modifier = Modifier.width(16.dp))
                    //Profile picture
                    AsyncImage(
                        model = ImageRequest.Builder(context)
                            .data(imageUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Profile picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    //Pick photo button
                    Button(onClick = {
                        //Launch photo picker
                        pickMedia.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                    }
                    ) {
                        Text(text = "Pick photo")
                    }

                }
                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Spacer(modifier = Modifier.width(16.dp))
                    //Text Input
                    OutlinedTextField(
                        value = text,

                        onValueChange = {
                            text = it
                        },
                        label = {
                            Text("Insert name...")
                        }
                    )
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewSettings() {
        ComposeTutorialTheme {
            Settings(navigateToConversation = {})
        }
    }
}