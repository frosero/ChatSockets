package sarsockets;
import java.io.OutputStream;
import java.net.Socket;


public class voteClientTCP {
    public static final int CANDIDATEID = 888;
    public static void main(String args[]) throws Exception {
        if (args.length != 2) { // Prueba si el número argumentos es correcto
             throw new IllegalArgumentException("Parámeto(s): <Servidor> <Puerto>");
    }
    String destAddr = args[0]; // Dirección de destino
    int destPort = Integer.parseInt(args[1]); // Puerto de destino

    Socket sock = new Socket(destAddr, destPort);
    OutputStream out = sock.getOutputStream();  
    
    // Cambia de binario a texto para una diferente estrategia de codificación
    VoteMsgCoder coder = new VoteMsgBinCoder();
    // Cambia la longitud de Delim para una diferente estrategia de codificación
    Framer framer = new LengthFramer(sock.getInputStream());
    // Creación de un requerimiento de sufrajio (2nd arg = true)
    VoteMsg msg = new VoteMsg(false, true, CANDIDATEID, 0);
    byte[] encodedMsg = coder.toWire(msg);

    // Envío del requerimiento
    System.out.println("Enviando investigación (" + encodedMsg.length + " bytes): ");
    System.out.println(msg);
    framer.frameMsg(encodedMsg, out);
    

    // Envío de un nuevo voto
    msg.setInquiry(false);
    encodedMsg = coder.toWire(msg);
    System.out.println("Envío de voto (" + encodedMsg.length + " bytes): ");
    framer.frameMsg(encodedMsg, out);
    
    // Reenvio de investigación de respuesta
    encodedMsg = framer.nextMsg();
    msg = coder.fromWire(encodedMsg);
    System.out.println("Envío de respuesta (" + encodedMsg.length
               + " bytes): ");
    System.out.println(msg);

    // Reenvío de respuesta de voto 
    msg = coder.fromWire(framer.nextMsg());
    System.out.println("Reenvío de respuesta (" + encodedMsg.length
           + " bytes): ");
    System.out.println(msg);
    
    sock.close();
    
    }
}


/*
 A continuación mostramos cómo implementar un cliente de votación TCP que conecta más de un socket TCP a
el servidor de votación, envía una consulta seguido por un voto, y luego recibe la consulta y votar
respuestas. 
*/