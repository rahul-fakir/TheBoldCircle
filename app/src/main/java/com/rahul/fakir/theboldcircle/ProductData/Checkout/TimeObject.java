package com.rahul.fakir.theboldcircle.ProductData.Checkout;

/**
 * Created by rahul.fakir on 2016/05/27.
 */
public class TimeObject {
    private double startTime = 0.0;
    private double endTime = 0.0;

    public TimeObject() {

    }

    public TimeObject(Double dTime, Double dEndTime) {
        this.startTime = dTime;
        this.endTime = dEndTime;
    }

    public String getSTime() {
        return convertTime(startTime) + " - " + convertTime(endTime);
    }

    public String convertTime(double time) {
        int hours = (int) time;
        int minutes = (int) (time * 60) % 60;
        String sHours = String.valueOf(hours);
        String sMinutes = String.valueOf(minutes);
        if (sHours.length() == 1) {
            if (sMinutes.length() == 1) {
                return "0" + sHours + ":" + sMinutes + "0";
            } else {
                return "0" + sHours + ":" + sMinutes;
            }
        } else {
            if (sMinutes.length() == 1) {
                return sHours + ":" + sMinutes + "0";
            } else {
                return sHours + ":" + sMinutes;
            }

        }
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

}
