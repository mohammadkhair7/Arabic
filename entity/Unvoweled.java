   package net.oujda_nlp_team.entity;
   
   import java.io.Serializable;
   
   public class Unvoweled implements Serializable {
     private static final long serialVersionUID = 1236L;
     
     private String val;
     
     private String rules;
     
     private String ids;
     
     private String id;
     
     private String vowform;
     
     private String pos;
     
     private String cas;
     
     public void setVal(String _val) {
       this.val = _val;
     }
     
     public void setRules(String _rules) {
       this.rules = _rules;
     }
     
     public void setIds(String _ids) {
       this.ids = _ids;
     }
     
     public void setId(String _id) {
       this.id = _id;
     }
     
     public void setVoweledform(String _vowform) {
       this.vowform = _vowform;
     }
     
     public void setPartOfSpeech(String _pos) {
       this.pos = _pos;
     }
     
     public void setCase(String _cas) {
       this.cas = _cas;
     }
     
     public String getVal() {
       return this.val;
     }
     
     public String getRules() {
       return this.rules;
     }
     
     public String getIds() {
       return this.ids;
     }
     
     public String getId() {
       return this.id;
     }
     
     public String getVoweledform() {
       return this.vowform;
     }
     
     public String getPartOfSpeech() {
       return this.pos;
     }
     
     public String getCase() {
       return this.cas;
     }
     
     public String toString() {
       return this.val + ";" + this.rules + ";" + this.ids;
     }
   }

