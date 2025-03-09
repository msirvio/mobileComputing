@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composetutorial

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class ConversationScreen {

    @Composable
    fun Conversation(
        items: List<String>,
        navigateToInfo: () -> Unit,
        navigateToSettings: () -> Unit,
        recomposer: () -> Unit
    ) {
        val components = Components()

        Scaffold(
            topBar = {
                // The top bar
                TopAppBar(
                    title = {
                        // Name of page
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_launcher_foreground),
                                contentDescription = "Home page",
                                modifier = Modifier.scale(0.5f)
                            )
                            //Spacer(modifier = Modifier.width(8.dp))
                            Text("Hey, " + DataSaving.getName(LocalContext.current) + "!")
                        }
                    },
                    actions = {
                        //Info button
                        IconButton(onClick = navigateToInfo) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_info_24),
                                contentDescription = "Info page",

                                )
                        }
                        //Settings button
                        IconButton(onClick = navigateToSettings) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_settings_24),
                                contentDescription = "Settings page"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(colorResource(R.color.blue))
                )
            }
        ) { innerPadding ->
            Column (modifier = Modifier.fillMaxWidth()) {
                //The scrollable messages
                LazyColumn(
                    modifier = Modifier.padding(innerPadding)
                        .fillMaxWidth()
                ) {
                    //Shopping list items
                    items(items) { item ->
                        components.ListItem(item, recomposer)
                    }
                    //Add Item button
                    item {
                        components.AddItem(recomposer)
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewConversation() {
        ComposeTutorialTheme {
            Conversation(SampleData.items, navigateToInfo = {}, navigateToSettings = {}, recomposer = {})
        }
    }
}
