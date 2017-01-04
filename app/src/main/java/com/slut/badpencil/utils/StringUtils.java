package com.slut.badpencil.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 七月在线科技 on 2016/12/29.
 */

public class StringUtils {

    /**
     * 简单校验是否是ip地址
     *
     * @param ip 待校验ip字符串
     * @return 返回bool
     */
    public static boolean isIPAddress(String ip) {
        if (TextUtils.isEmpty(ip)) {
            return false;
        }
        String regex = "\\d+\\.\\d+\\.\\d+\\.\\d+";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    /**
     * 检验用户输入是否是整型
     *
     * @param input 输入字符串
     * @return 输出bool值
     */
    public static boolean isInteger(String input) {
        Matcher mer = Pattern.compile("^[+-]?[0-9]+$").matcher(input);
        return mer.find();
    }

}
