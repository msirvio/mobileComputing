@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.composetutorial

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class InfoScreen {
    @Composable
    fun Info(navigateToConversation: () -> Unit) {
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
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                // Page content
                Text(
                    modifier = Modifier.padding(innerPadding),
                    text = "This is an info page."
                )
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