package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(1, "com.alex.bean");

        schema.setDefaultJavaPackageDao("com.alex.dao");

        initCommodityBeam(schema);

        new DaoGenerator().generateAll(schema,"E:/AndroidStudio/demo/YingMi/app/src/main/java-gen");// 自动创建

    }


    private static void initCommodityBeam(Schema schema) {
        Entity userBean = schema.addEntity("CommodityBean");// 表名
        userBean.addIdProperty();
        userBean.addStringProperty("accountID");
        userBean.addStringProperty("actorId");
        userBean.addStringProperty("bigImagePath");
        userBean.addStringProperty("commodityDesc");
        userBean.addStringProperty("commodityId");
        userBean.addStringProperty("commodityImagePath");
        userBean.addStringProperty("commodityName");
        userBean.addStringProperty("currency");
        userBean.addStringProperty("linkPath");
        userBean.addStringProperty("name");
        userBean.addStringProperty("nickName");
        userBean.addStringProperty("price");
        userBean.addStringProperty("productionId");
        userBean.addIntProperty("themeId");
    }
}
