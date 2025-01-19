  package net.oujda_nlp_team.util;
  
  import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.oujda_nlp_team.AlKhalil2Analyzer;
import net.oujda_nlp_team.entity.Result;
  
  public class Search {
    public Set<String> byWordSearch(String word) {
      Set<String> lWords = new HashSet<>();
      Iterator<String> it = Indexation.getiWordsID().keySet().iterator();
      while (it.hasNext()) {
        String st = it.next();
        if (!Validator.getInstance().notCompatible(word, st))
          lWords.add(st); 
      } 
      return lWords;
    }
    
    public Set<String> byRootSearch(String root) {
      Set<String> lWords = new HashSet<>();
      Iterator<String> it = Indexation.getiWordsID().keySet().iterator();
      while (it.hasNext()) {
        String st = it.next();
        Iterator<Result> it_r = ((List<Result>)(AlKhalil2Analyzer.getInstance()).allResults.get(st)).iterator();
        while (it_r.hasNext()) {
          Result res = it_r.next();
          if (root.equals(res.getRoot()))
            lWords.add(st); 
        } 
      } 
      return lWords;
    }
    
    public Set<String> byLemmaSearch(String lemme) {
      Set<String> lWords = new HashSet<>();
      Iterator<String> it = Indexation.getiWordsID().keySet().iterator();
      while (it.hasNext()) {
        String st = it.next();
        Iterator<Result> it_r = ((List<Result>)(AlKhalil2Analyzer.getInstance()).allResults.get(st)).iterator();
        while (it_r.hasNext()) {
          Result res = it_r.next();
          if (!Validator.getInstance().notCompatible(lemme, res.getLemma()))
            lWords.add(st); 
        } 
      } 
      return lWords;
    }
  }
