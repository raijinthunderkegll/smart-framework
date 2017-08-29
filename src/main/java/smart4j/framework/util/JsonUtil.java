package smart4j.framework.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Json工具类
 * Created by yang on 2017/8/29.
 */
public class JsonUtil {

    private final static Logger LOGGER = LoggerFactory.getLogger(JsonUtil.class);

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将POJO转换成JSON
     */
    public static <T> String toJson(T obj){
        String json = null;
        try {
            json = OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("transfer object to json failure",e);
            throw new RuntimeException(e);
        }
        return json;
    }

    /**
     * 将JSON转换成POJO
     */
    public static <T> T fromJson(String json,Class<T> type){
        T pojo = null;
        try {
            pojo = OBJECT_MAPPER.readValue(json,type);
        } catch (IOException e) {
            LOGGER.error("transfer json to object failure",e);
            throw new RuntimeException(e);
        }
        return pojo;
    }
}
