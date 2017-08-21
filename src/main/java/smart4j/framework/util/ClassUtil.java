package smart4j.framework.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * 类操作工具类
 *
 * Created by yang on 2017/8/18.
 */
public class ClassUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> cls = null;

        try {
            //isInitialized 加载时是否运行静态区块
            cls = Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("load class failure",e);
            throw new RuntimeException(e);
        }
        return cls;
    }

    public static Class<?> loaderClass(String className){
        return loadClass(className,true);
    }

    /**
     * 加载指定包名下的所有类
     */
    public static Set<Class<?>> getClassSet(String packageName){
        Set<Class<?>> classes = new HashSet<Class<?>>();
        //TODO
        //获取Enumeration对象,循环获取他的元素,判断是file还是jar,调用不同的逻辑
        try {
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".","/"));
            while (urls.hasMoreElements()){
                URL url = urls.nextElement();
                if (url != null){
                    String protocol = url.getProtocol();
                    if("file".equals(protocol)){
                        LOGGER.info("get classes protocol = file ************* start");
                        String path = url.toURI().getPath();
                        addClass(classes,path,packageName);
                    }else if("jar".equals(protocol)){
                        //TODO
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return classes;
    }


    /**
     * 创建包下的类
     */
    private static void addClass(Set<Class<?>> classes,String packagePath,String packageName){
        //获取路径下的所有文件
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            //匿名内部类  文件过滤器  筛选.class文件和目录的file对象
            public boolean accept(File pathname) {
                return (pathname.isFile() && pathname.getName().endsWith(".class") || pathname.isDirectory());
            }
        });

        for(File file : files){

            String fileName = file.getName();

            if(file.isDirectory()){
                addClass(classes,file.getPath(),packageName+file.getPath().substring(file.getPath().lastIndexOf("\\")).replace("\\","."));
            }

            if(file.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                className = !StringUtils.isEmpty(packageName) ? packageName + "." + className : className;
                Class<?> cls = loadClass(className,false);
                classes.add(cls);
            }
        }

    }
}
