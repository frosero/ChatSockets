package ClienteServidor;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

//INSTALACIÓN DE APLICACIONES Y PARÁMETROS DE ANÁLISIS
public class TCPEchoClient {
    public static void main(String[] args) throws IOException { //throws, permite manejar la excepción sin dar un tratamiento específico, quiere decir q si se produce una excepción el programa lanza (throws) a otro pedazo de código su ejecución; lo que no pasa con los bloques (try/catch)
        //if ((args.length < 2) || (args.length > 3)) // Prueba de un correcto número de argumentos.
        //    throw new IllegalArgumentException("Parámetro(s): <Servidor> <Palabra> <Puerto>"); //cuando ocurre la excepción nos regresa un mensaje (manejo de excepciones cuando los métodos no son compatibles con todos los tipos de parámatros)
        
        String server = args[0]; //Nombre del servidor o dirección IP
        //Convierte la cadena a bytes usando la decodificación de caracteres por defecto.
        byte[] data = args[1].getBytes(); //arreglo tipo byte que proporciona mejor manejo de espacios de memoria que un int  
                                          /*  Sockets TCP enviar y recibir secuencias de bytes. El método getBytes () de los Hilos
                                              devuelve una representación de matriz de bytes de la cadena. */
        
        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7; //Operador ternario igual al IF
                                                                           /*El puerto eco(Echo) por defecto es 7. Si nosotros espesificamos un tercer parámetro
                                                                             Integer.parseint() esta toma la cadena y devuelve el valor enterio equivalente.*/
                
        //CREA SOCKET QUE SE CONECTA AL SERVIDOR SOBRE UN PUERTO ESPECÍFICO
        Socket socket = new Socket(server, servPort);  /*Crea un objeto de clase Socket que recibe 2 parámetros //(NOTA: El constructor Socket crea un socket y lo conecta con el servidor especificado, identificados ya por el nombre o IP) 
                                                         El cliente establece una conexión con la máquina host a través del puerto que se designe en port#. 
                                                         El cliente y el servidor se comunican con manejadores InputStream y OutputStream  Si se está programando un cliente, 
                                                         el socket se abre de la forma:  
                                                        "Socket miSocket; miSocket= new Socket(host, puerto);" Donde 'host' es el nombre de la máquina sobre la que se está intentando abrir la conexión y 
                                                        'puerto' es el puerto (un número) que el servidor está atendiendo.  
                                                         Tenga en cuenta que TCP solo se encarga de direcciones IP, si se le da un nombre la aplicación lo resuelve a la dirección correspondiente.
                                                         Si el intento de conexión falla, el constructor lanza(throw) una IOException*/
        System.out.println("Conectado al servidor... enviando cadena eco(Echo) ");
        
        InputStream in = socket.getInputStream();   /*  Para poder leer los datos por sockets, el objeto tipo Sockets devuelve un objeto 'InputStream' o 'OutputStream' según el caso*/
        OutputStream out = socket.getOutputStream(); /* Para el envío de datos (Output) se puede usar directamente OutputStream en el caso de
                                                        que SE QUIERA ENVIAR UN FLUJO DE BYTES SIN BÚFER o también un objeto tipo "Stream" 
                                                        basado en el OutputStream que proporciona el socket(como en este caso). 
                                                        Asociado a cada instancia Socket conectado es un InputStream y un OutputStream.  Enviamos datos a través del Socket escribiendo bytes al OutputStream al igual que lo haríamos con cualquier
                                                        otra corriente, y recibimos leyendo desde el InputStream. */
        
        out.write(data); //Envía la cadena codificada al servidor. // El método write () del OutputStream transmite el conjunto de bytes dada por la conexión
                                                                   //al servidor
        
        //RECIBE LA MISMA CADENA DENUEVO DESDE EL SERVIDOR
        int totalBytesRcvd = 0; //Total de bytes recibidos hasta el momento.
        int bytesRcvd; //total de bytes recibidos en la última lectura.
        while(totalBytesRcvd < data.length){
            if ((bytesRcvd = in.read(data, totalBytesRcvd, 
                    data.length - totalBytesRcvd)) == -1){    //El método Read() el número total de bytes leídos en la memoria intermedia, 
                                                             //o -1 si no hay más datos porque se ha alcanzado el final de la secuencia. 
                                                             /*¿Por qué no una sola lectura (read)? 
                                                               TCP no conserva read () y write () mensaje obligados. Es decir, a pesar de que enviamos la cadena de eco con una sola escritura write (), 
                                                               el servidor de eco puede recibirlo en varios trozos. Incluso si la cadena de eco se maneja en un trozo por el servidor de eco, 
                                                               la respuesta todavía puede romperse en pedazos por TCP. Uno de los más comunes errores para los principiantes 
                                                               es el supuesto de que los datos enviados por una sola escritura write() siempre serán recibidos en una sola lectura read(). 
 */
            throw new SocketException("Conexión cerrada prematuramente...");
            }//Fin del IF
            
            totalBytesRcvd += bytesRcvd; //cuenta el número de bytes totales.
        } //La matriz de datos (Data array) está llena
        
        System.out.println("Recibido: " + new String(data)); /* Para imprimir la respuesta del servidor, debemos convertir la matriz de bytes en una cadena mediante el predeterminado
                                                                codificación de caracteres. */
        
        socket.close(); //Cierre del Socket y sus flujos de datos.
        
    }
    
}
