/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servidor;

import EnvioYReciboArchivos.EnviarArchivo;
import EnvioYReciboArchivos.RecibirArchivo;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

/**
 *
 * @author Felipe Rosero & Patricio Rodriguez
 */
public class VentServidor extends JFrame{
    public JTextField ingresoMensaje;
    public JTextArea pantallaChat;
    public JMenuItem adjuntar;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String ipCliente;
    public static String usuario;
    public static VentServidor ventanaServidor;
    
    //constructor
    public VentServidor(){
        super();
        //campo de texto en el area inferior
        ingresoMensaje=new JTextField();
        ingresoMensaje.setEditable(false);
        add(ingresoMensaje, BorderLayout.SOUTH);
        
        //area del chat
        pantallaChat=new JTextArea();
        pantallaChat.setEditable(false);
        Font fuente1=new Font("Dialog", Font.BOLD|Font.ITALIC, 14);
        pantallaChat.setFont(fuente1);
        add(new JScrollPane(pantallaChat), BorderLayout.CENTER);
        pantallaChat.setBackground(Color.BLUE);
        pantallaChat.setForeground(Color.white);
        ingresoMensaje.setForeground(Color.blue);
        Font fuente=new Font("Dialog", Font.BOLD|Font.ITALIC, 14);
        ingresoMensaje.setFont(fuente);
        
        //crea opciones de salir y adjuntar
        
        JMenuItem salir = new JMenuItem("Salir");
        adjuntar = new JMenuItem("Adjuntar Archivo");
        adjuntar.setEnabled(false);
        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);
        barra.add(salir);
        barra.add(adjuntar);

        //Accion que se realiza Salir
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Sale de la aplicacion
            }
        });

        //Accion que se realiza Adjuntar Archivo
        adjuntar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                javax.swing.JFileChooser ventanaEscojer = new javax.swing.JFileChooser();
                int seleccion = ventanaEscojer.showOpenDialog(ventanaEscojer);// Se abre el cuadro para escoger el archivo
                String direccion = ventanaEscojer.getSelectedFile().getAbsolutePath();//Se obtiene la direccion completa del archivo

                //bucle para realizar la comparacion del archivo y poderlo enviar
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    ventanaServidor.mostrarMensaje("Enviando Archivo...");
                    RecibirArchivo recibirArchivo = new RecibirArchivo(direccion, usuario, 6000, "localhost");
                    recibirArchivo.start();
                    EnviarArchivo enviarArchivo = new EnviarArchivo(ipCliente, direccion);
                    enviarArchivo.start();
                    ventanaServidor.mostrarMensaje("Archivo Enviado Existosamente");
                   

                }
            }
        });
        ipCliente = JOptionPane.showInputDialog(null, "Introduzca numero IP del CLiente: ");
        
        setSize(320, 500);//tamano de la ventana del chat
        
        setIconImage(new ImageIcon(getClass().getResource("/imagenes/icono.png")).getImage());
        try{
            for(javax.swing.UIManager.LookAndFeelInfo info:javax.swing.UIManager.getInstalledLookAndFeels()){
                if("Nimbuz".equals(info.getName())){
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR"+e.getMessage());
        }

        setVisible(true); //hace visible a la ventana

    }

    public static void main(String[] args) {
        ventanaServidor = new VentServidor();
        ventanaServidor.setLocationRelativeTo(null);
        ventanaServidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario = JOptionPane.showInputDialog(null, "Introduzca su nombre: ");// intrduce el nombre del usuario o el nick

        try {
            //Crear el socket Servidor
            
            servidor = new ServerSocket(11111, 100);
            ventanaServidor.mostrarMensaje("Esperando Cliente ...");
            //Bucle infinito para esperar conexiones de los clientes
            while (true) {
                try {
                    //Coneccion con el cliente
                    cliente = servidor.accept();
                    ventanaServidor.mostrarMensaje("Conectado a : " + cliente.getInetAddress().getHostName());
                    ventanaServidor.habilitar(true);
                    //Correr los hilos de enviar y recibir
                    ThreadEnvio he = new ThreadEnvio(cliente, ventanaServidor);
                    he.start();
                    ThreadRecibir hr = new ThreadRecibir(cliente, ventanaServidor);
                    hr.start();
                    
                } catch (IOException ex) {
                    Logger.getLogger(VentServidor.class.getName()).log(Level.SEVERE, null, ex);
                    ventanaServidor.mostrarMensaje("No se puede conectar con el cliente");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(VentServidor.class.getName()).log(Level.SEVERE, null, ex);
            ventanaServidor.mostrarMensaje("No se encuentra IP del Servidor");
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
