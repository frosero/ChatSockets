/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatenvioarchivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Usuario
 */
public class RecibirArchivo extends Thread{
    private String direccion;
    private String ipServidor;
    private String nombreServidor;
    private int puerto;
    private Socket clienteArchivos;
    
    //constructor
    public RecibirArchivo(String direccion, String nombreServidor, int puerto, String ipServidor){
        this.direccion=direccion;
        this.nombreServidor=nombreServidor;
        this.puerto=puerto;
        this.ipServidor=ipServidor;        
    }
    
    public void run(){
        ServerSocket servidor; //crea un nuevo socket server
        Socket receptor; //crea un nuevo socket receptor
        
        //crea los objetos buffered para la lectura del archivo recibido y la escritura del mismo
        BufferedInputStream bis;
        BufferedOutputStream bos;
        byte[] recibirArchivo;
        int n;
        String archivo;
        
        try{
            servidor=new ServerSocket(6000);
            //bucle para recibir el archivo
            while(true){
                receptor=servidor.accept();
                //obtiene el archivo como bytes
                recibirArchivo=new byte[1024];
                bis=new BufferedInputStream(receptor.getInputStream());
                DataInputStream dis=new DataInputStream(receptor.getInputStream());
                
                //recibimos el nombre del fichero y le damos una ubicacion
                archivo=dis.readUTF();
                archivo=archivo.substring(archivo.indexOf('/')+1, archivo.length());
                
                //escribe nuevamente el archivo recibido en bytes, pero ahora lo escribe en su formato
                bos=new BufferedOutputStream(new FileOutputStream(archivo));
                while((n=bis.read(recibirArchivo))!=-1){
                    bos.write(recibirArchivo, 0, n);
                }
                
                bos.close();
                bis.close();
            }
            
        }catch(Exception e){}
    }
}
