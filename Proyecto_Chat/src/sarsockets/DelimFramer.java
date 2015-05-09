
package sarsockets;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DelimFramer implements Framer{
    
    private InputStream in; //Fuente de los datos.
    private static final byte DELIMITER = '\n'; //Mensaje delimitador
    
    public DelimFramer(InputStream in){
        this.in = in;
    }

    @Override
    public void frameMsg(byte[] message, OutputStream out) throws IOException { //añade información enmarcar
        //DEBEMOS ASEGURARNOS QUE EL MENSAJE NO CONTIENE EL DELIMITADOR '\n'
        for(byte b : message){//Verificación de datos
            if (b == DELIMITER){
                throw new IOException("El mensaje contiene el delimitador");
            }
        }
        out.write(message); //Salida el mensaje enmarcado al stream
        out.write(DELIMITER);
        out.flush();
        
    }

    @Override
    public byte[] nextMsg() throws IOException { //nextMsg () extrae los mensajes de entrada
        ByteArrayOutputStream messageBuffer = new ByteArrayOutputStream();
        int nextByte;
        
        //RECUPERAR BYTES HASTA ENCONTRAR EL DELIMITADOR
        while((nextByte = in.read()) != DELIMITER){ //Leer cada byte el Stream hasta que se encuentra el delimitador
            if(nextByte == -1){ //fin de Stream?    
                /*Si el extremo de la corriente se produce antes de encontrar el delimitador, 
                se produce una excepción si algunos bytes se han leído desde la construcción de la enmarcador o el último delimitador; 
                de lo contrario devuelve un "nulo" para indicar que se han recibido todos los mensajes.*/                
                if (messageBuffer.size() == 0){ //Si no hay datos leidos
                    return null;                    
                } else { //si bytes seguido de final del stream: error enmarcar
                    throw new EOFException("Mensaje no vacío sin delimitador");
                }
            }
            messageBuffer.write(nextByte); // Escribe un byte en el búfer //Escribe byte no delimitador de almacenamiento intermedio de mensajes
           
        }
        return messageBuffer.toByteArray(); //Regresa los contenidos del mensaje en el Búfer como matriz de bytes.
        
    }
    
}

/*
 El DelimFramer.java clase implementa encuadre basado en delimitador utilizando la caracter - "nueva línea"
("\ n", valor de byte 10). El método frameMethod () no no hacer el relleno, sino que simplemente lanza
una excepción si la secuencia de bytes que se enmarca contiene el delimitador. (Extendiendo el método
hacer el relleno se deja como ejercicio.) El método nextMsg () analiza la corriente hasta que lea el
delimitador, luego regresa todo hasta el delimitador;
nulo
se devuelve si la corriente está vacía.
Si se acumulan algunos bytes de un mensaje y la corriente termina sin encontrar un delimitador,
se emite una excepción para indicar un error de trama. 

*/
