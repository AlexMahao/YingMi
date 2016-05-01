package com.mahao.alex.yingmi.db;

import android.content.Context;

import com.alex.bean.CommodityBean;
import com.alex.dao.CommodityBeanDao;
import com.mahao.alex.yingmi.base.App;

import java.util.List;

/**
 * Created by Alex_MaHao on 2016/5/1.
 */
public class CommodityRepository {

    //插入
    public static void insertOrUpdate(Context context, CommodityBean comm) {
        getCommodityDao(context).insertOrReplace(comm);
    }

    //删除
    public static void deleteCommodityWithId(Context context, String commodityId) {

        CommodityBean commodityForId = getCommodityForId(context, commodityId);
        getCommodityDao(context).delete(commodityForId);
    }

    //查询全部
    public static List<CommodityBean> findAll(Context context){
        return  getCommodityDao(context).loadAll();
    }



    //判断是否存在
    public static CommodityBean getCommodityForId(Context context, String commodityId) {
        List<CommodityBean> commodityBeen = getCommodityDao(context).queryBuilder().where(CommodityBeanDao.Properties.CommodityId.eq(commodityId)).list();
        return commodityBeen.size()==0?null:commodityBeen.get(0);
    }

    private static CommodityBeanDao getCommodityDao(Context c) {
        return (App.sApp).getDaoSession().getCommodityBeanDao();
    }
}
