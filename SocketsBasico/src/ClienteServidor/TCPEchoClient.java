package ClienteServidor;
import static java.lang.System.*;
import java.net.Socket;
import java.net.SocketException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class TCPEchoClient {
    public static void main(String[] args) throws IOException {
        if ((args.length < 2) || (args.length > 3)) // Prueba de un correcto número de argumentos.
            throw new IllegalArgumentException("Parámetro(s): <Servidor> <Palabra> <Puerto>");
        
        String server = args[0]; //Nombre del servidor o dirección IP
        //Convierte la cadena a bytes usando la decodificación de caracteres por defecto.
        byte[] data = args[1].getBytes();
        
        int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
        
        //CREA SOCKET QUE SE CONECTA AL SERVIDOR SOBRE UN PUERTO ESPECÍFICO
        Socket socket = new Socket(server, servPort);  //Crea un objeto de clase Socket que recibe 2 parámetros
        out.println("Conectado al servidor... enviando cadena eco ");
        
        InputStream in = socket.getInputStream();
        OutputStream out = socket.getOutputStream();
        
        out.write(data); //Envía la cadena codificada al servidor.
        
        //RECIBE LA MISMA CADENA DENUEVO DESDE EL SERVIDOR
        
    }
    
}
