package com.levent_j.potato.utils;

/**
 * Created by levent_j on 16-5-20.
 */
public class Util {
    private static int num = 0;
    public static int Minute2Second(double minute){
        //*60000
        return (int)minute*5000;
    }

    public static int createRandomColor(){
        if (num<3){
            return num++;
        }else {
            num = 0;
            return createRandomColor();
        }

    }
}
