
package sarsockets;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/*Formato del Wire 
 *                                1  1  1  1  1  1
 *  0  1  2  3  4  5  6  7  8  9  0  1  2  3  4  5
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |     Magica     |Banderas|      CERO           |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |               ID del candidato                |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 * |                                               |
 * |       Conteo Votos (solo en respuesta)        |
 * |                                               |
 * |                                               |
 * +--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+--+
 */
public class VoteMsgBinCoder implements VoteMsgCoder{
    //DECLARACIÓN DE CONSTANTES PARA LA CODIFICACIÓN
  public static final int MIN_WIRE_LENGTH = 4;
  public static final int MAX_WIRE_LENGTH = 16;
  public static final int MAGIC = 0x5400;
  public static final int MAGIC_MASK = 0xfc00;
  public static final int MAGIC_SHIFT = 8;
  public static final int RESPONSE_FLAG = 0x0200;
  public static final int INQUIRE_FLAG =  0x0100;

    @Override
    public byte[] toWire(VoteMsg msg) throws IOException {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
    DataOutputStream out = new DataOutputStream(byteStream); // Convierte enteros

    short magicAndFlags = MAGIC;
    if (msg.isInquiry()) {
      magicAndFlags |= INQUIRE_FLAG;
    }
    if (msg.isResponse()) {
      magicAndFlags |= RESPONSE_FLAG;
    }
    out.writeShort(magicAndFlags);
    // Nosotros sabemos que el ID del candidato está en el rango > 0 && < 1000
    out.writeShort((short) msg.getCandidateID());
    if (msg.isResponse()) {
      out.writeLong(msg.getVoteCount());
    }
    out.flush();
    byte[] data = byteStream.toByteArray();
    return data;
    }

    @Override
    public VoteMsg fromWire(byte[] input) throws IOException {
        //Comprobantes de sanidad de votos
            if (input.length < MIN_WIRE_LENGTH) {
      throw new IOException("mensaje de ronda");
    }
    /*  Al igual que en la sección 3.1.1, creamos un ByteArrayOutputStream y se envuelve en una DataOutputStream
        para recibir el resultado. El método de codificación toma ventaja del hecho de que el alto orden
        dos bytes de un candidateID válidos son siempre cero. Tenga en cuenta también el uso de bit a bit-u operaciones a
        codificar los booleanos utilizando un solo bit cada uno. 
            
    */
    ByteArrayInputStream bs = new ByteArrayInputStream(input);
    DataInputStream in = new DataInputStream(bs);
    int magic = in.readShort();
    if ((magic & MAGIC_MASK) != MAGIC) {
      throw new IOException("Mala cadena mágica #: " +
			    ((magic & MAGIC_MASK) >> MAGIC_SHIFT));
    }
    boolean resp = ((magic & RESPONSE_FLAG) != 0);
    boolean inq = ((magic & INQUIRE_FLAG) != 0);
    int candidateID = in.readShort();
    if (candidateID < 0 || candidateID > 1000) {
      throw new IOException("Mal ID de candidato: " + candidateID);
    }
    long count = 0;
    if (resp) {
      count = in.readLong();
      if (count < 0) {
        throw new IOException("Conteo Votos Malos: " + count);
      }
    }
    // Se ingnora los bytes extras
    return new VoteMsg(resp, inq, candidateID, count);
    }

    
}
