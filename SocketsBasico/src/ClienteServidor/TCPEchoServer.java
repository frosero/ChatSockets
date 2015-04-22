package ClienteServidor;                                               //NOTA IMPORTANTE: PARA PROBAR EL PROGRAMA
import static java.lang.System.*;                                      // 1. Correr la clase servidor TCPEchoServer.java ingresando como parámetro un puerto no ocupado (los puertos están ocupados desde 1 al 1023)  //para ingresar un argumento, (click derecho en el proyecto > Propiedades > run(o ejecutar) > Argumentos > [escribimos el parámetro o parámetros separados por espacios de ser más de uno])
import java.net.*;  //Para los socket, serversoket and InetAdrress     // 2. Mientras la clase TCPEchoServer.java se está ejecutando, ingresamos los 3 parámetros en la clase TCPEchoClient.java. Tomar en cuenta que el tercer parámetro es el puerto que usa el servidor(clase arrancada anteriormente) y este debe ser igual. Los parámetros a ingresar son Host, Mensaje, Puerto
import java.io.*;   //Para IOException and Input/OutputStream          //Aclaración final: Los parámetros para la clase TCPEchoClient.java si se va aprobar desde una misma máquina los argumentos serían 127.0.0.1 "mensaje" [Puerto]

public class TCPEchoServer {
    
    private static final int BUFSIZE = 32; //Tamaño del búfer de recepción
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1) //Prueba del número correcto de argumentos
           throw new IllegalArgumentException("Parámetro(s): <Puerto>");
        
        int servPort = Integer.parseInt(args[0]);
        
        //CREACIÓN DE UN SOCKET DE SERVIDOR PARA ACEPTAR SOLICITUDES DE CONEXIÓN AL CLIENTE
        ServerSocket servSock = new ServerSocket(servPort); //Creación de un objeto tipo ServerSocket. //servSock escucha las solicitudes de conexión de cliente en el puerto especificado en el constructor.
        
        int recvMsgSize;
        byte[] receiveBuf = new byte[BUFSIZE]; //Recibe búfer.
        
        while(true) {   //Correr para siempre, la aceptación y el servicio de conexiones
            Socket clntSock = servSock.accept(); //Obtiene, acepta o abre la conexión al cliente. //Cuando se establece una conexión esta bloquea cualquier otra conexión, el resto de conexiones quedan en cola.
            
            SocketAddress clientAddress = clntSock.getRemoteSocketAddress(); //Obtiene la dirección del cliente //
            out.println("Servicio dado al cliente en " + clientAddress);
            
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();
            
            //RECIBE HASTA QUE EL CLIENTE CIERRA LA CONEXIÓN, ESTO SE INDICA CUANDO RETORNA -1
            while ((recvMsgSize = in.read(receiveBuf)) != -1) {
                out.write(receiveBuf,0,recvMsgSize);
            }
            clntSock.close(); //Cierre del Socket 
            
        }
        //no alcanza
    }
}
