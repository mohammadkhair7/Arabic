   package net.oujda_nlp_team.factory;
   
   import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oujda_nlp_team.entity.VEntity;
   
   public abstract class UnderivedFactory {
     protected List iCaseOrMood;
     
     protected List partOfspeech;
     
     protected Map unvoweledForm;
     
     protected List allVoweled;
     
     public VEntity getCaseOrMood(int index) {
       return !isEmptyCaseOrMood() ? (VEntity) this.iCaseOrMood.get(index - 1) : null;
     }
     
     public Object getPartOfSpeech(int idType) {
       return this.partOfspeech.get(idType - 1);
     }
     
     public String getUnvoweledFormValue(String unvoweled) {
       return (String)this.unvoweledForm.get(unvoweled);
     }
     
     public Set getUnvoweledForm() {
       return this.unvoweledForm.keySet();
     }
     
     public boolean isEmptyCaseOrMood() {
       return this.iCaseOrMood.isEmpty();
     }
     
     public boolean isEmptyPartOfSpeech() {
       return this.partOfspeech.isEmpty();
     }
     
     public boolean containsVal(String unvoweled) {
       return this.unvoweledForm.containsKey(unvoweled);
     }
     
     public boolean isEmptyUnvoweled() {
       return this.unvoweledForm.isEmpty();
     }
     
     public boolean isEmptyVoweled() {
       return this.allVoweled.isEmpty();
     }
     
     public int size() {
       return this.allVoweled.size();
     }
     
     public void clear() {
       this.iCaseOrMood.clear();
       this.partOfspeech.clear();
       this.unvoweledForm.clear();
       this.allVoweled.clear();
     }
   }

