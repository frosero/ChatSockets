/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 *
 * @author Felipe Rosero & Patricio Rodriguez
 */
public class ThreadEnvio {

    private final VentCliente ventanaCliente;
    private ObjectOutputStream salida;
    private String mensaje;
    private Socket conexion;

    //constructor
    public ThreadEnvio(Socket conexion, final VentCliente ventanaCliente) {
        this.conexion = conexion;
        this.ventanaCliente = ventanaCliente;

        //mensajes en el area de texto
        ventanaCliente.ingresoMensaje.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                mensaje = e.getActionCommand();
                enviarMensaje(mensaje); //se envia el mensaje
                ventanaCliente.ingresoMensaje.setText(""); //se borra para ingresar nuevo texto
            }

        });
    }

    //enviar objeto a cliente
    private void enviarMensaje(String mensaje) {
        try {
            salida.writeObject(ventanaCliente.usuario + " dice: " + mensaje);
            salida.flush(); //salida a cliente 
            ventanaCliente.mostrarMensaje("YO: " + mensaje);
        } catch (IOException io) {
            ventanaCliente.mostrarMensaje(mensaje);
        }
    }

    //despacha los mensajes
    public void mostrarMensaje(String mensaje) {
        ventanaCliente.pantallaChat.append(mensaje);
    }

    public void run() {
        try {
            salida = new ObjectOutputStream(conexion.getOutputStream());
            salida.flush();
        } catch (SocketException ex) {
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (NullPointerException ex) {
        }

    }
}
