   package net.oujda_nlp_team.util;
   
   public class Transliteration {
     private static final Transliteration instance = new Transliteration();
     
     public static Transliteration getInstance() {
       return instance;
     }
     
     public String getBuckWalterToArabic(String word) {
       String st = "";
       for (int i = 0; i < word.length(); i++)
         st = st + "" + ArabicCharacterUtil.getArabicCharacter(word.charAt(i)); 
       return st;
     }
     
     public String getArabicToBuckWalter(String word) {
       String st = "";
       for (int i = 0; i < word.length(); i++)
         st = st + "" + ArabicCharacterUtil.getBuckWalterCharacter(word.charAt(i)); 
       return st;
     }
   }
