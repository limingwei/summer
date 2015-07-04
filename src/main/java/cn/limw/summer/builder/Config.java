package cn.limw.summer.builder;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import cn.limw.summer.builder.model.Entity;
import cn.limw.summer.builder.model.Mould;
import cn.limw.summer.builder.util.Assert;
import cn.limw.summer.builder.util.Daos;
import cn.limw.summer.builder.util.Files;
import cn.limw.summer.builder.util.Xmls;

/**
 * Config 配置
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月8日 下午6:04:53)
 */
public class Config {
    private static final Logger log = Logger.getLogger(Config.class);

    private Map<String, Object> consts = new HashMap<String, Object>();

    private List<Mould> moulds = new ArrayList<Mould>();

    private List<Entity> entities = new ArrayList<Entity>();

    public Config(String confilgFile) {
        Document document = Xmls.build(new ByteArrayInputStream(Files.read(confilgFile).getBytes()));

        // 读取常量
        this.consts.putAll(Xmls.attributes(Assert.noNull(Xmls.node(document, "//consts"), "配置文件缺少/builder/consts结点")));
        log.info("读取常量完成 共 " + this.consts.size() + " 个");

        String db_url = Assert.noNull((String) consts.get("db_url"), "必须配置/builder/consts@db_url");
        String db_user = Assert.noNull((String) consts.get("db_user"), "必须配置/builder/consts@db_user");
        String db_password = Assert.noNull((String) consts.get("db_password"), "必须配置/builder/consts@db_password");

        // 读取模版配置
        NodeList mould_nodes = Xmls.nodes(document, "//mould");
        for (int length = mould_nodes.getLength(), i = 0; i < length; i++) {
            Node mould_node = mould_nodes.item(i);
            Mould mould = new Mould();
            mould.setMap(Xmls.attributes(mould_node));
            this.moulds.add(mould);
        }
        log.info("读取模版完成 共 " + this.moulds.size() + " 个");

        // 读取对象配置
        NodeList entity_nodes = Xmls.nodes(document, "//entity");
        for (int length = (null == entity_nodes ? -1 : entity_nodes.getLength()), i = 0; i < length; i++) {
            Node entity_node = entity_nodes.item(i);
            Entity entity = new Entity();
            entity.setMap(Xmls.attributes(entity_node));

            String _table = Assert.noNull((String) entity.get("table"), "缺少表名配置/builder/entities/entity@table");
            Connection connection = Daos.getConnection(db_url, db_user, db_password);
            entity.setFields(Daos.getFieldsOfTable(connection, _table));
            this.entities.add(entity);
        }
        log.info("读取对象完成 共 " + this.entities.size() + " 个");
    }

    public List<Entity> getEntities() {
        return Assert.noEmpty(this.entities, "没有配置/builder/entities/entity");
    }

    public List<Mould> getMoulds() {
        return Assert.noEmpty(this.moulds, "没有配置/builder/moulds/mould");
    }

    public Map<String, Object> getMap() {
        return this.consts;
    }
}