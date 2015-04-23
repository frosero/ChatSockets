package ClienteServidor;                            //NOTA IMPORTANTE: en UDP no es necesario correr primero el servidor y luego el cliente, ya que funciona como un buzón de mensajes
import static java.lang.System.*;                                                    //El cliente espera unos segundos antes de descartar el mensaje si no obtiene respuesta del servidor. el número de puerto siempre debe ser el mismo
import java.net.DatagramSocket;                     //Parámetros a ingredar(ejemplo): en UDPEchoClientTimeOut.java >> 127.0.0.1 "hola UDP" 1025 
import java.net.DatagramPacket;                     // en UDPEchoServer.java >> 1025
import java.net.InetAddress;
import java.io.IOException;
import java.io.InterruptedIOException;

public class UDPEchoClientTimeout {

  private static final int TIMEOUT = 3000;   // Tiempo de espera para cada reenvío (milisegundos).
  private static final int MAXTRIES = 5;     // Maximo de retransmisiones.

  public static void main(String[] args) throws IOException {

    if ((args.length < 2) || (args.length > 3)) { // Prueba de número de argumentos correctos
      throw new IllegalArgumentException("Parámetros(s): <Servidor> <Palabra> [<Puerto>]");
    }
    InetAddress serverAddress = InetAddress.getByName(args[0]);  // Dirección del servidor
    

    // CONVERTIR EL ARGUMENTO STRING EN BYTES UTILIZANDO LA CODIFICACIÓN POR DEFECTO
    byte[] bytesToSend = args[1].getBytes();

    int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;

    DatagramSocket socket = new DatagramSocket();

    socket.setSoTimeout(TIMEOUT);  // Máxima tiempo de bloqueo para recibir (milisegundos).

    DatagramPacket sendPacket = new DatagramPacket(bytesToSend,  // Envio de paquetes
        bytesToSend.length, serverAddress, servPort);

    DatagramPacket receivePacket =                              // Recepción de paquetes
        new DatagramPacket(new byte[bytesToSend.length], bytesToSend.length);

    int tries = 0;      // Los paquetes se pueden perder, entonces se sigue intentando
    boolean receivedResponse = false;
    do {
      socket.send(sendPacket);          // Envía la cadena ECO (Echo)
      try {
        socket.receive(receivePacket);  // intento de recepción por parte de la cadena ECO (Echo)

        if (!receivePacket.getAddress().equals(serverAddress)) {// Comprobar Fuente
          throw new IOException("Se recibió un paquete de una fuente desconocida");
	}
        receivedResponse = true;
      } catch (InterruptedIOException e) {  // Excepción "Cuando no se a conceguido nada"
        tries += 1;
        out.println("Tiempo agotado, " + (MAXTRIES - tries) + " intento(s) más...");
      }
    } while ((!receivedResponse) && (tries < MAXTRIES));

    if (receivedResponse) {
      out.println("Recibido: " + new String(receivePacket.getData()));
    } else {
      out.println("No responde -- darse por vencido.");
    }
    socket.close();
  }
}
