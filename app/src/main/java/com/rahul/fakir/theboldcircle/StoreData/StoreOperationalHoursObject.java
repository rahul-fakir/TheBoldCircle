package com.rahul.fakir.theboldcircle.StoreData;

/**
 * Created by rahul.fakir on 2016/05/23.
 */
public class StoreOperationalHoursObject {
public double boundaryTimes[][] = {{-1, -1, -1, -1, -1, -1, -1, -1},
                                    {-1, -1, -1, -1, -1, -1, -1, -1,}};

    public StoreOperationalHoursObject(){

    }

    public void setDay(int day, double open, double close){
        boundaryTimes[0][day] = open;
        boundaryTimes[1][day] = close;
    }

    public String getStringTime(int day, int boundary) {
        double preConvertedTime = boundaryTimes[boundary][day];
        String time = String.valueOf(preConvertedTime);
        String[] splitTime = time.split(".");
        if (splitTime[0].length() == 1) {
            if (splitTime[1].length() == 1){
                return "0" + splitTime[0] + ":" + splitTime[1] + "0";
            } else {
                return "0" + splitTime[0] + ":" + splitTime[1];
            }
        } else {
            if (splitTime[1].length() == 1){
                return splitTime[0] + ":" + splitTime[1] + "0";
            } else {
                return splitTime[0] + ":" + splitTime[1];
            }
        }
    }

    public void setBoundaryTimes(int day, int boundary, double time) {
        boundaryTimes[boundary][day] = time;
    }
}
