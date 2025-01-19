   package net.oujda_nlp_team.Spline;
   
   import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
   
   public class MatrixB implements Serializable {
     private long wordFrequency;
     
     public Map<String, Long> allTagsFrequency = new HashMap<>();
     
     private static final long serialVersionUID = 668L;
     
     public MatrixB() {
       this.allTagsFrequency = new HashMap<>();
     }
     
     public Long getTagsFrequency(String _tag) {
       return Long.valueOf(this.allTagsFrequency.containsKey(_tag) ? ((Long)this.allTagsFrequency.get(_tag)).longValue() : 0L);
     }
     
     public long getWordFrequency() {
       return this.wordFrequency;
     }
     
     public void setWordFrequency(long _wordFrequency) {
       this.wordFrequency = _wordFrequency;
     }
     
     public void setAllTagsFrequency(String _tag, long _wordtagFrequency) {
       this.allTagsFrequency.put(_tag, Long.valueOf(_wordtagFrequency + (this.allTagsFrequency.containsKey(_tag) ? ((Long)this.allTagsFrequency.get(_tag)).longValue() : 0L)));
     }
   }
