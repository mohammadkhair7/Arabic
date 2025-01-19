   package net.oujda_nlp_team.util;
   
   import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
   
   public class Tokenization {
     private Map<Integer, List<String>> iMapAllText = new HashMap<>();
     
     private List<String> AllText = new LinkedList<>();
     
     private List<String> AllTokensUnvoweled = new LinkedList<>();
     
     public static Map<String, Integer> TokensRepeat = new HashMap<>();
     
     private Map<String, Integer> TokensUnvoweledRepeat = new HashMap<>();
     
     private List<String> allSentences = new LinkedList<>();
     
     private List<String> AllTokens = new LinkedList<>();
     
     private static final Tokenization instance = new Tokenization();
     
     public static Tokenization getInstance() {
       return instance;
     }
     
     public Map<Integer, List<String>> getMapAllText() {
       return this.iMapAllText;
     }
     
     public List<String> getAllTokens() {
       return this.AllTokens;
     }
     
     public List<String> getAllSentences() {
       return this.allSentences;
     }
     
     public Set<String> getTokens() {
       Set<String> Tokens = new HashSet<>();
       Tokens.addAll(this.AllTokens);
       return Tokens;
     }
     
     public Map<String, Integer> getTokensRepeat() {
       return TokensRepeat;
     }
     
     public Map<String, Integer> getTokensUnvoweledRepeat() {
       return this.TokensUnvoweledRepeat;
     }
     
     public List<String> getAllTokensUnvoweled() {
       return this.AllTokensUnvoweled;
     }
     
     public List<String> getAllText() {
       return this.AllText;
     }
     
     public int getNbAllTokens() {
       return getAllTokens().size();
     }
     
     public int getNbAllTokensUnvoweled() {
       return getAllTokensUnvoweled().size();
     }
     
     public int getNbTokens() {
       return getTokens().size();
     }
     
     public int getNbAllText() {
       return getAllText().size();
     }
     
     public void setTokenizationFile(String nameFile, String nameCharset) {
       TokensRepeat = new HashMap<>();
       this.TokensUnvoweledRepeat = new HashMap<>();
       this.AllTokensUnvoweled = new ArrayList<>();
       this.AllTokens = new ArrayList<>();
       this.allSentences = new ArrayList<>();
       this.AllText = new ArrayList<>();
       Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
       try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(nameFile)), nameCharset));
         String wSentence = "";
         String line;
         while ((line = in.readLine()) != null) {
           int start = 0;
           int end = 0;
           line = line.replaceAll("ـ", "");
           Matcher matcher = pattern.matcher(line);
           while (matcher.find()) {
             String word = matcher.group();
             char c1 = word.charAt(0);
             if (!Validator.getInstance().isSeparator(c1)) {
               word = ArabicStringUtil.getInstance().correctErreur(word);
               this.AllTokens.add(word);
               Collections.addStringToMap(TokensRepeat, word);
               Collections.addStringToMap(this.TokensUnvoweledRepeat, ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
               this.AllTokensUnvoweled.add(ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
               wSentence = wSentence + " " + word;
             } else if (!"".equals(wSentence)) {
               this.allSentences.add(wSentence.trim());
               wSentence = "";
             } 
             if (matcher.start() > 0) {
               String wrd = line.substring(start, matcher.start());
               this.AllText.add(wrd);
             } 
             this.AllText.add(word);
             start = matcher.end();
             end = matcher.end();
           } 
           if (line.length() > end) {
             String wrd = line.substring(start, line.length());
             this.AllText.add(wrd);
           } 
           this.AllText.add("\n");
           if (!"".equals(wSentence)) {
             this.allSentences.add(wSentence.trim());
             wSentence = "";
           } 
         } 
         in.close();
       } catch (IOException e) {
         System.out.println("Erreur : " + e);
       } 
     }
     
     public void setTokenizationFileRepeat(String nameFile, String nameCharset) {
       TokensRepeat = new HashMap<>();
       this.TokensUnvoweledRepeat = new HashMap<>();
       Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
       try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(nameFile)), nameCharset));
         String line;
         while ((line = in.readLine()) != null) {
           int start = 0;
           int end = 0;
           line = line.replaceAll("ـ", "");
           Matcher matcher = pattern.matcher(line);
           while (matcher.find()) {
             String word = matcher.group();
             char c1 = word.charAt(0);
             if (!Validator.getInstance().isSeparator(c1)) {
               word = ArabicStringUtil.getInstance().correctErreur(word);
               Collections.addStringToMap(TokensRepeat, word);
               Collections.addStringToMap(this.TokensUnvoweledRepeat, ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
             } 
             start = matcher.end();
             end = matcher.end();
           } 
         } 
         in.close();
       } catch (IOException e) {
         System.out.println("Erreur : " + e);
       } 
     }
     
     public void setTokenizationFileCoupleRepeat(String nameFile, String nameCharset) {
       TokensRepeat = new HashMap<>();
       this.TokensUnvoweledRepeat = new HashMap<>();
       Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
       try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(nameFile)), nameCharset));
         String line;
         while ((line = in.readLine()) != null) {
           int start = 0;
           int end = 0;
           String sWord = "$";
           line = line.replaceAll("ـ", "");
           Matcher matcher = pattern.matcher(line);
           while (matcher.find()) {
             String word = matcher.group();
             char c1 = word.charAt(0);
             if (!Validator.getInstance().isSeparator(c1)) {
               word = ArabicStringUtil.getInstance().correctErreur(word);
               String str = sWord + "\t" + word;
               Collections.addStringToMap(TokensRepeat, str);
             } 
             start = matcher.end();
             end = matcher.end();
             sWord = word;
           } 
         } 
         in.close();
       } catch (IOException e) {
         System.out.println("Erreur : " + e);
       } 
     }
     
     public void setTokenizationFileLine(String nameFile, String nameCharset, int startline, int nbline) {
       this.AllTokens = new LinkedList<>();
       this.AllTokensUnvoweled = new LinkedList<>();
       this.AllText = new LinkedList<>();
       this.TokensUnvoweledRepeat = new HashMap<>();
       TokensRepeat = new HashMap<>();
       Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
       try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(new File(nameFile)), nameCharset));
         int i = 0;
				String line = "";
         do {
           line = in.readLine();
           i++;
         } while (line != null && i < startline);
         i = 0;
         line = "";
         while ((line = in.readLine()) != null && i < nbline) {
           int start = 0;
           int end = 0;
           line = line.replaceAll("ـ", "");
           Matcher matcher = pattern.matcher(line);
           while (matcher.find()) {
             String word = matcher.group();
             char c1 = word.charAt(0);
             if (!Validator.getInstance().isSeparator(c1)) {
               word = ArabicStringUtil.getInstance().correctErreur(word);
               this.AllTokens.add(word);
               Collections.addStringToMap(TokensRepeat, word);
               Collections.addStringToMap(this.TokensUnvoweledRepeat, ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
               this.AllTokensUnvoweled.add(ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
             } 
             this.AllText.add(word);
             if (matcher.start() > 0) {
               String wrd = line.substring(start, matcher.start());
               this.AllText.add(wrd);
             } 
             start = matcher.end();
             end = matcher.end();
           } 
           if (line.length() > end) {
             String wrd = line.substring(start, line.length());
             this.AllText.add(wrd);
           } 
           i++;
         } 
         in.close();
       } catch (IOException e) {
         System.out.println("Erreur : " + e);
       } 
     }
     
     public void setTokenizationString(String Text) {
       List<String> allTokens = new LinkedList<>();
       this.AllTokens = new LinkedList<>();
       this.AllTokensUnvoweled = new LinkedList<>();
       this.AllText = new LinkedList<>();
       this.TokensUnvoweledRepeat = new HashMap<>();
       this.iMapAllText = new HashMap<>();
       TokensRepeat = new HashMap<>();
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
             this.AllTokens.add(word);
             Collections.addStringToMap(TokensRepeat, word);
             Collections.addStringToMap(this.TokensUnvoweledRepeat, ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
             this.AllTokensUnvoweled.add(ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word));
             allTokens.add(word);
           } else if (!allTokens.isEmpty()) {
             this.iMapAllText.put(Integer.valueOf(i), allTokens);
             i++;
             allTokens = new LinkedList<>();
           } 
           if (matcher.start() > 0)
             this.AllText.add(line.substring(start, matcher.start())); 
           this.AllText.add(word);
           start = matcher.end();
           end = matcher.end();
         } 
         if (line.length() > end)
           this.AllText.add(line.substring(start, line.length())); 
         this.AllText.add("");
       } 
     }
   }
