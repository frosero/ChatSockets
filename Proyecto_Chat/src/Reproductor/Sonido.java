/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reproductor;
import javax.swing.*;
import javax.sound.sampled.*;
import java.io.*;

/**
 *
 */
public class Sonido extends JFrame{


    
    public Sonido()
{

File sf=new File("Grabacion.wav");

AudioFileFormat aff;
AudioInputStream ais;

try
{
//obtiene el tipo de archivo
aff=AudioSystem.getAudioFileFormat(sf);
//Obtiene un flujo de entrada de audio desde el proporcionado File
ais=AudioSystem.getAudioInputStream(sf);


AudioFormat af=aff.getFormat();

//Construye una línea de datos de información objeto de la información especificada, 
//que incluye un único formato de audio y un tamaño de búfer deseado. Este constructor se utiliza 
//normalmente por una aplicación para describir una línea deseada.
//Parámetros:
//lineClass - la clase de la línea de datos descrito por el objeto info
//format formato deseado -
//bufferSize - deseada tamaño del buffer en bytes
DataLine.Info info = new DataLine.Info(
Clip.class,
ais.getFormat(),
((int) ais.getFrameLength() * af.getFrameSize()));
//El Clip interfaz representa un tipo especial de línea de datos cuyos datos de audio se pueden cargar 
//antes de la reproducción, en lugar de ser transmitido en tiempo real.
Clip ol = (Clip) AudioSystem.getLine(info);

ol.open(ais);
ol.start();
//para el numero de veces que se reproduce
ol.loop(0);

System.out.println("reprodución empezada :)");
Thread.sleep(20000);
}
catch(UnsupportedAudioFileException ee){
 
}
catch(IOException ea){
     
}
catch(LineUnavailableException LUE){
   
}       
catch (InterruptedException ex) {
            
}

}
    public static void main(String[] args) {
        // TODO code application logic here
        new Sonido();
    }
    
}
