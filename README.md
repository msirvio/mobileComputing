# mobileComputing
Mobile Computing 2025 - Homework repository

## HW1 Description:
I followed the Jetpack Compose Tutorial and learned the basics of working with Android Studio.

I added and adjusted an image by:

    1. Importing the wanted image into the project.
    2. Using the 'Image'-function and its 'painter'-parameter to show the imported image.
    3. Using the 'modifier'-parameter to set the correct size and clip the image into a circle shape.

I changed the color and the size of the texts (sender name and the message) 
using the 'Text' -function and its parameters. 
I also used modifiers to make padding and spaces between the texts, 
and the 'Surface' -function to create the text bubble.

I made the contect scrollable by using the 'LazyColumn' -function, 
which took the list of sample messages and showed them all after wrapping them into messagecards.

The clicking was registered by using the 'clickable' -modifier and to remember the current state of the message,
'remember' and 'mutableStateOf' -functions were used. The visible smooth change was done by 
using 'animation' -module and its functions and modifiers. 
Once a click has registered the message expands and changes its color, depending on the state of the message. 

All in all, following the tutorial went smoothly and I understood the basic idea of how to use Android Studio.

## HW2 Description:
I found the documentations of the different navigation components (NavHost, NavController...) and implemented navigation to the app based on what I learned.

The essential part of navigation is to keep track of where we are going. A 'NavController' is first created using the 'rememberNavController' -function, to keep track of the apps state.
The navigating happens inside the 'NavHost'-function that is created to store all the navigation information. In 'NavHost', I created the different paths, and functions to pass on to the wanted buttons.
These functions will be called when the buttons are clicked, and this will activate the navigation. These event handling functions are an important part of the process.
I created the buttons using the 'IconButton' -function, and attached the previously mentioned event handling functions to them. I was also able to attach icons to them with the 'Icon' -function.

I prevented circular navigation using the 'popUpTo' -function while navigating back to the starting (conversation) screen. This functions clears the previous state (info) from the stack.
Because of this, using the back button exits the app.

I also reformatted the app into multiple classes to make it more manageable for the next homeworks.

## HW3 Description:

## HW4 Description: