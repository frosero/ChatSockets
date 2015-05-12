
package sarsockets;

import java.util.HashMap;
import java.util.Map;

public class VoteService {
    // Mapa de candidatos con respecto número de votos
  private Map<Integer, Long> results = new HashMap<Integer, Long>(); //Para cualquier consulta, el ID de candidato determinado
                                                                     //se utiliza para buscar un paso de votar por el candidato
                                                                     //en el mapa. Por votos, el voto cuente incrementado se 
                                                                     //almacena de nuevo en el mapa

  public VoteMsg handleRequest(VoteMsg msg) {
    if (msg.isResponse()) { // Si a respondido o simplemente quiere enviar denuevo
      return msg;
    }
    msg.setResponse(true); // Hacer Un mensaje de respuesta
    // Obtener ID del candidato y conteo de votos
    int candidate = msg.getCandidateID();
    Long count = results.get(candidate);
    if (count == null) {
      count = 0L; // El candidato no existe
    }
    if (!msg.isInquiry()) {
      results.put(candidate, ++count); // Si votaron, incrementa el contador
    }
    msg.setVoteCount(count);
    return msg;
  }
}
/* Envío y recepción del mensaje (canal lógico de transmisión???)

 El envío de un mensaje a través de una corriente es tan simple como crear, llamando toWire (), añadiendo apropiada
enmarcando la información y escribirla. Recibir, por supuesto, hace las cosas en el orden inverso. Este
enfoque se aplica a TCP; en UDP encuadre explícita no es necesaria, ya que los límites de mensajes
se conservan. Para demostrar esto, considere un servidor de votación que 1) mantiene una asignación de
ID de candidatos a número de votos, 2) conteos presentaron votos, y 3) responde a las preguntas y
vota con la cuenta actual para el candidato especificado. Empezamos por la implementación de un servicio
para el uso de los servidores de voto. Cuando un servidor voto recibe un mensaje votar, que se encarga de la solicitud de
llamando al método handleRequest () de VoteService. 

*/