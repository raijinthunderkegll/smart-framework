package smart4j.framework.util;

/**
 * 提供相关配置项常量
 *
 * Created by yang on 2017/8/16.
 */
public interface ConfigConstant {
    /*
    使用interface定义常量,可以省略public static final,优美!
     */

    String CONFIG_FILE = "smart.properties";

    String JDBC_DRIVER = "jdbc.driver";
    String JDBC_URL = "jdbc.url";
    String JDBC_USERNAME = "jdbc.username";
    String JDBC_PASSWORD = "jdbc.password";

    String APP_BASE_PACKAGE = "app.base_package";
    String APP_VIEW_PATH = "app.view_path";
    String APP_ASSET_PATH = "app.asset_path";

}
