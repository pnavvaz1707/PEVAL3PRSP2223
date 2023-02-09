package peval3prsp2223;

import javax.crypto.*;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CifradorSimetricoDES {
    public static byte[] cifrarMensaje(byte[] clave, String mensaje){
        try {
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, GeneradorClavesSimetricasDES.recuperarClave(clave));
            return cipher.doFinal(mensaje.getBytes());
        } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException |
                 IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }
}
