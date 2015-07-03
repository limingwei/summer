package cn.limw.summer.spring.web.view.json;

import cn.limw.summer.spring.web.view.text.TextView;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.web.ContentTypes;

/**
 * @author li
 * @version 1 (2015年1月27日 下午3:55:03)
 * @since Java7
 */
public class JsonView extends TextView {
    public JsonView(Object data) {
        super(Jsons.toJson(data), ContentTypes.APPLICATION_JSON_CHARSET_UTF_8);
    }
}