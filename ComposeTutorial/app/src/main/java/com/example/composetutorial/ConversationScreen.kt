@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composetutorial

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class ConversationScreen {
    @Composable
    fun Conversation(
        messages: List<Message>,
        navigateToInfo: () -> Unit,
        navigateToSettings: () -> Unit
    ) {
        val components = Components()

        Scaffold (
            topBar = {
                // The top bar
                TopAppBar(
                    title = {
                        // Name of page
                        Text("Conversation")
                    },
                    actions = {
                        //Info button
                        IconButton(onClick = navigateToInfo) {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = "Info page"
                            )
                        }
                        //Settings button
                        IconButton(onClick = navigateToSettings) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                contentDescription = "Settings page"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            //The scrollable messages
            LazyColumn (
                modifier = Modifier.padding(innerPadding)
            ) {
                items(messages) { message ->
                    components.MessageCard(message)
                }
            }
        }
    }

    @Preview
    @Composable
    fun PreviewConversation() {
        ComposeTutorialTheme {
            Conversation(SampleData.conversationSample, navigateToInfo = {}, navigateToSettings = {})
        }
    }
}
