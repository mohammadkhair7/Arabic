   package net.oujda_nlp_team.Spline;
   
   import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
   
   public class Phi implements Serializable {
     private int indice;
     
     private String tag;
     
     private String lastTag;
     
     private double frequency;
     
     private List<String> previousTag = new ArrayList<>();
     
     private static final long serialVersionUID = 12390L;
     
     public Phi() {
       this.tag = "";
       this.frequency = 0.0D;
       this.previousTag = new ArrayList<>();
     }
     
     public Phi(String _tag, double _frequency, List<String> _list) {
       this.tag = _tag;
       this.frequency = _frequency;
       this.previousTag = _list;
     }
     
     public String getPreviousTag(int indice) {
       return this.previousTag.get(indice);
     }
     
     public List<String> getAllTags() {
       return this.previousTag;
     }
     
     public int getIndice() {
       return this.indice;
     }
     
     public String getTag() {
       return this.tag;
     }
     
     public String getLastTag() {
       return this.lastTag;
     }
     
     public double getFrequency() {
       return this.frequency;
     }
     
     public void setIndice(int _indice) {
       this.indice = _indice;
     }
     
     public void setTag(String _tag) {
       this.tag = _tag;
     }
     
     public void setLastTag(String _lastTag) {
       this.lastTag = _lastTag;
     }
     
     public void setFrequency(double _frequency) {
       this.frequency = _frequency;
     }
     
     public void setPreviousTag(String _previousTag) {
       this.previousTag.add(_previousTag);
     }
   }
