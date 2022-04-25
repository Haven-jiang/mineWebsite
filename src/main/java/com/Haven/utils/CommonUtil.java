package com.Haven.utils;

import com.Haven.MineWebsiteApplication;

import java.util.Objects;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 公共工具类
 *
 * @author yezhiqiu
 * @date 2021/07/28
 */


public class CommonUtil {

    /**
     * 检测邮箱是否合法
     *
     * @param username 用户名
     * @return 合法状态
     */
    public static boolean checkEmail(String username) {
        String rule = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";
        //正则表达式的模式 编译正则表达式
        Pattern p = Pattern.compile(rule);
        //正则表达式的匹配器
        Matcher m = p.matcher(username);
        //进行正则匹配
        return m.matches();
    }

    /**
     * 获取括号内容
     *
     * @param str str
     * @return {@link String} 括号内容
     */
    public static String getBracketsContent(String str) {
        return str.substring(str.indexOf("(") + 1, str.indexOf(")"));
    }

    /**
     * 生成6位随机验证码
     *
     * @return 验证码
     */
    public static String getRandomCode() {
        StringBuilder str = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            str.append(random.nextInt(10));
        }
        return str.toString();
    }


    public static String getLocalPath() {
        String path = Objects.requireNonNull(MineWebsiteApplication.class.getResource("")).getPath().replaceAll("file:", "");
        return path.substring(0, path.lastIndexOf("target")) + "target/";
    }

    public static String getImagePath() {
        String path = Objects.requireNonNull(MineWebsiteApplication.class.getResource("")).getPath().replaceAll("file:", "");
        return path.substring(0, path.lastIndexOf("target")) + "target/image/";
    }

}
