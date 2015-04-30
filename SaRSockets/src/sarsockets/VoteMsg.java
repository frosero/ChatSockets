
package sarsockets;


public class VoteMsg {
    private boolean isInquiry; // Verdadero si ;Falso si votó
    private boolean isResponse; //true if response from server
    private int candidateID; //en [0,1000]
    private long voteCount; //Distinto de 0 solo en respuesta
    
    public static final int MAX_CANDIDATE_ID = 1000;
    
  public VoteMsg(boolean isResponse, boolean isInquiry, int candidateID, long voteCount)
      throws IllegalArgumentException {
    // Verificación de Invariantes
    if (voteCount != 0 && !isResponse) {
      throw new IllegalArgumentException("Solicitud de recuento el voto debe ser cero");
    }
    if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
      throw new IllegalArgumentException("Mal candidato ID: " + candidateID);
    }
    if (voteCount < 0) {
      throw new IllegalArgumentException("El total debe ser >= cero");
    }
    this.candidateID = candidateID;
    this.isResponse = isResponse;
    this.isInquiry = isInquiry;
    this.voteCount = voteCount;
  }
  
  public void setInquiry(boolean isInquiry){
      this.isInquiry = isInquiry;
  }
  
  public void setResponse(boolean isResponse){
      this.isResponse = isResponse;
  }
  
  public boolean isInquiry() {
      return isInquiry;
  }
  
  public boolean isResponse() {
      return isResponse;
  }
  
    public void setCandidateID(int candidateID) throws IllegalArgumentException {
    if (candidateID < 0 || candidateID > MAX_CANDIDATE_ID) {
      throw new IllegalArgumentException("Mal candidato ID: " + candidateID);
    }
    this.candidateID = candidateID;
  }

  public int getCandidateID() {
    return candidateID;
  }

  public void setVoteCount(long count) {
    if ((count != 0 && !isResponse) || count < 0) {
      throw new IllegalArgumentException("Voto Mal contado");
    }
    voteCount = count;
  }
  
    public long getVoteCount() {
    return voteCount;
  }
  
  public String toString(){
      String res = (isInquiry ? "Investigación" : "voto") + " para el candidato " + candidateID;
      if(isResponse){
          res = "Respuesta a " + res + " que ahora tiene " + voteCount + " vote(s)";
      }
      return res;
  }
            
}

/*


 En la aplicación de un protocolo, es útil definir una clase para contener la información transportada
en un mensaje. La clase proporciona métodos para la manipulación de los campos del mensaje mientras-
el mantenimiento de los invariantes que se supone que mantenga entre los campos. Para nuestro simple ejem-
plo, los mensajes enviados por el cliente y el servidor son muy similares. La única diferencia es que la
los mensajes enviados por el servidor contienen el recuento de votos y una bandera que indica que son respuestas
(no peticiones). En este caso, podemos salir con una sola clase para ambos tipos de mensajes.



 Clase VoteMsg.java muestra la información básica de cada mensaje:
■
un booleano isInquiry , lo cual es cierto si la transacción solicitada es una investigación (y falsa si
es un voto real);
■
un booleano isResponse que indica si el mensaje es una respuesta (enviado por el servidor) o
solicitud;
■
un número entero candidateID que identifica el candidato;
■
un largo voteCount indicando el total de votos para el candidato solicitado
La clase mantiene las siguientes invariantes entre los campos:
■
candidateID está en el rango de 0-1000.
■
voteCount sólo es distinto de cero en los mensajes de respuesta ( isResponse es cierto).
■
voteCount es no negativo.

*/