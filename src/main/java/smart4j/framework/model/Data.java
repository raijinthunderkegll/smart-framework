package smart4j.framework.model;

/**
 * 数据类,封装返回数据结果
 *
 * Created by yang on 2017/8/27.
 */
public class Data {

    private Object model;

    public Object getModel() {
        return model;
    }

    public Data(Object model) {

        this.model = model;
    }
}
