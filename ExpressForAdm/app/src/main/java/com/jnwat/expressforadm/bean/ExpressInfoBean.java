package com.jnwat.expressforadm.bean;

import com.lidroid.xutils.db.annotation.Id;

/**
 * Created by chang-zhiyuan on 2016/3/18.
 * 快递的属性
 */
public class ExpressInfoBean {
    @Id
    private String express_id;//快递编号
    //private int id; //主键ID，必须
    private String username;//用户名
    private String express_type;//快递类型

    private String express_handname;//快递收件人
    private String express_handnamephone;//快递收件人电话
    private String express_sendname;//快递送货人
    private String express_sendphone;//快递送货人电话
    //...地址
    private String express_handadd;//收货人地址1
    private String express_handaddd;//收货人地址2
    private String express_handadddd;//收货人地址3

    private String express_position;//存放位置

    //...时间

    private String express_handtime;//拦件时间
    private String express_endtime;//送出时间,完成

    //...快递的状态
    private boolean isHad;//是否存在 true 未取件 ，false 已取件

    public boolean isSendSer() {
        return isSendSer;
    }

    //是否已经发送给服务器
    private boolean isSendSer;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getExpress_type() {
        return express_type;
    }

    public void setExpress_type(String express_type) {
        this.express_type = express_type;
    }

    public String getExpress_id() {
        return express_id;
    }

    public void setExpress_id(String express_id) {
        this.express_id = express_id;
    }

    public String getExpress_handname() {
        return express_handname;
    }

    public void setExpress_handname(String express_handname) {
        this.express_handname = express_handname;
    }

    public String getExpress_handnamephone() {
        return express_handnamephone;
    }

    public void setExpress_handnamephone(String express_handnamephone) {
        this.express_handnamephone = express_handnamephone;
    }

    public String getExpress_sendname() {
        return express_sendname;
    }

    public void setExpress_sendname(String express_sendname) {
        this.express_sendname = express_sendname;
    }

    public String getExpress_sendphone() {
        return express_sendphone;
    }

    public void setExpress_sendphone(String express_sendphone) {
        this.express_sendphone = express_sendphone;
    }

    public String getExpress_handadd() {
        return express_handadd;
    }

    public void setExpress_handadd(String express_handadd) {
        this.express_handadd = express_handadd;
    }

    public String getExpress_handaddd() {
        return express_handaddd;
    }

    public void setExpress_handaddd(String express_handaddd) {
        this.express_handaddd = express_handaddd;
    }

    public String getExpress_handadddd() {
        return express_handadddd;
    }

    public void setExpress_handadddd(String express_handadddd) {
        this.express_handadddd = express_handadddd;
    }

    public String getExpress_position() {
        return express_position;
    }

    public void setExpress_position(String express_position) {
        this.express_position = express_position;
    }

    public String getExpress_handtime() {
        return express_handtime;
    }

    public void setExpress_handtime(String express_handtime) {
        this.express_handtime = express_handtime;
    }

    public String getExpress_endtime() {
        return express_endtime;
    }

    public void setExpress_endtime(String express_endtime) {
        this.express_endtime = express_endtime;
    }

    public boolean isHad() {
        return isHad;
    }

    public void setIsHad(boolean isHad) {
        this.isHad = isHad;
    }
}
