  package net.oujda_nlp_team.util;
  
  import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
  
  public class Normalization {
    public static int nbTotalWord;
    
    private static List<String> normalizedWord = new LinkedList<>();
    
    public static List getNormalizedWord() {
      return normalizedWord;
    }
    
    public int getSize() {
      return normalizedWord.size();
    }
    
    public void setNormalization(String InText) {
      Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
      List<String> l = new LinkedList();
      l.addAll(Arrays.asList(InText.split("\n")));
      normalizedWord = new LinkedList<>();
      ListIterator<String> i = normalizedWord.listIterator();
      nbTotalWord = 0;
      Iterator<String> it_line = l.iterator();
      while (it_line.hasNext()) {
        String st_line = it_line.next();
        Matcher matcher = pattern.matcher(st_line);
        String word2 = "";
        while (matcher.find()) {
          String word = matcher.group();
          word = ArabicStringUtil.getInstance().correctErreur(word);
          i.add(word);
          if (!Validator.getInstance().isSeparator(word))
            nbTotalWord++; 
          String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2 + " " + word);
          word2 = word;
        } 
        i.add(".");
      } 
    }
    
    public void setNormalizationString(String InText) {
      Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
      List<String> l = new LinkedList();
      l.addAll(Arrays.asList(InText.split("\n")));
      normalizedWord = new LinkedList<>();
      ListIterator<String> i = normalizedWord.listIterator();
      nbTotalWord = 0;
      Iterator<String> it_line = l.iterator();
      while (it_line.hasNext()) {
        String st_line = it_line.next();
        Matcher matcher = pattern.matcher(st_line);
        while (matcher.find()) {
          String word = matcher.group();
          word = ArabicStringUtil.getInstance().correctErreur(word);
          i.add(word);
          if (!Validator.getInstance().isSeparator(word))
            nbTotalWord++; 
        } 
        i.add(".");
      } 
    }
    
    public void setNormalizationFile(File nameFile, String nameCharset, boolean unvowled) {
      normalizedWord = new LinkedList<>();
      ListIterator<String> i = normalizedWord.listIterator();
      nbTotalWord = 0;
      BufferedReader in = null;
      try {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile), nameCharset));
        String line = "";
        Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
        while ((line = in.readLine()) != null) {
          line = line.replaceAll("ـ", "");
          Matcher matcher = pattern.matcher(line);
          String word2 = "";
          while (matcher.find()) {
            String word = matcher.group();
            if (unvowled) {
              word = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
            } else {
              word = ArabicStringUtil.getInstance().correctErreur(word);
            } 
            i.add(word);
            if (!Validator.getInstance().isSeparator(word))
              nbTotalWord++; 
            String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2 + " " + word);
            word2 = word;
          } 
          i.add(".");
        } 
        in.close();
      } catch (IOException e) {
        System.out.println("Erreur : " + e);
      } 
    }
    
    public static void setNormalizationList(List<String> list, boolean unvowled) {
      normalizedWord = new LinkedList<>();
      ListIterator<String> i = normalizedWord.listIterator();
      nbTotalWord = 0;
      Iterator<String> it_line = list.iterator();
      while (it_line.hasNext()) {
        String st_line = it_line.next();
        Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
        st_line = st_line.replaceAll("ـ", "");
        Matcher matcher = pattern.matcher(st_line);
        String word2 = "";
        while (matcher.find()) {
          String word = matcher.group();
          if (unvowled) {
            word = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
          } else {
            word = ArabicStringUtil.getInstance().correctErreur(word);
          } 
          i.add(word);
          if (!Validator.getInstance().isSeparator(word))
            nbTotalWord++; 
          String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2 + " " + word);
          word2 = word;
        } 
      } 
    }
    
    public List NormalizationFileToList(File nameFile, String nameCharset, boolean unvowled) {
      List<String> listWords = new LinkedList();
      BufferedReader in = null;
      try {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(nameFile), nameCharset));
        String line = "";
        String[] Separator = ArabicCharacterUtil.getSeparator();
        String ptr = "[ابتثجحخدذرزسشـصضطظعغفقكلمنهويءآأإةؤئى][ابتثجحخدذرزسشصـضطظعغفقكلمنهويءآأإةؤئىًٌٍَُِّْ]*";
        String chainePattern = "((" + ptr + ")";
        for (int i = 0; i < 6; i++)
          chainePattern = chainePattern + "|(" + Separator[i] + ")"; 
        chainePattern = chainePattern + ")";
        Pattern pattern = Pattern.compile(chainePattern);
        while ((line = in.readLine()) != null) {
          line = line.replaceAll("ـ", "");
          Matcher matcher = pattern.matcher(line);
          while (matcher.find()) {
            String word = matcher.group();
            if (unvowled) {
              word = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
            } else {
              word = ArabicStringUtil.getInstance().correctErreur(word);
            } 
            listWords.add(word);
          } 
        } 
        in.close();
      } catch (IOException e) {
        System.out.println("Erreur : " + e);
      } 
      return listWords;
    }
    
    public void NormalizedFile(String nameFileIn, String nameCharset) {
      String ptr = "[ابتثجحخدذرزسشصضطظعغفقكلمنهويءآأإةؤئى][ابتثجحخدذرزسشصضطظعغفقكلمنهويءآأإةؤئىًٌٍَُِّْ]*";
      Pattern pattern = Pattern.compile(ptr);
      BufferedReader in = null;
      try {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(nameFileIn), nameCharset));
        String line = "";
        while ((line = in.readLine()) != null) {
          Matcher matcher = pattern.matcher(line);
          String word2 = "";
          while (matcher.find()) {
            String word = matcher.group();
            word = ArabicStringUtil.getInstance().correctErreur(word);
            normalizedWord.add(word);
            String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2 + " " + word);
            word2 = word;
          } 
        } 
        in.close();
      } catch (IOException ex) {
        System.out.println("Erreur : " + ex);
      } 
    }
    
    public void NormalizedFileUnvoweled(String nameFileIn, String nameCharset) {
      String ptr = "[ابتثجحخدذرزسشصضطظعغفقكلمنهويءآأإةؤئى][ابتثجحخدذرزسشصضطظعغفقكلمنهويءآأإةؤئىًٌٍَُِّْ]*";
      Pattern pattern = Pattern.compile(ptr);
      BufferedReader in = null;
      try {
        in = new BufferedReader(new InputStreamReader(new FileInputStream(nameFileIn), nameCharset));
        String line = "";
        while ((line = in.readLine()) != null) {
          Matcher matcher = pattern.matcher(line);
          String word2 = "";
          while (matcher.find()) {
            String word = matcher.group();
            word = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word);
            word = ArabicStringUtil.getInstance().correctErreur(word);
            normalizedWord.add(word);
            String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2 + " " + word);
            word2 = word;
          } 
        } 
        in.close();
      } catch (IOException ex) {
        System.out.println("Erreur : " + ex);
      } 
    }
    
    public List getNormalizationString(String InText) {
      List<String> lReturn = new ArrayList();
      Pattern pattern = Pattern.compile(ArabicStringUtil.getInstance().getPatternCompile());
      Matcher matcher = pattern.matcher(InText);
      String word2 = "";
      while (matcher.find()) {
        String word = matcher.group();
        word = ArabicStringUtil.getInstance().correctErreur(word);
        lReturn.add(word);
        if (!Validator.getInstance().isSeparator(word))
          nbTotalWord++; 
        String wrd = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(word2 + " " + word);
        word2 = word;
      } 
      return lReturn;
    }
  }

