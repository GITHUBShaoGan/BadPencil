package com.slut.badpencil.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class StringUtils {

    public static boolean isIPAddress(String ip){
        if(TextUtils.isEmpty(ip)){
            return false;
        }
        String regex = "\\d+\\.\\d+\\.\\d+\\.\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

}
