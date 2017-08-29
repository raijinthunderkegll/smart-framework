package smart4j.framework.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 视图类  封装返回页面信息
 *
 * Created by yang on 2017/8/27.
 */
public class View {

    private String path;

    private Map<String,Object> model;

    public View(String path) {
        this.path = path;
        this.model = new HashMap<String,Object>();
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public View addModel(String key,Object value){
        model.put(key,value);
        return this;
    }
}
