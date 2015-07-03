package cn.limw.summer.open.push.apple;

import java.io.InputStream;

import javapns.Push;
import javapns.notification.Payload;
import cn.limw.summer.spring.util.ResourceUtil;

/**
 * @author li
 * @version 1 (2014年11月27日 下午6:13:23)
 * @since Java7
 */
public class ApplePushApiTest {
    public void push() throws Throwable {
        String message = "{\"aps\":{\"sound\":\"default\",\"badge\":5,\"alert\":\"Hello! this is a test##&&&&&&&&&&&&&!\"}}";
        InputStream keystore = ResourceUtil.classPathRead("apple-push-certificate.p12");
        boolean production = false;//开发模式
        String devices = "742787a0709a87a7ec8f384659f437849d6fbdbcd78bfc6a4987f775ba87e643";
        String password = "111111";
        Push.payload(new Payload(message) {}, keystore, password, production, devices);
    }
}