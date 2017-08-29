package smart4j.framework.base;

import smart4j.framework.helper.BeanHelper;
import smart4j.framework.helper.ClassHelper;
import smart4j.framework.helper.ControllerHelper;
import smart4j.framework.helper.IocHelper;
import smart4j.framework.util.ClassUtil;

/**
 * 加载helper类
 *
 * 使加载更集中
 *
 * Created by yang on 2017/8/26.
 */
public class HelperLoader {

    /**
     * 初始化方法加载helper类
     */
    public static void init(){
        Class<?>[] classes = {
                ClassHelper.class,
                BeanHelper.class,
                IocHelper.class,
                ControllerHelper.class
        };
        for (Class<?> cls : classes){
            ClassUtil.loadClass(cls.getName(),false);
        }
    }
}
