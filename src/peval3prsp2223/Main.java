package peval3prsp2223;

import java.io.File;
import java.security.KeyPair;

public class Main {
    public static void main(String[] args) {
        //Generamos las claves
        byte[] claveDES = GeneradorClavesSimetricasDES.generarClave();
        File fClavePublica = new File("src/peval3prsp2223/ClavePublica.txt");
        File fClavePrivada = new File("src/peval3prsp2223/ClavePrivada.txt");
        GeneradorClavesAsimetricasRSA.generarParDeClavesYGuardarEnFichero(fClavePublica, fClavePrivada);

        //Ciframos el texto
        String mensajeACifrar = "Mensaje a cifra";
        byte[] mensajeCifrado = CifradorSimetricoDES.cifrarMensaje(claveDES, mensajeACifrar);
        //Ciframos la llave simétrica con la clave pública del receptor
        byte[] claveCifrada = CifradorAsimetricoRSA.cifrarMensaje(fClavePublica, claveDES);

        //Desciframos con nuestra clave privada la clave simétrica recibida
        byte[] claveDescifrada = DescifradorAsimetricoRSA.descifrarMensaje(fClavePrivada, claveCifrada);
        //Desciframos el mensaje cifrado con la clave simétrica que acabmos de descifrar
        byte[] mensajeDescifrado = DescifradorSimetricoDES.descifrarFichero(claveDescifrada, mensajeCifrado);

        System.out.println("Mensaje descifrado --> " + new String(mensajeDescifrado));
    }
}
