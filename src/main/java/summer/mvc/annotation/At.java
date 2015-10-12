package summer.mvc.annotation;

/**
 * @author li
 * @version 1 (2015年10月9日 下午3:43:38)
 * @since Java7
 */
public @interface At {
    public static final String GET = "GET";

    public static final String POST = "POST";

    String[] value();

    String[] method() default { GET, POST };
}