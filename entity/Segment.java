   package net.oujda_nlp_team.entity;
   
   import java.io.Serializable;
   
   public class Segment implements Serializable {
     private static final long serialVersionUID = 121214L;
     
     private Clitic proclitic;
     
     private String stem;
     
     private Clitic enclitic;
     
     public Segment() {}
     
     public Segment(Clitic _proclitic, String _stem, Clitic _enclitic) {
       setProclitic(_proclitic);
       setStem(_stem);
       setEnclitic(_enclitic);
     }
     
     public Clitic getProclitic() {
       return this.proclitic;
     }
     
     public String getStem() {
       return this.stem;
     }
     
     public Clitic getEnclitic() {
       return this.enclitic;
     }
     
     public void setProclitic(Clitic _proclitic) {
       this.proclitic = _proclitic;
     }
     
     public void setStem(String _stem) {
       this.stem = _stem;
     }
     
     public void setEnclitic(Clitic _enclitic) {
       this.enclitic = _enclitic;
     }
     
     public String toString() {
       return getProclitic().getUnvoweledform() + "|" + getProclitic().getVoweledform() + "|" + getStem() + "|" + getEnclitic().getVoweledform() + "|" + getEnclitic().getUnvoweledform();
     }
   }

