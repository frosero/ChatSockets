/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatCliente;

import static chatCliente.Cliente.usuario;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
public class hiloRecibir extends Thread{
    private final Cliente ventaCliente;
    private String mensaje;
    private ObjectInputStream entrada;
    private Socket cliente;
    
    //constructor
    public hiloRecibir(Socket cliente, Cliente ventanaCliente){
        this.cliente=cliente;
        this.ventaCliente=ventanaCliente;
    }
    
    //metodos para mostrar el mensaje
    public void mostrarMensaje(String mensaje){
        ventaCliente.pantallaChat.append(mensaje);
        
    }
    
    public void run(){
        try{
            entrada = new ObjectInputStream(cliente.getInputStream());
        }catch (IOException ex) {
            Logger.getLogger(hiloRecibir.class.getName()).log(Level.SEVERE, null, ex);
        }

        do {
            //leer el mensaje y mostrarlo
            try {
                mensaje = (String) entrada.readObject();
                ventaCliente.mostrarMensaje(mensaje);
            } catch (SocketException ex) {
            } catch (EOFException eofException) {
                ventaCliente.mostrarMensaje(usuario+" Desconectado");
                mensaje ="xxxx";
            } catch (IOException ex) {
                Logger.getLogger(hiloRecibir.class.getName()).log(Level.SEVERE, null, ex);
                ventaCliente.mostrarMensaje(usuario+ "Desconectado");
                mensaje ="xxxx";
            } catch (ClassNotFoundException classNotFoundException) {
                ventaCliente.mostrarMensaje("Objeto Desconocido");
                mensaje ="xxxx";
            }

        } while (!mensaje.equals("xxxx")); //Ejecuta hasta que el server escriba TERMINATE

        try {
            entrada.close();//cierra la entrada
            cliente.close();//cierra el socket
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        ventaCliente.mostrarMensaje("Fin de la Conexion");
    }
}
