/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

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
    private final VentServidor ventanaServidor;
    private String mensaje;
    private ObjectInputStream entrada;
    private Socket cliente;
    
    //constructor
    public hiloRecibir(Socket cliente, VentServidor ventaServidor){
        this.cliente=cliente;
        this.ventanaServidor=ventaServidor;
    }
    
    public void mostrarMensaje(String mensaje){
        ventanaServidor.pantallaChat.append(mensaje);
    }
    
    public void run(){
        try{
            entrada=new ObjectInputStream(cliente.getInputStream());
        }catch(IOException ex){
            Logger.getLogger(hiloRecibir.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //leer el mensaje y mostrarlo
        do{
            try{
                mensaje=(String) entrada.readObject();
                ventanaServidor.mostrarMensaje(mensaje);
            }catch(SocketException e){
                ventanaServidor.mostrarMensaje(ventanaServidor.usuario+" Desconectado");
                mensaje="xxxxx";
            } catch(EOFException eo){
                ventanaServidor.mostrarMensaje(ventanaServidor.usuario+" Desconectado");
                mensaje="xxxxx";
            } catch(IOException ex){
                Logger.getLogger(hiloRecibir.class.getName()).log(Level.SEVERE, null, ex);
            } catch(ClassNotFoundException nf){
                ventanaServidor.mostrarMensaje("Objeto Desconocido");
            }
        } //cierra el socket y la entrda
        while(!mensaje.equals("xxxxx"));
        
        try{
            entrada.close(); //cerramos la entrada
            cliente.close(); //cerramos el socket
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
}
