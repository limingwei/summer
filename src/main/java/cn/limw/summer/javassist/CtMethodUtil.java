package cn.limw.summer.javassist;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import cn.limw.summer.util.StringUtil;

/**
 * @author li
 * @version 1 (2014年11月25日 上午11:56:26)
 * @since Java7
 */
@SuppressWarnings("unchecked")
public class CtMethodUtil {
    public static final String $_PARAMETER_PREFIX = "$";

    public static String returnNullStatement(String returnType) {
        if ("void".equals(returnType)) {
            return "";
        } else if ("int".equals(returnType)) {
            return " return 0; ";
        } else if ("boolean".equals(returnType)) {
            return " return false; ";
        } else if ("char".equals(returnType)) {
            return " return '\\\u0000'; ";
        } else if ("double".equals(returnType)) {
            return " return 0.0d; ";
        } else if ("float".equals(returnType)) {
            return " return 0.0f; ";
        } else if ("long".equals(returnType)) {
            return " return 0L; ";
        } else if ("short".equals(returnType)) {
            return " return (short)0; ";
        } else if ("byte".equals(returnType)) {
            return " return (byte)0; ";
        } else {
            return " return null; ";
        }
    }

    public static String getReturnValue(String returnType) {
        return getReturnValue(Collections.EMPTY_MAP, returnType);
    }

    public static String getReturnValue(Map<String, String> returnValueMap, String returnType) {
        if ("void".equals(returnType)) {
            return " /* return void; */ ";
        } else {
            String value = returnValueMap.get(returnType);
            if (StringUtil.isEmpty(value)) {
                return " return null; ";
            } else {
                return " return " + value + "; ";
            }
        }
    }

    public static CtClass[] getExceptionTypes(CtMethod ctMethod) {
        try {
            return ctMethod.getExceptionTypes();
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static CtClass[] getParameterTypes(CtMethod ctMethod) {
        try {
            return ctMethod.getParameterTypes();
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static CtClass getReturnType(CtMethod ctMethod) {
        try {
            return ctMethod.getReturnType();
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<Integer, String> getParameterNames(CtMethod ctMethod) {
        MethodInfo methodInfo = ctMethod.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute localVariableAttribute = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        int len = getParameterTypes(ctMethod).length;
        Map<Integer, String> parameterNames = new HashMap<Integer, String>();
        for (int j = 0; j < len; j++) {
            parameterNames.put(new Integer(j + 1), localVariableAttribute.variableName(j + 1));//第一个是this
        }
        return parameterNames;
    }

    public static Map<Integer, String> getParameterNames(Method method) {
        return getParameterNames(getCtMethod(method));
    }

    public static CtMethod getCtMethod(Method method) {
        CtMethod[] ctMethods = ClassPoolUtil.get(method.getDeclaringClass().getName()).getMethods();
        for (CtMethod ctMethod : ctMethods) {
            if (ctMethod.getName().equals(method.getName())) {
                CtClass[] ctTypes = getParameterTypes(ctMethod);
                Class<?>[] types = method.getParameterTypes();
                if (ctTypes.length == types.length) {
                    boolean parameterTypesMatch = true;
                    for (int i = 0; i < types.length; i++) {
                        if (!ctTypes[i].getName().equals(types[i].getName())) {
                            parameterTypesMatch = false;
                            break;
                        }
                    }
                    if (parameterTypesMatch) {
                        return ctMethod;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 得到参数应引用列表
     */
    public static String getParameters(CtMethod ctMethod) {
        StringBuffer parameterBuffer = new StringBuffer();
        CtClass[] parameters = CtMethodUtil.getParameterTypes(ctMethod);
        for (int i = 0; i < parameters.length; i++) {
            CtClass parameter = parameters[i];
            String parameterType = parameter.getName();
            String refName = $_PARAMETER_PREFIX + (i + 1); //动态指定方法参数的变量名  
            if (i != parameters.length - 1)
                parameterBuffer.append(parameterType).append(" " + refName).append(",");
            else
                parameterBuffer.append(parameterType).append(" " + refName);
        }
        return parameterBuffer.toString();
    }

    /**
     * 生成抛出异常代码
     */
    public static String getExceptions(CtMethod ctMethod) { //组装方法的Exception声明  
        StringBuffer exceptionBuffer = new StringBuffer();
        CtClass[] exceptionTypes = CtMethodUtil.getExceptionTypes(ctMethod);
        if (exceptionTypes.length > 0)
            exceptionBuffer.append(" throws ");
        for (int i = 0; i < exceptionTypes.length; i++) {
            if (i != exceptionTypes.length - 1) {
                exceptionBuffer.append(exceptionTypes[i].getName()).append(",");
            } else {
                exceptionBuffer.append(exceptionTypes[i].getName());
            }
        }
        return exceptionBuffer.toString();
    }
}