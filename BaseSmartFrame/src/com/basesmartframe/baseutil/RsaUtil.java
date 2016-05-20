package com.basesmartframe.baseutil;

import android.util.Base64;

import com.basesmartframe.log.L;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by xieningtao on 16-5-17.
 */
public class RsaUtil {
    private static final String TAG = RsaUtil.class.getName();

    public static final String new_pu = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCO+LfTrG"
            + "SiMOgMtUQ8GkNsCMsZCiZQOL3hDkAf12S8lMRaK3Vdmi50VKy5/SdQTGLV9Qd98R5IVggJrg4q9/9"
            + "npn6mnV8PN4UXJ6MbWwUb/23zNWJtqxiyQeYnaCLA3bu0R9po5S7axT1jO5kFZuSwUiV2faSt4Ymk"
            + "q/I3kZsxtwIDAQAB";

    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnEKB4xIxVIIG6lj9i5Rk0gxp5" +
            "mHvnG66Z4rXU0O6+ODyu7RXfJibtHBhusxquk4z43fAyPATK4Jwazu4eqAF9eno6" +
            "1pqGdDxU6aMOoazaGq+MO1p/jTAWilYB8NAdjPSBiqFk3ipy9j0YPdawCVpOkOx6" +
            "xo/qu5PA010ToqrEHQIDAQAB";

    public static final String PUBLIC_STANDARD_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCfRTdcPIH10gT9f31rQuIInLwe" +
            "7fl2dtEJ93gTmjE9c2H+kLVENWgECiJVQ5sonQNfwToMKdO0b3Olf4pgBKeLThra" +
            "z/L3nYJYlbqjHC3jTjUnZc0luumpXGsox62+PuSGBlfb8zJO6hix4GV/vhyQVCpG" +
            "9aYqgE7zyTRZYX9byQIDAQAB";
    

    public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKcQoHjEjFUggbqW"
            + "P2LlGTSDGnmYe+cbrpnitdTQ7r44PK7tFd8mJu0cGG6zGq6TjPjd8DI8BMrgnBrO"
            + "7h6oAX16ejrWmoZ0PFTpow6hrNoar4w7Wn+NMBaKVgHw0B2M9IGKoWTeKnL2PRg9"
            + "1rAJWk6Q7HrGj+q7k8DTXROiqsQdAgMBAAECgYBsH3+sCZDFO1+akcj+SUWRzdPh"
            + "pIV0Il9TKv4BeSM6qwzjLX/9RyTkSk2pv3LiT45qNsFQ0bVMVCfT1d8YqiUjs4OW"
            + "0z3WpRZ+qT7eMWq0YGWkqg7c4ET+yb6+cxR9nSt0jVz1Z2MPBUTVcHTF6p2V39lM"
            + "0IKJyTHKkyjBTjSHhQJBANoFGWxRkclWQLXVvZTAGwLjzld8nHO364SfVypKuYlc"
            + "yjwMv4RqtAo6bgKLQtkwF9sWLT8tythH/kQ4+WlMh8MCQQDEKx60ylwU/d9tuoBA"
            + "0crvJupIo9ZMFCVO5Flw1wxUtGSScR2gPfW2DAcnawnrFBDuTJKNmQv3SdR6qQWL"
            + "3qafAkEAtUN4F67Z3uNveb7zmDEARC7S57oa+br4fZNdDTSDfck/+x0+PgHZHofl"
            + "3weZ1Kk/rE+L3vNbquZwaJhWBU4zsQJAcTatlwt+z0KhcxOCt2Ycp58e8WNU5z1t"
            + "NrXFBMGnxS8DKB0OSw/XQgR5EH/PDpPbHZylrADbDhHXBh+MKugJ4wJAZGjXzRWl"
            + "4eP9ScAxWoOES/tuzK7QWWt+MPs6B6vAAL9cP7xdUsOz32m1eXfvbRzfHfmJDwoU"
            + "bOqaU1T+92eR6Q=="; // 私钥

    public static final String PRIVATE_STANDARD_KEY =
            "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJ9FN1w8gfXSBP1/" +
                    "fWtC4gicvB7t+XZ20Qn3eBOaMT1zYf6QtUQ1aAQKIlVDmyidA1/BOgwp07Rvc6V/" +
                    "imAEp4tOGtrP8vedgliVuqMcLeNONSdlzSW66alcayjHrb4+5IYGV9vzMk7qGLHg" +
                    "ZX++HJBUKkb1piqATvPJNFlhf1vJAgMBAAECgYA736xhG0oL3EkN9yhx8zG/5RP/" +
                    "WJzoQOByq7pTPCr4m/Ch30qVerJAmoKvpPumN+h1zdEBk5PHiAJkm96sG/PTndEf" +
                    "kZrAJ2hwSBqptcABYk6ED70gRTQ1S53tyQXIOSjRBcugY/21qeswS3nMyq3xDEPK" +
                    "XpdyKPeaTyuK86AEkQJBAM1M7p1lfzEKjNw17SDMLnca/8pBcA0EEcyvtaQpRvaL" +
                    "n61eQQnnPdpvHamkRBcOvgCAkfwa1uboru0QdXii/gUCQQDGmkP+KJPX9JVCrbRt" +
                    "7wKyIemyNM+J6y1ZBZ2bVCf9jacCQaSkIWnIR1S9UM+1CFE30So2CA0CfCDmQy+y" +
                    "7A31AkB8cGFB7j+GTkrLP7SX6KtRboAU7E0q1oijdO24r3xf/Imw4Cy0AAIx4KAu" +
                    "L29GOp1YWJYkJXCVTfyZnRxXHxSxAkEAvO0zkSv4uI8rDmtAIPQllF8+eRBT/deD" +
                    "JBR7ga/k+wctwK/Bd4Fxp9xzeETP0l8/I+IOTagK+Dos8d8oGQUFoQJBAI4Nwpfo" +
                    "MFaLJXGY9ok45wXrcqkJgM+SN6i8hQeujXESVHYatAIL/1DgLi+u46EFD69fw0w+" +
                    "c7o0HLlMsYPAzJw="; // 私钥

    private static final String RSA = "RSA";

    public static byte[] encodeData(byte[] data, String pubkey_str) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            byte key[] = Base64.decode(pubkey_str, Base64.DEFAULT);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
            PublicKey publicKey = keyFactory.generatePublic(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        }
        return null;
    }

    public static byte[] decodeData(byte[] data, String private_key_str) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            byte key[] = Base64.decode(private_key_str, Base64.DEFAULT);
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(key);
            PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (InvalidKeyException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
            L.error(TAG, "method->encodeData,exception: " + e.getMessage());
        }
        return null;
    }
}
