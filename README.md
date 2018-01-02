# NASA-feed
Gets information from NASA Open APIs, currently Mars Rover Photos (https://api.nasa.gov/api.html#MarsPhotos) 
and Astronomy Picture of the day (https://api.nasa.gov/api.html#apod), and displays it. 
Makes use of Retrofit, Glide, recyclerView, diffUtils, and floating action button to present and interact with the content 
provided by the APIs. I attempted to create the UI with Material Design in mind. 

The app requires an API key to work. It is really easy to get one, and it will only take a couple of minutes. 
https://api.nasa.gov/index.html#apply-for-an-api-key 
After the repository is cloned, a gradle.properties file needs to created in the Android Studio project folder. 
It will need to contain one variable. API_KEY = "yourNasaApiKeyHere" with API_KEY written in this exact form. 
After that it will work. 
