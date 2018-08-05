package com.test.xyz.demo.domain.model.weather;

public class WeatherSummaryInfo {
    private String city;
    private int temperature;
    private long time;
    private String introMessage;

    public WeatherSummaryInfo(String city, String introMessage, int temperature) {
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
