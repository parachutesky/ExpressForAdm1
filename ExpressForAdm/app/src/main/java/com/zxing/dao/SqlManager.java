package com.zxing.dao;

import android.content.Context;

import com.jnwat.expressforadm.bean.ExpressInfoBean;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;

/**
 * Created by chang-zhiyuan on 2016/3/21.
 * 数据库管理
 */
public class SqlManager {
    DbUtils.DaoConfig config;
    Context mContext;

    /**
     * @param context 初始化
     */
    public SqlManager(Context context) {
        this.mContext = context;
    }

        /**
         * 创建数据库
         */

    public void creatSQL() {
        config.setDbName("expressfor-Adm"); //物业版本db名
        config.setDbVersion(1);  //db版本
        DbUtils db = DbUtils.create(config);//db还有其他的一些构造方法，比如含有更新表版本的监听器的


    }

    /**
     * 创建表
     */
    private void creatTable(DbUtils db) {
        try {
            db.createTableIfNotExist(ExpressInfoBean.class); //创建一个表User
            //    db.save(user);//在表中保存一个user对象。最初执行保存动作时，也会创建User表
        } catch (DbException e) {
            e.printStackTrace();
        }


    }


    /**
     * 删除表
     */
    public void deleteSql(DbUtils db) throws DbException {
        db.dropTable(ExpressInfoBean.class);
    }
}
