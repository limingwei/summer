package cn.limw.summer.logreader;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author li
 * @version 1 (2015年6月15日 上午9:55:23)
 * @since Java7
 */
public class LogReader {
    public static void main(String[] args) throws Throwable {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader("C:/Users/li/Desktop/新建文件夹/all.log.2015-06-14.log"))) {
            int i = 0;
            for (String line = ""; null != line; line = bufferedReader.readLine()) {
                if (line.contains("is running ips=")) {
                    // 
                } else if (line.contains("cn.limw.summer.hibernate.delegate.QueryLogger DelegateQuery.list() hql=")) {
                    // 
                } else if (line.contains("cn.limw.summer.dao.hibernate.search.SearchService search(SearchCriteria) result.list.size=")) {
                    // 
                } else if (line.contains("beanDefinitionName->")) {
                    // 
                } else if (line.contains("com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol  [DUBBO] disconected from")) {
                    // 
                } else if (line.contains("cn.limw.summer.hibernate.delegate.QueryLogger DelegateQuery.uniqueResult() hql=")) {
                    // 
                } else if (line.contains("cn.limw.summer.hibernate.delegate.QueryLogger DelegateSqlQuery.list() sql=")) {
                    // 
                } else if (line.contains("cn.limw.summer.task.fileclean.AbstractLogFileCleanTask 开始")) {
                    // 
                } else if (line.contains("cn.limw.summer.task.fileclean.AbstractLogFileCleanTask 当前时间是")) {
                    // 
                } else if (line.contains("cn.limw.summer.task.fileclean.AbstractLogFileCleanTask 结束")) {
                    // 
                } else if (line.contains("cn.limw.summer.task.fileclean.AbstractLogFileCleanTask 删除过老的日志文件")) {
                    // 
                } else if (line.contains("org.hibernate.tool.hbm2ddl.TableMetadata")) {
                    //
                } else if (line.contains("com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol  [DUBBO] Unexport service:")) {
                    // 
                } else if (line.contains("com.alibaba.dubbo.rpc.protocol.injvm.InjvmProtocol  [DUBBO] Unexport service:")) {
                    // 
                } else if (line.contains("com.alibaba.dubbo.config.AbstractConfig  [DUBBO]")) {
                    // 
                } else if (line.contains("cn.limw.summer.spring.web.servlet.mvc.handler.mapping.AtHandlerMapping Mapped")) {
                    // 
                } else if (line.contains("cn.limw.summer.spring.web.servlet.mvc.handler.mapping.ExtendableHandlerMapping Mapped")) {
                    // 
                } else if (line.contains("org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping Mapped")) {
                    // 
                } else { //
                    System.err.println((i++) + " -> " + line);
                }
            }
        }
    }
}