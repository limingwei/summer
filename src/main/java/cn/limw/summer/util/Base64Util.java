package cn.limw.summer.util;

/**
 * @author li
 * @version 1 (2015年3月12日 下午3:15:00)
 * @since Java7
 */
public class Base64Util {
    public static String urlWellEncrypt(String content) {
        return Base64Encrypter.encrypt(StringUtil.emptyWhenNull(content)).replace("+", "(plus)").replace("=", "(equal)");
    }

    public static String urlWellDecrypt(String content) {
        return Base64Encrypter.decrypt(StringUtil.emptyWhenNull(content).replace("(plus)", "+").replace("(equal)", "="));
    }

    public static void main(String args[]) {
        System.out.println(urlWellDecrypt("eyJjaGF0SWQiOjc4NCwiaGVhZFVybCI6Ijc3NDUxZGNiNzJkOTQzN2ZiMzJiODcxMmEzMGJhMGJkIiwicDJwTGljZW5zZSI6IlZGTUhUbEVjVTJZQ1BBOXpVWEpTTzFKalZTdFJmVkJoVXpnRVBRUTZVVDVSU2xNOEFWaFVHMUJoQ0hGYVhWRXFCaVVEUVFRbFVFa0NkZzlDQVRvTEpsUlNCejlSRGxNWUFuNFBaRkYwVWdkU1UxVmkiLCJwMnBTZXJ2ZXIiOiJVMWNIVGdKZEFGWUVRd2cvV3lWUWZBRmxCRHdFZndWaFVtSlFZd2t1Q3poVU53SXRBVHdIT2dRM0NUNEFaVkJpVkRVSE5BWm9CQUVIVFFCUlV3Z0ZSRk5ZQjA0Q1dnQlFCRkFJVmxzd1VId0JmZ1E2QkdNRmZsSmhVR0FKTVFzblZERUNOQUVqQnpJRU9Ra3pBR3RRWjFRd0J6ND0iLCJyZXF1ZXN0Q2xpZW50SWQiOiJsOHFhcGJubWE0b3oxNGgxNXhleHc2eWc2IiwicmVxdWVzdFRpbWUiOiIyMDE1LTA0LTI4IDE2OjIzOjIzIiwicmVxdWVzdGVySWQiOjE4NSwicmVxdWVzdGVyTmFtZSI6InpycCIsInJlcXVlc3Rlck5pY2tOYW1lIjoiIiwidHlwZSI6InJlcXVlc3RfcmVtb3RlX2Rlc2sifQ(equal)(equal)"));
    }
}