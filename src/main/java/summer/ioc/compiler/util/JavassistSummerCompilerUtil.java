package summer.ioc.compiler.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import summer.aop.AopChain;
import summer.ioc.BeanDefinition;
import summer.ioc.BeanField;
import summer.util.Reflect;

/**
 * @author li
 * @version 1 (2015年10月11日 上午10:30:55)
 * @since Java7
 */
public class JavassistSummerCompilerUtil {
    public static String buildAopTypeInvokeMethodSrc(List<Method> methods) {
        String src = "public Object invoke(String methodSignature, Object[] args){";

        for (Method method : methods) {
            String methodSignature = JavassistSummerCompilerUtil.getMethodSignature(method);
            String argumentsCasted = JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes());
            String methodName = method.getName();
            String methodReturnTypeName = method.getReturnType().getName();

            src += "if(\"" + methodSignature + "\".equals(methodSignature)){";
            if ("void".equals(methodReturnTypeName)) {
                src += "super." + methodName + "(" + argumentsCasted + ");";
                src += "return null;";
            } else if ("int".equals(methodReturnTypeName)) {
                src += "return java.lang.Integer.valueOf(super." + methodName + "(" + argumentsCasted + "));";
            } else {
                src += "return super." + methodName + "(" + argumentsCasted + ");";
            }
            src += "}";
        }
        src += " return null; ";
        src += "}";

