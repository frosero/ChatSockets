package sarsockets;
import static java.lang.System.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class VoteServerTCP {
    
      public static void main(String args[]) throws Exception {
              if (args.length != 1) { // Prueba de número de argumentos correctos
      throw new IllegalArgumentException("Parámetro(s): <Puerto>");
    }
              int port = Integer.parseInt(args[0]); // Se guarda el número de puerto en la varaible port
              ServerSocket servSock = new ServerSocket(port); //Objeto con el atributo port 
              
//CAMBIAR DE TEXTO A BINARIO SOBRE LAS DIREFENTES CODIFICACIONES DEL CLIENTE/SERVIDOR (ESTRATEGIA DE ENCUADRE)
              VoteMsgCoder coder = new VoteMsgBinCoder();                               //CLASE VoteMsgCoder
              VoteService service = new VoteService();                                  //CLASE VoteService
              
              while (true) {
               Socket clntSock = servSock.accept();
               out.println("Manejo de cliente en" +clntSock.getLocalSocketAddress());
               //Cambiar Longitud de DELIM por una estrategia diferente encuadre
               Framer framer = new LengthFramer(clntSock.getInputStream());             //CLASE Framer //CLASE LengthFramer
               try{
                   byte[] req;
                   while ((req = framer.nextMsg()) != null) {
                       out.println("Mensaje recibido (" + req.length + " bytes)");
                       VoteMsg responseMsg = service.handleRequest(coder.fromWire(req)); //CLASE VoteMsg
                       framer.frameMsg(coder.toWire(responseMsg), clntSock.getOutputStream());
                   }
                  
              }catch (IOException ioe){
                  err.println("Error manejo del cliente: " + ioe.getMessage());
              } finally {
                   out.println("Cerrando Conexión");
                   clntSock.close();
               }
                  
                  
              }
          
          
      }
    
}

/*
 Siguiente demostramos la versión TCP del servidor de votación. Aquí, el servidor acepta repetidamente
una nueva conexión de cliente y utiliza la VoteService para generar respuestas a la votación del cliente
mensajes. 

*/