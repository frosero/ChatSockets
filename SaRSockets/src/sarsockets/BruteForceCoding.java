
package sarsockets;
import static java.lang.System.*;
public class BruteForceCoding {
    //ELEMENTO DE DATOS PARA CODIFICARD 
    //Nota: variables byte, short, int y long son enteros primitivos
    private static byte byteVal = 101; // cientouno
    private static short shortVal = 10001; //Diez mil uno
    private static int intVal = 100000001; //Cien millones y uno
    private static long longVal = 1000000000001L; //Un trillon y uno //Recordar L indica que se trata de un tipo "long"
    
    
    //OBTENER EL NÚMERO DE BYTES DE LOS ENTEROS PRIMITIVOS
    //Nota: Obtiene la longitud de los enteros primitivos en Bytes.
    private final static int BSIZE = Byte.SIZE / Byte.SIZE; //El método "SIZE" simplemente retorna el tamaño del arreglo o número de elementos //static final declaran una constante fija diferente de las variables inicializadas
    private final static int SSIZE = Short.SIZE / Byte.SIZE; 
    private final static int ISIZE = Integer.SIZE / Byte.SIZE;
    private final static int LSIZE = Long.SIZE / Byte.SIZE;
    
    private final static int BYTEMASK = 0XFF; //8bits //fitratemos los 8 primeros bytes(32 bytes en total) por medio de la máscara para cada uno de los enteros primitivos
                                              /* (Arquitectura de SW nivel bits)
                                                byte = 1 byte
                                                short = 2 bytes
                                                int = 4 bytes
                                                long = 8 bytes
                                                float = 4 bytes
                                                double = 8 bytes
                                                char = 2 bytes
                                                bolean = 1 byte
                                                BYTEMASK mantiene el valor del byte de ser extendido a firmar cuando se convierte a un int en la llamada a append (). Lo cual lo hace como un entero sin signo
                                                bytemask Borra los bits más altos - esencialmente negando el signo solicitado implícitamente durante la conversión a la representación de cadena decimal del valor binario.
                                                */
    //IMPRIME CADA BYTE DE LA MATRIZ DADA COMO UN VALOR DECIMAL SIN SIGNO
    
    public static String byteArrayToDecimalString(byte[] bArray){
        StringBuilder rtn = new StringBuilder(); //declaro objeto del tipo StringBuilder
        for (byte b : bArray){
            //Conversión Decimal
            rtn.append(b & BYTEMASK).append(" "); //setText reemplaza el texto antes escrito, append añade el texto luego de lo que ya está escrito.
           
        }
        return rtn.toString();        
    }
    // Advertencia: Condiciones previas no provadas (EJ: 0<= size <=8)
    public static int encodeIntBigEndian(byte[] dst, long val, int offset, int size){ //Iterar sobre bytes de tamaño de la matriz dada, acumulando el resultado en un long, que se desplaza a la izquierda con cada interación
        for (int i = 0; i < size; i++){
            dst[offset++] = (byte) (val >> ((size - i -1) * Byte.SIZE)); //Operaciones con bytes >> recorre estacios en el numero binario val
                                                                          //El reparto (byte) descarta los bit más altos, esto equivale a una operación de desplazamiento y máscara.
        }
        return offset;
        
    }
    //Advertencia: COndiciones previas no probadas (EJ: 0 <= size <=8)
    public static long decodeIntBigEndian(byte[] val, int offset, int size){//Iterar sobre bytes de tamaño de la matriz dada, acumulando el resultado en un long, que se desplaza a la izquierda con cada interación
        long rtn = 0;
        for(int i = 0; i < size; i++){
            rtn = (rtn << Byte.SIZE)| ((long) val[offset + i] & BYTEMASK);
        }
        return rtn;        
    }
    
    //Demostrar métidos aplicados
    public static void main(String[] args){
        byte[] message = new byte[BSIZE + SSIZE + ISIZE + LSIZE]; //Preparar matriz para recibir la serie de números enteros
        //CODIFICAR LOS CAMPOS DE LA MATRIZ (ARREGLO) DE BYTES DE DESTINO
        //Nota:  El byte, short, int y long se codifica en la matriz en la secuencia descrita anterior. 
        int offset = encodeIntBigEndian(message, byteVal, 0, BSIZE); //Parametrizar objeto offset
        offset = encodeIntBigEndian(message, shortVal, offset, SSIZE);
        offset = encodeIntBigEndian(message, intVal, offset, SSIZE);
        encodeIntBigEndian(message, longVal, offset, LSIZE);
        out.println("Mensaje decodificado: " + byteArrayToDecimalString(message));
        
        //DECODIFICAR VARIOS CAMPOS
        
    }
    
    
    
}
