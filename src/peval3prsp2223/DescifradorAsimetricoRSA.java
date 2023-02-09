package peval3prsp2223;

import javax.crypto.*;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

public class DescifradorAsimetricoRSA {
    public static byte[] descifrarMensaje(File rutaClavePrivada, byte[] mensaje) {
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, GeneradorClavesAsimetricasRSA.recuperarClavePrivada(rutaClavePrivada));

            return cipher.doFinal(mensaje);
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
