package cn.limw.summer.java.collection;

import java.util.List;

import org.slf4j.Logger;

import cn.limw.summer.java.collection.wrapper.ListWrapper;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Jsons;
import cn.limw.summer.util.Logs;

/**
 * @author li
 * @version 1 (2015年5月22日 上午10:49:58)
 * @since Java7
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class NiceToStringList extends ListWrapper {
    private static final long serialVersionUID = -4107198140825299409L;

    private static final Logger log = Logs.slf4j();

    public NiceToStringList(List list) {
        super(list);
    }

    public NiceToStringList(Object[] array) {
        this(ArrayUtil.asList(array));
    }

    public String toString() {
        try {
            return Jsons.toJson(this);
        } catch (Exception e) {
            log.error("" + e);
            return super.toString();
        }
    }
}