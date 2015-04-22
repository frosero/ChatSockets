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
        ServerSocket servSock = new ServerSocket(servPort); //Creación de un objeto tió ServerSocket.
        
        int recvMsgSize;
        byte[] receoveBuf = new byte[BUFSIZE]; //Recibe búfer.
        
        while(true) {   //Correr para siempre, la aceptación y el servicio de conexiones
            Socket clntSock = servSock.accept(); //Obtiene, acepta o abre la conexión al cliente.
            
            SocketAddress clientAddress = clntSock.getRemoteSocketAddress(); //Obtiene la dirección del cliente
            out.println("Manejo de cliente en " + clientAddress);
            
        }
        
    }
}
