package com.example.android.sunshine.app;

import android.text.format.Time;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;

public class WeatherDataParser {

    private String weatherJson;

    public WeatherDataParser(String weatherJson) {
        this.weatherJson = weatherJson;
    }

    private final String LOG_TAG = WeatherDataParser.class.getSimpleName();
    /**
     * openweathermaps api fields
     */
    private final String OWM_LIST = "list";
    private final String OWM_WEATHER = "weather";
    private final String OWM_MAX = "temp_max";
    private final String OWM_MIN = "temp_min";
    private final String OWM_MAIN = "main";
    private final String OWM_DESC = "description";

    /**
     * getNumDays - fetches the number of days in list
     *
     * @return the number of day entries in weather data
     */
    private int getNumDays() {
        try {
            JSONArray days = new JSONObject(weatherJson).getJSONArray(OWM_LIST);
            return days.length();
        } catch(JSONException ex) {
            Log.e(LOG_TAG, ex.toString());
            return 0;
        }
    }

    /**
     * getDayWeather - finds the weather object for a days weather
     *
     * @param day an int index representing which day to select
     *            this is an offset from today so 0 selects today.
     * @return JSONObject representing the weather for the day
     */
    private JSONObject getDayWeather(int day) {
        /* TODO: handle this case */
        if (day > 7)
            return null;

        try {
            JSONArray days = new JSONObject(weatherJson).getJSONArray(OWM_LIST);
            return days.getJSONObject(day);
        } catch(JSONException ex) {
            Log.e(LOG_TAG, ex.toString());
            return null;
        }
    }

    /**
     * getHighTemp - finds the high temp on a day
     *
     * @param day the offset from today to select the day
     * @return the high temperature for the day in a double
     */
    public double getHighTemp(int day) {
        try {
            return getDayWeather(day).getJSONObject(OWM_MAIN).getDouble(OWM_MAX);
        } catch(JSONException ex) {
            Log.e(LOG_TAG, ex.toString());
            return -1;
        }
    }

    /**
     * getLowTemp - finds the low temp on a day
     *
     * @param day the offset from today to select the day
     * @returns the low temp for a day
     */
    public double getLowTemp(int day) {
        try {
            return getDayWeather(day).getJSONObject(OWM_MAIN).getDouble(OWM_MIN);
        } catch(JSONException ex) {
            Log.e(LOG_TAG, ex.toString());
            return -1;
        }
    }

    /**
     * getDesc - finds the weather description for a day
     *
     * @param day the offset from today to select the day
     * @return the weather desc for a day
     */
    public String getDesc(int day) {
        try {
            return getDayWeather(day).getJSONArray(OWM_WEATHER).getJSONObject(0).getString(OWM_DESC);
        } catch (JSONException ex) {
            Log.e(LOG_TAG, ex.toString());
            return "";
        }
    }

    /**
     * getWeather - fetches the forecast
     *
     * @return the array of displayable weather strings
     */
    public String[] getWeather(boolean metric) {
        Time dayTime = new Time();
        dayTime.setToNow();
        int numDays = getNumDays();
        // we start at the day returned by local time. Otherwise this is a mess.
        int julianStartDay = Time.getJulianDay(System.currentTimeMillis(), dayTime.gmtoff);

        // now we work exclusively in UTC
        dayTime = new Time();

        String[] resultStrs = new String[numDays];
        for(int i = 0; i < numDays; i++) {
            // For now, using the format "Day, description, hi/low"
            String day;
            String highAndLow;
            double high = getHighTemp(i);
            double low = getLowTemp(i);

            if (!metric) {
                high = (high * 1.8) + 32;
                low = (low * 1.8) + 32;
            }

            // The date/time is returned as a long.  We need to convert that
            // into something human-readable, since most people won't read "1400356800" as
            // "this saturday".
            long dateTime;
            // Cheating to convert this to UTC time, which is what we want anyhow
            dateTime = dayTime.setJulianDay(julianStartDay+i);
            day = getReadableDateString(dateTime);


            highAndLow = formatHighLows(high, low);
            resultStrs[i] = day + " - " + getDesc(i) + " - " + highAndLow;
        }

        return resultStrs;
    }


    /* The date/time conversion code is going to be moved outside the asynctask later,
     * so for convenience we're breaking it out into its own method now.
     */
    private String getReadableDateString(long time){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat shortenedDateFormat = new SimpleDateFormat("EEE, MMM dd");
        return shortenedDateFormat.format(time);
    }

    /**
     * Prepare the weather high/lows for presentation.
     */
    private String formatHighLows(double high, double low) {
        // For presentation, assume the user doesn't care about tenths of a degree.
        long roundedHigh = Math.round(high);
        long roundedLow = Math.round(low);

        String highLowStr = roundedHigh + "/" + roundedLow;
        return highLowStr;
    }

}
