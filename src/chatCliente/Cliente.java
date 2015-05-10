/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatCliente;

import chatServidor.Servidor;
import chatenvioarchivos.EnviarArchivo;
import chatenvioarchivos.RecibirArchivo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
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
 * @author Usuario
 */
public class Cliente extends JFrame {

    public JTextField ingresoMensaje;
    public JTextArea pantallaChat;
    public JMenuItem adjuntar;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String ipServidor;
    public static Cliente ventanaCliente;
    public static String usuario;
    public boolean recibir;

    //creamos la ventana del chat del cliente
    public Cliente() {
        super();
        //campo de texto en la parte inferior
        ingresoMensaje = new JTextField();
        ingresoMensaje.setEditable(false);
        add(ingresoMensaje, BorderLayout.SOUTH);

        //pantalla del chat
        pantallaChat = new JTextArea();
        pantallaChat.setEditable(false);
        Font fuente = new Font("Dialog", Font.BOLD | Font.ITALIC, 14);
        pantallaChat.setFont(fuente);
        add(new JScrollPane(pantallaChat), BorderLayout.CENTER);
        pantallaChat.setBackground(Color.white);
        pantallaChat.setForeground(Color.blue);
        ingresoMensaje.setForeground(Color.blue);
        Font fuente1 = new Font("Dialog", Font.BOLD | Font.ITALIC, 14);
        ingresoMensaje.setFont(fuente1);

        //creacion del menu
        JMenuItem salir = new JMenuItem("Salir");
        adjuntar = new JMenuItem("Adjuntar Archivo");
        adjuntar.setEnabled(false);
        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);
        barra.add(salir);
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

        ipServidor = JOptionPane.showInputDialog(null, "Introduzca la IP del Servidor");
        setSize(320, 500); //tamanio de la ventana del chat
        
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage());
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbuz".equals(info.getName())) {
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

    public static void main(String[] args) {
        ventanaCliente = new Cliente();
        ventanaCliente.setLocationRelativeTo(null);
        ventanaCliente.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario = JOptionPane.showInputDialog(null, "Introduzca su nombre: ");// intrduce el nombre del usuario o el nick

        try {
            //Coneccion con el cliente
            ventanaCliente.mostrarMensaje("Buscando Servidor ...");
            cliente = new Socket(InetAddress.getByName(ipServidor), 11111);
            ventanaCliente.mostrarMensaje("Conectado a :" + cliente.getInetAddress().getHostName());
            ventanaCliente.habilitar(true);

            //Correr los hilos de enviar y recibir
            hiloEnviar he = new hiloEnviar(cliente, ventanaCliente);
            he.start();
            hiloRecibir hr = new hiloRecibir(cliente, ventanaCliente);
            hr.start();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            ventanaCliente.mostrarMensaje("No se encuentra al Servidor");
        }
    }

    public void mostrarMensaje(String mensaje) {
        pantallaChat.append(mensaje + "\n");
        
    }

    public void habilitar(boolean editable) {
        ingresoMensaje.setEditable(editable);
        adjuntar.setEnabled(editable);
    }
}
