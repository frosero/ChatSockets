/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import static Cliente.VentCliente.usuario;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Felipe Rosero & Patricio Rodriguez
 */
public class ThreadRecibir {
    private final VentCliente ventaCliente;
    private String mensaje;
    private ObjectInputStream entrada;
    private Socket cliente;
    
    //constructor
    public ThreadRecibir(Socket cliente, VentCliente ventanaCliente){
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
            Logger.getLogger(ThreadRecibir.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ThreadRecibir.class.getName()).log(Level.SEVERE, null, ex);
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
