package threadsockets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EchoProtocol implements Runnable {

    private static final int BUFSIZE = 32; // Longitud de I/O búfer (en bytes) 
    private Socket clntSock;               // Conexión del Socket al cliente
    private Logger logger;                 // Servidor anfotrión

    public EchoProtocol(Socket clntSock, Logger logger) {
        this.clntSock = clntSock;
        this.logger = logger;
    }

    public static void handleEchoClient(Socket clntSock, Logger logger) {
        try {
            // Obtiene la entrada y salidade los I/O streams del socket
            InputStream in = clntSock.getInputStream();
            OutputStream out = clntSock.getOutputStream();

            int recvMsgSize; // ancho del mensaje recibido
            int totalBytesEchoed = 0; // Bytes recibidos por el cliente
            byte[] echoBuffer = new byte[BUFSIZE]; // Recepción del búfer
            // Recive hasta que el cliente cierre la conexión, indicado por el valor -1
            while ((recvMsgSize = in.read(echoBuffer)) != -1) {
                out.write(echoBuffer, 0, recvMsgSize);
                totalBytesEchoed += recvMsgSize;
            }

            logger.info("Cliente " + clntSock.getRemoteSocketAddress() + ", replicado "
                    + totalBytesEchoed + " bytes.");

        } catch (IOException ex) {
            logger.log(Level.WARNING, "Excepción en el protocolo eco (ECHO)", ex);
        } finally {
            try {
                clntSock.close();
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void run() {
        handleEchoClient(clntSock, logger);
    }

}
