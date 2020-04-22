package gg.steve.mc.ap.utils;

import java.util.concurrent.TimeUnit;

/**
 * Class that handles converting seconds into days, hours, minutes and seconds
 */
public class TimeUtil {
    //Store the number of days
    private int days;
    //Store the number of hours
    private long hours;
    //String the number of minutes
    private long minutes;
    //Store the number of seconds
    private long seconds;

    /**
     * Converts the parameter into days, hours, minutes and seconds.
     *
     * @param secondsToConvert Integer, the number of seconds to convert
     */
    public TimeUtil(int secondsToConvert) {
        this.days = (int) TimeUnit.SECONDS.toDays(secondsToConvert);
        this.hours = TimeUnit.SECONDS.toHours(secondsToConvert) - (days * 24);
        this.minutes = TimeUnit.SECONDS.toMinutes(secondsToConvert) - (TimeUnit.SECONDS.toHours(secondsToConvert) * 60);
        this.seconds = TimeUnit.SECONDS.toSeconds(secondsToConvert) - (TimeUnit.SECONDS.toMinutes(secondsToConvert) * 60);
    }

    /**
     * Getter for the number of days
     *
     * @return String
     */
    public String getDays() {
        return String.valueOf(days);
    }

    /**
     * Getter for the number of hours
     *
     * @return String
     */
    public String getHours() {
        return String.valueOf(hours);
    }

    /**
     * Getter for the number of minutes
     *
     * @return String
     */
    public String getMinutes() {
        return String.valueOf(minutes);
    }

    /**
     * Getter for the number of seconds
     *
     * @return String
     */
    public String getSeconds() {
        return String.valueOf(seconds);
    }
}