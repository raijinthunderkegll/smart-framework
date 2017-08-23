package smart4j.framework.helper;

import smart4j.framework.annotation.Controller;
import smart4j.framework.annotation.Service;
import smart4j.framework.util.ClassUtil;
import smart4j.framework.util.PropsUtil;

import java.util.HashSet;
import java.util.Set;

/**
 * 类操作助手类
 *
 * Created by yang on 2017/8/23.
 */
public final class ClassHelper {

    private static final Set<Class<?>> CLASS_SET;

    static{
        //加载应用项目下的所有类
        String basePackage = ConfigHelper.getAppBasePackage();
        CLASS_SET = ClassUtil.getClassSet(basePackage);
    }

    /**
     * 获取所有类
     */
    public static Set<Class<?>> getClassSet(){
        return CLASS_SET;
    }

    /**
     * 获取service类
     */
    public static Set<Class<?>> getService(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        if(CLASS_SET != null && CLASS_SET.size()>0){
            for(Class<?> cls : CLASS_SET){
                if(cls.isAnnotationPresent(Service.class)){
                    classSet.add(cls);
                }
            }
        }
        return classSet;
    }

    /**
     * 获取controller类
     */
    public static Set<Class<?>> getController(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        if(CLASS_SET != null && CLASS_SET.size()>0){
            for(Class<?> cls : CLASS_SET){
                if(cls.isAnnotationPresent(Controller.class)){
                    classSet.add(cls);
                }
            }
        }
        return classSet;
    }

    /**
     * 获取smart-famework管理的bean
     * (service和controller等)
     */
    public static Set<Class<?>> getBeanClass(){
        Set<Class<?>> classSet = new HashSet<Class<?>>();
        if(CLASS_SET != null && CLASS_SET.size()>0){
            classSet.addAll(getService());
            classSet.addAll(getController());
        }
        return classSet;
    }

}
