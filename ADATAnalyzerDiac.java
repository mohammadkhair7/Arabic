   package net.oujda_nlp_team;
   
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

import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.Transliteration;
import net.oujda_nlp_team.util.Validator;
   
   public class ADATAnalyzerDiac {
     private List<String> allTokens;
     
     private Set<String> allTokensOutil;
     
     private List<String> allText;
     
     private final Set<String> notAnaTokens;
     
     private final Map<String, List<String>> morph;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_01;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_02;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_03;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_04;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_05;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_06;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_07;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_08;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_09;
     
     private final Map<Integer, Map<Integer, Integer>> mapC_10;
     
     private final Map<String, Integer> mapTOKENtoINTvow;
     
     private final Map<String, Integer> mapTOKENtoINTunv;
     
     private final Map<Integer, Integer> mapINTVOWSTART;
     
     private final Map<Integer, Integer> mapINTUNVTOKEN;
     
     private final Map<Integer, Integer> mapINTVOWTOKEN;
     
     private static final ADATAnalyzerDiac instance = new ADATAnalyzerDiac();
     
     public static ADATAnalyzerDiac getInstance() {
       return instance;
     }
     
     private ADATAnalyzerDiac() {
       String outTOKENtoINTvow = "/net/oujda_nlp_team/resources/diac/TOKEN.INT.VOW.SH.map";
       String outTOKENtoINTunv = "/net/oujda_nlp_team/resources/diac/TOKEN.INT.UNV.SH.map";
       String outINTVOWSTART = "/net/oujda_nlp_team/resources/diac/INT.VOW.START.SH.map";
       String outINTVOWTOKEN = "/net/oujda_nlp_team/resources/diac/TOKEN.VAL.VOW.SH.map";
       String outINTUNVTOKEN = "/net/oujda_nlp_team/resources/diac/TOKEN.VAL.UNV.SH.map";
       this.mapTOKENtoINTvow = IOFile.getInstance().deserializeMap(outTOKENtoINTvow);
       this.mapTOKENtoINTunv = IOFile.getInstance().deserializeMap(outTOKENtoINTunv);
       this.mapINTVOWSTART = IOFile.getInstance().deserializeMap(outINTVOWSTART);
       this.mapINTVOWTOKEN = IOFile.getInstance().deserializeMap(outINTVOWTOKEN);
       this.mapINTUNVTOKEN = IOFile.getInstance().deserializeMap(outINTUNVTOKEN);
       String outTOKEN_A_01 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.01.map";
       String outTOKEN_A_02 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.02.map";
       String outTOKEN_A_03 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.03.map";
       String outTOKEN_A_04 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.04.map";
       String outTOKEN_A_05 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.05.map";
       String outTOKEN_A_06 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.06.map";
       String outTOKEN_A_07 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.07.map";
       String outTOKEN_A_08 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.08.map";
       String outTOKEN_A_09 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.09.map";
       String outTOKEN_A_10 = "/net/oujda_nlp_team/resources/diac/DIAC.VOW.SH.10.map";
       this.mapC_01 = IOFile.getInstance().deserializeMap(outTOKEN_A_01);
       this.mapC_02 = IOFile.getInstance().deserializeMap(outTOKEN_A_02);
       this.mapC_03 = IOFile.getInstance().deserializeMap(outTOKEN_A_03);
       this.mapC_04 = IOFile.getInstance().deserializeMap(outTOKEN_A_04);
       this.mapC_05 = IOFile.getInstance().deserializeMap(outTOKEN_A_05);
       this.mapC_06 = IOFile.getInstance().deserializeMap(outTOKEN_A_06);
       this.mapC_07 = IOFile.getInstance().deserializeMap(outTOKEN_A_07);
       this.mapC_08 = IOFile.getInstance().deserializeMap(outTOKEN_A_08);
       this.mapC_09 = IOFile.getInstance().deserializeMap(outTOKEN_A_09);
       this.mapC_10 = IOFile.getInstance().deserializeMap(outTOKEN_A_10);
       System.out.println(this.mapTOKENtoINTvow.size());
       System.out.println(this.mapTOKENtoINTunv.size());
       System.out.println(this.mapINTVOWSTART.size());
       System.out.println(this.mapINTVOWTOKEN.size());
       System.out.println(this.mapINTUNVTOKEN.size());
       System.out.println(this.mapC_01.size());
       System.out.println(this.mapC_02.size());
       System.out.println(this.mapC_03.size());
       System.out.println(this.mapC_04.size());
       System.out.println(this.mapC_05.size());
       System.out.println(this.mapC_06.size());
       System.out.println(this.mapC_07.size());
       System.out.println(this.mapC_08.size());
       System.out.println(this.mapC_09.size());
       System.out.println(this.mapC_10.size());
       this.morph = new HashMap<>();
       this.notAnaTokens = new HashSet<>();
       this.allTokensOutil = new HashSet<>();
       this.allTokensOutil.add("فِي");
       this.allTokensOutil.add("عَنْ");
       this.allTokensOutil.add("مِنْ");
       this.allTokensOutil.add("وَفِي");
       this.allTokensOutil.add("عَلَى");
       this.allTokensOutil.add("إِلَى");
       this.allTokensOutil.add("وَعَنْ");
       this.allTokensOutil.add("وَمِنْ");
       this.allTokensOutil.add("وَإِلَى");
       this.allTokensOutil.add("وَعَلَى");
     }
     
     public String processDiacrized1(String text) {
       setTokenizationStringDiac(text);
       if (this.allTokens.size() > 1)
         return getResult(this.allText, this.morph, analyzed(this.allTokens)); 
       if (this.allTokens.size() == 1)
         return getResult(this.allText, this.morph, analyzedToken(this.allTokens)); 
       return text;
     }
     
     private String getResult(List<String> _allText, Map<String, List<String>> _morph, List<String> res) {
       String result = "";
       int j = 0;
       for (int i = 0; i < _allText.size(); i++) {
         String token = _allText.get(i);
         if (_morph.containsKey(token)) {
           if (!this.notAnaTokens.contains(token)) {
             String diacword = res.get(j);
             result = result + "<span class='couple'><span class='token' >" + diacword + "</span></span>";
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
     
     private List<String> analyzed(List<String> _allTokens) {
       Map<Integer, List<Integer>> iMapRes = new HashMap<>();
       Map<String, Double> startMatrix = new HashMap<>();
       Map<String, Double> IMapResW1 = new HashMap<>();
       Map<String, Double> IMapResW2 = new HashMap<>();
       String sword = _allTokens.get(0);
       int size = ((List)this.morph.get(sword)).size();
       double som = 0.0D;
       double D = 0.5D;
       int maxStart = 1;
       int maxN = 5191554;
       int max1 = 5191554;
       Iterator<String> it = ((List<String>)this.morph.get(sword)).iterator();
       if (it.hasNext()) {
         String tag = it.next();
         String bwTag = Transliteration.getInstance().getArabicToBuckWalter(tag);
         int itag = this.mapTOKENtoINTvow.containsKey(bwTag) ? ((Integer)this.mapTOKENtoINTvow.get(bwTag)).intValue() : -1;
         double x = (1 / maxN);
         if (itag > -1 && this.mapINTVOWSTART.containsKey(Integer.valueOf(itag))) {
           maxStart = ((Integer)this.mapINTVOWSTART.get(Integer.valueOf(itag))).intValue();
           x = maxStart / maxN;
         } 
         som = x;
         startMatrix.put(tag, Double.valueOf(x));
       } 
       while (it.hasNext()) {
         String tag = it.next();
         String bwTag = Transliteration.getInstance().getArabicToBuckWalter(tag);
         int itag = this.mapTOKENtoINTvow.containsKey(bwTag) ? ((Integer)this.mapTOKENtoINTvow.get(bwTag)).intValue() : -1;
         int start = 0;
         double x = (1 / max1);
         if (itag > -1 && this.mapINTVOWSTART.containsKey(Integer.valueOf(itag))) {
           start = ((Integer)this.mapINTVOWSTART.get(Integer.valueOf(itag))).intValue();
           x = start / maxN;
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
         double val = ((maxStart / maxN) + ((Double)startMatrix.get(tag)).doubleValue()) / (som + (maxStart / maxN * size));
         startMatrix.put(tag, Double.valueOf(val));
       } 
       int j = 0;
       int i;
       for (i = 1; i < _allTokens.size(); i++) {
         IMapResW1 = new HashMap<>();
         List<Integer> il = new ArrayList<>();
         String word1 = _allTokens.get(i - 1);
         String word2 = _allTokens.get(i);
         Iterator<String> it_2 = ((List<String>)this.morph.get(word2)).iterator();
         while (it_2.hasNext()) {
           String tag2 = it_2.next();
           String bwTag2 = Transliteration.getInstance().getArabicToBuckWalter(tag2);
           int itag2 = this.mapTOKENtoINTvow.containsKey(bwTag2) ? ((Integer)this.mapTOKENtoINTvow.get(bwTag2)).intValue() : -1;
           String uword2 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2);
           String uBWword2 = Transliteration.getInstance().getArabicToBuckWalter(uword2);
           int iuword2 = this.mapTOKENtoINTunv.containsKey(uBWword2) ? ((Integer)this.mapTOKENtoINTunv.get(uBWword2)).intValue() : -1;
           double d1 = 0.0D;
           int k = 0;
           String str1 = "";
           j = 0;
           Iterator<String> it_1 = ((List<String>)this.morph.get(word1)).iterator();
           if (it_1.hasNext()) {
             String tag1 = it_1.next();
             String bwTag1 = Transliteration.getInstance().getArabicToBuckWalter(tag1);
             int itag1 = this.mapTOKENtoINTvow.containsKey(bwTag1) ? ((Integer)this.mapTOKENtoINTvow.get(bwTag1)).intValue() : -1;
             String uword1 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1);
             String uBWword1 = Transliteration.getInstance().getArabicToBuckWalter(uword1);
             int iuword1 = this.mapTOKENtoINTunv.containsKey(uBWword1) ? ((Integer)this.mapTOKENtoINTunv.get(uBWword1)).intValue() : -1;
             j = 0;
             String str3 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1) + ":" + tag1;
             String couple = tag1 + ":" + tag2;
             if (i == 1) {
               double start = ((Double)startMatrix.get(tag1)).doubleValue();
               double d2 = (itag1 > -1 && iuword1 > -1) ? (((Integer)this.mapINTVOWTOKEN.get(Integer.valueOf(itag1))).intValue() / ((Integer)this.mapINTUNVTOKEN.get(Integer.valueOf(iuword1))).intValue()) : 1.0E-7D;
               int val = getVal(itag1, itag2);
               double resA = (val > -1) ? (val / ((Integer)this.mapINTVOWTOKEN.get(Integer.valueOf(itag1))).intValue()) : 1.0E-7D;
               if (this.allTokensOutil.contains(tag1)) {
                 if (AlKhalil2Analyzer.getInstance().processToken(word2).getAllDiacMajrour().contains(tag2)) {
                   d1 = resA + d2 + start;
                 } else {
                   d1 = 0.0D;
                 } 
               } else {
                 d1 = resA + d2 + start;
               } 
             } else {
               double d2 = !IMapResW1.containsKey(str3) ? 1.0E-5D : ((Double)IMapResW1.get(str3)).doubleValue();
               int val = getVal(itag1, itag2);
               double resA = (val > -1) ? (val / ((Integer)this.mapINTVOWTOKEN.get(Integer.valueOf(itag1))).intValue()) : 1.0E-7D;
               if (this.allTokensOutil.contains(tag1)) {
                 if (AlKhalil2Analyzer.getInstance().processToken(word2).getAllDiacMajrour().contains(tag2)) {
                   d1 = resA + d2;
                 } else {
                   d1 = 0.0D;
                 } 
               } else {
                 d1 = resA + d2;
               } 
             } 
             str1 = tag1;
             k = j;
           } 
           while (it_1.hasNext()) {
             double val1;
             String tag1 = it_1.next();
             String bwTag1 = Transliteration.getInstance().getArabicToBuckWalter(tag1);
             int itag1 = this.mapTOKENtoINTvow.containsKey(bwTag1) ? ((Integer)this.mapTOKENtoINTvow.get(bwTag1)).intValue() : -1;
             String uword1 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1);
             String uBWword1 = Transliteration.getInstance().getArabicToBuckWalter(uword1);
             int iuword1 = this.mapTOKENtoINTunv.containsKey(uBWword1) ? ((Integer)this.mapTOKENtoINTunv.get(uBWword1)).intValue() : -1;
             j++;
             String str3 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word1) + ":" + tag1;
             String couple = tag1 + ":" + tag2;
             if (i == 1) {
               double start = ((Double)startMatrix.get(tag1)).doubleValue();
               double d2 = (itag1 > -1 && iuword1 > -1) ? (((Integer)this.mapINTVOWTOKEN.get(Integer.valueOf(itag1))).intValue() / ((Integer)this.mapINTUNVTOKEN.get(Integer.valueOf(iuword1))).intValue()) : 1.0E-7D;
               int val = getVal(itag1, itag2);
               double resA = (val > -1) ? (val / ((Integer)this.mapINTVOWTOKEN.get(Integer.valueOf(itag1))).intValue()) : 1.0E-7D;
               if (this.allTokensOutil.contains(tag1)) {
                 if (AlKhalil2Analyzer.getInstance().processToken(word2).getAllDiacMajrour().contains(tag2)) {
                   val1 = resA + d2 + start;
                 } else {
                   val1 = 0.0D;
                 } 
               } else {
                 val1 = resA + d2 + start;
               } 
             } else {
               double d2 = !IMapResW1.containsKey(str3) ? 1.0E-5D : ((Double)IMapResW1.get(str3)).doubleValue();
               int val = getVal(itag1, itag2);
               double resA = (val > -1) ? (val / ((Integer)this.mapINTVOWTOKEN.get(Integer.valueOf(itag1))).intValue()) : 1.0E-7D;
               if (this.allTokensOutil.contains(tag1)) {
                 if (AlKhalil2Analyzer.getInstance().processToken(word2).getAllDiacMajrour().contains(tag2)) {
                   val1 = resA + d2;
                 } else {
                   val1 = 0.0D;
                 } 
               } else {
                 val1 = resA + d2;
               } 
             } 
             if (val1 > d1) {
               d1 = val1;
               str1 = tag1;
               k = j;
             } 
           } 
           String str2 = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2) + ":" + tag2;
           double resB = (itag2 > -1 && iuword2 > -1) ? (((Integer)this.mapINTVOWTOKEN.get(Integer.valueOf(itag2))).intValue() / ((Integer)this.mapINTUNVTOKEN.get(Integer.valueOf(iuword2))).intValue()) : 1.0E-7D;
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
       Iterator<String> itt = ((List<String>)this.morph.get(word)).iterator();
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
         String tag = ((List<String>)this.morph.get(word)).get(((Integer)((List<Integer>)iMapRes.get(Integer.valueOf(i))).get(imax)).intValue());
         imax = ((Integer)((List<Integer>)iMapRes.get(Integer.valueOf(i))).get(imax)).intValue();
         ilp.add(tag);
       } 
       Collections.reverse(ilp);
       return ilp;
     }
     
     private List<String> analyzedToken(List<String> _allTokens) {
       Map<String, Double> startMatrix = new HashMap<>();
       String sword = _allTokens.get(0);
       int size = ((List)this.morph.get(sword)).size();
       double som = 0.0D;
       double D = 0.5D;
       double maxStart = 1.0D;
       int maxN = 5191554;
       int max1 = 5191554;
       Iterator<String> it = ((List<String>)this.morph.get(sword)).iterator();
       if (it.hasNext()) {
         String tag = it.next();
         String bwTag = Transliteration.getInstance().getArabicToBuckWalter(tag);
         int itag = this.mapTOKENtoINTvow.containsKey(bwTag) ? ((Integer)this.mapTOKENtoINTvow.get(bwTag)).intValue() : -1;
         double x = (1 / maxN);
         if (itag > -1 && this.mapINTVOWSTART.containsKey(Integer.valueOf(itag))) {
           maxStart = ((Integer)this.mapINTVOWSTART.get(Integer.valueOf(itag))).intValue();
           x = maxStart / maxN;
         } 
         som = x;
         startMatrix.put(tag, Double.valueOf(x));
       } 
       while (it.hasNext()) {
         String tag = it.next();
         String bwTag = Transliteration.getInstance().getArabicToBuckWalter(tag);
         int itag = this.mapTOKENtoINTvow.containsKey(bwTag) ? ((Integer)this.mapTOKENtoINTvow.get(bwTag)).intValue() : -1;
         int start = 0;
         double x = (1 / max1);
         if (itag > -1 && this.mapINTVOWSTART.containsKey(Integer.valueOf(itag))) {
           start = ((Integer)this.mapINTVOWSTART.get(Integer.valueOf(itag))).intValue();
           x = start / maxN;
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
     
     private void setTokenizationStringDiac(String Text) {
       this.allTokens = new LinkedList<>();
       this.allText = new LinkedList<>();
       Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
       List<String> Lines = new LinkedList<>();
       Lines.addAll(Arrays.asList(Text.split("\n")));
       Iterator<String> it_line = Lines.iterator();
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
             addMorphoResultDiac(word);
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
     
     private void addMorphoResultDiac(String word) {
       if (!this.morph.containsKey(word))
         this.morph.put(word, getAllDiac(word, AlKhalil2Analyzer.getInstance().processToken(word))); 
     }
     
     private List<String> getAllDiac(String word, ResultList _result) {
       if (_result.isAnalyzed())
         return _result.getAllDiac(); 
       this.notAnaTokens.add(word);
       List<String> _res = new ArrayList<>();
       _res.add(word);
       return _res;
     }
     
     private int getVal(int itag1, int itag2) {
       if (this.mapC_01.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_01.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_01.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_02.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_02.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_02.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_03.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_03.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_03.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_04.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_04.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_04.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_05.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_05.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_05.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_06.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_06.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_06.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_07.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_07.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_07.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_08.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_08.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_08.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_09.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_09.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_09.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       if (this.mapC_10.containsKey(Integer.valueOf(itag1)) && ((Map)this.mapC_10.get(Integer.valueOf(itag1))).containsKey(Integer.valueOf(itag2)))
         return ((Integer)((Map)this.mapC_10.get(Integer.valueOf(itag1))).get(Integer.valueOf(itag2))).intValue(); 
       return -1;
     }
   }

