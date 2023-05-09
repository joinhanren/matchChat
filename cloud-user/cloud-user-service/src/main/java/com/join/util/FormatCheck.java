package com.join.util;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author join
 * @Description  校验工具
 * @date 2023/2/23 19:45
 */
public class FormatCheck {

    private static final String emailStyle
            = "^(\\w+([-.][A-Za-z0-9]+)*){3,18}@\\w+([-.][A-Za-z0-9]+)*\\.\\w+([-.][A-Za-z0-9]+)*$";

    private static final String usernameStyle
            ="[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";

    /**
     * 校验邮箱格式
     * @param email
     * @return
     */
    public static boolean checkEmail(String email){
        return Pattern.matches(emailStyle,email);
    }


    /**
     * 用户名校验
     * @param username
     * @return
     */
    public static boolean checkUsername(String username){
        return username.matches(username);
    }

    /**
     * 校验verification
     * @param verification
     * @return
     */
    public static boolean checkVerification(String verification){
        return !Objects.isNull(verification)&&!verification.equals("")?true:false;
    }

//    public static void main(String[] args) {
//        System.out.println(FormatCheck.checkUsername("bbb"));
//    }
}
