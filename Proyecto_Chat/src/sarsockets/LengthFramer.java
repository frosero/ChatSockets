package sarsockets;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class LengthFramer implements Framer {
    public static final int MAXMESSAGELENGTH = 65535;
    public static final int BYTEMASK = 0xff;
    public static final int SHORTMASK = 0xffff;
    public static final int BYTESHIFT = 8;
    
    private DataInputStream in; //Contenedor de los datos de E / S
    
    public LengthFramer(InputStream in) throws IOException { //Toma la fuente del Stream de entrada de mensajes enmarcadas y se envuelve en una DataInputStream
        this.in = new DataInputStream(in); //Con this le indicamos a java que nos referimos al atributo de la clase no a la variable local
    }
    //AÑADE INFORMACIÓN DE MASCARA
    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException {
        if (message.length > MAXMESSAGELENGTH){ // Debido a que se utiliza un campo de longitud de dos bytes, 
                                                // la longitud no puede exceder de 65.535. (Tenga en cuenta que este
                                                // valor es demasiado grande como para almacenar en poco, por lo que escribir 
                                                // un byte a la vez.) 
            throw new IOException("Mensaje demaciado largo");
        }
        //PREFIJO DE LONGITUD DE ESCRITURA
        out.write((message.length >> BYTESHIFT)& BYTEMASK); //Salida del mensaje de bytes como prefijo la longitud (unsigned short).
        out.write(message.length & BYTEMASK);
        //ESCRIBE UN MENSAJE
        out.write(message); //Mensaje de salida
        out.flush();        
    }
    
    @Override
    public byte[] nextMsg() throws IOException { //nextMsg() extrae siguiente frame de la entrada
        int length;
        try {                                   //Leer la longitud del prefijo  //  El método readUnsignedShort () lee dos bytes, los interpreta como un big-endian
                                                                                // número entero, y devuelve su valor como un int. 
        length = in.readUnsignedShort(); //Lee 2 bytes
        } catch (EOFException e) { //Sin mensaje o solo tiene 1 byte
        return null;
    }
       // 0<= length <= 65535
        byte[] msg = new byte[length]; //Lee un número específico de Bytes.
        in.readFully(msg); //Si se produce una excepción es una error de trama.
        return msg; //Devolver Bytes como mensaje.
    }
    
}


/*
 El LengthFramer.java clase implementa encuadre basado en las tallas de mensajes de hasta 65.535
(2~16- 1) bytes de longitud. El emisor determina la longitud del mensaje dado y escribe
que el flujo de salida como de dos bytes, número entero big-endian, seguido por el mensaje completo.
En el lado receptor, se utiliza un DataInputStream para ser capaz de leer la longitud como un entero;
los readFully () bloques de método hasta que la matriz dada es completamente lleno, que es exactamente lo
que necesitamos aquí. Tenga en cuenta que, con este método encuadre, el remitente no tiene que inspeccionar el
el contenido del mensaje que se enmarca; se necesita sólo para comprobar que el mensaje no exceda
el límite de longitud. 
*/
