package ClienteServidor;                                            //NOTA IMPORTANTE: en UDP no es necesario correr primero el servidor y luego el cliente, ya que funciona como un buzón de mensajes
import static java.lang.System.*;                                   //El cliente espera unos segundos antes de descartar el mensaje si no obtiene respuesta del servidor. el número de puerto siempre debe ser el mismo.
import java.io.IOException;                                         //Parámetros a ingredar(ejemplo): en UDPEchoClientTimeOut.java >> 127.0.0.1 "hola UDP" 1025 
import java.net.DatagramPacket;                                     // en UDPEchoServer.java >> 1025
import java.net.DatagramSocket;

public class UDPEchoServer {

  private static final int ECHOMAX = 255; // Longitud mázima del datagrama

  public static void main(String[] args) throws IOException {
//
    if (args.length != 1) { // Prueba del número correcto de argumentos
      throw new IllegalArgumentException("Parámetro(s): <Puerto>");
    }

    int servPort = Integer.parseInt(args[0]);

    DatagramSocket socket = new DatagramSocket(servPort);
    DatagramPacket packet = new DatagramPacket(new byte[ECHOMAX], ECHOMAX);

    while (true) { // Corre por siempre, recibiendo y haciendo eco al datagrama
      socket.receive(packet); // Recibe el paquete del cliente
      out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
      socket.send(packet); // Envía el mismo paquete denuevo al cliente
      packet.setLength(ECHOMAX); // Cambia la longitud para evitar la reducción del buffer
    }
    
  }
}
