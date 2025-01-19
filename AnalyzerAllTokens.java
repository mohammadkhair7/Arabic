   package net.oujda_nlp_team;
   
   import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.util.Settings;
import net.oujda_nlp_team.util.Statistic;
import net.oujda_nlp_team.util.Times;
import net.oujda_nlp_team.util.Tokenization;
   
   public class AnalyzerAllTokens {
     public static Map<String, Integer> iMapRepeat = new HashMap<>();
     
     public static List<String> getAnalyzedText(String textToAnalyzed) {
       Tokenization.getInstance().setTokenizationString(textToAnalyzed);
       iMapRepeat = Tokenization.getInstance().getTokensRepeat();
       (AlKhalil2Analyzer.getInstance()).allResults = new HashMap<>();
       int nbNoAnalyzedWord = 0;
       int nbAnalyzedWord = 0;
       int i = 0;
       List<Comparable> l = new ArrayList();
       l.addAll(Tokenization.getInstance().getTokens());
       Collections.sort(l);
       Iterator<String> it_normalized = (Iterator)l.iterator();
       Times.start();
       while (it_normalized.hasNext()) {
         String normalizedWord = it_normalized.next();
         List result = AlKhalil2Analyzer.getInstance().analyzerToken(normalizedWord);
         if (result.isEmpty()) {
           nbNoAnalyzedWord++;
         } else {
           nbAnalyzedWord++;
         } 
         (AlKhalil2Analyzer.getInstance()).allResults.put(normalizedWord, result);
         i++;
       } 
       Times.end();
       (new Statistic()).setNbTotalWords(Tokenization.getInstance().getNbAllTokens());
       (new Statistic()).setNbWord(Tokenization.getInstance().getNbTokens());
       (new Statistic()).setNbAnalyzedWord(nbAnalyzedWord);
       (new Statistic()).setNbNoAnalyzedWord(nbNoAnalyzedWord);
       (new Statistic()).setTimes(Times.getTimes());
       return Tokenization.getInstance().getAllText();
     }
     
     public static List<String> getAnalyzedFile(String nameFile, String nameCharset) {
       Tokenization.getInstance().setTokenizationFile(nameFile, nameCharset);
       iMapRepeat = Tokenization.getInstance().getTokensRepeat();
       (AlKhalil2Analyzer.getInstance()).allResults = new HashMap<>();
       int nbNoAnalyzedWord = 0;
       int nbAnalyzedWord = 0;
       int i = 0;
       List<Comparable> l = new ArrayList();
       l.addAll(Tokenization.getInstance().getTokens());
       Collections.sort(l);
       Iterator<String> it_normalized = (Iterator)l.iterator();
       Times.start();
       while (it_normalized.hasNext()) {
         String normalizedWord = it_normalized.next();
         List result = AlKhalil2Analyzer.getInstance().analyzerToken(normalizedWord);
         if (result.isEmpty()) {
           nbNoAnalyzedWord++;
         } else {
           nbAnalyzedWord++;
         } 
         (AlKhalil2Analyzer.getInstance()).allResults.put(normalizedWord, result);
         i++;
       } 
       Times.end();
       (new Statistic()).setNbTotalWords(Tokenization.getInstance().getNbAllTokens());
       (new Statistic()).setNbWord(Tokenization.getInstance().getNbTokens());
       (new Statistic()).setNbAnalyzedWord(nbAnalyzedWord);
       (new Statistic()).setNbNoAnalyzedWord(nbNoAnalyzedWord);
       (new Statistic()).setTimes(Times.getTimes());
       return Tokenization.getInstance().getAllText();
     }
     
     public List<String> getResultLemma(String word) {
       Settings.changeSettings(false, false, false, false, false, false, true, false, false, false, false);
       List<String> res = new ArrayList<>();
       List<Result> result = AlKhalil2Analyzer.getInstance().analyzerToken(word);
       if (result.isEmpty()) {
         System.out.println("");
       } else {
         Set<String> resL = new HashSet<>();
         for (int i = 0; i < result.size(); i++) {
           String str = ((Result)result.get(i)).getLemma();
           if (!str.equals("#"))
             resL.add(str); 
         } 
         res.addAll(resL);
         Collections.sort(res);
       } 
       return res;
     }
     
     public List<String> getResultStem(String word) {
       Settings.changeSettings(false, false, true, false, false, false, false, false, false, false, false);
       List<String> res = new ArrayList<>();
       List<Result> result = AlKhalil2Analyzer.getInstance().analyzerToken(word);
       if (result.isEmpty()) {
         System.out.println("");
       } else {
         Set<String> resL = new HashSet<>();
         for (int i = 0; i < result.size(); i++) {
           String str = ((Result)result.get(i)).getStem();
           if (!str.equals("#"))
             resL.add(str); 
         } 
         res.addAll(resL);
         Collections.sort(res);
       } 
       return res;
     }
   }
