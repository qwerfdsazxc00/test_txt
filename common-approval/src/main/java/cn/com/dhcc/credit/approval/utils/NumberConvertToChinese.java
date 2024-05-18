package cn.com.dhcc.credit.approval.utils;

import cn.com.dhcc.credit.approval.constants.Constants;

/**
 * @author by 王豪伟
 * @Description 数字转换成汉字
 * @Date 2020/7/29 16:00
 */
public class NumberConvertToChinese {


    public static  String numToChinese(int num) {
        String[] s1 = Constants.s1;
        if (num <= 10) {
            return s1[num];
        }
        String result = "";
        result += s1[num / 10];
        if (num / 10 == 1) {
            result = s1[10] + s1[num % 10];
        } else {
            result += s1[10];
            result += s1[num % 10];
        }
        return result;
    }
}
