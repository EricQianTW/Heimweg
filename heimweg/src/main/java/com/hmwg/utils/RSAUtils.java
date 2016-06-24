package com.hmwg.utils;

import com.hmwg.common.Constant;
import com.hmwg.utils.compare.RsaComparator;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by eric_qiantw on 16/5/9.
 */
public class RSAUtils {

    private static final String ALGORITHM = "RSA";

    private static final String SIGN_ALGORITHMS = "SHA1WithRSA";

    private static final String DEFAULT_CHARSET = "UTF-8";

    public static String getRSA(HashMap map){
        Object[] key = map.keySet().toArray();
        RsaComparator comparator = new RsaComparator();
        Arrays.sort(key,comparator);
        StringBuffer info = new StringBuffer();
        for(int i = 0 ;i < key.length; i++){
            if(i == 0){
                info.append(key[i] + "=" + map.get(key[i]));
            } else {
                info.append("&"+key[i] + "=" + map.get(key[i]));
            }
        }
        String result = "";
        try {
            result = URLEncoder.encode(sign(info.toString(), Constant.RSA_PRIVATE_KEY), "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String sign(String content, String privateKey) {
        try {
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(
                    Base64.decode(privateKey));
            KeyFactory keyf = KeyFactory.getInstance(ALGORITHM);
            PrivateKey priKey = keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature
                    .getInstance(SIGN_ALGORITHMS);

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
