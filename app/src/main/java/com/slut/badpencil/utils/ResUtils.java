package com.slut.badpencil.utils;


import com.slut.badpencil.App;

/**
 * Created by 七月在线科技 on 2016/12/6.
 */

public class ResUtils {

    public static String getString(int id) {
        return App.getContext().getString(id);
    }

    public static String [] getStringArray(int id){
        return App.getContext().getResources().getStringArray(id);
    }

}
