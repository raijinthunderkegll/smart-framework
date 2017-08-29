package smart4j.framework.model;

import java.util.Map;

/**
 * 参数类 封装request的参数
 *
 * Created by yang on 2017/8/27.
 */
public class Param {

    private Map<String,Object> paramMap;

    public Map<String, Object> getParamMap() {
        return paramMap;
    }

    public Param(Map<String, Object> paramMap) {

        this.paramMap = paramMap;
    }
}
