package sarsockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import static java.lang.System.*;

public class VoteClientUDP {

    public static void main(String args[]) throws IOException {

        // Al llamar a connect (), que no tenemos que 1) especificar una dirección / puerto remoto para cada datagrama
        //enviamos y 2) prueba la fuente de cualquier datagramas que recibimos. 
        if (args.length != 3) { // Verificar el número corecto de argumentos
            throw new IllegalArgumentException("Parámetro(s): <Destinación>"
                    + " <Puerto> <Candidato#>");
        }

        InetAddress destAddr = InetAddress.getByName(args[0]); // Dirección de destino
        int destPort = Integer.parseInt(args[1]); // Puerto de Destino
        int candidate = Integer.parseInt(args[2]); // 0 <= candidato <= 1000 

        DatagramSocket sock = new DatagramSocket(); // Creación de un socket UDP para envío
        sock.connect(destAddr, destPort);

        // Crea un mensaje de voto (2nd param false = vote)
        /* Esta vez utilizamos un codificador de texto; Sin embargo, podríamos cambiar fácilmente a un codificador binario. Nota
         que no necesitamos un enmarcador porque UDP ya preserva límites de mensaje para nosotros como
         siempre que cada envío contiene exactamente un mensaje votar. 
         */
        VoteMsg vote = new VoteMsg(false, false, candidate, 0);

        // Cambia el texto a binario para una estrategia de codificación diferente
        VoteMsgCoder coder = new VoteMsgTextCoder();

        // Envía los requerimientos
        byte[] encodedVote = coder.toWire(vote);
        out.println("Enviando Texto-Decodificado Requerido (" + encodedVote.length
                + " bytes): ");
        out.println(vote);
        DatagramPacket message = new DatagramPacket(encodedVote, encodedVote.length);
        sock.send(message);

        // Recibir  respuesta
        /*
         Al crear el DatagramPacket, necesitamos saber el tamaño máximo de mensaje para evitar
         truncamiento datagrama. Por supuesto, cuando desciframos el datagrama, sólo usamos el actual
         bytes del datagrama así que usamos Arrays.copyOfRange () para copiar la subsecuencia de la
         datagrama matriz de soporte. 
         */
        message = new DatagramPacket(new byte[VoteMsgTextCoder.MAX_WIRE_LENGTH],
                VoteMsgTextCoder.MAX_WIRE_LENGTH);
        sock.receive(message);
        encodedVote = Arrays.copyOfRange(message.getData(), 0, message.getLength());
        System.out.println("Reenviando Texto-Decodificado de Respuesta (" + encodedVote.length
                + " bytes): ");
        vote = coder.fromWire(encodedVote);
        System.out.println(vote);

    }

}
