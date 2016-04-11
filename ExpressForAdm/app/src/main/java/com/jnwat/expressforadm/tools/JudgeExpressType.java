package com.jnwat.expressforadm.tools;

import java.util.regex.Pattern;

/**
 * Created by chang-zhiyuan on 2016/3/18.
 * 判断订单类型
 */
public class JudgeExpressType {
    /**
     * 正则验证方法
     * 返回订单类型
     */
    public static String judegeType(String str_type) {
        //验证标识符必须由字母、数字、下划线组成

        Pattern p_ems = Pattern.compile("^[C,E][A-Z][0-9]{9}(CN)$");//eMS
        Pattern p_st = Pattern.compile("^(268|888|588|688|998)[0-9]{9}$");//申通E物流
        Pattern p_yt = Pattern.compile("^(0|1|2|3|5|6|7|8|E|D|F|G|V|W|e|d|f|g|v|w)[0-9]{9}$");//圆通物流
        Pattern p_zt = Pattern.compile("^((618|680|828|571|518)[0-9]{9})$|^(2008[0-9]{8})$|^((00|10)[0-9]{10})$");//中通物流
        Pattern p_zjs = Pattern.compile("^[a-zA-Z0-9]{10}$");//宅急送
        Pattern p_yd = Pattern.compile("^[\\s]*[0-9]{13}[\\s]*$");//韵达快递
        Pattern p_tt = Pattern.compile("^[0-9]{14}$");//天天快递
        Pattern p_sf = Pattern.compile("^[0-9]{12}$");//顺风快递

        if (p_ems.matcher(str_type).matches()) {//是不是EMS
            return "EMS";
        }

        if (p_st.matcher(str_type).matches()) {//
            return "申通物流";
        }
        if (p_yt.matcher(str_type).matches()) {//
            return "圆通物流";
        }
        if (p_sf.matcher(str_type).matches()) {//
            return "顺风快递";
        }
        if (p_zt.matcher(str_type).matches()) {//
            return "中通物流";
        }
        if (p_zjs.matcher(str_type).matches()) {//
            return "宅急送";
        }

        if (p_yd.matcher(str_type).matches()) {//
            return "韵达快递";
        }
        if (p_tt.matcher(str_type).matches()) {//
            return "天天快递";
        }


        return "其他快递";
    }


}
