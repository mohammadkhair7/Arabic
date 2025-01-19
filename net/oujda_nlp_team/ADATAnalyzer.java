  package net.oujda_nlp_team;
  
  import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.Tokenization;
import net.oujda_nlp_team.util.Transliteration;
import net.oujda_nlp_team.util.Validator;
  
  public class ADATAnalyzer {
    public static List<String> listLemma;
    
    public static List<String> listRoot;
    
    public static List<String> listStem;
    
    private final String nameFile;
    
    private final String encoding;
    
    private Set<String> setMatrixB;
    
    private Set<String> setMatrixA;
    
    private Set<String> setMatrixB_1;
    
    private Set<String> setMatrixA_1;
    
    private Set<String> setMatrixS;
    
    private Map<String, String> matrixB;
    
    private Map<String, String> matrixA;
    
    private Map<String, String> matrixS;
    
    private List<String> listLM;
    
    private List<String> allTokens;
    
    private List<String> allText;
    
    private final Set<String> notAnaTokens;
    
    private final Map<String, Long> mapRoot;
    
    private final Map<String, Double> mapLemma;
    
    private final Map<String, Long> mapStem;
    
    private final Map<String, List<String>> morph;
    
    private static final ADATAnalyzer instance = new ADATAnalyzer();
    
    public static ADATAnalyzer getInstance() {
     return instance;
    }
    
    private ADATAnalyzer() {
    	this.nameFile = "/net/oujda_nlp_team/resources/DATA.MSA.ALL.TRAIN.141809.lm";
    	this.encoding = "Cp1252";
    	this.listLM = IOFile.getInstance().readFileToList(IOFile.getInstance().openFileXml(this.nameFile), this.encoding);
       String fileRoot = "/net/oujda_nlp_team/resources/DATA.MSA.SHA.ROOT.map";
       String fileLemma = "/net/oujda_nlp_team/resources/DATA.MSA.SHA.LEMMA.map";
       String fileLemma_train = "/net/oujda_nlp_team/resources/DATA.MSA-LEMMA.ALL-train.map";
       String fileStem = "/net/oujda_nlp_team/resources/DATA.MSA.SHA.STEM.map";
       this.mapRoot = IOFile.getInstance().deserializeMap(fileRoot);
       this.mapLemma = IOFile.getInstance().deserializeMap(fileLemma_train);
       this.mapStem = IOFile.getInstance().deserializeMap(fileStem);
       setAllMatrixS(this.listLM);
       this.morph = new HashMap<>();
       this.notAnaTokens = new HashSet<>();
    }
    
    public String processLemmatizer1(String text) {
       setTokenizationString(text);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResult(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResult(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processLemmatizer(String text) {
				System.out.println(text);
       setTokenizationString(text);
       addMatrixS(this.allTokens, this.morph);
       addMatrixA(this.allTokens, this.morph);
       addMatrixB(this.morph);
       setAllMatrix(this.listLM, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResult(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResult(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processLemmatizer2(String text) {
       setTokenizationString(text);
       addMatrixS(this.allTokens, this.morph);
       addMatrixA(this.allTokens, this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getLemmaResult(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getLemmaResult(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processRacineur(String text) {
       setTokenizationString(text);
       addMatrixS(this.allTokens, this.morph);
       addMatrixA(this.allTokens, this.morph);
       addMatrixB(this.morph);
       setAllMatrix(this.listLM, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultRoots(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultRoots(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processRacineur1(String text) {
       setTokenizationString(text);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultRoots(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultRoots(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processStemmer1(String text) {
       setTokenizationString(text);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultStems(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultStems(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processStemmer(String text) {
       setTokenizationString(text);
       addMatrixS(this.allTokens, this.morph);
       addMatrixA(this.allTokens, this.morph);
       addMatrixB(this.morph);
       setAllMatrix(this.listLM, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultStems(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultStems(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processTokenized1(String text) {
       setTokenizationString(text);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultTokenized(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultTokenized(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public String processTokenized(String text) {
       setTokenizationString(text);
       addMatrixS(this.allTokens, this.morph);
       addMatrixA(this.allTokens, this.morph);
       addMatrixB(this.morph);
       setAllMatrix(this.listLM, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       if (this.allTokens.size() > 1) {
         List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultTokenized(this.allText, this.morph, res);
      } 
       if (this.allTokens.size() == 1) {
         List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
         return getResultTokenized(this.allText, this.morph, res);
      } 
       return text;
    }
    
    public void processStemmerFileResultLine(String fileIN, String encodingIN, String fileOUT, String encodingOUT, AlKhalil2Analyzer _analyzer) {
       System.out.println("Etape1");
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       System.out.println("Etape2");
       List<String> result = new ArrayList<>();
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             List<String> res = analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
            continue;
          } 
           if (iTokens.size() == 1) {
             List<String> res = analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
          } 
        } 
      } 
       System.out.println("Etape3");
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getResultTokenizedSample(Tokenization.getInstance().getAllText(), this.morph, result, _analyzer));
    }
    
    public void processLemmaFileResultLine(String fileIN, String encodingIN, String fileOUT, String encodingOUT, AlKhalil2Analyzer _analyzer) {
       System.out.println("Etape1");
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       System.out.println("Etape2");
       List<String> result = new ArrayList<>();
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             List<String> res = analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
            continue;
          } 
           if (iTokens.size() == 1) {
             List<String> res = analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
          } 
        } 
      } 
       System.out.println("Etape3");
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getResultLemmaSample(Tokenization.getInstance().getAllText(), this.morph, result, _analyzer));
    }
    
    public void processRacineFileResultLine(String fileIN, String encodingIN, String fileOUT, String encodingOUT, AlKhalil2Analyzer _analyzer) {
       System.out.println("Etape1");
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       System.out.println("Etape2");
       List<String> result = new ArrayList<>();
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             List<String> res = analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
            continue;
          } 
           if (iTokens.size() == 1) {
             List<String> res = analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
          } 
        } 
      } 
       System.out.println("Etape3");
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getResultRacineSample(Tokenization.getInstance().getAllText(), this.morph, result, _analyzer));
    }
    
    public void processLemmatizationFile(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> list = IOFile.getInstance().readFileToList(fileIN, encodingIN);
       List<String> result = new ArrayList<>();
       Iterator<String> it = list.iterator();
       while (it.hasNext()) {
         String text = it.next();
         if (!text.equals("")) {
           setTokenizationString(text);
           addMatrixS(this.allTokens, this.morph);
           addMatrixA(this.allTokens, this.morph);
           addMatrixB(this.morph);
           setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
           if (this.allTokens.size() > 1) {
             List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.add(text);
             result.add("\t##Lemmatization results:");
             result.addAll(getResultFile(this.allTokens, this.morph, res));
          } 
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    public void processLemmatizationFileOutXml(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> list = IOFile.getInstance().readFileToList(fileIN, encodingIN);
       Map<Integer, List<String>> tokenn = new HashMap<>();
       Set<String> _setMatrixB = new HashSet<>();
       Set<String> _setMatrixA = new HashSet<>();
       Set<String> _setMatrixB_1 = new HashSet<>();
       Set<String> _setMatrixA_1 = new HashSet<>();
       Set<String> _setMatrixS = new HashSet<>();
       int i = 0;
       Iterator<String> it = list.iterator();
       while (it.hasNext()) {
         String text = it.next();
         if (!text.equals("")) {
           setTokenizationString(text);
           tokenn.put(Integer.valueOf(i), this.allTokens);
           addMatrixS(this.allTokens, this.morph);
           addMatrixA(this.allTokens, this.morph);
           addMatrixB(this.morph);
           _setMatrixB.addAll(this.setMatrixB);
           _setMatrixB_1.addAll(this.setMatrixB_1);
           _setMatrixA.addAll(this.setMatrixA);
           _setMatrixA_1.addAll(this.setMatrixA_1);
           _setMatrixS.addAll(this.setMatrixS);
           i++;
        } 
      } 
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, _setMatrixS, _setMatrixB, _setMatrixB_1, _setMatrixA, _setMatrixA_1);
       List<String> result = new ArrayList<>();
       int j = 1;
       for (int p = 0; p < tokenn.keySet().size(); p++) {
         if (((List)tokenn.get(Integer.valueOf(p))).size() > 1) {
           List<String> res = analyzed(tokenn.get(Integer.valueOf(p)), this.morph, this.matrixS, this.matrixA, this.matrixB);
           result.add("+\t" + (String)list.get(p));
           result.addAll(getResultFileOutXml(tokenn.get(Integer.valueOf(p)), this.morph, res));
        } else {
           result.add("+\t" + (String)list.get(p));
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    private List<String> getResultFileOutXml(List<String> _allTokens, Map<String, List<String>> _morph, List<String> res) {
       List<String> result = new ArrayList<>();
       int j = 0;
       for (int i = 0; i < _allTokens.size(); i++) {
         String token = _allTokens.get(i);
         if (_morph.containsKey(token)) {
           String lemma = res.get(j);
           j++;
           result.add("-\t" + token + "\t" + lemma);
        } 
      } 
       return result;
    }
    
    private List<String> getResultFile(List<String> _allTokens, Map<String, List<String>> _morph, List<String> res) {
       List<String> list = new ArrayList<>();
       int j = 0;
       for (int i = 0; i < _allTokens.size(); i++) {
         String token = _allTokens.get(i);
         if (_morph.containsKey(token)) {
           String lemma = res.get(j);
           String BW = Transliteration.getInstance().getArabicToBuckWalter(lemma);
           list.add("\t\tWord: " + token + "\tLemma: " + lemma + "\tBW: " + BW);
           j++;
        } 
      } 
       list.add("\t------------------------");
       return list;
    }
    
    private String getResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "<span class='couple'><span class='token' >" + token + "</span><span class='lemma' contenteditable>" + lemma + "</span></span>";
          } else {
             result = result + "<span class='couple'><span class='notoken' >" + token + "</span></span>";
          } 
           j++;
        } else {
           result = result + "<span class='couple'>" + token + "</span>";
        } 
      } 
       return result;
    }
    
    private String getLemmaResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + token + ":" + lemma;
          } else {
             result = result + token + ":-";
          } 
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
    
    private String getResultRoots(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "<span class='couple'><span class='token' >" + token + "</span><span class='root'>" + getRoots(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token)) + "</span></span>";
          } else {
             result = result + "<span class='couple'><span class='notoken' >" + token + "</span></span>";
          } 
           j++;
        } else {
           result = result + "<span class='couple'>" + token + "</span>";
        } 
      } 
       return result;
    }
    
    private String getResultStems(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "<span class='couple'><span class='token' >" + token + "</span><span class='root'>" + getStems(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token)) + "</span></span>";
          } else {
             result = result + "<span class='couple'><span class='notoken' >" + token + "</span></span>";
          } 
           j++;
        } else {
           result = result + "<span class='couple'>" + token + "</span>";
        } 
      } 
       return result;
    }
    
    private String getResultTokenized(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "<span class='couple'>" + getTokenized(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token)) + "</span>";
          } else {
             result = result + "<span class='couple'><span class='notoken' >" + token + "</span></span>";
          } 
           j++;
        } else {
           result = result + "<span class='couple'>" + token + "</span>";
        } 
      } 
       return result;
    }
    
    private List<String> getResultTokenizedSample(List<String> _allText, Map<String, List<String>> _morph, List<String> res, AlKhalil2Analyzer _analyzer) {
       List<String> results = new ArrayList<>();
       String result = "";
       int j = 0;
       for (String token : _allText) {
         if (token.equals("\n")) {
           results.add(result);
           result = "";
          continue;
        } 
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + getTokenizedSample(token, lemma, _analyzer.analyzerToken(token));
          } else {
             result = result + token;
          } 
           j++;
          continue;
        } 
         result = result + token;
      } 
       if (!result.equals(""))
         results.add(result); 
       return results;
    }
    
    private List<String> getResultLemmaSample(List<String> _allText, Map<String, List<String>> _morph, List<String> res, AlKhalil2Analyzer _analyzer) {
       List<String> results = new ArrayList<>();
       String result = "";
       int j = 0;
       for (String token : _allText) {
         if (token.equals("\n")) {
           results.add(result);
           result = "";
          continue;
        } 
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + getLemmaSample(token, lemma, _analyzer.analyzerToken(token));
          } else {
             result = result + token;
          } 
           j++;
          continue;
        } 
         result = result + token;
      } 
       if (!result.equals(""))
         results.add(result); 
       return results;
    }
    
    private List<String> getResultRacineSample(List<String> _allText, Map<String, List<String>> _morph, List<String> res, AlKhalil2Analyzer _analyzer) {
       List<String> results = new ArrayList<>();
       String result = "";
       int j = 0;
       for (String token : _allText) {
         if (token.equals("\n")) {
           results.add(result);
           result = "";
          continue;
        } 
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + getRacineSample(token, lemma, _analyzer.analyzerToken(token));
          } else {
             result = result + token;
          } 
           j++;
          continue;
        } 
         result = result + token;
      } 
       if (!result.equals(""))
         results.add(result); 
       return results;
    }
    
    public void processLemmatizationFileResultLine(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       String file = "/net/oujda_nlp_team/resources/DATA.MSA.ALL.TRAIN.141809.lm";
       List<String> list = IOFile.getInstance().readFileToList(fileIN, encodingIN);
       List<String> result = new ArrayList<>();
       Iterator<String> it = list.iterator();
       while (it.hasNext()) {
         String text = it.next();
         if (!text.equals("")) {
           setTokenizationString(text);
           addMatrixS(this.allTokens, this.morph);
           addMatrixA(this.allTokens, this.morph);
           addMatrixB(this.morph);
           setAllMatrix(IOFile.getInstance().openFileXml(file), "Cp1252", this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
           if (this.allTokens.size() > 1) {
             List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.add(getResult(this.allText, this.morph, res));
            continue;
          } 
           if (this.allTokens.size() == 1) {
             List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.add(getResult(this.allText, this.morph, res));
            continue;
          } 
           result.add(text);
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    private List<String> analyzed(List<String> _allTokens, Map<String, List<String>> _morph, Map<String, String> _matrixS, Map<String, String> _matrixA, Map<String, String> _matrixB) {
       Map<Integer, List<Integer>> iMapRes = new HashMap<>();
       Map<String, Double> startMatrix = new HashMap<>();
       Map<String, Double> IMapResW1 = new HashMap<>();
       Map<String, Double> IMapResW2 = new HashMap<>();
       String sword = _allTokens.get(0);
       int size = ((List)_morph.get(sword)).size();
       double som = 0.0D;
       double D = 0.5D;
       double maxStart = 1.0D;
       double maxN = 13336.0D;
       Iterator<String> it = ((List<String>)_morph.get(sword)).iterator();
       if (it.hasNext()) {
         String tag = it.next();
         double x = 0.0D;
         if (_matrixS.containsKey(tag)) {
           String[] st = ((String)_matrixS.get(tag)).split(":");
           maxStart = Double.parseDouble(st[1]) - D;
           maxN = Double.parseDouble(st[0]);
           x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
        } 
         som = x;
         startMatrix.put(tag, Double.valueOf(x));
      } 
       while (it.hasNext()) {
         String tag = it.next();
         double start = 0.0D;
         double x = 0.0D;
         if (_matrixS.containsKey(tag)) {
           String[] st = ((String)_matrixS.get(tag)).split(":");
           start = Double.parseDouble(st[1]) - D;
           maxN = Double.parseDouble(st[0]);
           x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
        } 
         if (maxStart < start)
           maxStart = start; 
         som += x;
         startMatrix.put(tag, Double.valueOf(x));
      } 
       size = (size == 0) ? 1 : size;
       it = startMatrix.keySet().iterator();
       while (it.hasNext()) {
         String tag = it.next();
         double val = (maxStart / maxN + ((Double)startMatrix.get(tag)).doubleValue()) / (som + maxStart / maxN * size);
         startMatrix.put(tag, Double.valueOf(val));
      } 
       int j = 0;
      int i;
       for (i = 1; i < _allTokens.size(); i++) {
         IMapResW1 = new HashMap<>();
         List<Integer> il = new ArrayList<>();
         String word1 = _allTokens.get(i - 1);
         String word2 = _allTokens.get(i);
         Iterator<String> it_2 = ((List<String>)_morph.get(word2)).iterator();
         while (it_2.hasNext()) {
           String tag2 = it_2.next();
           double d1 = 0.0D;
           int k = 0;
           String str1 = "";
           j = 0;
           Iterator<String> it_1 = ((List<String>)_morph.get(word1)).iterator();
           if (it_1.hasNext()) {
             String tag1 = it_1.next();
             j = 0;
             String str3 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1) + ":" + tag1;
             String couple = tag1 + ":" + tag2;
             if (i == 1) {
               double start = ((Double)startMatrix.get(tag1)).doubleValue();
               double d2 = !_matrixB.containsKey(str3) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (_matrixB.containsKey(str3)) {
                 String[] st = ((String)_matrixB.get(str3)).split(":");
                 d2 = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
              } 
               double resA = !_matrixA.containsKey(couple) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (_matrixA.containsKey(couple)) {
                 String[] st = ((String)_matrixA.get(couple)).split(":");
                 resA = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
              } 
               d1 = resA + d2 + start;
            } else {
               double d2 = !IMapResW1.containsKey(str3) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (IMapResW1.containsKey(str3))
                 d2 = ((Double)IMapResW1.get(str3)).doubleValue(); 
               double resA = !_matrixA.containsKey(couple) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (_matrixA.containsKey(couple)) {
                 String[] st = ((String)_matrixA.get(couple)).split(":");
                 resA = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
              } 
               d1 = resA + d2;
            } 
             str1 = tag1;
             k = j;
          } 
           while (it_1.hasNext()) {
            double val;
             String tag1 = it_1.next();
             j++;
             String str3 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1) + ":" + tag1;
             String couple = tag1 + ":" + tag2;
             if (i == 1) {
               double start = ((Double)startMatrix.get(tag1)).doubleValue();
               double d2 = !_matrixB.containsKey(str3) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (_matrixB.containsKey(str3)) {
                 String[] st = ((String)_matrixB.get(str3)).split(":");
                 d2 = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
              } 
               double resA = !_matrixA.containsKey(couple) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (_matrixA.containsKey(couple)) {
                 String[] st = ((String)_matrixA.get(couple)).split(":");
                 resA = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
              } 
               val = resA + d2 + start;
            } else {
               double d2 = !IMapResW1.containsKey(str3) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (IMapResW1.containsKey(str3))
                 d2 = ((Double)IMapResW1.get(str3)).doubleValue(); 
               double resA = !_matrixA.containsKey(couple) ? (this.mapLemma.containsKey(tag1) ? ((Double)this.mapLemma.get(tag1)).doubleValue() : 1.0E-5D) : 0.0D;
               if (_matrixA.containsKey(couple)) {
                 String[] st = ((String)_matrixA.get(couple)).split(":");
                 resA = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
              } 
               val = resA + d2;
            } 
             if (val > d1) {
               d1 = val;
               str1 = tag1;
               k = j;
            } 
          } 
           String str2 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2) + ":" + tag2;
           double resB = !_matrixB.containsKey(str2) ? (this.mapLemma.containsKey(tag2) ? ((Double)this.mapLemma.get(tag2)).doubleValue() : 1.0E-5D) : 0.0D;
           if (_matrixB.containsKey(str2)) {
             String[] st = ((String)_matrixB.get(str2)).split(":");
             resB = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
          } 
           d1 = resB + d1;
           IMapResW2.put(str2, Double.valueOf(d1));
           il.add(Integer.valueOf(k));
        } 
         IMapResW1 = IMapResW2;
         IMapResW2 = new HashMap<>();
         iMapRes.put(Integer.valueOf(i), il);
      } 
       double max = 0.0D;
       int imax = 0;
       String maxTag = "";
       String word = _allTokens.get(i - 1);
       Iterator<String> itt = ((List<String>)_morph.get(word)).iterator();
       if (itt.hasNext()) {
         String tag = itt.next();
         String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word) + ":" + tag;
         j = 0;
         max = ((Double)IMapResW1.get(wrd)).doubleValue();
         maxTag = tag;
         imax = j;
      } 
       while (itt.hasNext()) {
         String tag = itt.next();
         String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word) + ":" + tag;
         j++;
         double val = ((Double)IMapResW1.get(wrd)).doubleValue();
         if (val > max) {
           max = val;
           maxTag = tag;
           imax = j;
        } 
      } 
       List<String> ilp = new ArrayList<>();
       ilp.add(maxTag);
       for (i = _allTokens.size() - 1; i >= 1; i--) {
         word = _allTokens.get(i - 1);
         String tag = ((List<String>)_morph.get(word)).get(((Integer)((List<Integer>)iMapRes.get(Integer.valueOf(i))).get(imax)).intValue());
         imax = ((Integer)((List<Integer>)iMapRes.get(Integer.valueOf(i))).get(imax)).intValue();
         ilp.add(tag);
      } 
       Collections.reverse(ilp);
       return ilp;
    }
    
    private List<String> analyzedToken(List<String> _allTokens, Map<String, List<String>> _morph, Map<String, String> _matrixS, Map<String, String> _matrixA, Map<String, String> _matrixB) {
       Map<Integer, List<Integer>> iMapRes = new HashMap<>();
       Map<String, Double> startMatrix = new HashMap<>();
       Map<String, Double> IMapResW1 = new HashMap<>();
       Map<String, Double> IMapResW2 = new HashMap<>();
       String sword = _allTokens.get(0);
       int size = ((List)_morph.get(sword)).size();
       double som = 0.0D;
       double D = 0.5D;
       double maxStart = 1.0D;
       double maxN = 13336.0D;
       Iterator<String> it = ((List<String>)_morph.get(sword)).iterator();
       if (it.hasNext()) {
         String tag = it.next();
         double x = 0.0D;
         if (_matrixS.containsKey(tag)) {
           String[] st = ((String)_matrixS.get(tag)).split(":");
           maxStart = Double.parseDouble(st[1]) - D;
           maxN = Double.parseDouble(st[0]);
           x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
        } 
         som = x;
         startMatrix.put(tag, Double.valueOf(x));
      } 
       while (it.hasNext()) {
         String tag = it.next();
         double start = 0.0D;
         double x = 0.0D;
         if (_matrixS.containsKey(tag)) {
           String[] st = ((String)_matrixS.get(tag)).split(":");
           start = Double.parseDouble(st[1]) - D;
           maxN = Double.parseDouble(st[0]);
           x = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
        } 
         if (maxStart < start)
           maxStart = start; 
         som += x;
         startMatrix.put(tag, Double.valueOf(x));
      } 
       size = (size == 0) ? 1 : size;
       it = startMatrix.keySet().iterator();
       while (it.hasNext()) {
         String tag = it.next();
         double resB = !_matrixB.containsKey(sword) ? 1.0E-6D : 0.0D;
         if (_matrixB.containsKey(sword)) {
           String[] st = ((String)_matrixB.get(sword)).split(":");
           resB = Double.parseDouble(st[1]) / Double.parseDouble(st[0]);
        } 
         double val = (maxStart / maxN + ((Double)startMatrix.get(tag)).doubleValue()) / (som + maxStart / maxN * size) + resB;
         startMatrix.put(tag, Double.valueOf(val));
      } 
       double max = 0.0D;
       String maxTag = "";
       it = startMatrix.keySet().iterator();
       if (it.hasNext()) {
         String tag = it.next();
         max = ((Double)startMatrix.get(tag)).doubleValue();
         maxTag = tag;
      } 
       while (it.hasNext()) {
         String tag = it.next();
         double val = ((Double)startMatrix.get(tag)).doubleValue();
         if (max < val) {
           max = val;
           maxTag = tag;
        } 
      } 
       List<String> ilp = new ArrayList<>();
       ilp.add(maxTag);
       return ilp;
    }
    
    private void setTokenizationString(String Text) {
       this.allTokens = new LinkedList<>();
       this.allText = new LinkedList<>();
       Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
       List<String> Lines = new LinkedList<>();
       Lines.addAll(Arrays.asList(Text.split("\n")));
       Iterator<String> it_line = Lines.iterator();
       int i = 1;
       while (it_line.hasNext()) {
         String line = it_line.next();
         int start = 0;
         int end = 0;
         line = line.replaceAll("ـ", "");
         Matcher matcher = pattern.matcher(line);
         while (matcher.find()) {
           String word = matcher.group();
           char c1 = word.charAt(0);
           if (!Validator.getInstance().isSeparator(c1)) {
             word = ArabicStringUtil.getInstance().correctErreur(word);
             this.allTokens.add(word);
             addMorphoResult(word);
          } 
           if (matcher.start() > 0) {
             String wrd = line.substring(start, matcher.start());
             this.allText.add(wrd);
          } 
           this.allText.add(word);
           start = matcher.end();
           end = matcher.end();
        } 
         if (line.length() > end) {
           String wrd = line.substring(start, line.length());
           this.allText.add(wrd);
        } 
         this.allText.add("");
      } 
    }
    
    private void addMorphoResult(String word) {
       if (!this.morph.containsKey(word))
         this.morph.put(word, getAllLemmas(word, AlKhalil2Analyzer.getInstance().processToken(word))); 
    }
    
    private void addAllMorphoResult(Set<String> listWords) {
       Iterator<String> it_result = listWords.iterator();
       while (it_result.hasNext()) {
         String word = it_result.next();
         if (!this.morph.containsKey(word))
           this.morph.put(word, getAllLemmas(word, AlKhalil2Analyzer.getInstance().processToken(word))); 
      } 
    }
    
    private List<String> getAllLemmas(String word, ResultList _result) {
       if (_result.isAnalyzed())
         return _result.getAllLemmas(); 
       this.notAnaTokens.add(word);
       List<String> _res = new ArrayList<>();
       _res.add(word);
       return _res;
    }
    
    private String getRoots(String word, String lemma, List<Result> _result) {
       int i = 0;
       int imax = 0;
       long max = 0L;
       List<String> list = new ArrayList<>();
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             String root = st_result.getRoot();
             list.add(root);
             long val = this.mapRoot.containsKey(root) ? ((Long)this.mapRoot.get(root)).longValue() : 0L;
             if (i == 0) {
               imax = i;
               max = val;
             } else if (max < val) {
               max = val;
               imax = i;
            } 
             i++;
          } 
        } 
         return list.get(imax);
      } 
       return word;
    }
    
    private String getStems(String word, String lemma, List<Result> _result) {
       int i = 0;
       int imax = 0;
       long max = 0L;
       List<String> list = new ArrayList<>();
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             String root = st_result.getStem();
             list.add(root);
             long val = this.mapStem.containsKey(root) ? ((Long)this.mapStem.get(root)).longValue() : 0L;
             if (i == 0) {
               imax = i;
               max = val;
             } else if (max < val) {
               max = val;
               imax = i;
            } 
             i++;
          } 
        } 
         return list.get(imax);
      } 
       return word;
    }
    
    private String getTokenized(String word, String lemma, List<Result> _result) {
       int i = 0;
       int imax = 0;
       long max = 0L;
       List<Result> list = new ArrayList<>();
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             list.add(st_result);
             String str = st_result.getStem();
             long val = this.mapStem.containsKey(str) ? ((Long)this.mapStem.get(str)).longValue() : 0L;
             if (i == 0) {
               imax = i;
               max = val;
              continue;
            } 
             if (max < val) {
               max = val;
               imax = i;
            } 
          } 
        } 
         Result re = list.get(imax);
         String resProc = "<span class='proc'>" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getProcliticNoDec()) + "</span><span class='plus'>+</span>";
         String resEnc = "<span class='plus'>+</span><span class='enc'>" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getEncliticNoDec()) + "</span>";
         String stem = "<span class='stem'>" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getStem()) + "</span>";
         resProc = (re.getProcliticNoDec().equals("#") || re.getProcliticNoDec().equals("-")) ? "" : resProc;
         resEnc = (re.getEncliticNoDec().equals("#") || re.getEncliticNoDec().equals("-")) ? "" : resEnc;
         return resProc + stem + resEnc;
      } 
       return word;
    }
    
    private String getTokenizedSample(String word, String lemma, List<Result> _result) {
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             String resProc = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getProcliticNoDec()) + "+";
             String resEnc = "+" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getEncliticNoDec());
             String stem = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getStem());
             resProc = (st_result.getProcliticNoDec().equals("#") || st_result.getProcliticNoDec().equals("-")) ? "" : resProc;
             resEnc = (st_result.getEncliticNoDec().equals("#") || st_result.getEncliticNoDec().equals("-")) ? "" : resEnc;
             return resProc + stem + resEnc;
          } 
        } 
      } 
       return word;
    }
    
    private String getLemmaSample(String word, String lemma, List<Result> _result) {
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             String resProc = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getProcliticNoDec()) + "+";
             String resEnc = "+" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getEncliticNoDec());
             String stem = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getStem());
             resProc = (st_result.getProcliticNoDec().equals("#") || st_result.getProcliticNoDec().equals("-")) ? "" : resProc;
             resEnc = (st_result.getEncliticNoDec().equals("#") || st_result.getEncliticNoDec().equals("-")) ? "" : resEnc;
             return resProc + lemma + resEnc;
          } 
        } 
      } 
       return word;
    }
    
    private String getRacineSample(String word, String lemma, List<Result> _result) {
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             String resProc = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getProcliticNoDec()) + "+";
             String resEnc = "+" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getEncliticNoDec());
             String root = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getRoot());
             if (root.equals("-"))
               root = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getStem()); 
             resProc = (st_result.getProcliticNoDec().equals("#") || st_result.getProcliticNoDec().equals("-")) ? "" : resProc;
             resEnc = (st_result.getEncliticNoDec().equals("#") || st_result.getEncliticNoDec().equals("-")) ? "" : resEnc;
             return resProc + root + resEnc;
          } 
        } 
      } 
       return word;
    }
    
    private void addMatrixS(List<String> _allTokens, Map<String, List<String>> _morph) {
       this.setMatrixS = new HashSet<>();
       if (!_allTokens.isEmpty()) {
         String word = _allTokens.get(0);
         Iterator<String> iKey = ((List<String>)_morph.get(word)).iterator();
         while (iKey.hasNext()) {
           String tag = iKey.next();
           String tagBW = Transliteration.getInstance().getArabicToBuckWalter(tag);
           this.setMatrixS.add(tagBW);
        } 
      } 
    }
    
    private void addMatrixA(List<String> _allTokens, Map<String, List<String>> _morph) {
       this.setMatrixA_1 = new HashSet<>();
       this.setMatrixA = new HashSet<>();
       for (int i = 1; i < _allTokens.size(); i++) {
         String word1 = _allTokens.get(i - 1);
         String word2 = _allTokens.get(i);
         Iterator<String> iKey1 = ((List<String>)_morph.get(word1)).iterator();
         while (iKey1.hasNext()) {
           String tag1 = iKey1.next();
           String tag1BW = Transliteration.getInstance().getArabicToBuckWalter(tag1);
           Iterator<String> iKey2 = ((List<String>)_morph.get(word2)).iterator();
           while (iKey2.hasNext()) {
             String tag2 = iKey2.next();
             String tag2BW = Transliteration.getInstance().getArabicToBuckWalter(tag2);
             this.setMatrixA_1.add(tag1BW);
             this.setMatrixA.add(tag1BW + ":" + tag2BW);
          } 
        } 
      } 
    }
    
    private void addMatrixB(Map<String, List<String>> _morph) {
       this.setMatrixB_1 = new HashSet<>();
       this.setMatrixB = new HashSet<>();
       Iterator<String> iKey = _morph.keySet().iterator();
       while (iKey.hasNext()) {
         String word = iKey.next();
         String wordBW = Transliteration.getInstance().getArabicToBuckWalter(ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
         Iterator<String> iKey1 = ((List<String>)_morph.get(word)).iterator();
         while (iKey1.hasNext()) {
           String tag = iKey1.next();
           String tagBW = Transliteration.getInstance().getArabicToBuckWalter(tag);
           this.setMatrixB.add(wordBW + ":" + tagBW);
           this.setMatrixB_1.add(wordBW);
        } 
      } 
    }
    
    private void setAllMatrixS(List<String> listLM) {
       this.matrixS = new HashMap<>();
       this.matrixB = new HashMap<>();
       this.matrixA = new HashMap<>();
       String SFreq = "";
       Iterator<String> it = listLM.iterator();
       while (it.hasNext()) {
         String line = it.next();
         if (line.charAt(0) == 'S') {
           if (line.charAt(1) == '1') {
             int size = Integer.parseInt(line.substring(3, 5));
             String Stag = line.substring(6, 6 + size);
             String StagFreq = line.substring(7 + size);
             this.matrixS.put(Transliteration.getInstance().getBuckWalterToArabic(Stag), SFreq + ":" + StagFreq);
            continue;
          } 
           SFreq = line.substring(3);
          continue;
        } 
         if (line.charAt(0) == 'B') {
           int sizeT = Integer.parseInt(line.substring(2, 4));
           int sizeF = Integer.parseInt(line.substring(4, 5));
           String word = line.substring(6, 6 + sizeT);
           String wordFreq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
           int taille = 8 + sizeT + sizeF;
           while (taille < line.length()) {
             int size2T = Integer.parseInt(line.substring(taille, taille + 2));
             int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
             String tag = line.substring(taille + 4, taille + 4 + size2T);
             String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
             String wordArb = Transliteration.getInstance().getBuckWalterToArabic(word);
             String tagArb = Transliteration.getInstance().getBuckWalterToArabic(tag);
             this.matrixB.put(wordArb + ":" + tagArb, wordFreq + ":" + tagFreq);
             taille += 6 + size2T + size2F;
          } 
          continue;
        } 
         if (line.charAt(0) == 'A') {
           int sizeT = Integer.parseInt(line.substring(2, 4));
           int sizeF = Integer.parseInt(line.substring(4, 5));
           String tag1 = line.substring(6, 6 + sizeT);
           String tag1Freq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
           int taille = 8 + sizeT + sizeF;
           while (taille < line.length()) {
             int size2T = Integer.parseInt(line.substring(taille, taille + 2));
             int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
             String tag2 = line.substring(taille + 4, taille + 4 + size2T);
             String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
             String tag1Arb = Transliteration.getInstance().getBuckWalterToArabic(tag1);
             String tag2Arb = Transliteration.getInstance().getBuckWalterToArabic(tag2);
             this.matrixA.put(tag1Arb + ":" + tag2Arb, tag1Freq + ":" + tagFreq);
             taille += 6 + size2T + size2F;
          } 
        } 
      } 
    }
    
    private void setAllMatrix(List<String> listLM, Set<String> setMatrixS, Set<String> setMatrixB, Set<String> setMatrixB_1, Set<String> setMatrixA, Set<String> setMatrixA_1) {
       this.matrixS = new HashMap<>();
       this.matrixB = new HashMap<>();
       this.matrixA = new HashMap<>();
       String SFreq = "";
       Iterator<String> it = listLM.iterator();
       while (it.hasNext()) {
         String line = it.next();
         if (line.charAt(0) == 'S') {
           if (line.charAt(1) == '1') {
             int size = Integer.parseInt(line.substring(3, 5));
             String Stag = line.substring(6, 6 + size);
             String StagFreq = line.substring(7 + size);
             if (setMatrixS.contains(Stag)) {
               String tagArb = Transliteration.getInstance().getBuckWalterToArabic(Stag);
               this.matrixS.put(tagArb, SFreq + ":" + StagFreq);
            } 
            continue;
          } 
           SFreq = line.substring(3);
          continue;
        } 
         if (line.charAt(0) == 'B') {
           int sizeT = Integer.parseInt(line.substring(2, 4));
           int sizeF = Integer.parseInt(line.substring(4, 5));
           String word = line.substring(6, 6 + sizeT);
           String wordFreq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
           if (setMatrixB_1.contains(word)) {
             int taille = 8 + sizeT + sizeF;
             while (taille < line.length()) {
               int size2T = Integer.parseInt(line.substring(taille, taille + 2));
               int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
               String tag = line.substring(taille + 4, taille + 4 + size2T);
               String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
               if (setMatrixB.contains(word + ":" + tag)) {
                 String wordArb = Transliteration.getInstance().getBuckWalterToArabic(word);
                 String tagArb = Transliteration.getInstance().getBuckWalterToArabic(tag);
                 this.matrixB.put(wordArb + ":" + tagArb, wordFreq + ":" + tagFreq);
              } 
               taille += 6 + size2T + size2F;
            } 
          } 
          continue;
        } 
         if (line.charAt(0) == 'A') {
           int sizeT = Integer.parseInt(line.substring(2, 4));
           int sizeF = Integer.parseInt(line.substring(4, 5));
           String tag1 = line.substring(6, 6 + sizeT);
           String tag1Freq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
           if (setMatrixA_1.contains(tag1)) {
             int taille = 8 + sizeT + sizeF;
             while (taille < line.length()) {
               int size2T = Integer.parseInt(line.substring(taille, taille + 2));
               int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
               String tag2 = line.substring(taille + 4, taille + 4 + size2T);
               String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
               if (setMatrixA.contains(tag1 + ":" + tag2)) {
                 String tag1Arb = Transliteration.getInstance().getBuckWalterToArabic(tag1);
                 String tag2Arb = Transliteration.getInstance().getBuckWalterToArabic(tag2);
                 this.matrixA.put(tag1Arb + ":" + tag2Arb, tag1Freq + ":" + tagFreq);
              } 
               taille += 6 + size2T + size2F;
            } 
          } 
        } 
      } 
    }
    
    private void setAllMatrix(InputStream nameFileIn, String nameCharset, Set<String> setMatrixS, Set<String> setMatrixB, Set<String> setMatrixB_1, Set<String> setMatrixA, Set<String> setMatrixA_1) {
       this.matrixS = new HashMap<>();
       this.matrixB = new HashMap<>();
       this.matrixA = new HashMap<>();
      try {
         BufferedReader in = new BufferedReader(new InputStreamReader(nameFileIn, nameCharset));
         String SFreq = "";
        String line;
         while ((line = in.readLine()) != null) {
           if (line.charAt(0) == 'S') {
             if (line.charAt(1) == '1') {
               int size = Integer.parseInt(line.substring(3, 5));
               String Stag = line.substring(6, 6 + size);
               String StagFreq = line.substring(7 + size);
               if (setMatrixS.contains(Stag)) {
                 String tagArb = Transliteration.getInstance().getBuckWalterToArabic(Stag);
                 this.matrixS.put(tagArb, SFreq + ":" + StagFreq);
              } 
              continue;
            } 
             SFreq = line.substring(3);
            continue;
          } 
           if (line.charAt(0) == 'B') {
             int sizeT = Integer.parseInt(line.substring(2, 4));
             int sizeF = Integer.parseInt(line.substring(4, 5));
             String word = line.substring(6, 6 + sizeT);
             String wordFreq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
             if (setMatrixB_1.contains(word)) {
               int taille = 8 + sizeT + sizeF;
               while (taille < line.length()) {
                 int size2T = Integer.parseInt(line.substring(taille, taille + 2));
                 int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
                 String tag = line.substring(taille + 4, taille + 4 + size2T);
                 String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
                 if (setMatrixB.contains(word + ":" + tag)) {
                   String wordArb = Transliteration.getInstance().getBuckWalterToArabic(word);
                   String tagArb = Transliteration.getInstance().getBuckWalterToArabic(tag);
                   this.matrixB.put(wordArb + ":" + tagArb, wordFreq + ":" + tagFreq);
                } 
                 taille += 6 + size2T + size2F;
              } 
            } 
            continue;
          } 
           if (line.charAt(0) == 'A') {
             int sizeT = Integer.parseInt(line.substring(2, 4));
             int sizeF = Integer.parseInt(line.substring(4, 5));
             String tag1 = line.substring(6, 6 + sizeT);
             String tag1Freq = line.substring(7 + sizeT, 7 + sizeT + sizeF);
             if (setMatrixA_1.contains(tag1)) {
               int taille = 8 + sizeT + sizeF;
               while (taille < line.length()) {
                 int size2T = Integer.parseInt(line.substring(taille, taille + 2));
                 int size2F = Integer.parseInt(line.substring(taille + 2, taille + 3));
                 String tag2 = line.substring(taille + 4, taille + 4 + size2T);
                 String tagFreq = line.substring(taille + 4 + size2T, taille + 5 + size2T + size2F);
                 if (setMatrixA.contains(tag1 + ":" + tag2)) {
                   String tag1Arb = Transliteration.getInstance().getBuckWalterToArabic(tag1);
                   String tag2Arb = Transliteration.getInstance().getBuckWalterToArabic(tag2);
                   this.matrixA.put(tag1Arb + ":" + tag2Arb, tag1Freq + ":" + tagFreq);
                } 
                 taille += 6 + size2T + size2F;
              } 
            } 
          } 
        } 
         in.close();
      } catch (IOException ex) {
         System.out.println("Erreur : " + ex);
      } 
    }
    
    public void processAllTagsFileResultLine(String fileIN, String encodingIN, String fileOUT, String encodingOUT, AlKhalil2Analyzer _analyzer) {
       List<String> list = IOFile.getInstance().readFileToList(fileIN, encodingIN);
       List<String> result = new ArrayList<>();
       Iterator<String> it = list.iterator();
       while (it.hasNext()) {
         String text = it.next();
         if (!text.equals("")) {
           setTokenizationString(text);
           addMatrixS(this.allTokens, this.morph);
           addMatrixA(this.allTokens, this.morph);
           addMatrixB(this.morph);
           setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), "Cp1252", this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
           if (this.allTokens.size() > 1) {
             List<String> res = analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.add(getResultAllTagsSample(this.allText, this.morph, res, _analyzer));
            continue;
          } 
           if (this.allTokens.size() == 1) {
             List<String> res = analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.add(getResultAllTagsSample(this.allText, this.morph, res, _analyzer));
            continue;
          } 
           result.add(text);
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    private String getResultAllTagsSample(List<String> _allText, Map<String, List<String>> _morph, List<String> res, AlKhalil2Analyzer _analyzer) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           result = result + (!this.notAnaTokens.contains(token) ? getAllTagsSample(token, res.get(j), _analyzer.analyzerToken(token), _analyzer) : token);
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
    
    private String getAllTagsSample(String word, String lemma, List<Result> _result, AlKhalil2Analyzer _analyzer) {
       if (!_result.isEmpty()) {
         String ValRoot = "";
         String rootMax = "";
         String stemMax = "";
         String resProc = "";
         String resEnc = "";
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             if (ValRoot.equals(""))
               ValRoot = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getRoot()); 
             String vroot = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getRoot());
             if (vroot.equals("-"))
               vroot = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getStem()); 
             String vstem = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getStem());
             if (rootMax.equals("") || (_analyzer.map.containsKey(vroot) && _analyzer.map.containsKey(rootMax) && ((Double)_analyzer.map.get(vroot)).doubleValue() > ((Double)_analyzer.map.get(rootMax)).doubleValue())) {
               rootMax = vroot;
               stemMax = vstem;
               resProc = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getProcliticNoDec()) + "+";
               resEnc = "+" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(st_result.getEncliticNoDec());
               resProc = (st_result.getProcliticNoDec().equals("#") || st_result.getProcliticNoDec().equals("-")) ? "" : resProc;
               resEnc = (st_result.getEncliticNoDec().equals("#") || st_result.getEncliticNoDec().equals("-")) ? "" : resEnc;
            } 
          } 
        } 
         return resProc + stemMax + resEnc;
      } 
       return word;
    }
    
    public void processLemmatization(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       List<String> result = new ArrayList<>();
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             List<String> res = analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
            continue;
          } 
           if (iTokens.size() == 1) {
             List<String> res = analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
          } 
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getADDLemma(Tokenization.getInstance().getAllText(), this.morph, result));
    }
    
    private List<String> getADDLemma(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       List<String> results = new ArrayList<>();
       String result = "";
       int j = 0;
       for (String token : _allText) {
         if (token.equals("\n")) {
           results.add(result);
           result = "";
          continue;
        } 
         if (_morph.containsKey(token)) {
           String lemma = !this.notAnaTokens.contains(token) ? res.get(j) : token;
           result = result + "{{" + token + ":" + lemma + "}}";
           j++;
          continue;
        } 
         result = result + token;
      } 
       if (!result.equals(""))
         results.add(result); 
       return results;
    }
    
    public void processLightStemmer(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       List<String> result = new ArrayList<>();
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             List<String> res = analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
            continue;
          } 
           if (iTokens.size() == 1) {
             List<String> res = analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
          } 
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getADDStem(Tokenization.getInstance().getAllText(), this.morph, result));
    }
    
    private List<String> getADDStem(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       List<String> results = new ArrayList<>();
       String result = "";
       int j = 0;
       for (String token : _allText) {
         if (token.equals("\n")) {
           results.add(result);
           result = "";
          continue;
        } 
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             long max = 0L;
             String stem = "";
             Iterator<String> it_result = ((Set<String>)AlKhalil2Analyzer.getInstance().processToken(token).getAllLemmaStems().get(lemma)).iterator();
             while (it_result.hasNext()) {
               String stem1 = it_result.next();
               long val = this.mapStem.containsKey(stem1) ? ((Long)this.mapStem.get(stem1)).longValue() : 0L;
               if (max <= val) {
                 stem = stem1;
                 max = val;
              } 
            } 
             result = result + "{{" + token + ":" + stem + "}}";
             listStem.add(stem);
          } else {
             result = result + "{{" + token + ":" + token + "}}";
          } 
           j++;
          continue;
        } 
         result = result + token;
      } 
       if (!result.equals(""))
         results.add(result); 
       return results;
    }
    
    public void processHeavyStemmer(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       List<String> result = new ArrayList<>();
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             List<String> res = analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
            continue;
          } 
           if (iTokens.size() == 1) {
             List<String> res = analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
          } 
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getADDRoot(Tokenization.getInstance().getAllText(), this.morph, result));
    }
    
    private List<String> getADDRoot(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       List<String> results = new ArrayList<>();
       String result = "";
       int j = 0;
       for (String token : _allText) {
         if (token.equals("\n")) {
           results.add(result);
           result = "";
          continue;
        } 
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             long max = 0L;
             String root = "";
             Iterator<String> it_result = ((Set<String>)AlKhalil2Analyzer.getInstance().processToken(token).getAllLemmaRoots().get(lemma)).iterator();
             while (it_result.hasNext()) {
               String root1 = it_result.next();
               long val = this.mapRoot.containsKey(root1) ? ((Long)this.mapRoot.get(root1)).longValue() : 0L;
               if (max <= val) {
                 root = root1;
                 max = val;
              } 
            } 
             result = result + "{{" + token + ":" + root + "}}";
             listRoot.add(root);
          } else {
             result = result + "{{" + token + ":" + token + "}}";
          } 
           j++;
          continue;
        } 
         result = result + token;
      } 
       if (!result.equals(""))
         results.add(result); 
       return results;
    }
    
    public void processLemmatization1(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       List<String> result = new ArrayList<>();
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             List<String> res = analyzed(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
            continue;
          } 
           if (iTokens.size() == 1) {
             List<String> res = analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB);
             result.addAll(res);
          } 
        } 
      } 
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, getADDLemma(Tokenization.getInstance().getAllText(), this.morph, result));
    }
    
    public void writeMorphoResult(String fileOUT, String encodingOUT) {
       Map<String, String> outmorph = new HashMap<>();
       Iterator<String> it_result = Tokenization.getInstance().getTokens().iterator();
       while (it_result.hasNext()) {
         String word = it_result.next();
         if (!outmorph.containsKey(word))
           outmorph.put(word, AlKhalil2Analyzer.getInstance().processToken(word).getAllLemmasString('\t')); 
      } 
       List<String> l = new ArrayList<>();
       it_result = outmorph.keySet().iterator();
       while (it_result.hasNext()) {
         String word = it_result.next();
         l.add(word + '\t' + (String)outmorph.get(word));
      } 
       Collections.sort(l);
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, l);
    }
    
    public void processADATLemmatizerFile(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> result = new ArrayList<>();
       Iterator<String> it = IOFile.getInstance().readFileToList(fileIN, encodingIN).iterator();
       for (; it.hasNext(); result.add(processADATLemmatizerString(it.next())));
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    public void processADATRacineurFile(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> result = new ArrayList<>();
       Iterator<String> it = IOFile.getInstance().readFileToList(fileIN, encodingIN).iterator();
       for (; it.hasNext(); result.add(processADATRacineurString(it.next())));
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    public void processADATStemmerFile(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> result = new ArrayList<>();
       Iterator<String> it = IOFile.getInstance().readFileToList(fileIN, encodingIN).iterator();
       for (; it.hasNext(); result.add(processADATStemmerString(it.next())));
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    public void processADATStemmerWithPOSFile(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> result = new ArrayList<>();
       Iterator<String> it = IOFile.getInstance().readFileToList(fileIN, encodingIN).iterator();
       for (; it.hasNext(); result.add(processADATStemmerWithPOSString(it.next())));
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    public void processADATTripleFile(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> result = new ArrayList<>();
       Iterator<String> it = IOFile.getInstance().readFileToList(fileIN, encodingIN).iterator();
       for (; it.hasNext(); result.add(processADATTripleString(it.next())));
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    public void processADATTokenizedFile(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       List<String> result = new ArrayList<>();
       Iterator<String> it = IOFile.getInstance().readFileToList(fileIN, encodingIN).iterator();
       for (; it.hasNext(); result.add(processADATTokenizedString(it.next())));
       IOFile.getInstance().writeListToFile(fileOUT, encodingOUT, result);
    }
    
    public String processADATLemmatizerString(String text) {
       setTokenizationString(text);
       return (this.allTokens.size() > 1) ? getADATLemmatizerResult(this.allText, this.morph, analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : (
         (this.allTokens.size() == 1) ? getADATLemmatizerResult(this.allText, this.morph, analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : text);
    }
    
    public String processADATRacineurString(String text) {
       setTokenizationString(text);
       return (this.allTokens.size() > 1) ? getADATRacineurResult(this.allText, this.morph, analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : (
         (this.allTokens.size() == 1) ? getADATRacineurResult(this.allText, this.morph, analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : text);
    }
    
    public String processADATStemmerString(String text) {
       setTokenizationString(text);
       return (this.allTokens.size() > 1) ? getADATStemmerResult(this.allText, this.morph, analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : (
         (this.allTokens.size() == 1) ? getADATStemmerResult(this.allText, this.morph, analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : text);
    }
    
    public String processADATStemmerWithPOSString(String text) {
       setTokenizationString(text);
       return (this.allTokens.size() > 1) ? getADATStemmerWithPOSResult(this.allText, this.morph, analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : (
         (this.allTokens.size() == 1) ? getADATStemmerWithPOSResult(this.allText, this.morph, analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : text);
    }
    
    public String processADATTokenizedString(String text) {
       setTokenizationString(text);
       return (this.allTokens.size() > 1) ? getADATTokenizedResult(this.allText, this.morph, analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : (
         (this.allTokens.size() == 1) ? getADATTokenizedResult(this.allText, this.morph, analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : text);
    }
    
    public String processADATTripleString(String text) {
       setTokenizationString(text);
       return (this.allTokens.size() > 1) ? getADATTripleResult(this.allText, this.morph, analyzed(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : (
         (this.allTokens.size() == 1) ? getADATTripleResult(this.allText, this.morph, analyzedToken(this.allTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)) : text);
    }
    
    private String getADATTokenizedResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + getADATTokenizedResultWord(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token));
          } else {
             result = result + token;
          } 
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
    
    private String getADATTokenizedResultWord(String word, String lemma, List<Result> _result) {
       int i = 0;
       int imax = 0;
       long max = 0L;
       List<Result> list = new ArrayList<>();
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             list.add(st_result);
             String str = st_result.getStem();
             long val = this.mapStem.containsKey(str) ? ((Long)this.mapStem.get(str)).longValue() : 0L;
             if (i == 0) {
               imax = i;
               max = val;
              continue;
            } 
             if (max < val) {
               max = val;
               imax = i;
            } 
          } 
        } 
         Result re = list.get(imax);
         String resProc = "" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getProcliticNoDec()) + "+ ";
         String resEnc = " +" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getEncliticNoDec()) + "";
         String stem = "" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getStem()) + "";
         resProc = (re.getProcliticNoDec().equals("#") || re.getProcliticNoDec().equals("-")) ? "" : resProc;
         resEnc = (re.getEncliticNoDec().equals("#") || re.getEncliticNoDec().equals("-")) ? "" : resEnc;
         return resProc + stem + resEnc;
      } 
       return word;
    }
    
    private String getTriples(String word, String lemma, List<Result> _result) {
       int i = 0;
       int imax = 0;
       long max = 0L;
       List<Result> list = new ArrayList<>();
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             list.add(st_result);
             String str = st_result.getStem();
             long val = this.mapStem.containsKey(str) ? ((Long)this.mapStem.get(str)).longValue() : 0L;
             if (i == 0) {
               imax = i;
               max = val;
              continue;
            } 
             if (max < val) {
               max = val;
               imax = i;
            } 
          } 
        } 
         Result re = list.get(imax);
         String resProc = "" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getProcliticNoDec()) + "+ ";
         String resEnc = " +" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getEncliticNoDec()) + "";
         String stem = "" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getStem()) + "";
         resProc = (re.getProcliticNoDec().equals("#") || re.getProcliticNoDec().equals("-")) ? "" : resProc;
         resEnc = (re.getEncliticNoDec().equals("#") || re.getEncliticNoDec().equals("-")) ? "" : resEnc;
         return resProc + stem + resEnc + "|" + re.getRoot() + "|" + ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(re.getDiacPatternStem());
      } 
       return word + "|" + word + "|" + word;
    }
    
    private String getStemWithPOS(String word, String lemma, List<Result> _result) {
       int i = 0;
       int imax = 0;
       long max = 0L;
       List<Result> list = new ArrayList<>();
       if (!_result.isEmpty()) {
         Iterator<Result> it_result = _result.iterator();
         while (it_result.hasNext()) {
           Result st_result = it_result.next();
           if (lemma.equals(st_result.getLemma())) {
             list.add(st_result);
             String stem = st_result.getStem();
             long val = this.mapStem.containsKey(stem) ? ((Long)this.mapStem.get(stem)).longValue() : 0L;
             if (i == 0) {
               imax = i;
               max = val;
              continue;
            } 
             if (max < val) {
               max = val;
               imax = i;
            } 
          } 
        } 
         Result re = list.get(imax);
         return re.getStem() + "+" + re.getPartOfSpeech();
      } 
       return word + "+" + word;
    }
    
    private String getADATLemmatizerResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "{{" + token + ":" + lemma + "}}";
          } else {
             result = result + "{{" + token + ":" + token + "}}";
          } 
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
    
    private String getADATRacineurResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "{{" + token + ":" + getRoots(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token)) + "}}";
          } else {
             result = result + "{{" + token + ":" + token + "}}";
          } 
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
    
    private String getADATStemmerResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "{{" + token + ":" + getStems(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token)) + "}}";
          } else {
             result = result + "{{" + token + ":" + token + "}}";
          } 
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
    
    private String getADATStemmerWithPOSResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "{{" + token + ":" + getStemWithPOS(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token)) + "}}";
          } else {
             result = result + "{{" + token + ":" + token + "}}";
          } 
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
    
    private String getADATTripleResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String lemma = res.get(j);
             result = result + "{{" + token + ":" + getTriples(token, lemma, AlKhalil2Analyzer.getInstance().analyzerToken(token)) + "}}";
          } else {
             result = result + "{{" + token + ":" + token + "|" + token + "|" + token + "}}";
          } 
           j++;
        } else {
           result = result + token;
        } 
      } 
       return result;
    }
  }

