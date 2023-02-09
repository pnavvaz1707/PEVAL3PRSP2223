package peval3prsp2223;

import javax.crypto.*;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;

public class CifradorAsimetricoRSA {

    public static byte[] cifrarMensaje(File rutaClavePublica, byte[] mensaje) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, GeneradorClavesAsimetricasRSA.recuperarClavePublica(rutaClavePublica));

            return cipher.doFinal(mensaje);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

    }
}
