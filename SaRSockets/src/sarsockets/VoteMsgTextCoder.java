
package sarsockets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class VoteMsgTextCoder implements VoteMsgCoder{
   /*
   * Formato de WIRE "VOTEPROTO" <"v" | "i"> [<RESPFLAG>] <CANDIDATE> [<VOTECNT>]
   * Charset is fixed by the wire format.
   */
    
    // constantes manifiestas para la codificación
  public static final String MAGIC = "Voting";
  public static final String VOTESTR = "v"; //V para voto
  public static final String INQSTR = "i"; // i para investigación del voto
  public static final String RESPONSESTR = "R"; // respuesta de voto

  public static final String CHARSETNAME = "US-ASCII";
  public static final String DELIMSTR = " ";
  public static final int MAX_WIRE_LENGTH = 2000;

    @Override
    public byte[] toWire(VoteMsg msg) throws IOException {
       String msgString = MAGIC + DELIMSTR + (msg.isInquiry() ? INQSTR : VOTESTR)
        + DELIMSTR + (msg.isResponse() ? RESPONSESTR + DELIMSTR : "")
        + Integer.toString(msg.getCandidateID()) + DELIMSTR
        + Long.toString(msg.getVoteCount());
    byte data[] = msgString.getBytes(CHARSETNAME);
    return data;
    }

    @Override
    public VoteMsg fromWire(byte[] message) throws IOException {
        
        ByteArrayInputStream msgStream = new ByteArrayInputStream(message);
    Scanner s = new Scanner(new InputStreamReader(msgStream, CHARSETNAME));
    boolean isInquiry;
    boolean isResponse;
    int candidateID;
    long voteCount;
    String token;
     try{
      token = s.next();
      if (!token.equals(MAGIC)) { //una secuencia de caracteres que permite a un receptor a reconocer rápidamente el mensaje 
                                  //como un mensaje de protocolo de votación, a diferencia de la basura aleatoria que pasó
                                  //para llegar al red.
        throw new IOException("Cadena Mágica mala: " + token);
      }
      token = s.next();
      if (token.equals(VOTESTR)) {
        isInquiry = false;
      } else if (!token.equals(INQSTR)) {
        throw new IOException("Mal Indicador voto/invest : " + token);
      } else {
        isInquiry = true;
      }

      token = s.next();
      if (token.equals(RESPONSESTR)) {
        isResponse = true;
        token = s.next();
      } else {
        isResponse = false;
      }
      // El toquen "token" Currente  es candidateID
      // Nota: isResponse ahora es válido
      candidateID = Integer.parseInt(token);
      if (isResponse) {
        token = s.next();
        voteCount = Long.parseLong(token);
      } else {
        voteCount = 0;
      }        
     }
     catch (IOException ioe) {
       throw new IOException("Error de análisis..."); 
     }
     return new VoteMsg(isResponse, isInquiry, candidateID, voteCount);
     /*
      El método toWire () simplemente construye una cadena que contiene todos los campos del mensaje,
    separados por espacios en blanco. El método fromWire () primero busca la cadena "mágica"; si es
    No es la primera cosa en el mensaje, se produce una excepción. Esto ilustra una muy importante
    apunte sobre la implementación de protocolos: no asumir nada sobre cualquier entrada de la red.
     */
     
    }

  
  
}


/*codificar y decodificar mensaje 
 En primer lugar, presentamos una versión en la que los mensajes se codifican como texto. El protocolo especifica que
el texto se codifica mediante el juego de caracteres US-ASCII. El mensaje comienza con una llamada "magia 
 cadena "-una secuencia de caracteres que permite a un receptor a reconocer rápidamente el mensaje como
un mensaje de protocolo de votación, a diferencia de la basura aleatoria que pasó para llegar al
red. El boolean Votar / mensaje se codifica con el carácter 'v' para una votación o "i" para una
investigación. El estado del mensaje como una respuesta se indica por la presencia del carácter 'R'.
Luego viene el ID de candidato, seguido por el recuento de votos, tanto codificados como cadenas decimales. La
VoteMsgTextCoder ofrece una codificación basada en texto de VoteMsg. 



 Su programa debe estar siempre preparado para cualquier entradas posibles, y manejarlos con gracia.
En este caso, el método fromWire () lanza una excepción si la cadena esperada no se encuentra presente.
De lo contrario, se pone los campos token modo, utilizando el ejemplo de escáner. Tenga en cuenta que el número de
campos en el mensaje depende de si se trata de una petición (enviado por el cliente) o la respuesta (enviado
por el servidor). fromWire () lanza una excepción si la entrada termina prematuramente o es de otra manera
con formato incorrecto. 


*/