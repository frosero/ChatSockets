package threadsockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

public class TCPEchoServerTheread {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) { // Verificación del número de argumentos correctos
            throw new IllegalArgumentException("Parameter(s): <Puerto>");
        }

        int echoServPort = Integer.parseInt(args[0]); // Puerto del servidor

        // Crea un socket del servidor para aceptar la conexión requerida
        ServerSocket servSock = new ServerSocket(echoServPort);
        //Loger registra eventos en un período de tiempo en particular
        Logger logger = Logger.getLogger("practical");

        // Correrá por siempre, aceptando y atendiendo hilos para la conexión
        while (true) {
            Socket clntSock = servSock.accept(); // Bloque que espera una conexión
            // Hilo que se crea al obtener una nueva conexión
            /*
             Desde EchoProtocol implementa el interface Runnable, podemos dar nuestra nueva instancia
             al constructor de Thread, y el nuevo hilo ejecutará el método run () de
             EchoProtocol (que llama handleEchoClient ()) cuando se invoca start (). 
             */
            Thread thread = new Thread(new EchoProtocol(clntSock, logger));
            thread.start();
            logger.info("Creado e iniciado el hilo " + thread.getName());
        }

    }

}
