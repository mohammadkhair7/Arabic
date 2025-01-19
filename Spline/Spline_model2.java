   package net.oujda_nlp_team.Spline;
   
   import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.oujda_nlp_team.util.ArabicStringUtil;
   
   public class Spline_model2 {
     private static final Spline_model2 instance = new Spline_model2();
     
     public static Spline_model2 getInstance() {
       return instance;
     }
     
     public Map<String, Double> getProba_Words_To_Tags(String word, Map<String, List<String>> _iMapMorph, Map<String, String> _matrixB) {
       Map<String, Double> _iMapResult = new HashMap<>();
       String unvWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
       Iterator<String> it = ((List<String>)_iMapMorph.get(word)).iterator();
       while (it.hasNext()) {
         String tag = it.next();
         String Tag = "" + unvWord + ":" + tag;
         double ival = 0.0D;
         if (_matrixB.containsKey(Tag)) {
           String[] vall = ((String)_matrixB.get(Tag)).split(":");
           ival = Double.parseDouble(vall[1]) / Double.parseDouble(vall[0]);
           _iMapResult.put(tag, Double.valueOf(ival));
           continue;
         } 
         _iMapResult.put(tag, Double.valueOf(ival));
       } 
       return _iMapResult;
     }
     
     public Map<String, Double> getProba_Tags_To_Tags(String word_j, String word_j_1, Map<String, List<String>> _iMapMorph, Map<String, String> _matrixA) {
       Map<String, Double> _iMapResult = new HashMap<>();
       double Som = 0.0D;
       Iterator<String> it_j_1 = ((List<String>)_iMapMorph.get(word_j_1)).iterator();
       while (it_j_1.hasNext()) {
         String tag_j_1 = it_j_1.next();
         Iterator<String> it_j = ((List<String>)_iMapMorph.get(word_j)).iterator();
         while (it_j.hasNext()) {
           String tag_j = it_j.next();
           String E_Tj_Tj1 = tag_j + ":" + tag_j_1;
           double ival = 0.0D;
           if (_matrixA.containsKey(E_Tj_Tj1)) {
             String[] vall = ((String)_matrixA.get(E_Tj_Tj1)).split(":");
             ival = Double.parseDouble(vall[1]) / Double.parseDouble(vall[0]);
           } 
           Som += ival;
           _iMapResult.put(E_Tj_Tj1, Double.valueOf(ival));
         } 
       } 
       Iterator<String> it = _iMapResult.keySet().iterator();
       while (it.hasNext()) {
         String E_Tj_Tj1 = it.next();
         _iMapResult.put(E_Tj_Tj1, Double.valueOf((Som != 0.0D) ? (((Double)_iMapResult.get(E_Tj_Tj1)).doubleValue() / Som) : 0.0D));
       } 
       return _iMapResult;
     }
     
     public Map<String, Phi> getArgMaxI(String _word_j, String _word_j1, Map<String, List<String>> _iMapMorph, Map<String, String> _matrixA, Map<String, String> _matrixB, Map<String, Phi> _iMapPsy, int indice) {
       Map<String, Phi> _iMapPsyResult = new HashMap<>();
       Map<String, Double> iMap_Words_j = getProba_Words_To_Tags(_word_j, _iMapMorph, _matrixB);
       Map<String, Double> iMap_Words_j_1 = getProba_Words_To_Tags(_word_j1, _iMapMorph, _matrixB);
       Map<String, Double> iMap_Trans_j_j1 = getProba_Tags_To_Tags(_word_j, _word_j1, _iMapMorph, _matrixA);
       Iterator<String> it_j_1 = ((List<String>)_iMapMorph.get(_word_j1)).iterator();
       double somTrans = 0.0D;
       double somProb = 0.0D;
       boolean notAnalyzed = false;
       while (it_j_1.hasNext()) {
         String tag_j_1 = it_j_1.next();
         double Prob_j_1 = iMap_Words_j_1.containsKey(tag_j_1) ? ((Double)iMap_Words_j_1.get(tag_j_1)).doubleValue() : 0.0D;
         double Max = 0.0D;
         String argMax = "#";
         somProb = 0.0D;
         Iterator<String> it_j = ((List<String>)_iMapMorph.get(_word_j)).iterator();
         while (it_j.hasNext()) {
           String tag_j = it_j.next();
           double Prob_j = iMap_Words_j.containsKey(tag_j) ? ((Double)iMap_Words_j.get(tag_j)).doubleValue() : 0.0D;
           String E_Tj_Tj1 = tag_j + ":" + tag_j_1;
           double Trans_j_j1 = (Prob_j != 0.0D) ? ((Double)iMap_Trans_j_j1.get(E_Tj_Tj1)).doubleValue() : 0.0D;
           double I = 2.0D * Prob_j / 3.0D + Prob_j_1 / 3.0D + Trans_j_j1 / 6.0D;
           I += _iMapPsy.containsKey(tag_j) ? ((Phi)_iMapPsy.get(tag_j)).getFrequency() : 0.0D;
           if (Max <= I) {
             Max = I;
             argMax = tag_j;
           } 
           somProb += Prob_j;
           somTrans += Trans_j_j1;
           if (tag_j.equals("##"))
             notAnalyzed = true; 
         } 
         List<String> list = (List<String>) (_iMapPsy.containsKey(argMax) ? ((Phi)_iMapPsy.get(argMax)).getAllTags() : new ArrayList<>());
         if (indice != list.size())
           list.add(argMax); 
         _iMapPsyResult.put(tag_j_1, new Phi(tag_j_1, Max, list));
       } 
       return _iMapPsyResult;
     }
   }
