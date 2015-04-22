package ClienteServidor;
import static java.lang.System.*;
import java.net.*;  //Para los socket, serversoket and InetAdrress
import java.io.*;   //Para IOException and Input/OutputStream

public class TCPEchoServer {
    
    private static final int BUFSIZE = 32; //Tamaño del búfer de recepción
    
    public static void main(String[] args) throws IOException {
        if (args.length != 1) //Prueba del número correcto de argumentos
            throw new IllegalArgumentException("Parametro(s): <Port>");
        
        int servPort = Integer.parseInt(args[0]);
        
        //CREACIÓN DE UN SOCKET DE SERVIDOR PARA ACEPTAR SOLICITUDES DE CONEXIÓN AL CLIENTE
        ServerSocket servSock = new ServerSocket(servPort); //Creación de un objeto tipo ServerSocket. //servSock escucha las solicitudes de conexión de cliente en el puerto especificado en el constructor.
        
        int recvMsgSize;
        byte[] receiveBuf = new byte[BUFSIZE]; //Recibe búfer.
        
        while(true) {   //Correr para siempre, la aceptación y el servicio de conexiones
            Socket clntSock = servSock.accept(); //Obtiene, acepta o abre la conexión al cliente. //Cuando se establece una conexión esta bloquea cualquier otra conexión, el resto de conexiones quedan en cola.
            
            SocketAddress clientAddress = clntSock.getRemoteSocketAddress(); //Obtiene la dirección del cliente //
            out.println("Manejo de cliente en " + clientAddress);
            
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
