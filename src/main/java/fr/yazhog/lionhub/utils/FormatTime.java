package fr.yazhog.lionhub.utils;

public class FormatTime {

    private int sec;
    private int min;
    private int hour;

    public FormatTime(int seconds) {
        this.sec = seconds % 60;
        int hour = seconds / 60;
        this.min = hour % 60;
        this.hour = hour/60;
    }

    public FormatTime(int min, int sec) {
        this.sec = sec;
        this.min = min;
    }

    public String toString() {
        String s_min = String.valueOf(min);
        if(min < 10) {
            s_min = "0" + String.valueOf(min);
        }
        String s_sec = String.valueOf(sec);
        if(sec < 10) {
            s_sec = "0" + String.valueOf(sec);
        }
        String s_hour = String.valueOf(hour);
        if(hour < 10){
            s_hour = "0" + String.valueOf(hour);
        }
        return s_hour + ":" +  s_min + ":" + s_sec;
    }

    public int getHour() {
        return hour;
    }

    public int getMin() {
        return min;
    }

    public int getSec() {
        return sec;
    }

    public FormatTime setHour(int hour) {
        this.hour = hour;
        return this;
    }

    public FormatTime setMin(int min) {
        this.min = min;
        return this;
    }

    public FormatTime setSec(int sec) {
        this.sec = sec;
        return this;
    }

}
