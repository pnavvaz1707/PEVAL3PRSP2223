package peval3prsp2223;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {
    final int PUERTO = 5000;
    Socket cliente;
    String mensaje;
    String mensajeRecibido;
    DataInputStream entrada;
    DataOutputStream salida;
    Scanner teclado = new Scanner(System.in);

    public void initClient() {
        try {
            cliente = new Socket("localhost", PUERTO);

            salida = new DataOutputStream(cliente.getOutputStream());
            entrada = new DataInputStream(cliente.getInputStream());

            byte[] claveDES = GeneradorClavesSimetricasDES.generarClave();

            System.out.println("Escriba el mensaje que desee cifrar");
            String mensajeACifrar = teclado.nextLine();

            //Ciframos la llave simétrica con la clave pública del receptor
            byte[] claveDESCifrada = CifradorAsimetricoRSA.cifrarMensaje(Servidor.F_CLAVE_PUBLICA, claveDES);
            int longitudClaveDESCifrada = claveDESCifrada.length;

            salida.writeInt(longitudClaveDESCifrada);
//            System.out.println("Tamaño de la clave enviado");

            salida.write(claveDESCifrada);
            System.out.println("Clave de comunicación enviada");

            byte[] mensajeCifrado = CifradorSimetricoDES.cifrarMensaje(claveDES, mensajeACifrar);
            int longitudMensajeCifrado = mensajeCifrado.length;

            salida.writeInt(longitudMensajeCifrado);
//            System.out.println("Tamaño del mensaje enviado");

            salida.write(mensajeCifrado);
            System.out.println("Mensaje enviado");

            mensajeRecibido = entrada.readUTF();
            System.out.println("El servidor ha recibido este mensaje --> " + mensajeRecibido);

            entrada.close();
            salida.close();
            cliente.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        Cliente c = new Cliente();
        c.initClient();
    }
}