package smart4j.framework.base;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import smart4j.framework.helper.BeanHelper;
import smart4j.framework.helper.ConfigHelper;
import smart4j.framework.helper.ControllerHelper;
import smart4j.framework.model.Data;
import smart4j.framework.model.Handler;
import smart4j.framework.model.Param;
import smart4j.framework.model.View;
import smart4j.framework.util.JsonUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yang on 2017/8/27.
 */
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       //获取请求方法和路径
        String requestMethod = req.getMethod();
        String requestPath = req.getPathInfo();
        //获取对应的handler
        Handler handler = ControllerHelper.getHander(requestMethod,requestPath);

        if(handler != null){
            //获取Controller对象
            Class<?> controllerClass = handler.getControllerClass();
            Object controller = BeanHelper.getBean(controllerClass);
            //找到对应的Action方法
            Method method = handler.getActionMethod();

            //设置请求参数
            Map<String,Object> paramMap = new HashMap<String,Object>();
//            Map<String,String[]> map = req.getParameterMap();
            Enumeration<String> paramNames = req.getParameterNames();
            while(paramNames.hasMoreElements()){
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }
            //TODO BODY

            //获得param对象
            Param param = new Param(paramMap);

            //调用Action方法    判断返回值 : View或者Data
            try{
                Object result = method.invoke(controller,param.getParamMap().values());
                //如果返回值是View的实例
                if(result instanceof View){
                    View view = (View) result;
                    String path = view.getPath();
                    //如果路径以"/"开头则重定向,否则请求转发
                    if(path.startsWith("/")){
                        resp.sendRedirect(req.getContextPath() + path);
                    }else{
                        //设置model属性
                        Map<String,Object> model = view.getModel();
                        if(MapUtils.isNotEmpty(model)){
                            for(Map.Entry<String,Object> entry : model.entrySet()){
                                req.setAttribute(entry.getKey(),entry.getValue());
                            }
                        }
                        //转发
                        req.getRequestDispatcher(ConfigHelper.getAppViewPath()).forward(req,resp);
                    }
                }else if(result instanceof Data){
                    //如果返回值是Data的实例  返回jason数据
                    Data data = (Data) result;
                    Object model = data.getModel();
                    if(model != null){
                        resp.setContentType("application/json");
                        resp.setCharacterEncoding("UTF-8");
                        String json = JsonUtil.toJson(model);
                        PrintWriter writer = resp.getWriter();
                        writer.write(json);
                        writer.flush();
                        writer.close();
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //初始化所有类
        HelperLoader.init();
        //获取servletContext对象,用于注册servlet
        ServletContext servletContext = config.getServletContext();
        //注册处理JSP的servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(ConfigHelper.getAppViewPath() + "*");
        //注册处理静态资源的默认servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }
}
