package cn.limw.summer.spring.web.servlet.config.interceptor;

import java.util.List;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.parsing.BeanComponentDefinition;
import org.springframework.beans.factory.parsing.CompositeComponentDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.springframework.web.servlet.handler.MappedInterceptor;
import org.w3c.dom.Element;

/**
 * @author li
 * @version 1 (2015年6月5日 上午10:04:33)
 * @since Java7
 * @see org.springframework.web.servlet.config.InterceptorsBeanDefinitionParser
 */
public class AbstractInterceptorsBeanDefinitionParser implements BeanDefinitionParser {
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        CompositeComponentDefinition compDefinition = new CompositeComponentDefinition(element.getTagName(), parserContext.extractSource(element));
        parserContext.pushContainingComponent(compDefinition);

        RuntimeBeanReference pathMatcherRef = null;
        if (element.hasAttribute("path-matcher")) {
            pathMatcherRef = new RuntimeBeanReference(element.getAttribute("path-matcher"));
        }

        List<Element> interceptors = DomUtils.getChildElementsByTagName(element, "bean", "ref", "interceptor");
        for (Element interceptor : interceptors) {
            RootBeanDefinition mappedInterceptorDef = new RootBeanDefinition(MappedInterceptor.class);
            mappedInterceptorDef.setSource(parserContext.extractSource(interceptor));
            mappedInterceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);

            ManagedList<String> includePatterns = null;
            ManagedList<String> excludePatterns = null;
            Object interceptorBean;
            if ("interceptor".equals(interceptor.getLocalName())) {
                includePatterns = getIncludePatterns(interceptor, "mapping");
                excludePatterns = getIncludePatterns(interceptor, "exclude-mapping");
                Element beanElem = DomUtils.getChildElementsByTagName(interceptor, "bean", "ref").get(0);
                interceptorBean = parserContext.getDelegate().parsePropertySubElement(beanElem, null);
            }
            else {
                interceptorBean = parserContext.getDelegate().parsePropertySubElement(interceptor, null);
            }
            mappedInterceptorDef.getConstructorArgumentValues().addIndexedArgumentValue(0, includePatterns);
            mappedInterceptorDef.getConstructorArgumentValues().addIndexedArgumentValue(1, excludePatterns);
            mappedInterceptorDef.getConstructorArgumentValues().addIndexedArgumentValue(2, interceptorBean);

            if (pathMatcherRef != null) {
                mappedInterceptorDef.getPropertyValues().add("pathMatcher", pathMatcherRef);
            }

            String beanName = parserContext.getReaderContext().registerWithGeneratedName(mappedInterceptorDef);
            parserContext.registerComponent(new BeanComponentDefinition(mappedInterceptorDef, beanName));
        }

        parserContext.popAndRegisterContainingComponent();
        return null;
    }

    private ManagedList<String> getIncludePatterns(Element interceptor, String elementName) {
        List<Element> paths = DomUtils.getChildElementsByTagName(interceptor, elementName);
        ManagedList<String> patterns = new ManagedList<String>(paths.size());
        for (Element path : paths) {
            patterns.add(path.getAttribute("path"));
        }
        return patterns;
    }
}