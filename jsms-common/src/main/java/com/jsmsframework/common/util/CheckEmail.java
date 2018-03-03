package com.jsmsframework.common.util;



import com.ucpaas.sms.exception.OperationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiongfenglin on 2017/11/23.
 *
 * @author: xiongfenglin
 */
public class CheckEmail {
    public static boolean checkEmail(String email){
        boolean flag = false;
        try{
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
            throw new OperationException("校验邮箱格式异常");
        }
        return flag;
    }
}
