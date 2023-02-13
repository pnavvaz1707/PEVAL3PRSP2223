package peval3prsp2223;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {
    final int PUERTO = 5000;
    ServerSocket servidor;
    Socket cliente;
    DataInputStream entrada;
    DataOutputStream salida;
    public final static File F_CLAVE_PUBLICA = new File("src/peval3prsp2223/ClavePublica.txt");
    //    public final static File F_CLAVE_PUBLICA = new File("D:\\peval3prsp2223");
    private final File F_CLAVE_PRIVADA = new File("src/peval3prsp2223/ClavePrivada.txt");
//    private final File F_CLAVE_PRIVADA = new File("D:\\peval3prsp2223");

    public void initServer() {
        try {
            servidor = new ServerSocket(PUERTO);
            cliente = new Socket();

            GeneradorClavesAsimetricasRSA.generarParDeClavesYGuardarEnFichero(F_CLAVE_PUBLICA, F_CLAVE_PRIVADA);
//            System.out.println("Par de claves asimétricas generadas");

            while (true) {
                System.out.println("Esperando conexión...");
                cliente = servidor.accept();

                entrada = new DataInputStream(cliente.getInputStream());
                salida = new DataOutputStream(cliente.getOutputStream());

                System.out.println("Cliente conectado");

                //Recibimos el tamaño de la clave
                int longitudClave = entrada.readInt();
//                System.out.println("Tamaño de la clave cifrada recibido");

                //Recibimos la clave
                byte[] claveCifrada = new byte[longitudClave];
                entrada.readFully(claveCifrada, 0, claveCifrada.length);
                System.out.println("Clave de comunicación recibida");

                //Recibimos el tamaño del mensaje
                int longitudMensaje = entrada.readInt();
//                System.out.println("Tamaño del mensaje recibido");

                //Recibimos el mensaje
                byte[] mensajeCifrado = new byte[longitudMensaje];
                entrada.readFully(mensajeCifrado, 0, mensajeCifrado.length);
                System.out.println("Mensaje cifrado recibido");

                //Desciframos la clave recibida
                byte[] claveDescifrada = DescifradorAsimetricoRSA.descifrarMensaje(F_CLAVE_PRIVADA, claveCifrada);
//                System.out.println("Clave descifrada");

                //Desciframos el mensaje con la clave recibida
                String mensajeDescifrado = new String(DescifradorSimetricoDES.descifrarFichero(claveDescifrada, mensajeCifrado));
                System.out.println("Mensaje descifrado --> " + mensajeDescifrado);

                salida.writeUTF(mensajeDescifrado);

                entrada.close();
                salida.close();
                cliente.close();
            }
//            servidor.close();
//            System.out.println("Servidor cerrado");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Servidor servidor = new Servidor();
        servidor.initServer();
    }
}
