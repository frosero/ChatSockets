/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EnvioYReciboArchivos;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 *
 * @author Felipe Rosero & Patricio Rodriguez
 */
public class EnviarArchivo extends Thread{
    int n;
    byte[] vectorDatos;
    Socket cliente;
    String ipServidor;
    String nombreArchivo;
    BufferedInputStream ingreso; //Nuevo Objeto BufferedInputStream
    BufferedOutputStream salida;
    
    //constructor
    public EnviarArchivo(String ipServidor, String nombreArchivo){
        this.ipServidor=ipServidor;
        this.nombreArchivo=nombreArchivo;
    }
    
    @Override
    public void run(){
        try{
            //nuevo archivo con el nombre 
            final File ubicacion=new File(nombreArchivo);
            
            //nuevo socket con la direccion ip del servidor a donde enviaremos el archivo
            //especificando el puerto 
            cliente=new Socket(ipServidor, 6000);
            
            //instancia los objetos para leer el archivo y enviar y para escribirlo y enviarlo como byte
            ingreso=new BufferedInputStream(new FileInputStream(ubicacion));
            salida=new BufferedOutputStream(cliente.getOutputStream());
            
            //enviamos el nombre del archivo
            DataOutputStream dos=new DataOutputStream(cliente.getOutputStream());
            dos.writeUTF(ubicacion.getName());
            vectorDatos=new byte[8192];
            //realiza la lectura del archivo a enviar y lo ecribe en bytes
            while((n=ingreso.read(vectorDatos))!=-1){
                //escribe como bytes el archivo a enviar
                salida.write(vectorDatos, 0, n);
            }
            ingreso.close(); //cierra el objeto
            salida.close(); //cierra el objeto
            System.out.println("jjj");
        } catch(Exception e){}
    }
}
