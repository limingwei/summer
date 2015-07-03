package cn.limw.summer.spring.beans.factory.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javassist.CtMethod;

import org.slf4j.Logger;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.ManagedMap;

import cn.limw.summer.javassist.CtMethodUtil;
import cn.limw.summer.spring.beans.util.PropertyValueUtil;
import cn.limw.summer.util.ArrayUtil;
import cn.limw.summer.util.Logs;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年12月4日 下午1:17:53)
 * @since Java7
 */
public class MockImplementationUtil {
    private static final Logger log = Logs.slf4j();

    /**
     * 生成一个接口方法的实现代码
     */
    public static String methodCode(CtMethod ctMethod, List<PropertyValue> propertyValues) {
        String returnType = CtMethodUtil.getReturnType(ctMethod).getName();
        String methodName = ctMethod.getName();
        String parameters = CtMethodUtil.getParameters(ctMethod);
        String exceptions = CtMethodUtil.getExceptions(ctMethod);

        String src = " /* mock inplementation method */ public " + returnType + " " + methodName + "(" + parameters + ") " + exceptions + " { ";//
        src += logParameters(ctMethod);
        src += returnValue(ctMethod, propertyValues);
        src += " } ";
        log.info(src);
        return src;
    }

    /**
     * 日志打印所有参数
     */
    public static String logParameters(CtMethod ctMethod) {
        int parameterTypeLength = CtMethodUtil.getParameterTypes(ctMethod).length;
        String src;
        if (parameterTypeLength > 0) {
            src = " log.info(\"MockImplementationMethodLog " + ctMethod.getName() + "() " + StringUtil.dup(" {} ", parameterTypeLength) + "\", new Object[]{" + StringUtil.join(", ", CtMethodUtil.$_PARAMETER_PREFIX, "", ArrayUtil.sequence(1, parameterTypeLength)) + "}); ";
        } else {
            src = " log.info(\"MockImplementationMethodLog " + ctMethod.getName() + "()\"); ";
        }
        return src;//;
    }

    /**
     * 返回空
     * @param propertyValues
     */
    public static String returnValue(CtMethod ctMethod, List<PropertyValue> propertyValues) {
        PropertyValue propertyValue = PropertyValueUtil.getPropertyValue(propertyValues, "returnValueForType");
        Map<String, String> returnValueMap = returnValueMap(propertyValue);

        String returnType = CtMethodUtil.getReturnType(ctMethod).getName();
        return CtMethodUtil.getReturnValue(returnValueMap, returnType);
    }

    private static Map<String, String> returnValueMap(PropertyValue propertyValue) {
        if (null != propertyValue) {
            Map<String, String> map = new HashMap<String, String>();
            ManagedMap<TypedStringValue, TypedStringValue> managedMap = (ManagedMap<TypedStringValue, TypedStringValue>) propertyValue.getValue();
            for (Entry<TypedStringValue, TypedStringValue> entry : managedMap.entrySet()) {
                map.put(entry.getKey().getValue(), entry.getValue().getValue());
            }
            return map;
        } else {
            return new HashMap<String, String>();
        }
    }
}