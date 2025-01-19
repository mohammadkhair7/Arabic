   package net.oujda_nlp_team;
   
   import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oujda_nlp_team.Spline.Phi;
import net.oujda_nlp_team.Spline.Spline_model2;
import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.Tokenization;
import net.oujda_nlp_team.util.Transliteration;
   
   public class ADATAnalyzer1 {
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
     
     private final Map<String, Long> mapRoot;
     
     private final Map<String, Long> mapLemma;
     
     private final Map<String, Long> mapStem;
     
     private final Set<String> notAnaTokens;
     
     private final Map<String, List<String>> morph;
     
     private static final ADATAnalyzer1 instance = new ADATAnalyzer1();
     
     public static ADATAnalyzer1 getInstance() {
       return instance;
     }
     
     private ADATAnalyzer1() {
       this.nameFile = "/net/oujda_nlp_team/resources/DATA.MSA.ALL.TRAIN.141809.lm";
       this.encoding = "Cp1252";
       String fileRoot = "/net/oujda_nlp_team/resources/DATA.MSA.SHA.ROOT.map";
       String fileLemma = "/net/oujda_nlp_team/resources/DATA.MSA.SHA.LEMMA.map";
       String fileStem = "/net/oujda_nlp_team/resources/DATA.MSA.SHA.STEM.map";
       this.mapRoot = IOFile.getInstance().deserializeMap(fileRoot);
       this.mapLemma = IOFile.getInstance().deserializeMap(fileLemma);
       this.mapStem = IOFile.getInstance().deserializeMap(fileStem);
       this.morph = new HashMap<>();
       this.notAnaTokens = new HashSet<>();
     }
     
     private List<String> analyzedToken(List<String> _allTokens, Map<String, List<String>> _morph, Map<String, String> _matrixS, Map<String, String> _matrixA, Map<String, String> _matrixB) {
       Map<String, Double> startMatrix = new HashMap<>();
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
     
     private void setAllMatrix(InputStream nameFileIn, String nameCharset, Set<String> setMatrixS, Set<String> setMatrixB, Set<String> setMatrixB_1, Set<String> setMatrixA, Set<String> setMatrixA_1) {
       this.matrixS = new HashMap<>();
       this.matrixB = new HashMap<>();
       this.matrixA = new HashMap<>();
       try {
         BufferedReader in = new BufferedReader(new InputStreamReader(nameFileIn, nameCharset));
         String SFreq = "";
         String Stag = "";
         String StagFreq = "";
         String line;
         while ((line = in.readLine()) != null) {
           if (line.charAt(0) == 'S') {
             if (line.charAt(1) == '1') {
               int size = Integer.parseInt(line.substring(3, 5));
               Stag = line.substring(6, 6 + size);
               StagFreq = line.substring(7 + size);
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
     
     public void processLemmatization(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       List<String> result = new ArrayList<>();
       int i = 0;
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             result.addAll(analyzed_Spline_Model2(iTokens, this.morph, this.matrixA, this.matrixB));
             continue;
           } 
           if (iTokens.size() == 1)
             result.addAll(analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)); 
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
           listLemma.add(lemma);
           j++;
           continue;
         } 
         result = result + token;
       } 
       if (!result.equals(""))
         results.add(result); 
       return results;
     }
     
     private List<String> analyzed_Spline_Model2(List<String> words, Map<String, List<String>> _morph, Map<String, String> _matrixA, Map<String, String> _matrixB) {
       List<String> _result = new ArrayList<>();
       Map<String, Phi> _iMapPsyResult = new HashMap<>();
       for (int i = 1; i < words.size(); i++) {
         String word_j = words.get(i - 1);
         String word_j_1 = words.get(i);
         _iMapPsyResult = Spline_model2.getInstance().getArgMaxI(word_j, word_j_1, _morph, _matrixA, _matrixB, _iMapPsyResult, i);
       } 
       double Max_C = 0.0D;
       String argMax_C = "#";
       Iterator<String> itt = _iMapPsyResult.keySet().iterator();
       while (itt.hasNext()) {
         String tag = itt.next();
         Phi Psy = _iMapPsyResult.get(tag);
         if (Max_C <= Psy.getFrequency()) {
           Max_C = Psy.getFrequency();
           argMax_C = tag;
         } 
       } 
       for (int j = 0; j < words.size() - 1; j++) {
         String tag = ((Phi)_iMapPsyResult.get(argMax_C)).getPreviousTag(j);
         _result.add(tag);
       } 
       _result.add(argMax_C);
       return _result;
     }
     
     public void processLightStemmer(String fileIN, String encodingIN, String fileOUT, String encodingOUT) {
       Tokenization.getInstance().setTokenizationFile(fileIN, encodingIN);
       addAllMorphoResult(Tokenization.getInstance().getTokens());
       addMatrixS(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixA(Tokenization.getInstance().getAllTokens(), this.morph);
       addMatrixB(this.morph);
       setAllMatrix(IOFile.getInstance().openFileXml(this.nameFile), this.encoding, this.setMatrixS, this.setMatrixB, this.setMatrixB_1, this.setMatrixA, this.setMatrixA_1);
       List<String> result = new ArrayList<>();
       int i = 0;
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             result.addAll(analyzed_Spline_Model2(iTokens, this.morph, this.matrixA, this.matrixB));
             continue;
           } 
           if (iTokens.size() == 1)
             result.addAll(analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)); 
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
       int i = 0;
       Iterator<String> it = Tokenization.getInstance().getAllSentences().iterator();
       while (it.hasNext()) {
         String sentence = it.next();
         if (!sentence.equals("")) {
           List<String> iTokens = new ArrayList<>();
           iTokens.addAll(Arrays.asList(sentence.split(" ")));
           if (iTokens.size() > 1) {
             result.addAll(analyzed_Spline_Model2(iTokens, this.morph, this.matrixA, this.matrixB));
             continue;
           } 
           if (iTokens.size() == 1)
             result.addAll(analyzedToken(iTokens, this.morph, this.matrixS, this.matrixA, this.matrixB)); 
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
   }
