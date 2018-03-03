package com.jsmsframework.common.util;

import com.ucpaas.sms.exception.OperationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiongfenglin on 2017/11/23.
 *
 * @author: xiongfenglin
 */
@Deprecated
public class CheckMobileNumber {
    public static boolean checkMobileNumber(String mobileNumber){
        boolean flag = false;
        try{
            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        }catch(Exception e){
            flag = false;
            throw new OperationException("校验手机格式异常");
        }
        return flag;
    }


}
