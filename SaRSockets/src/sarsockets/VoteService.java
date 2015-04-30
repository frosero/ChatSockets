
package sarsockets;

import java.util.HashMap;
import java.util.Map;

public class VoteService {
    // Mapa de candidatos con respecto número de votos
  private Map<Integer, Long> results = new HashMap<Integer, Long>();

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
