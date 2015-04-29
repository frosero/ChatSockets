
package sarsockets;

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
    
    private final static int BYTEMASK = 0XFF; //8bits //fitratemos los 8 primeros bits por medio de la máscara para cada uno de los enteros primitivos
                                              /* (Arquitectura de SW nivel bits)
                                                byte = 1 byte
                                                short = 2 bytes
                                                int = 4 bytes
                                                long = 8 bytes
                                                float = 4 bytes
                                                double = 8 bytes
                                                char = 2 bytes
                                                bolean = 1 byte
                                                */
    
}
