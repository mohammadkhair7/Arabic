   package net.oujda_nlp_team.util;
   
   import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import net.oujda_nlp_team.entity.Segment;
   
   public class Validator {
     private static Validator instance = new Validator();
     
     public static Validator getInstance() {
       return instance;
     }
     
     public boolean isHamza(char c) {
       switch (c) {
         case 'ء':
           return true;
         case 'أ':
           return true;
         case 'إ':
           return true;
         case 'ؤ':
           return true;
         case 'ئ':
           return true;
       } 
       return false;
     }
     
     public boolean isSeparator(char car) {
       switch (car) {
         case '.':
           return true;
         case '!':
           return true;
         case ':':
           return true;
         case '؟':
           return true;
         case '؛':
           return true;
       } 
       return false;
     }
     
     public boolean isDefinit(String proClass) {
       return (proClass.equals("N1") || proClass.equals("N2") || proClass.equals("N3") || proClass.equals("N5"));
     }
     
     public boolean isSolar(char c) {
       switch (c) {
         case 'ت':
           return true;
         case 'ث':
           return true;
         case 'د':
           return true;
         case 'ذ':
           return true;
         case 'ر':
           return true;
         case 'ز':
           return true;
         case 'س':
           return true;
         case 'ش':
           return true;
         case 'ص':
           return true;
         case 'ض':
           return true;
         case 'ط':
           return true;
         case 'ظ':
           return true;
         case 'ل':
           return true;
         case 'ن':
           return true;
       } 
       return false;
     }
     
     public boolean isNumeric(char c) {
       switch (c) {
         case '0':
         case '1':
         case '2':
         case '3':
         case '4':
         case '5':
         case '6':
         case '7':
         case '8':
         case '9':
           return true;
       } 
       return false;
     }
     
     public boolean isDiacritic(char c) {
       switch (c) {
         case 'ً':
         case 'ٌ':
         case 'ٍ':
         case 'َ':
         case 'ُ':
         case 'ِ':
         case 'ّ':
         case 'ْ':
           return true;
       } 
       return false;
     }
     
     public boolean isCompletVoyeled(String word) {
       String ch_1 = "ضصثقفغعهخحجدذطكمنتليسشبءؤرةزظإأآ";
       String ch_2 = "وي";
       String ch_3 = "اى";
       String ch_4 = "ّ";
       String ch_5 = "ًٌٍَُِْ";
       String exp = "(([" + ch_1 + ch_2 + "][" + ch_4 + "]*[" + ch_5 + "])|([" + ch_3 + ch_2 + "]))+";
       try {
         Pattern p = Pattern.compile(exp);
         Matcher m = p.matcher(word);
         String str = "";
         while (m.find())
           str = m.group(0); 
         return word.equals(str);
       } catch (PatternSyntaxException patternSyntaxException) {
         return false;
       } 
     }
     
     public boolean isSeparator(String pat) {
       Set<String> sep = new HashSet<>();
       sep.add(".");
       sep.add(":");
       sep.add("،");
       sep.add("؟");
       sep.add("؛");
       sep.add("!");
       return sep.contains(pat);
     }
     
     public boolean notCompatible(String normalizedWord, String voweledWord) {
       String norWord = normalizedWord;
       String vowWord = voweledWord;
       String unorWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(norWord);
       String uvowWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(vowWord);
       String str1 = "َا";
       String str2 = "اً";
       if (vowWord.endsWith(str1) && norWord.endsWith(str2))
         return true; 
       int in = 0;
       int iv = 0;
       if (unorWord.length() != uvowWord.length())
         return true; 
       while (in < norWord.length() && iv < vowWord.length()) {
         if (norWord
           .charAt(in) == vowWord.charAt(iv) || (norWord
           .charAt(in) == 'ا' && vowWord
           .charAt(iv) == 'ى')) {
           if (isDiacritic(norWord.charAt(in))) {
             in++;
             iv++;
             continue;
           } 
           in++;
           iv++;
           continue;
         } 
         if (!isDiacritic(norWord.charAt(in)) && !isDiacritic(vowWord.charAt(iv)))
           return true; 
         if (!isDiacritic(norWord.charAt(in)) && isDiacritic(vowWord.charAt(iv))) {
           iv++;
           continue;
         } 
         if (isDiacritic(norWord.charAt(in)))
           return true; 
       } 
       return false;
     }
     
     public synchronized boolean notCompatible(String normalizedWord, String vowledWord, Segment segment, boolean isTool) {
       String norWord = normalizedWord;
       String vowWord = vowledWord;
       String unorWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(norWord);
       String uvowWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(vowWord);
       if ((!segment.getEnclitic().getUnvoweledform().equals("") || isTool || norWord
         
         .charAt(norWord.length() - 1) == 'ِ') && vowWord
         
         .charAt(vowWord.length() - 1) == 'ْ' && 
         isDiacritic(norWord.charAt(norWord.length() - 1))) {
         norWord = norWord.substring(0, norWord.length() - 1);
         vowWord = vowWord.substring(0, vowWord.length() - 1);
       } 
       String str1 = "َا";
       String str2 = "اً";
       if (vowWord.endsWith(str1) && norWord.endsWith(str2))
         return true; 
       if (unorWord.length() != uvowWord.length())
         return true; 
       int in = 0;
       int iv = 0;
       while (in < norWord.length() && iv < vowWord.length()) {
         String norval = ArabicStringUtil.getInstance().getIsHamza(norWord.charAt(in));
         String vowval = ArabicStringUtil.getInstance().getIsHamza(vowWord.charAt(iv));
         if (norWord.charAt(in) == vowWord.charAt(iv) || norval.equals(vowval) || (norWord
           .charAt(in) == 'ا' && vowWord.charAt(iv) == 'ى')) {
           if (isDiacritic(norWord.charAt(in))) {
             in++;
             iv++;
             continue;
           } 
           in++;
           iv++;
           continue;
         } 
         if (!isDiacritic(norWord.charAt(in)) && !isDiacritic(vowWord.charAt(iv)))
           return true; 
         if (!isDiacritic(norWord.charAt(in)) && isDiacritic(vowWord.charAt(iv))) {
           iv++;
           continue;
         } 
         if (isDiacritic(norWord.charAt(in)))
           return true; 
       } 
       return false;
     }
     
     public boolean isDiacPattern(String stem, String diac) {
       int i = 0;
       int j = 0;
       while (i < diac.length() && j < stem.length()) {
         if (diac.charAt(i) != 'ف' && diac.charAt(i) != 'ع' && diac.charAt(i) != 'ل' && 
           diac.charAt(i) != stem.charAt(j))
           return false; 
         i++;
         j++;
       } 
       return true;
     }
   }
