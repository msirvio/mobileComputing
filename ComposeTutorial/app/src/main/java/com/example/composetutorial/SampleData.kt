import android.util.Log
import com.example.composetutorial.Message

/**
 * SampleData for Jetpack Compose Tutorial 
 */
object SampleData {
    val items = mutableListOf(
        "test1",
        "test2",
        "test3"
    )

    fun addToItems(item: String) {
        items.add(item)
    }

    fun removeFromItems(item: String) {
        items.remove(item)
    }

    fun testToString() {
        var itemString = items.joinToString("#")
        Log.d("TEST", itemString)
        val itemsTest = itemString.split("#").toMutableList()
        itemString = itemsTest.joinToString("#")
        Log.d("TEST", itemString)
    }

    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Miro",
            "Test...Test...Test..."
        ),
        Message(
            "Miro",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim()
        ),
        Message(
            "Miro",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim()
        ),
        Message(
            "Miro",
            "This is a message."
        ),
        Message(
            "Miro",
            """This message has multiple rows.
            | This is the second row!
            | And here is the third one!""".trimMargin().trim()
        ),
        Message(
            "Miro",
            "Testing..."
        ),
        Message(
            "Miro",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?"
        ),
        Message(
            "Miro",
            "Android Studio next version's name is Arctic Fox"
        ),
        Message(
            "Miro",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^"
        ),
        Message(
            "Miro",
            "I didn't know you can now run the emulator directly from Android Studio"
        ),
        Message(
            "Miro",
            "Compose Previews are great to check quickly how a composable layout looks like"
        ),
        Message(
            "Miro",
            "Previews are also interactive after enabling the experimental setting"
        ),
        Message(
            "Miro",
            "Have you tried writing build.gradle with KTS?"
        ),
    )
}
