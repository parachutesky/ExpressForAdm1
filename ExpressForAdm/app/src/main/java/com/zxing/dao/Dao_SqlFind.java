package com.zxing.dao;

import com.jnwat.expressforadm.bean.ExpressInfoBean;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * 查找
 * Created by chang-zhiyuan on 2016/3/21.
 */
public class Dao_SqlFind {

    DbUtils db;

    public Dao_SqlFind(DbUtils dbUtils) {
        this.db = dbUtils;
    }


    /**
     * 得到Express 快递的列表
     * 排序 DESC
     */
    public List<ExpressInfoBean> getListExpressList() {
        List<ExpressInfoBean> expressInfoBeansList = new ArrayList<ExpressInfoBean>();
        try {
            System.out.println("db");
            System.out.println("db"+db);
            expressInfoBeansList = db.findAll(Selector.from(ExpressInfoBean.class));
                            // .where("MsgType", "=", type)
                   // .and("username", "=", "test").orderBy("express_id", true));
            if (null == expressInfoBeansList) {
    return new ArrayList<ExpressInfoBean>();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }


        return expressInfoBeansList;

    }






}
