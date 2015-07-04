package builder;

import builder.util.Errors;

public class Demo {
    public static void main(String[] args) {
        // new Builder("src\\test\\resources\\builder.xml").build();
        new Builder("src\\test\\resources\\builder_test.xml").build();
    }

    public static String mysqlTypeToJavaType(String mysqlType) {
        if (mysqlType.startsWith("int")) {
            return "Integer";
        } else if (mysqlType.startsWith("varchar")) {
            return "String";
        } else if (mysqlType.startsWith("double")) {
            return "Double";
        } else {
            throw Errors.wrap(mysqlType + " 未指定对应的Java类型");
        }
    }
}