        return src;
    }

    public static String buildAopTypeCallMethodSrc(List<Method> methods) {
        String src = "public Object call(String methodSignature, Object[] args){";
        for (Method method : methods) {
            String methodSignature = JavassistSummerCompilerUtil.getMethodSignature(method);
            String argumentsCasted = JavassistSummerCompilerUtil.argumentsCasted(method.getParameterTypes());
            String methodName = method.getName();
            String methodReturnTypeName = method.getReturnType().getName();

            src += "if(\"" + methodSignature + "\".equals(methodSignature)){";
            if ("void".equals(methodReturnTypeName)) {
                src += "this." + methodName + "(" + argumentsCasted + ");";
                src += "return null;";
            } else if ("byte".equals(methodReturnTypeName)) {
                src += "return java.lang.Byte.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else if ("short".equals(methodReturnTypeName)) {
                src += "return java.lang.Short.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else if ("int".equals(methodReturnTypeName)) {
                src += "return java.lang.Integer.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else if ("long".equals(methodReturnTypeName)) {
                src += "return java.lang.Long.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else if ("double".equals(methodReturnTypeName)) {
                src += "return java.lang.Double.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else if ("float".equals(methodReturnTypeName)) {
                src += "return java.lang.Float.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else if ("boolean".equals(methodReturnTypeName)) {
                src += "return java.lang.Boolean.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else if ("char".equals(methodReturnTypeName)) {
                src += "return java.lang.Character.valueOf(this." + methodName + "(" + argumentsCasted + "));";
            } else {
                src += "return this." + methodName + "(" + argumentsCasted + ");";
            }
            src += "}";
        }
        src += " return null; ";
        src += "}";

        return src;
    }

    public static String buildOverrideAopMethodSrc(Method method) {
        Class<?>[] parameterTypes = method.getParameterTypes();

        String returnTypeName = Reflect.typeToJavaCode(method.getReturnType());
        String methodSignature = "\"" + getMethodSignature(method) + "\"";

        String src = "public " + returnTypeName + " " + method.getName() + "(" + parameters(parameterTypes) + ") {";

        String args = (0 == parameterTypes.length) ? "new Object[0]" : "new Object[] { " + argumentsPrimitived(parameterTypes) + " }";

        if ("void".equals(returnTypeName)) {
            src += "new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter();";
        } else if ("byte".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Byte)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else if ("short".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Short)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else if ("int".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Integer)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else if ("long".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Long)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else if ("double".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Double)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else if ("float".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Float)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else if ("char".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Character)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else if ("boolean".equals(returnTypeName)) {
            src += "return summer.aop.util.AopUtil.valueOf((java.lang.Boolean)new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult());";
        } else {
            src += "return (" + returnTypeName + ")new " + AopChain.class.getName() + "(this, " + methodSignature + ", " + args + ", getAopTypeMeta()).doFilter().getResult();";
        }
        src += "}";
        return src;
    }

    public static String buildCallDelegateOverrideAopMethodSrc(Method method, BeanDefinition beanDefinition, BeanField beanField) {
        Field field = Reflect.getDeclaredField(beanDefinition.getBeanType(), beanField.getName());
        Class<?> fieldType = field.getType();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String returnTypeName = Reflect.typeToJavaCode(method.getReturnType());
        String methodName = method.getName();
        String fieldTypeName = fieldType.getName();

        String src = "public " + returnTypeName + " " + methodName + "(" + parameters(parameterTypes) + "){";

        if ("void".equals(returnTypeName)) {
            src += "((" + fieldTypeName + ")getAopTypeMeta().getReferenceTarget())" + "." + methodName + "(" + argumentsOriginal(parameterTypes) + ");";
        } else {
            src += "return " + "((" + fieldTypeName + ")getAopTypeMeta().getReferenceTarget())" + "." + methodName + "(" + argumentsOriginal(parameterTypes) + ");";
        }

        src += "}";
        return src;
    }

    /**
     * 获得方法签名
     */
    public static String getMethodSignature(Method method) {
        String genericString = method.toGenericString();
        String declaringClassName = method.getDeclaringClass().getName();
        int a = genericString.indexOf(" " + declaringClassName + ".");
        int b = genericString.lastIndexOf(')');
        return genericString.substring(a + declaringClassName.length() + 2, b + 1);
    }

    /**
     * 申明形参列表
     */
    private static String parameters(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += Reflect.typeToJavaCode(parameterTypes[i]) + " _param_" + i;
        }
        return src;
    }

    /**
     * 原本的实参列表
     */
    private static String argumentsOriginal(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }
            src += " _param_" + i;
        }
        return src;
    }

    /**
     * 引用实参列表 参数名为 _param_1
     */
    private static String argumentsPrimitived(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }

            Class<?> type = parameterTypes[i];
            if (int.class.equals(type)) {
                src += "java.lang.Integer.valueOf(_param_" + i + ")";
            } else if (byte.class.equals(type)) {
                src += "java.lang.Byte.valueOf(_param_" + i + ")";
            } else if (short.class.equals(type)) {
                src += "java.lang.Short.valueOf(_param_" + i + ")";
            } else if (long.class.equals(type)) {
                src += "java.lang.Long.valueOf(_param_" + i + ")";
            } else if (double.class.equals(type)) {
                src += "java.lang.Double.valueOf(_param_" + i + ")";
            } else if (float.class.equals(type)) {
                src += "java.lang.Float.valueOf(_param_" + i + ")";
            } else if (char.class.equals(type)) {
                src += "java.lang.Character.valueOf(_param_" + i + ")";
            } else if (boolean.class.equals(type)) {
                src += "java.lang.Boolean.valueOf(_param_" + i + ")";
            } else {
                src += " _param_" + i;
            }
        }
        return src;
    }

    /**
     * 引用实参列表 参数为(String)args[]
     */
    private static String argumentsCasted(Class<?>[] parameterTypes) {
        String src = "";
        for (int i = 0; i < parameterTypes.length; i++) {
            if (i > 0) {
                src += ", ";
            }

            Class<?> type = parameterTypes[i];
            if (byte.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Byte)args[" + i + "])";
            } else if (short.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Short)args[" + i + "])";
            } else if (int.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Integer)args[" + i + "])";
            } else if (long.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Long)args[" + i + "])";
            } else if (double.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Double)args[" + i + "])";
            } else if (float.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Float)args[" + i + "])";
            } else if (char.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Character)args[" + i + "])";
            } else if (boolean.class.equals(type)) {
                src += "summer.aop.util.AopUtil.valueOf((java.lang.Boolean)args[" + i + "])";
            } else {
                src += "(" + Reflect.typeToJavaCode(type) + ")args[" + i + "]";
            }
        }
        return src;
    }
}