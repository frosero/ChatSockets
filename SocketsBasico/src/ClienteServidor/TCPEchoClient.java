package ClienteServidor;
import static java.lang.System.*;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class TCPEchoClient {
    public static void main(String[] args) throws IOException { //throws, permite manejar la excepción sin dar un tratamiento específico, quiere decir q si se produce una excepción el programa lanza (throws) a otro pedazo de código su ejecución; lo que no pasa con los bloques (try/catch)
        if ((args.length < 2) || (args.length > 3)) // Prueba de un correcto número de argumentos.
            throw new IllegalArgumentException("Parámetro(s): <Servidor> <Palabra> <Puerto>"); //cuando ocurre la excepción nos regresa un mensaje (manejo de excepciones cuando los métodos no son compatibles con todos los tipos de parámatros)
        
        String server = args[0]; //Nombre del servidor o dirección IP
        //Convierte la cadena a bytes usando la decodificación de caracteres por defecto.
        byte[] data = args[1].getBytes(); //arreglo tipo byte que proporciona mejor manejo de espacios de memoria que un int 
        
        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7; //Operador ternario igual al IF
        
        //CREA SOCKET QUE SE CONECTA AL SERVIDOR SOBRE UN PUERTO ESPECÍFICO
        Socket socket = new Socket(server, servPort);  /*Crea un objeto de clase Socket que recibe 2 parámetros  
                                                         El cliente establece una conexión con la máquina host a través del puerto que se designe en port#. 
                                                         El cliente y el servidor se comunican con manejadores InputStream y OutputStream  Si se está programando un cliente, 
                                                         el socket se abre de la forma:  
                                                        "Socket miSocket; miSocket= new Socket(host, puerto);" Donde 'host' es el nombre de la máquina sobre la que se está intentando abrir la conexión y 
                                                        'puerto' es el puerto (un número) que el servidor está atendiendo.  
                                                        */
        System.out.println("Conectado al servidor... enviando cadena eco(Echo) ");
        
        InputStream in = socket.getInputStream();   /*  Para poder leer los datos por sockets*/
        OutputStream out = socket.getOutputStream();
        
        out.write(data); //Envía la cadena codificada al servidor.
        
        //RECIBE LA MISMA CADENA DENUEVO DESDE EL SERVIDOR
        int totalBytesRcvd = 0; //Total de bytes recibidos hasta el momento.
        int bytesRcvd; //total de bytes recibidos en la última lectura.
        while(totalBytesRcvd < data.length){
            if ((bytesRcvd = in.read(data, totalBytesRcvd, 
                    data.length - totalBytesRcvd)) == -1) 
            throw new SocketException("Conexión cerrada prematuramente...");
            totalBytesRcvd += bytesRcvd;
        } //La matriz de datos (Data array) está llena
        
        System.out.println("Received: " + new String(data));
        
        socket.close(); //Cierre del Socket y sus flujos de datos.
        
    }
    
}
