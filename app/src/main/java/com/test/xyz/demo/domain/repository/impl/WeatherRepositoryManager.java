package com.test.xyz.demo.domain.repository.impl;

import android.util.Log;

import com.test.xyz.demo.domain.repository.api.WeatherRepository;
import com.test.xyz.demo.domain.repository.exception.InvalidCityException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * WeatherRepositoryManager interacts with yahooapis weather service using Java.net.URL connection
 * (Another example of HTTP interaction other than Retrofit).
 * I definitely recommend using Retrofit but I would like another way here.
 */
public class WeatherRepositoryManager implements WeatherRepository {
    private static final String TAG = WeatherRepositoryManager.class.getName();

    @Override
    public Observable<Integer> getWeatherInfo(final String city) {

        Observable observable = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    String encodedCity = encodeCity(city);

                    Integer temperature = getTemperature(encodedCity);

                    subscriber.onNext(temperature);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        return observable;
    }

    private int getTemperature(String city) throws InvalidCityException {
        int temperature;

        try {
            URL url = new URL("http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20weather.forecast%20where%20woeid%20in%20(select%20woeid%20from%20geo.places(1)%20where%20text%3D%22" + city + "%22)&format=json&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            httpURLConnection.setInstanceFollowRedirects(true);

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Receive response ...
            int responseCode = httpURLConnection.getResponseCode();

            if (responseCode >= 400) {
                throw new Exception("HTTP error");
            }

            InputStream is = httpURLConnection.getInputStream();
            InputStreamReader reader = new InputStreamReader(is);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line = "";
            StringBuffer sb = new StringBuffer();

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }

            bufferedReader.close();

            String result = sb.toString();

            int startIndex = result.indexOf("\"temp\":");

            if (startIndex == -1) {
                throw new InvalidCityException("Invalid city provided");
            }

            int endIndex = result.indexOf(",", startIndex);

            temperature = Integer.parseInt(result.substring(startIndex + 8, endIndex - 1));
        } catch (InvalidCityException ex) {
            throw ex;
        } catch (Exception ex) {
            Log.e(TAG, ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }

        return temperature;
    }

    private String encodeCity(String city) {
        if (city == null || city.trim().equals("")) {
            throw new RuntimeException("City is required");
        }

        try {
            city = URLEncoder.encode(city, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Invalid city is provided");
        }

        return city;
    }
}
