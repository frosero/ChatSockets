package sarsockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Arrays;
import static java.lang.System.*;

public class VoteServerUDP {

    public static void main(String[] args) throws IOException {
        if (args.length != 1) { // Verificación de número de parámetros correctos
            throw new IllegalArgumentException("Parámatro(s): <Puerto>");
        }
        int port = Integer.parseInt(args[0]); // Reciviendo el puerto

        DatagramSocket sock = new DatagramSocket(port); // Reciviendo el socket

        byte[] inBuffer = new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH];
        // Cambio de binario a texto para un enfoque diferente
        VoteMsgCoder coder = new VoteMsgTextCoder();
        VoteService service = new VoteService();

        while (true) {

            DatagramPacket packet = new DatagramPacket(inBuffer, inBuffer.length);
            //UDP hace el encuadre para nosotros!
            sock.receive(packet);
            byte[] encodedMsg = Arrays.copyOfRange(packet.getData(), 0, packet.getLength());
            out.println("Manejo de petición de " + packet.getSocketAddress() + " ("
                    + encodedMsg.length + " bytes)");
            try {
                //El servicio devuelve una respuesta al mensaje
                VoteMsg msg = coder.fromWire(encodedMsg);
                msg = service.handleRequest(msg);
                packet.setData(coder.toWire(msg));
                System.out.println("Sending response (" + packet.getLength() + " bytes):");
                System.out.println(msg);
                sock.send(packet);

            } catch (IOException ioe) {
                err.println("Error léxico en el mensaje" + ioe.getMessage());
            }

        }

    }

}
