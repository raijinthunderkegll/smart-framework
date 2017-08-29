package smart4j.framework.helper;

import org.apache.commons.collections4.CollectionUtils;
import smart4j.framework.annotation.Action;
import smart4j.framework.model.Handler;
import smart4j.framework.model.Request;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器帮助类
 *
 * <p>
 * Created by yang on 2017/8/26.
 */
public class ControllerHelper {

    /**
     * 用来存放请求与适配器映射关系,获取handler
     */
    private static final Map<Request,Handler> ACTION_MAP = new HashMap<Request, Handler>();

    //初始化
    static {
        Set<Class<?>> controllerSet = ClassHelper.getController();
        if (!CollectionUtils.isEmpty(controllerSet)) {
            //遍历controller集合  获取Method[]
            for (Class<?> controllerClass : controllerSet) {
                Method[] methods = controllerClass.getDeclaredMethods();
                //找到方法中带有Action注解的方法
                for (Method method : methods) {
                    if(method.isAnnotationPresent(Action.class)){
                        //获取Action对象的url  并获取请求路径与请求方法
                        Action action = method.getAnnotation(Action.class);
                        String urlMapping = action.value();
                        if(urlMapping.matches("\\w+:/\\w+")){//如果满足请求格式xxx:/xxx
                            String[] strs = urlMapping.split(":");
                            String requestMethod = strs[0];
                            String requestUrl = strs[1];
                            Request request = new Request(requestMethod,requestUrl);
                            Handler handler = new Handler(controllerClass,method);
                            ACTION_MAP.put(request,handler);
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取handler
     */
    public static Handler getHander(String reqeustMethod,String reqeustUrl){
        Request request = new Request(reqeustMethod,reqeustUrl);
        return ACTION_MAP.get(request);
    }
}
