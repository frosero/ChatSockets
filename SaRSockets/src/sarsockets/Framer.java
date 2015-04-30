
package sarsockets;

import java.io.IOException;
import java.io.OutputStream;

//Una interface sirve para establecer un 'protocolo' entre clases. 
public interface Framer {
  void frameMsg(byte[] message, OutputStream out) throws IOException;
  
  byte[] nextMsg() throws IOException;
    
}

/*
 El enfoque basado en la longitud es más simple, pero requiere un límite superior conocida en el tamaño de
el mensaje. El remitente primero determina la longitud del mensaje, lo codifica como un entero,
y prefijos el resultado al mensaje. El límite superior de la longitud del mensaje determina la
número de bytes requeridos para codificar la longitud: un byte si los mensajes siempre contener menos de
256 bytes, dos bytes si son siempre más corto que 65.536 bytes, y así sucesivamente.


Dispone de dos métodos: frameMsg () añade que enmarca la información y da salida a una determinada
mensaje a una corriente dada, mientras que nextMsg () escanea una corriente dada, extraer el siguiente mensaje. 
*/
