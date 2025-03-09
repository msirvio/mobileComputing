package com.example.composetutorial

import android.app.AlertDialog
import android.content.Context
import android.content.res.Configuration
import android.widget.EditText
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.composetutorial.ui.theme.ComposeTutorialTheme

class Components {

    @Composable
    fun ShopCard(name: String, branch: String, time: String, street: String, number: String, postCode: String) {
        Row {
            Spacer(modifier = Modifier.width(16.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 4.dp,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Column {
                    Text(
                        text = name + " " + branch,
                        modifier = Modifier.padding(all = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = time,
                        modifier = Modifier.padding(all = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = street + " " + number + ", " + postCode,
                        modifier = Modifier.padding(all = 8.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
    @Composable
    fun ListItem(item: String, recomposer: () -> Unit) {
        if(item != "" && item != "#") {
            val context = LocalContext.current

            Column (modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
            )

            {
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 4.dp,
                    modifier = Modifier
                        .padding(1.dp)
                ) {
                    Row (verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(all = 8.dp)) {
                        Text(
                            text = item,
                            modifier = Modifier.padding(all = 8.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                        IconButton(onClick = {
                            //REMOVE ITEM
                            val itemList = DataSaving.getList(context = context)
                            itemList.remove(item)
                            DataSaving.saveList(list = itemList, context = context)
                            recomposer()
                        }) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.baseline_remove_24),
                                contentDescription = "Remove button"
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun AddItem(recomposer: () -> Unit) {
        val context = LocalContext.current


        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Add item",
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            IconButton(onClick = {

                alertDialogPopUp(context, recomposer)
                recomposer()

            }) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.baseline_add_24),
                    contentDescription = "Add button"
                )
            }
        }
    }

    private fun alertDialogPopUp(context: Context, recomposer: () -> Unit) {
        val editText = EditText(context)
        val alertDialog = AlertDialog.Builder(context)
            .setTitle("Input text: ")
            .setView(editText)
            .setPositiveButton("Add") { dialogInterface, _ ->
                val input = editText.text.toString()

                //ADD ITEM
                if (input != "") {
                    val itemList = DataSaving.getList(context = context)
                    itemList.add(input)
                    DataSaving.saveList(list = itemList, context = context)
                }

                dialogInterface.dismiss()
                recomposer()
            }
            .setNegativeButton("Cancel") { dialogInterface, _ ->
                dialogInterface.dismiss()
                recomposer()
            }
        alertDialog.show()
    }

    @Composable
    fun MessageCard(msg: Message) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            //Profile picture
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(DataSaving.getImageUri(LocalContext.current))
                    .crossfade(true)
                    .build(),
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .border(1.5.dp, MaterialTheme.colorScheme.primary)
            )

            Spacer(modifier = Modifier.width(8.dp))

            var isExpanded by remember { mutableStateOf(false) }

            val surfaceColor by animateColorAsState(
                if (isExpanded) {
                    MaterialTheme.colorScheme.primary
                } else {
                    MaterialTheme.colorScheme.surface
                }, label = ""
            )
            //Name and text bubble
            Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
                //Name
                Text(
                    text = DataSaving.getName(LocalContext.current),
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.titleSmall
                )
                Spacer(modifier = Modifier.height(4.dp))

                //Text bubble
                Surface(
                    shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = surfaceColor,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(1.dp)
                ) {
                    Text(
                        text = msg.body,
                        modifier = Modifier.padding(all = 8.dp),
                        maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }

    @Composable
    fun SlipperyWarning(warning: SlipWarning) {
        Row(modifier = Modifier.padding(all = 8.dp)) {
            Surface(shape = MaterialTheme.shapes.medium,
                    shadowElevation = 1.dp,
                    color = MaterialTheme.colorScheme.surface,
                    modifier = Modifier
                    .animateContentSize()
                    .padding(1.dp)
            ){
                Column {
                    Text(text = warning.city,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(all = 8.dp))
                    Text(text = warning.timeStamp,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(all = 8.dp))
                }
            }
        }
    }

    @Preview(name = "Light Mode")
    @Preview(
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        showBackground = true,
        name = "Dark Mode"
    )
    @Composable
    fun PreviewMessageCard() {
        ComposeTutorialTheme {
            Column {
                ListItem("Maito", recomposer = {})
                ShopCard(
                    name = "Lidl",
                    branch = "Tuira",
                    time = "Ma-Pe 8-21",
                    street = "Esimerkkikatu",
                    number = "22",
                    postCode = "90570"
                )
            }
        }
    }
}