package net.pmkjun.pyrofishinghelper.util;

public class ConvertActivateTime {
    private ConvertActivateTime(){

    }
    public static int asMinute(int level){
        if(level<=15) return 5+level;
        else return 20 + (2 * (level-15));
    }
    public static int asLevel(int minute){
        if(minute<=20) return minute - 5;
        else return (minute-20)/2+15;
    }
}
