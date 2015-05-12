package Sockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class Cliente {

    public static void main(String args[]) {
        Socket cliente;
        DataInputStream entrada;
        DataOutputStream salida;
        String mensaje, respuesta;
        try {
            cliente = new Socket(InetAddress.getLocalHost(), 5000);   //Creamos el socket para conectarnos al puerto 5000 del servidor
            entrada = new DataInputStream(cliente.getInputStream());  //Creamos los canales de entrada/salida
            salida = new DataOutputStream(cliente.getOutputStream());
            mensaje = "RedesZone";
            salida.writeUTF(mensaje);                                 // Enviamos un mensaje al servidor
            respuesta = entrada.readUTF();                            // Leemos la respuesta
            System.out.println("Mi mensaje: " + mensaje);
            System.out.println("Respuesta del Servidor: " + respuesta);
            cliente.close();                                          // Cerramos la conexion
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
