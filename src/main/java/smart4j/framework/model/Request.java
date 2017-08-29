package smart4j.framework.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;

/**
 * 请求对象
 * 封装请求信息
 *
 * Created by yang on 2017/8/26.
 */
public class Request {
    private String requestMothod;
    private String requestPath;

    public Request(String requestMothod, String requestPath) {
        this.requestMothod = requestMothod;
        this.requestPath = requestPath;
    }

    public String getRequestMothod() {
        return requestMothod;
    }

    public String getRequestPath() {
        return requestPath;
    }

    public void setRequestMothod(String requestMothod) {
        this.requestMothod = requestMothod;
    }

    public void setRequestPath(String requestPath) {
        this.requestPath = requestPath;
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this,o);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }
}
