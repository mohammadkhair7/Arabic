   package net.oujda_nlp_team.Spline;
   
   import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
   
   public class MatrixA implements Serializable {
     private String tag;
     
     private long frequency;
     
     private Map<String, Long> allNextTag = new HashMap<>();
     
     private static final long serialVersionUID = 4021L;
     
     public MatrixA() {
       this.tag = "";
       this.frequency = 0L;
       this.allNextTag = new HashMap<>();
     }
     
     public Map<String, Long> getAllNextTag() {
       return this.allNextTag;
     }
     
     public String getTag() {
       return this.tag;
     }
     
     public long getFrequency() {
       return this.frequency;
     }
     
     public void setTag(String _tag) {
       this.tag = _tag;
     }
     
     public void setFrequency(long _frequency) {
       this.frequency = _frequency;
     }
     
     public void setAllNexTag(String _nextTag, long _frequency) {
       this.allNextTag.put(_nextTag, Long.valueOf(_frequency + (this.allNextTag.containsKey(_nextTag) ? ((Long)this.allNextTag.get(_nextTag)).longValue() : 0L)));
     }
   }
