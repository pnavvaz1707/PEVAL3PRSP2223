package peval3prsp2223;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public class GeneradorClavesSimetricasDES {
    public static byte[] generarClave() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
            SecretKey key = keyGenerator.generateKey();

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");

            DESKeySpec keySpec = (DESKeySpec) keyFactory.getKeySpec(key, DESKeySpec.class);
            return keySpec.getKey();

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

    public static Key recuperarClave(byte[] clave) {
        SecretKey key;
        try {
            DESKeySpec keySpec = new DESKeySpec(clave);

            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            key = keyFactory.generateSecret(keySpec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return key;
    }
}
