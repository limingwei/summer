package cn.limw.summer.fastjson;

import cn.limw.summer.util.ListUtil;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author li
 * @version 1 (2015年7月1日 下午6:18:27)
 * @since Java7
 */
public class FastJson {
    private SerializeWriter serializeWriter = new SerializeWriter();

    private JSONSerializer jsonSerializer = new JSONSerializer(serializeWriter);

    public FastJson setDateFormat(String string) {
        jsonSerializer.config(SerializerFeature.WriteDateUseDateFormat, true);
        jsonSerializer.setDateFormat("yyyy-MM-dd HH:mm:ss");
        return this;
    }

    public FastJson addPropertyFilters(PropertyFilter... propertyFilters) {
        jsonSerializer.getPropertyFilters().addAll(ListUtil.asList(propertyFilters));
        return this;
    }

    public String toJson(Object data) {
        jsonSerializer.write(data);
        return serializeWriter.toString();
    }
}