  package net.oujda_nlp_team.util;
  
  import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import net.oujda_nlp_team.entity.Result;
  
  public class Collections {
    public List regroupList(List<Comparable> list) {
      java.util.Collections.sort(list);
      List<String> l_word = new LinkedList();
      ListIterator<Comparable> it = list.listIterator();
      String word = "";
      String chaine = "";
      if (it.hasNext()) {
        String[] str = ((String)it.next()).split("_");
        word = str[0];
        String ind = str[1];
        chaine = word + "_" + ind;
      } 
      while (it.hasNext()) {
        String[] str = ((String)it.next()).split("_");
        if (word.equals(str[0])) {
          chaine = chaine + " " + str[1];
          continue;
        } 
        l_word.add(chaine);
        word = str[0];
        String ind = str[1];
        chaine = word + "_" + ind;
      } 
      l_word.add(chaine);
      return l_word;
    }
    
    public List regroupListInteger(List<Comparable> list) {
      java.util.Collections.sort(list);
      List<String> l_word = new LinkedList();
      ListIterator<Comparable> it = list.listIterator();
      String word = "";
      int ind = 0;
      String chaine = "";
      if (it.hasNext()) {
        String[] str = ((String)it.next()).split("_");
        word = str[0];
        ind = Integer.parseInt(str[1]);
        chaine = word + "_" + ind;
      } 
      while (it.hasNext()) {
        String[] str = ((String)it.next()).split("_");
        if (word.equals(str[0])) {
          chaine = word + "_" + (ind + Integer.parseInt(str[1]));
          continue;
        } 
        l_word.add(chaine);
        word = str[0];
        ind = Integer.parseInt(str[1]);
        chaine = word + "_" + ind;
      } 
      l_word.add(chaine);
      return l_word;
    }
    
    public static Map<String, Integer> addStringToMap(Map<String, Integer> iMap, String str) {
      if (iMap.containsKey(str)) {
        iMap.put(str, Integer.valueOf(((Integer)iMap.get(str)).intValue() + 1));
      } else {
        iMap.put(str, Integer.valueOf(1));
      } 
      return iMap;
    }
    
    public Set Intersection(Set L1, List L2) {
      Set<Object> result = new HashSet();
      for (Object str_L2 : L2) {
        if (L1.contains(str_L2))
          result.add(str_L2); 
      } 
      return result;
    }
    
    public static String getResultToString(Result result, boolean choiceAll, boolean choiceVoweled, boolean choiceProclitic, boolean choiceEnclitic, boolean choiceStem, boolean choiceLemma, boolean choiceRoot, boolean choicePatternStem, boolean choicePatternLemma, boolean choicePOS, boolean choiceCase) {
      StringBuilder str = new StringBuilder();
      if (choiceAll || choiceVoweled)
        str.append(result.getVoweledWord()).append('\t'); 
      if (choiceAll || choiceProclitic)
        str.append(result.getProclitic()).append('\t'); 
      if (choiceAll || choiceEnclitic)
        str.append(result.getEnclitic()).append('\t'); 
      if (choiceAll || choiceStem)
        str.append(result.getStem()).append('\t'); 
      if (choiceAll || choiceLemma)
        str.append(result.getLemma()).append('\t'); 
      if (choiceAll || choiceRoot)
        str.append(result.getRoot()).append('\t'); 
      if (choiceAll || choicePatternStem)
        str.append(result.getPatternStem()).append('\t'); 
      if (choiceAll || choicePatternLemma)
        str.append(result.getPatternLemma()).append('\t'); 
      if (choiceAll || choicePOS)
        str.append(result.getPartOfSpeech()).append('\t'); 
      if (choiceAll || choiceCase)
        str.append(result.getCaseOrMood()).append('\t'); 
      return str.toString();
    }
    
    public static Result getStringToResult(String str, boolean choiceAll, boolean choiceVoweled, boolean choiceProclitic, boolean choiceEnclitic, boolean choiceStem, boolean choiceLemma, boolean choiceRoot, boolean choicePatternStem, boolean choicePatternLemma, boolean choicePOS, boolean choiceCase) {
      Result result = new Result();
      String[] strs = str.split("\t");
      int i = 0;
      if (choiceAll || choiceVoweled)
        result.setVoweledWord(strs[i++]); 
      if (choiceAll || choiceProclitic)
        result.setProclitic(strs[i++]); 
      if (choiceAll || choiceEnclitic)
        result.setEnclitic(strs[i++]); 
      if (choiceAll || choiceStem)
        result.setStem(strs[i++]); 
      if (choiceAll || choiceLemma)
        result.setLemma(strs[i++]); 
      if (choiceAll || choiceRoot)
        result.setRoot(strs[i++]); 
      if (choiceAll || choicePatternStem)
        result.setPatternStem(strs[i++]); 
      if (choiceAll || choicePatternLemma)
        result.setPatternLemma(strs[i++]); 
      if (choiceAll || choicePOS)
        result.setPartOfSpeech(strs[i++]); 
      if (choiceAll || choiceCase)
        result.setCaseOrMood(strs[i++]); 
      return result;
    }
    
    public static List sort(List<Result> result, boolean choiceAll, boolean choiceVoweled, boolean choiceProclitic, boolean choiceEnclitic, boolean choiceStem, boolean choiceLemma, boolean choiceRoot, boolean choicePatternStem, boolean choicePatternLemma, boolean choicePOS, boolean choiceCase) {
      Map<String, String> iMap = new HashMap<>();
      Iterator<Result> it = result.iterator();
      while (it.hasNext()) {
        Result rs = it.next();
        String stRes = getResultToString(rs, choiceAll, choiceVoweled, choiceProclitic, choiceEnclitic, choiceStem, choiceLemma, choiceRoot, choicePatternStem, choicePatternLemma, choicePOS, choiceCase);
        iMap.put(stRes, rs.getPriority());
      } 
      List<String> lKey = new ArrayList<>();
      Iterator<String> itt = iMap.keySet().iterator();
      while (itt.hasNext()) {
        String rs = itt.next();
        String val = iMap.get(rs);
        lKey.add(val + "x" + rs);
      } 
      java.util.Collections.sort(lKey, java.util.Collections.reverseOrder());
      result = new ArrayList<>();
      itt = lKey.iterator();
      while (itt.hasNext()) {
        String rs = itt.next();
        String[] val = rs.split("x");
        Result Res = getStringToResult(val[1], choiceAll, choiceVoweled, choiceProclitic, choiceEnclitic, choiceStem, choiceLemma, choiceRoot, choicePatternStem, choicePatternLemma, choicePOS, choiceCase);
        result.add(Res);
      } 
      return result;
    }
    
    public String getListToString(List<String> list) {
      StringBuilder str = new StringBuilder();
      Iterator<String> it = list.iterator();
      for (; it.hasNext(); str.append(it.next()).append('\n'));
      return str.toString();
    }
  }
