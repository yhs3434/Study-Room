package com.yoonhs3434.suroom;

public class MySetting {
    private static String my_url;
    private static int my_id;

    public MySetting(){
        my_url = null;
        my_id = 0;
    }

    public static void setMyUrl(String urlParam){
        my_url = urlParam;
    }

    public static String getMyUrl(){
        return my_url;
    }

    public static void setMyId(int idParam){
        my_id = idParam;
    }

    public static int getMyId(){
        return my_id;
    }
}
