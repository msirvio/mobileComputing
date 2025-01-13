package com.example.composetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTutorialTheme {
                NavigationManager()
            }
        }
    }
}

data class Message(val author: String, val body: String)

const val CONVERSATION: String = "CONVERSATION"
const val INFO: String = "INFO"

@Composable
fun NavigationManager() {
    val navController = rememberNavController()
    val conversationScreen = ConversationScreen()
    val infoScreen  = InfoScreen()


    NavHost(
        navController = navController,
        startDestination = CONVERSATION
    ) {
        composable(CONVERSATION) {
            conversationScreen.Conversation(
                SampleData.conversationSample,
                navigateToInfo = {
                    navController.navigate(route = INFO)
                }
            )
        }
        composable(INFO) {
            infoScreen.Info(
                navigateToConversation = {
                    navController.navigate(route = CONVERSATION) {
                        popUpTo(route = CONVERSATION) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewNavigationManager() {
    ComposeTutorialTheme {
        NavigationManager()
    }
}