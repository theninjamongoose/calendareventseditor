package com.example.tank.mygooogleeventeditor;

import org.joda.time.DateTime;

/**
 * Created by tank on 6/29/16.
 */

public enum Util {
    INSTANCE;

    public String buildEventStartEndTime (DateTime startDateTime, DateTime endDateTime){
        String eventTime = buildEventTime(startDateTime);
        if(startDateTime.getHourOfDay() < 12 && endDateTime.getHourOfDay() >= 12
                || startDateTime.getHourOfDay() < 12 && endDateTime.getHourOfDay() >= 12){
            eventTime += startDateTime.getHourOfDay() > 11 ? " PM" : " AM";
        }
        if(endDateTime != null){
            eventTime += " - " + buildEventTime(endDateTime);
            eventTime += endDateTime.getHourOfDay() > 11 ? " PM" : " AM";
        }
        return eventTime;
    }

    private String buildEventTime(org.joda.time.DateTime dateTime){
        int hour = dateTime.getHourOfDay();
        if(hour > 11){
            hour -= 12;
        }
        if(hour == 0){
            hour = 12;
        }
        int minute = dateTime.getMinuteOfHour();
        String time;
        if(minute > 0){
            String addZero = minute < 10 ? "0" : "";
            time = hour + ":" + addZero + minute;
        } else {
            time = Integer.toString(hour);
        }
        return time;
    }

}
