  package net.oujda_nlp_team.util;
  
  import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.oujda_nlp_team.entity.Result;
  
  public class Processing {
    private Result result = new Result();
    
    public Result getResult() {
      return this.result;
    }
    
    public void setResult(Result result) {
      this.result = result;
    }
    
    public List listResultSS(String voweledWord, String prefix, String stem, String wordType, String wordDiacPattern, String wordCanPattern, String lemme, String lemmePattern, String wordRoot, String pos, String suffix) {
      List<String> _result = new ArrayList();
      if (Settings.choiceVoweledword)
        _result.add(voweledWord); 
      if (Settings.prefchoice)
        _result.add(prefix); 
      if (Settings.choiceStem)
        _result.add(stem); 
      if (Settings.choicePartOfSpeech)
        _result.add(wordType); 
      if (Settings.choiceDiacPattern)
        _result.add(wordDiacPattern); 
      if (Settings.choicePatternStem)
        _result.add(wordCanPattern); 
      if (Settings.choiceLemma)
        _result.add(lemme); 
      if (Settings.choicePatternLemma)
        _result.add(lemmePattern); 
      if (Settings.choiceRoot)
        _result.add(wordRoot); 
      if (Settings.choiceCaseOrMood)
        _result.add(pos); 
      if (Settings.suffixchoice)
        _result.add(suffix); 
      return _result;
    }
    
    public List nOcurrenceResult(List result) {
      Set<String> hashTemp = new HashSet();
      List<Result> resultat = new ArrayList();
      Iterator<Result> it_result = result.iterator();
      while (it_result.hasNext()) {
        setResult(it_result
            .next());
        if (!hashTemp.contains(toString())) {
          hashTemp.add(toString());
          resultat.add(this.result);
        } 
      } 
      return resultat;
    }
    
    public void print(Result r) {
      setResult(r);
    }
    
    public static List sortResult_(List<Result> result) {
      if (!result.isEmpty())
        for (int i = 0; i < result.size() - 1; i++) {
          Result ri = result.get(i);
          int ind = i;
          Result temp = ri;
          for (int j = i; j < result.size(); j++) {
            Result rj = result.get(j);
            if (rj.getPriority().compareTo(temp.getPriority()) < 0) {
              ind = j;
              temp = rj;
            } 
          } 
          result.add(i, temp);
          result.remove(i + 1);
          result.add(ind, ri);
          result.remove(ind + 1);
        }  
      return result;
    }
    
    public List sortResultssSSS(List<Result> result) {
      List<String> lRes = new ArrayList();
      int i = 0;
      Iterator<Result> it = result.iterator();
      while (it.hasNext()) {
        Result st = it.next();
        lRes.add(st.getPriority() + "_" + i);
        i++;
      } 
      Collections.sort(lRes);
      List<Result> resul = new ArrayList();
      Iterator<String> iit = lRes.iterator();
      while (it.hasNext()) {
        String st = iit.next();
        String[] str = st.split("_");
        int ind = Integer.parseInt(str[1]);
        resul.add(result.get(ind));
      } 
      return nOcurrenceResult(resul);
    }
    
    public String toString() {
      String str = "";
      str = str + (Settings.choiceVoweledword ? (this.result.getVoweledWord() + ";") : "");
      str = str + (Settings.prefchoice ? (this.result.getProclitic() + ";" + this.result.getProcliticNoDec() + ";") : "");
      str = str + (Settings.choiceStem ? (this.result.getStem() + ";") : "");
      str = str + (Settings.choicePatternStem ? (this.result.getPatternStem() + ";") : "");
      str = str + (Settings.choiceLemma ? (this.result.getLemma() + ";") : "");
      str = str + (Settings.choicePatternLemma ? (this.result.getPatternLemma() + ";") : "");
      str = str + (Settings.choiceRoot ? (this.result.getRoot() + ";") : "");
      str = str + (Settings.choicePartOfSpeech ? (this.result.getPartOfSpeech() + ";") : "");
      str = str + (Settings.choiceCaseOrMood ? (this.result.getCaseOrMood() + ";") : "");
      str = str + (Settings.suffixchoice ? (this.result.getEnclitic() + ";" + this.result.getEncliticNoDec() + ";") : "");
      return str;
    }
    
    public Result toResult(String str) {
      Result result1 = new Result();
      String[] Str = str.split(";");
      int i = 0;
      result1.setPriority("");
      if (Settings.choicePartOfSpeech) {
        result1.setPartOfSpeech(Str[i]);
        i++;
      } 
      if (Settings.choiceVoweledword) {
        result1.setVoweledWord(Str[i]);
        i++;
      } 
      if (Settings.prefchoice) {
        result1.setProclitic(Str[i]);
        i++;
        result1.setProcliticNoDec(Str[i]);
        i++;
      } 
      if (Settings.choiceStem) {
        result1.setStem(Str[i]);
        i++;
      } 
      if (Settings.choicePatternStem) {
        result1.setPatternStem(Str[i]);
        i++;
      } 
      if (Settings.choiceLemma) {
        result1.setLemma(Str[i]);
        i++;
      } 
      if (Settings.choicePatternLemma) {
        result1.setPatternLemma(Str[i]);
        i++;
      } 
      if (Settings.choiceRoot) {
        result1.setRoot(Str[i]);
        i++;
      } 
      if (Settings.choicePartOfSpeech) {
        result1.setPartOfSpeech(Str[i]);
        i++;
      } 
      if (Settings.choiceCaseOrMood) {
        result1.setCaseOrMood(Str[i]);
        i++;
      } 
      if (Settings.suffixchoice) {
        result1.setEnclitic(Str[i]);
        i++;
        result1.setEnclitic(Str[i]);
        i++;
      } 
      return result1;
    }
    
    public List sorts(List<Result> result) {
      Set<String> strRes = new HashSet<>();
      Iterator<Result> it = result.iterator();
      while (it.hasNext()) {
        this.result = it.next();
        strRes.add(toString());
      } 
      List<String> lres = new ArrayList();
      lres.addAll(strRes);
      Collections.sort(lres);
      List<Result> Res = new ArrayList();
      Iterator<String> itt = lres.iterator();
      while (itt.hasNext()) {
        String st = itt.next();
        Result res = toResult(st);
        Res.add(res);
      } 
      return Res;
    }
    
    public static List<String> getAllLemma(List<Result> _result) {
      List<String> _res = new ArrayList<>();
      Set<String> lSet = new HashSet<>();
      if (!_result.isEmpty()) {
        Iterator<Result> it_result = _result.iterator();
        while (it_result.hasNext()) {
          Result st_result = it_result.next();
          lSet.add(st_result.getLemma());
        } 
      } else {
        lSet.add("##");
      } 
      _res.addAll(lSet);
      return _res;
    }
    
    public static List<String> getAllLemma(List<Result> _result, String root) {
      List<String> _res = new ArrayList<>();
      Set<String> lSet = new HashSet<>();
      if (!_result.isEmpty()) {
        Iterator<Result> it_result = _result.iterator();
        while (it_result.hasNext()) {
          Result st_result = it_result.next();
          if (st_result.getRoot().equals(root))
            lSet.add(st_result.getLemma()); 
        } 
      } 
      _res.addAll(lSet);
      return _res;
    }
    
    public List<String> getAllDiacritizerWords(List<Result> _result) {
      List<String> _res = new ArrayList<>();
      Set<String> lSet = new HashSet<>();
      if (!_result.isEmpty()) {
        Iterator<Result> it_result = _result.iterator();
        while (it_result.hasNext()) {
          Result st_result = it_result.next();
          String word = st_result.getVoweledWord();
          word = ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(word);
          lSet.add(word);
        } 
      } 
      _res.addAll(lSet);
      return _res;
    }
    
    public static List<String> getAllStem(List<Result> _result) {
      List<String> _res = new ArrayList<>();
      Set<String> lSet = new HashSet<>();
      if (!_result.isEmpty()) {
        Iterator<Result> it_result = _result.iterator();
        while (it_result.hasNext()) {
          Result st_result = it_result.next();
          lSet.add(st_result.getStem());
        } 
      } 
      _res.addAll(lSet);
      return _res;
    }
    
    public static List<String> getAllRoot(List<Result> _result) {
      List<String> _res = new ArrayList<>();
      Set<String> lSet = new HashSet<>();
      if (!_result.isEmpty()) {
        Iterator<Result> it_result = _result.iterator();
        while (it_result.hasNext()) {
          Result st_result = it_result.next();
          lSet.add(st_result.getRoot());
        } 
      } 
      _res.addAll(lSet);
      return _res;
    }
    
    public int getNbrHeaderResult() {
      int cmp = 1;
      cmp += Settings.choiceVoweledword ? 1 : 0;
      cmp += Settings.prefchoice ? 1 : 0;
      cmp += Settings.choiceStem ? 1 : 0;
      cmp += Settings.choicePartOfSpeech ? 1 : 0;
      cmp += Settings.choicePatternStem ? 1 : 0;
      cmp += Settings.choiceLemma ? 1 : 0;
      cmp += Settings.choicePatternLemma ? 1 : 0;
      cmp += Settings.choiceRoot ? 1 : 0;
      cmp += Settings.choiceCaseOrMood ? 1 : 0;
      cmp += Settings.suffixchoice ? 1 : 0;
      return cmp;
    }
  }

