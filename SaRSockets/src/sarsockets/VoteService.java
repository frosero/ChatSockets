
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
