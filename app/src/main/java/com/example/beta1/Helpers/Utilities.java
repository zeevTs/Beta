package com.example.beta1.Helpers;

public class Utilities {
    public static String db2Dsiplay(String str){
        //from yyyyMMddhhmm to DD-MM-yyyy HH:mm
        return str.substring(6,8)+"-"+str.substring(4,6)+"-"+str.substring(0,4)+ " "+str.substring(8,10)+":"+str.substring(10,12);
    }
}
