package com.test.xyz.demo.domain.model;

public class WeatherInfo {
    private String city;
    private int temperature;
    private long time;
    private String introMessage;

    public WeatherInfo(String city, String introMessage, int temperature) {
        this.city = city;
        this.introMessage = introMessage;
        this.temperature = temperature;
        this.time = System.currentTimeMillis();
    }

    public String city() {
        return city;
    }

    public int temperature() {
        return temperature;
    }

    public long time() {
        return time;
    }

    public String introMessage() {
        return introMessage;
    }
}
