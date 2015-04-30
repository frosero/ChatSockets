
package sarsockets;

import java.io.IOException;
public interface VoteMsgCoder {
    /*
     El método toWire () convierte el mensaje de voto a una secuencia de bytes de acuerdo con un particular,
    protocolo, y la fromWire () método analiza una secuencia dada de bytes de acuerdo con el mismo
    protocolo y construye una instancia de la clase de mensaje. */
    
    byte[] toWire(VoteMsg msg) throws IOException;
    
    VoteMsg fromWire(byte[] input) throws IOException;
    
}
