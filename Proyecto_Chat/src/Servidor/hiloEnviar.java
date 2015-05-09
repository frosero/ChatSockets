/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Usuario
 */
public class hiloEnviar extends Thread{
    private final Servidor ventanaServidor;
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion;
    
    //constructor
    public hiloEnviar(Socket conexion, final Servidor ventanaServidor){
        this.conexion=conexion;
        this.ventanaServidor=ventanaServidor;
        
        //escribe en el area de texto
        ventanaServidor.ingresoMensaje.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mensaje=e.getActionCommand();
                enviarMensaje(mensaje); //se envia el mensaje
                ventanaServidor.ingresoMensaje.setText(""); //borra el texto para un nuevo mensaje
            }
        });
    }
    
    //enviar objeto al cliente
    private void enviarMensaje(String mensaje) {
        try {
            salida.writeObject(ventanaServidor.usuario + " dice: " + mensaje);
            salida.flush(); //flush salida a cliente //borra el buffer
            ventanaServidor.mostrarMensaje("YO: " + mensaje);
        } catch (IOException ex) {
            ventanaServidor.mostrarMensaje(ventanaServidor.usuario+" Cliente Perdido");
        }
    }

    //manipula area Pantalla en el hilo despachador de eventos
    public void mostrarMensaje(String mensaje) {
        ventanaServidor.pantallaChat.append(mensaje);
    }
    
    public void run(){
        try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.flush(); //flush salida a cliente 
        } catch (SocketException ex) {
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }
    }
}
