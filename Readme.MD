Demo Sample
===
This is a demo sample that shows MVP Dagger 2.x app in action with Retrofit, ButterKnife, Mockito 2.x, 
Rx Android, Jacoco, PIT and more.  

In order to run this sample locally, and see the weather information, make sure to:
1. Go to https://openweathermap.org/api, and obtain an API key.
2. Place the key in build.gradle as a value for `OPEN_WEATHER_APP_KEY`.
```
    buildConfigField 'String', 'OPEN_WEATHER_APP_KEY', "\"YOUR-KEY-HERE\""
```