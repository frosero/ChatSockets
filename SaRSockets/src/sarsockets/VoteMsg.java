
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
  
  public String toString(){
      String res = (isInquiry ? "Investigación" : "voto") + " para el candidato " + candidateID;
      if(isResponse){
          res = "Respuesta a " + res + " que ahora tiene " + voteCount + " vote(s)";
      }
      return res;
  }
            
}
