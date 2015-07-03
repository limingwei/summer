package cn.limw.summer.web.wro4j.freemarker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import cn.limw.summer.util.Files;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;
import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * @author lgb
 * @version 1 (2014年9月28日下午6:26:02)
 * @since Java7
 */
public class DynamicImportJsOrCssDirective implements TemplateDirectiveModel, ApplicationContextAware {
    private static final Logger log = Logs.slf4j();

    private static ConcurrentMap<String, String> staticFileCache = new ConcurrentHashMap<>();

    private static AtomicBoolean initialized = new AtomicBoolean(false);

    private static final String WRO_PATH = "/source/wro/";

    protected static final String JS_SCRIPT = "<script type=\"text/javascript\" src=\"%s\"></script> <!-- by wro --> \n";

    protected static final String CSS_SCRIPT = "<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"%s\"> <!-- by wro --> \n";

    private ApplicationContext context;

    public ApplicationContext getContext() {
        return context;
    }

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.context = context;
    }

    public void dynamicImportJsOrCssDirectiveExecute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) {
        try {
            String result = "";
            String type = null;
            String filePath = null;
            if (params.get("type") != null) {
                type = ((SimpleScalar) params.get("type")).getAsString();
            }
            if (params.get("filePath") != null) {
                filePath = ((SimpleScalar) params.get("filePath")).getAsString();
            }
            if (!StringUtil.isEmpty(type) && !StringUtil.isEmpty(filePath)) {
                String cacheKey = buildCacheKey(filePath, type);
                String staticFile = staticFileCache.get(cacheKey);
                if (staticFile == null) {
                    log.error("加载文件失败,缓存中找不到对应的文件 filePath={}, type={}, cacheKey={}, staticFileCache={}", filePath, type, cacheKey, staticFileCache);
                    result = "<!-- 加载文件失败,缓存中找不到对应的文件 filePath=" + filePath + ", type=" + type + ", cacheKey=" + cacheKey + ", staticFileCache=" + staticFileCache + " -->";
                } else {
                    if (staticFile.startsWith("/")) {
                        staticFile = staticFile.substring(1);
                    }

                    if ("css".equals(type)) {
                        result = String.format(CSS_SCRIPT, staticFile);
                    } else if ("js".equals(type)) {
                        result = String.format(JS_SCRIPT, staticFile);
                    }
                }
            }
            env.getOut().write(result);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
        dynamicImportJsOrCssDirectiveExecute(env, params, loopVars, body);
    }

    public void init() throws IOException {
        if (initialized.compareAndSet(false, true)) {
            File wroDirectory = getWroDirectory();
            if (!wroDirectory.exists() || !wroDirectory.isDirectory()) {
                log.error("查找静态资源的时候发现对应目录不存在 {}", wroDirectory.getAbsolutePath());
                throw new FileNotFoundException(Files.getCanonicalPath(wroDirectory));
            }
            //将wro目录下已有文件加入缓存  
            for (File file : wroDirectory.listFiles()) {
                handleNewFile(file);
            }
            //监控wro目录,如果有文件生成,则判断是否是较新的文件,是的话则把文件名加入缓存  
            new Thread(new WroFileWatcher(wroDirectory.getAbsolutePath())).start();
        }
    }

    public File getWroDirectory() throws IOException {
        Resource resource = getContext().getResource(WRO_PATH);
        File wroDirectory = resource.getFile();
        return wroDirectory;
    }

    private static void handleNewFile(File file) {
        String fileName = file.getName();
        Pattern p = Pattern.compile("^(\\w+)\\-\\d+\\.(js|css)$");
        Matcher m = p.matcher(fileName);
        if (!m.find() || m.groupCount() < 2) {
            return;
        }
        String fakeName = m.group(1);
        String fileType = m.group(2);
        String key = buildCacheKey(WRO_PATH + fakeName, fileType);
        if (staticFileCache.putIfAbsent(key, WRO_PATH + fileName) != null) {
            synchronized (staticFileCache) {
                String cachedFileName = staticFileCache.get(key);
                if ((WRO_PATH + fileName).compareTo(cachedFileName) > 0) {
                    staticFileCache.put(key, WRO_PATH + fileName);
                } else {
                    file.delete();
                }
            }
        }
    }

    private static String buildCacheKey(String fakeName, String fileType) {
        return fakeName + "-" + fileType;
    }

    static class WroFileWatcher implements Runnable {
        private String wroAbsolutePathStr;

        public WroFileWatcher(String wroPathStr) {
            this.wroAbsolutePathStr = wroPathStr;
        }

        public void run() {
            Path path = Paths.get(wroAbsolutePathStr);
            File wroDirectory = path.toFile();
            if (!wroDirectory.exists() || !wroDirectory.isDirectory()) {
                log.error("监控wro目录的时候发现对应目录不存在 {}", wroAbsolutePathStr);
            }
            log.warn("开始监控wro目录 {}", wroAbsolutePathStr);
            try {
                WatchService watcher = FileSystems.getDefault().newWatchService();
                path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE);
                while (true) {
                    WatchKey key = null;
                    try {
                        key = watcher.take();
                    } catch (InterruptedException e) {
                        log.error("InterruptedException", e);
                        continue;
                    }
                    for (WatchEvent<?> event : key.pollEvents()) {
                        if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
                            continue;
                        }
                        WatchEvent<Path> e = (WatchEvent<Path>) event;
                        Path filePath = e.context();
                        handleNewFile(filePath.toFile());
                    }
                    if (!key.reset()) {
                        break;
                    }
                }
            } catch (IOException e) {
                log.error("监控wro目录发生错误", e);
            }
            log.warn("停止监控wro目录 {}", wroAbsolutePathStr);
        }
    }
}