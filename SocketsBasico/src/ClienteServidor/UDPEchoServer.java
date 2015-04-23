package ClienteServidor;                                            //NOTA IMPORTANTE: en UDP no es necesario correr primero el servidor y luego el cliente, ya que funciona como un buzón de mensajes
import static java.lang.System.*;                                   //El cliente espera unos segundos antes de descartar el mensaje si no obtiene respuesta del servidor. el número de puerto siempre debe ser el mismo.
import java.io.IOException;                                         //Parámetros a ingredar(ejemplo): en UDPEchoClientTimeOut.java >> 127.0.0.1 "hola UDP" 1025 
import java.net.DatagramPacket;                                     // en UDPEchoServer.java >> 1025
import java.net.DatagramSocket;

public class UDPEchoServer {

  private static final int ECHOMAX = 255; // Maximum size of echo datagram

  public static void main(String[] args) throws IOException {
//
    if (args.length != 1) { // Test for correct argument list
      throw new IllegalArgumentException("Parameter(s): <Port>");
    }

    int servPort = Integer.parseInt(args[0]);

    DatagramSocket socket = new DatagramSocket(servPort);
    DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

    while (true) { // Run forever, receiving and echoing datagrams
      socket.receive(packet); // Receive packet from client
      out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
      socket.send(packet); // Send the same packet back to client
      packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
    }
    /* NOT REACHED */
  }
}
