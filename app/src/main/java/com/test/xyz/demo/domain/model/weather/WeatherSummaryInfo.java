package com.test.xyz.demo.domain.model.weather;

public class WeatherSummaryInfo {
    private String city;
    private int temperature;
    private String introMessage;

    public WeatherSummaryInfo(String city, String introMessage, int temperature) {
        this.city = city;
        this.introMessage = introMessage;
        this.temperature = temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String city() {
        return city;
    }
    public int temperature() {
        return temperature;
    }
    public String introMessage() {
        return introMessage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof WeatherSummaryInfo) {
            return city.equals(((WeatherSummaryInfo) obj).city) &&
                   introMessage.equals(((WeatherSummaryInfo) obj).introMessage) &&
                   temperature == (((WeatherSummaryInfo) obj).temperature);
        }

        return false;
    }

    @Override
    public int hashCode() {
        int hashcode = 13 + temperature;

        if (city != null) {
            hashcode += city.length();
        }

        if (introMessage != null) {
            hashcode += introMessage.length();
        }

        return hashcode;
    }

    @Override
    public String toString() {
        return "{" + "City=" + city + ", Intro Message=" + introMessage + ", Temp=" + temperature + "}";
    }
}
