/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Cliente;

import EnvioYReciboArchivos.EnviarArchivo;
import EnvioYReciboArchivos.RecibirArchivo;
import Reproductor.Grabador;
import Reproductor.Sonido;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.synth.SynthLookAndFeel;

/**
 *
 * @author Felipe Rosero & Patricio Rodriguez
 */
public class VentCliente extends JFrame {

    public JTextField ingresoMensaje;
    public JTextArea pantallaChat;
    public JMenuItem grabacion;
    public JMenuItem reproductor;
    public JMenuItem adjuntar;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String ipServidor;
    public static VentCliente ventanaCliente;
    public static String usuario;
    public boolean recibir;

    //creamos la ventana del chat del cliente
    public VentCliente() {
        super();
        //campo de texto en la parte inferior
        ingresoMensaje = new JTextField();
        ingresoMensaje.setEditable(false);
        add(ingresoMensaje, BorderLayout.SOUTH);

        //pantalla del chat
        pantallaChat = new JTextArea();
        pantallaChat.setEditable(false);
        Font fuente = new Font("Dialog", Font.BOLD | Font.ITALIC, 20);
        pantallaChat.setFont(fuente);
        add(new JScrollPane(pantallaChat), BorderLayout.CENTER);
        pantallaChat.setBackground(Color.green);
        pantallaChat.setForeground(Color.yellow);
        ingresoMensaje.setForeground(Color.green);
        Font fuente1 = new Font("Dialog", Font.BOLD | Font.ITALIC, 20);
        ingresoMensaje.setFont(fuente1);

        //creacion del menu
        JMenuItem salir = new JMenuItem("Exit");
        adjuntar = new JMenuItem("Adjuntar Archivo");
        adjuntar.setEnabled(true);
        grabacion = new JMenuItem("Grabacion");
        reproductor = new JMenuItem("Reproducir");
        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);
        barra.add(salir);
        barra.add(grabacion);
        barra.add(reproductor);
        barra.add(adjuntar);

        //accion que realiza el boton salir
        salir.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //sale de la aplicacion
            }
        });

        //acccion que realiza el boton adjuntar
        adjuntar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.JFileChooser ventanaEscoger = new javax.swing.JFileChooser();
                int seleccion = ventanaEscoger.showOpenDialog(ventanaEscoger); //abre la ventana para escoger el archivo
                String direccion = ventanaEscoger.getSelectedFile().getAbsolutePath(); //se obtiene la direccion completa del archivo

                //bucle para realizar la comparacion de archivo y poderlo enviar
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    ventanaCliente.mostrarMensaje("Enviando Archivo.........");
                    RecibirArchivo ra = new RecibirArchivo(direccion, usuario, 6000, "localhost");
                    ra.start();
                    EnviarArchivo ea = new EnviarArchivo(ipServidor, direccion);
                    ea.start();
                    ventanaCliente.mostrarMensaje("Archivo Enviado Exitosamente");

                }
            }

        });
        //accion que realiza el boton  grabador
        grabacion.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Comenzando a grabar por 20 segundos");
                Grabador gb = new Grabador();

            }

        });
        //accion que realiza el boton reproducir
        reproductor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                Component frame = null;
                JOptionPane.showMessageDialog(frame, "Reproduciendo la grabacion");
                Sonido sd = new Sonido();
            }
        }
        );

        ipServidor = JOptionPane.showInputDialog(null, "Introduzca la IP del Servidor");
        setSize(500, 500); //tamanio de la ventana del chat

        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Cliente".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "ERROR" + e.getMessage());
        }

//        try{
//            UIManager.setLookAndFeel(new SynthLookAndFeel());
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, "ERROR"+e.getMessage());
//        }
        setVisible(true); //hace visible la ventana

    }

    public void mostrarMensaje(String mensaje) {
        pantallaChat.append(mensaje + "\n");

    }

    public static void main(String[] args) {
        ventanaCliente = new VentCliente();
    }

    public void habilitar(boolean editable) {
        ingresoMensaje.setEditable(editable);
        adjuntar.setEnabled(editable);
    }
}
