package com.sf.pay.ali;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

public class SignUtils {

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    // 商户PID
    public static final String PARTNER = "2088911959072533";

    // 商户收款账号
    public static final String SELLER = "betttong@myantu.com";

    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN0DeHLZJZobil4UWtGvCnM4RKAywP+QYfblBShyghGST5grb6Q0sZzg5ygBkubc4SWEoZPo6W6dUirG55dBf+FnPpSiBOOLXg9grPlKeDd0U3c472dr+x2qRiTCAOpT8OR1tqqOPmd0KgR6Vr6gA8xUR20vQkFvmrtEJmv0p9EvAgMBAAECgYB2K6HePm6VfH46GUYJ8T50JrTCtwBsZOWMXh2o57jV7lXydd8GfEovKC1QPa8vExCqv8So0hLl2uYRCzLOs+3MN8lI4FY0G9dHprpkJVuwnwt5aogL7Tzplbz7+95m6i5xDC6P1S1lSgBdrl090h74HwHgiZ21RJPT9BkLvp4MKQJBAO/ZfgmdItP4L0fjuvY3LFGF4uEk6cdMJitF9K3nHUBx12mg1VtGsqJRea13jEb9OrerQ9/sV69lRtYBopuAGvUCQQDr5UjJ17H5HhBURN812ywug3AE0QLvKMMJRacXA1DNLRJceRpZhotksYoK3mvA5mg/NM+BGbUaiT9kJkNvze0TAkAphRv1bBdIXHFK35hQ4RIx9pxk3y/9CSieWxjVNenxawOgTHBCwcVQpgwLRGOrMlEE1IgGbnJrcXvGtjfA9Xc1AkAuuzpjOga42jrT5tztN2EWSdWMLrfNFx8kHBdQ2MhODSrBBQCyUZQVu5IkOxHPohVuyBB/e89D1YKoXWgoovvfAkEAylqDDEC8Q/Va5sWdDqqVp2uY/KJpfMigne0skkcMyusMwY72sji533JId5dT52JWUgZ7sbCaW5uML5lhP2ZK2w==";

    // 支付宝公钥
    // public static final String RSA_PUBLIC =
    // "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDdA3hy2SWaG4peFFrRrwpzOESgMsD/kGH25QUocoIRkk+YK2+kNLGc4OcoAZLm3OElhKGT6OlunVIqxueXQX/hZz6UogTji14PYKz5Sng3dFN3OO9na/sdqkYkwgDqU/Dkdbaqjj5ndCoEela+oAPMVEdtL0JBb5q7RCZr9KfRLwIDAQAB";

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update(content.getBytes(DEFAULT_CHARSET));

            byte[] signed = signature.sign();

            return Base64.encode(signed);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
