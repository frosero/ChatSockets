
package sarsockets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Arrays;
import static java.lang.System.*;

public class VoteClientUDP {
    
    public static void main(String args[]) throws IOException {
        if (args.length != 3) { // Test for correct # of args
      throw new IllegalArgumentException("Parámetro(s): <Destinación>" +
                                          " <Puerto> <Candidato#>");
    }
        
    InetAddress destAddr = InetAddress.getByName(args[0]); // Dirección de destino
    int destPort = Integer.parseInt(args[1]); // Puerto de Destino
    int candidate = Integer.parseInt(args[2]); // 0 <= candidato <= 1000 
    
    DatagramSocket sock = new DatagramSocket(); // Creación de un socket UDP para envío
    sock.connect(destAddr, destPort);
    
    // Crea un mensaje de voto (2nd param false = vote)
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
    
    // Reenvío de respuesta
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
