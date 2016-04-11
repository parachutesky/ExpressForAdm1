package com.zxing.dao;

import android.util.Log;

import com.jnwat.expressforadm.bean.ExpressInfoBean;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

/**
 * Created by chang-zhiyuan on 2016/3/21.
 * 数据库存储
 */
public class Dao_SqlSave {
    DbUtils db;

    public Dao_SqlSave(DbUtils dbUtils) {
        if (null == dbUtils) {

        } else {
            this.db = dbUtils;
        }

    }

    /**
     * 存储和数据
     *
     * @param expressInfoBean obj
     */
    public void saveThisClass(ExpressInfoBean expressInfoBean) throws DbException {
        if (isHad(expressInfoBean.getExpress_position())) {//判读是否已经存在   false表示不存在，需要添加数据
        } else {//添加数据
            db.save(expressInfoBean); // 使用saveBindingId保存实体时会为实体的id赋值
            Log.w("DaoSQL_SAVE", "saveThisClass: "+"存储成功" );
        }

    }


    /**
     * 判断是否已经存储
     *
     * @param str 快递单号
     * @return
     */
    private boolean isHad(String str) {
        if (!str.equals("")) {//不能为空
            try {
                ExpressInfoBean mExpressInfoBean = db.findFirst(Selector.from(ExpressInfoBean.class).where("express_id", "=", null));
                if (null == mExpressInfoBean) {//如果没有存在
                    return false;
                } else {
                    return true;//存在
                }
            } catch (DbException e) {
                e.printStackTrace();
            }
        }
        return true;
//存在    }


    }

}