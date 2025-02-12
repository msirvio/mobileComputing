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
I implemented the image picking using Photo Picker. To make the Photo picker work, it had to be registered first.
When the 'Pick photo' -button is pressed, the Photo picker is launched, to choose an image.

The text input was implemented using 'TextField'. I used a version of it called 'OutlinedTextField', to make it look better.
The text could be updated through the 'onValueChange' -parameter of 'TextField'.

The current imageUri and text are remembered using 'remember' and 'mutableStateOf' -functions. 

I made a "DataSaving" -object, with functions that can be called from anywhere. 
There are functions to save and get the name and imageUri.
Everytime this data is needed, it is fetched using the get functions 
and when the data is changed in the settings menu, the data is saved using the save functions.
I simply save and get the name and the imageUri using SharedPreferences.

Note - After restarting the app, the imageUri does not work properly, the text does work.
File, input and output streams would have been a better way to save the image, but I did not get it to work properly either. 


## HW4 Description:
I prompt for notification and location permissions when the application is started, 
if the permissions have not been given yet. 
I used 'ContextCompat.checkSelfPermission' and 'ActivityCompat.requestPermissions' for this.

The notification can be interacted with by tapping it, to open the app. 
I created an 'Intent' to be set in the notification when building it, to make it interactable.

I used both, an API and a sensor, to get some data.

I got slippery warning info from here: 
https://data.ouka.fi/data/fi/dataset/jalankulkijoiden-liukastumisvaroitukset-oulussa
with the help of this documentation:
https://sva-konsultointi.github.io/liukastumisvaroitus-api-doc/#introduction

I used 'LocationServices' to get the location, and 'Geocoder' to extract the city name from the location.
I constructed a URL with the correct city name to get the information from the previously mentioned API.
With the URL, I got the city's slippery warnings in a descending order (from newest to oldest), and I chose the newest one to be displayed.

I used the temperature sensor to trigger a notification, when the temperature went below 0 °C.
In the notification, I showed the current temperature in celsius and in fahrenheit.
To read the sensor, 
I had to register a 'SensorEventListener' to the temperature sensor with the help of 'SensorManager'.
Now, when the temperature goes low enough, a notification is triggered.

I used the Extended Controls to change the location and temperature.
At around 0:20 in the recording, I changed the location from Oulu to Helsinki.
At around 0:55 in the recording, I lowered the temperature to below 0 °C.

Note! - I did not implement triggering the notification, whilst the app is not in the foreground.