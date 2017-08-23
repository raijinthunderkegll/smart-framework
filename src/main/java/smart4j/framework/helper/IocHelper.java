package smart4j.framework.helper;

import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import smart4j.framework.annotation.Inject;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类
 * <p>
 * Created by yang on 2017/8/24.
 */
public final class IocHelper {

    private final static Logger LOGGER = LoggerFactory.getLogger(IocHelper.class);

    //加载类之后  加载当前类  实现依赖注入
    static {
        //获取所有的beanMap
        Map<Class<?>, Object> beanMap = BeanHelper.getBeanMap();
        //遍历beanMap 获取每个对象的属性集合field[]
        for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
            Class<?> beanClass = beanEntry.getKey();
            Object beanValue = beanEntry.getValue();

            Field[] fields = beanClass.getDeclaredFields();
            if(ArrayUtils.isNotEmpty(fields)){
                for (Field field : fields) {
                    //获取属性集合中带有inject注解的
                    if (field.isAnnotationPresent(Inject.class)) {
                        //在beanMap中找到对应的属性类的实例
                        if (beanMap.containsKey(field.getClass())) {
                            Class<?> fieldClass = field.getType();
                            Object fieldBean = beanMap.get(field.getClass());
                            if(fieldBean != null){
                                //通过反射初始化field
                                try {
                                    field.setAccessible(true);//给属性设置可访问
                                    field.set(beanValue,fieldBean);//给属性赋值
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                    LOGGER.error("set field failure",e);
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
