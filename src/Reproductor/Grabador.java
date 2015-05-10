package Reproductor;

import java.io.File;
import javax.sound.sampled.*;

/**
 *
 */
public class Grabador {
//creamos elsrchivo de audio con el formato weve

    AudioFileFormat.Type aFF_T = AudioFileFormat.Type.WAVE;
//Construye una AudioFormat con una codificación PCM lineal. El tamaño de trama se establece en el número de bytes
//necesarios para contener una muestra de cada canal, y la velocidad de cuadro está ajustado a la frecuencia de muestreo.
//Parámetros:
//sampleRate - el número de muestras por segundo
//sampleSizeInBits - el número de bits en cada muestra
//channels - el número de canales (1 para mono, 2 para estéreo, y así sucesivamente)
//signed - indica si los datos de la firma o sin firma
//bigEndian - indica si los datos de una sola muestra se almacena en orden de bytes big-endian ( false significa little-endian)
    AudioFormat aF = new AudioFormat(8000.0F, 16, 1, true, false);
//es una línea de datos que obtiene sus datos desde un dispositivo de captura de audio
    TargetDataLine tD;
    File f = new File("Grabacion.wav");

    public Grabador() {
        try {
   // una línea de datos de información objeto de la información especificada, 
            //que incluye un conjunto de formatos de audio soportados y un rango para el tamaño del búfer 
            DataLine.Info dLI = new DataLine.Info(TargetDataLine.class, aF);
//El AudioSystem clase actúa como el punto de entrada a los recursos del sistema muestreado-audio. 
//Esta clase le permite consultar y acceder a los mezcladores que están instalados en el sistema.
//getLine Obtiene una línea que coincide con la descripción en la especificada Line.Info objeto
            tD = (TargetDataLine) AudioSystem.getLine(dLI);
            new CapThread().start();
            System.out.println("Grabando durante 20s...");
            Thread.sleep(20000);
            tD.close();
        } catch (Exception e) {
        }
    }

    class CapThread extends Thread {

        @Override
        public void run() {
            try {
                tD.open(aF);
                tD.start();
//write() Escribe un flujo de bytes que representan a un archivo de audio del tipo de archivo especificado 
//en el fichero externa existente.
//Parámetros:
//stream - el flujo de entrada de audio que contiene los datos de audio que se escriben en el archivo
//fileType - el tipo de archivo de audio para escribir
//out - el archivo externo al que los datos del archivo se deben escribir
                AudioSystem.write(new AudioInputStream(tD), aFF_T, f);
            } catch (Exception e) {
            }
        }
    }

}
