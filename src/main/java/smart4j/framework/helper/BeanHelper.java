package smart4j.framework.helper;


import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean操作助手类
 * <p>
 * Created by yang on 2017/8/23.
 */
public final class BeanHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(BeanHelper.class);
    /**
     * BeanMap beanClass与bean映射关系
     */
    private final static Map<Class<?>, Object> BEAN_MAP;

    static {
        BEAN_MAP = new HashMap<Class<?>,Object>();
        try {
            Set<Class<?>> classSet = ClassHelper.getBeanClass();
            if (!CollectionUtils.isEmpty(classSet)) {
                for (Class<?> cls : classSet) {
                    Object o = cls.newInstance();
                    BEAN_MAP.put(cls.getClass(),cls);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("beanHelper static bean instance error",e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取映射beanMap
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }

    /**
     * 获取smart-framework容器中管理的bean实例
     */
    public static <T> T getBean(Class<T> cls){
        if(BEAN_MAP.containsKey(cls)){
            return (T)BEAN_MAP.get(cls);
        }else{
            LOGGER.error("class is not in bean pool . connot get bean by class : " + cls);
            throw new RuntimeException("cannot get bean by class:" + cls);
        }
    }

}
