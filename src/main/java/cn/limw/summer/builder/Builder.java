package cn.limw.summer.builder;

import java.io.File;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.limw.summer.builder.model.Entity;
import cn.limw.summer.builder.model.Mould;
import cn.limw.summer.builder.util.Assert;
import cn.limw.summer.builder.util.Files;
import cn.limw.summer.builder.util.Fms;

/**
 * Builder 入口
 * 
 * @author li (limingwei@mail.com)
 * @version 1 (2014年1月7日 上午9:38:35)
 */
public class Builder {
    private static final String DEFAULT_CONFILG_FILE = "builder.xml";

    private static final Logger log = Logger.getLogger(Builder.class);

    private Config config;

    private Writer writer;

    public Builder() {
        this.config = new Config(DEFAULT_CONFILG_FILE);
    }

    public Builder(String confilgFile) {
        this.config = new Config(confilgFile);
    }

    public void build() {
        log.info("开始 ^_^");
        List<Mould> moulds = config.getMoulds();
        List<Entity> entities = config.getEntities();
        for (Entity entity : entities) {
            for (Mould mould : moulds) {
                writeMouldOfEntity(mould, entity);
            }
        }
        log.info("搞定 ^_^");
    }

    private void writeMouldOfEntity(Mould mould, Entity entity) {
        Map<String, Object> data = getData(mould, entity);// 合并后的所有数据

        String _mould_name = Assert.noNull((String) mould.get("name"), "缺少模版名称配置/builder/moulds/mould@name");
        String mouldName = Fms.merge(_mould_name, data);// 替换变量后的模版路径
        String mouldSource = Files.read(mouldName);// 读取模版内容

        String _mould_out = Assert.noNull((String) mould.get("out"), "缺少模版输出路径配置/builder/moulds/mould@out");
        String outPut = Fms.merge(_mould_out, data);// 替换变量后的输出路径

        String overwrite = (String) data.get("overwrite");
        Writer writer = getWriter(outPut, overwrite);

        Fms.write(mouldName, mouldSource, data, writer);
    }

    private Map<String, Object> getData(Mould mould, Entity entity) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.putAll(config.getMap());
        data.put("mould", mould);
        data.put("entity", entity);
        data.put("now", new Date());
        return data;
    }

    private Writer getWriter(String outPut, String overwrite) {
        if (null != this.writer) {
            return this.writer;// 可以用setWriter传入System.out以在控制台预览输出内容
        } else {
            File file = new File(outPut);
            String canonicalPath = Files.getCanonicalPath(file);
            Boolean fileExists = file.exists();

            if (!fileExists || "true".equalsIgnoreCase(overwrite)) {
                log.info((fileExists ? "覆盖文件 " : "生成文件 ") + canonicalPath);// 覆盖
                return Files.newWriter(file);
            } else if (fileExists && "backup".equalsIgnoreCase(overwrite)) {
                log.info("备份文件 " + canonicalPath + " 已经存在 将旧文件备份 并生成新文件");// 备份旧文件
                return Files.newWriter(Files.backup(file));
            } else if (fileExists && "rename".equalsIgnoreCase(overwrite)) {
                log.info("重命名文件 " + canonicalPath + " 已经存在 生成新文件并重命名");// 新文件重命名
                return Files.newWriter(Files.rename(file));
            } else {
                log.info("跳过文件 " + canonicalPath + " 已经存在 配置不允许覆盖");// 默认不覆盖,跳过
                return Files.nullWriter();
            }
        }
    }

    public Builder setWriter(OutputStream writer) {
        this.writer = new OutputStreamWriter(writer);
        return this;
    }
}