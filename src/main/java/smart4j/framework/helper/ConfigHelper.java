package smart4j.framework.helper;

import smart4j.framework.util.ConfigConstant;
import smart4j.framework.util.PropsUtil;

import java.util.Properties;

/**
 * 获取配置文件信息助手类
 *
 * Created by yang on 2017/8/17.
 */
public  class ConfigHelper {

    private static final Properties CONFIG_PROPERTIES = PropsUtil.loadProps(ConfigConstant.CONFIG_FILE);

    public static String getJdbcDriver(){
        return PropsUtil.getStringVal(CONFIG_PROPERTIES,ConfigConstant.JDBC_DRIVER);
    }

    public static String getJdbcUrl(){
        return PropsUtil.getStringVal(CONFIG_PROPERTIES,ConfigConstant.JDBC_URL);
    }

    public static String getJdbcUsername(){
        return PropsUtil.getStringVal(CONFIG_PROPERTIES,ConfigConstant.JDBC_USERNAME);
    }

    public static String getJdbcPassword(){
        return PropsUtil.getStringVal(CONFIG_PROPERTIES,ConfigConstant.JDBC_PASSWORD);
    }

    public static String getAppBasePackage(){
        return PropsUtil.getStringVal(CONFIG_PROPERTIES,ConfigConstant.APP_BASE_PACKAGE);
    }

    public static String getAppViewPath(){
        return PropsUtil.getStringVal(CONFIG_PROPERTIES,ConfigConstant.APP_VIEW_PATH,"/WEB-INF/view");
    }

    public static String getAppAssetPath(){
        return PropsUtil.getStringVal(CONFIG_PROPERTIES,ConfigConstant.APP_ASSET_PATH,"/asset/");
    }
}
