   package net.oujda_nlp_team.entity;
   
   import java.io.Serializable;
   
   public class VEntity implements Serializable {
     private static final long serialVersionUID = 1239L;
     
     private String id;
     
     private String valAR;
     
     private String valEN;
     
     public String getId() {
       return this.id;
     }
     
     public String getValAR() {
       return this.valAR;
     }
     
     public String getValEN() {
       return this.valEN;
     }
     
     public void setId(String _id) {
       this.id = _id;
     }
     
     public void setValAR(String _valAR) {
       this.valAR = _valAR;
     }
     
     public void setValEN(String _valEN) {
       this.valEN = _valEN;
     }

	@Override
	public String toString() {
		return "VEntity [id=" + id + ", valAR=" + valAR + ", valEN=" + valEN + "]";
	}
     
   }

