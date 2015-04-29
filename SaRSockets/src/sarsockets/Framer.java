
package sarsockets;

import java.io.IOException;
import java.io.OutputStream;

//Una interface sirve para establecer un 'protocolo' entre clases. 
public interface Framer {
  void frameMsg(byte[] message, OutputStream out) throws IOException;
  byte[] nextMsg() throws IOException;
    
}
