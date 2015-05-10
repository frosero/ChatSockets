
package chatServidor;

import chatCliente.Cliente;
import static chatCliente.Cliente.ventanaCliente;
import chatenvioarchivos.EnviarArchivo;
import chatenvioarchivos.RecibirArchivo;
import Reproductor.Grabador;
import Reproductor.Sonido;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import static java.lang.System.out;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.*;
import javax.swing.*;
import javax.swing.plaf.synth.SynthLookAndFeel;

/**
 *
 * @author 
 */
public class Servidor extends JFrame{
    
    public JTextField ingresoMensaje;
    public JTextArea pantallaChat;
    public JMenuItem adjuntar;
    private static ServerSocket servidor;
    private static Socket cliente;
    private static String ipCliente;
    public static String usuario;
    public static Servidor ventanaServidor;
    public JMenuItem grabacion;
    public JMenuItem reproductor;
    
    //constructor
    public Servidor(){
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
        
        //Creación de items del menú        
        JMenuItem salir = new JMenuItem("Salir");
        adjuntar = new JMenuItem("Adjuntar Archivo");
        grabacion = new JMenuItem("Grabacion");
        reproductor = new JMenuItem("Reproducir");
        adjuntar.setEnabled(false);
        JMenuBar barra = new JMenuBar();
        setJMenuBar(barra);
        barra.add(salir);
        barra.add(grabacion);
        barra.add(reproductor);
        barra.add(adjuntar);

        //Accion que se realiza Salir
        salir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0); //Sale de la aplicación
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
        
        //Método para obtener dirección IPv4 de la computadora Servidor
        try{
        Enumeration<NetworkInterface> interfaceList = NetworkInterface.getNetworkInterfaces();
        NetworkInterface iface = interfaceList.nextElement();
        Enumeration<InetAddress> addrList = iface.getInetAddresses();
        InetAddress address = addrList.nextElement();
        ipCliente=address.getHostAddress();
        }
        catch(SocketException se){
            out.println("Error al obtener las interfaces de red: " + se.getMessage()); //La llamada a getNetworkInterfaces () puede lanzar una SocketException.
         }
        Component frame = null;
        ImageIcon icono=(new ImageIcon(getClass().getResource("/imagenes/ip.jpg")));
   
       JOptionPane.showMessageDialog(frame,"Bienvenidos Al chat","FATSY",JOptionPane.INFORMATION_MESSAGE,icono);
//       ipCliente = (String) JOptionPane.showInputDialog(null, "Introduzca numero IP del Cliente: ", "IP Cliente", JOptionPane.INFORMATION_MESSAGE, icono, null, "");
//        ipCliente = JOptionPane.showInputDialog(null, "Introduzca numero IP del CLiente: ");
        
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
//        try{
//            UIManager.setLookAndFeel(new SynthLookAndFeel());
//        }catch(Exception e){
//            JOptionPane.showMessageDialog(null, "ERROR"+e.getMessage());
//        }
        setVisible(true); //hace visible a la ventana

    }

    public static void main(String[] args) {
        ventanaServidor = new Servidor();
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
                    hiloEnviar he = new hiloEnviar(cliente, ventanaServidor);
                    he.start();
                    hiloRecibir hr = new hiloRecibir(cliente, ventanaServidor);
                    hr.start();
                    
                } catch (IOException ex) {
                    Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    ventanaServidor.mostrarMensaje("No se puede conectar con el cliente");
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class.getName()).log(Level.SEVERE, null, ex);
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
