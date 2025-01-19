   package net.oujda_nlp_team.util;
   
   import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.oujda_nlp_team.entity.Clitic;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.impl.EncliticImpl;
import net.oujda_nlp_team.impl.ProcliticImpl;
   
   public class Stemming {
     private static final Stemming instance = new Stemming();
     
     public static Stemming getInstance() {
       return instance;
     }
     
     public List getListsSegment(String unvoweledWord) {
       List<Segment> result = new ArrayList();
       Set<Segment> setResult = new HashSet();
       List<Clitic> listsProclitic = ProcliticImpl.getInstance().getListsClitics(unvoweledWord);
       List<Clitic> listsEnclitic = EncliticImpl.getInstance().getListsClitics(unvoweledWord);
       boolean valid = true;
       Iterator<Clitic> it_p = listsProclitic.iterator();
       while (it_p.hasNext()) {
         Clitic p = it_p.next();
         Iterator<Clitic> it_s = listsEnclitic.iterator();
         while (valid && it_s.hasNext()) {
           Clitic s = it_s.next();
           if (unvoweledWord.length() - s.getUnvoweledform().length() - p.getUnvoweledform().length() >= 0) {
             Set<String> Alternatives = new HashSet();
             String stem = unvoweledWord.substring(p.getUnvoweledform().length(), unvoweledWord.length() - s.getUnvoweledform().length());
             Alternatives.add(stem);
             if ((p.getClasse().equals("N1") || p.getClasse().equals("N2") || p.getClasse().equals("N3") || p.getClasse().equals("N5")) && p.getUnvoweledform().endsWith("ل") && !stem.startsWith("ل"))
               Alternatives.add('ل' + stem); 
             if (s.getUnvoweledform().equals("ي") && !stem.startsWith("ي"))
               Alternatives.add(stem + 'ي'); 
             if ((p.getClasse().equals("N4") || p.getClasse().equals("C3")) && p.getUnvoweledform().endsWith("ل") && stem.startsWith("ل")) {
               Alternatives.add(getAlternatives(stem, stem.charAt(stem.length() - 1), (s.getUnvoweledform().length() != 0), (s.getUnvoweledform().length() > 0)));
               stem = "ا" + stem;
             } 
             Alternatives.add(stem);
             if (p.getUnvoweledform().equals("أ") || p.getUnvoweledform().equals("ب"))
               Alternatives.add('ا' + stem); 
             String stem1 = "";
             if (stem.length() > 1)
               stem1 = getAlternatives(stem, stem.charAt(stem.length() - 1), (s.getUnvoweledform().length() != 0), (s.getUnvoweledform().length() > 0)); 
             if (!"".equals(stem1))
               Alternatives.add(stem1); 
             String stem3 = stem1.replaceAll("آ", "ءا");
             if (!stem3.equals(stem1))
               Alternatives.add(stem3); 
             String stem2 = stem.replaceAll("آ", "ءا");
             if (!stem2.equals(stem))
               Alternatives.add(stem2); 
             Iterator<String> it = Alternatives.iterator();
             while (it.hasNext()) {
               String sch = it.next();
               Segment segment = new Segment(p, sch, s);
               if (isValidSegment(segment))
                 setResult.add(segment); 
             } 
             continue;
           } 
           valid = false;
         } 
       } 
       result.addAll(setResult);
       return result;
     }
     
     private boolean isValidSegment(Segment segment) {
       String proClass = segment.getProclitic().getClasse();
       String encClass = segment.getEnclitic().getClasse();
       return ((!encClass.startsWith("V") || !proClass.startsWith("N")) && (
         !proClass.startsWith("V") || !encClass.startsWith("N")) && (
         !Validator.getInstance().isDefinit(proClass) || segment.getEnclitic().getUnvoweledform().equals("")));
     }
     
     public String getAlternatives(String stem, char st, boolean valid, boolean validSuf) {
       switch (st) {
         case 'ت':
           return valid ? (stem.substring(0, stem.length() - 1) + 'ة') : "";
         case 'آ':
           return valid ? (stem.substring(0, stem.length() - 1) + "أى") : "";
         case 'ا':
           return valid ? (stem.substring(0, stem.length() - 1) + 'ى') : "";
         case 'و':
           return valid ? (stem + 'ا') : "";
         case 'ئ':
           return validSuf ? (stem.substring(0, stem.length() - 1) + 'ء') : "";
         case 'ؤ':
           return validSuf ? (stem.substring(0, stem.length() - 1) + 'ء') : "";
       } 
       return "";
     }
     
     public static void print(Segment segment) {
       System.out.println(segment);
     }
     
     public void clear() {
       EncliticImpl.getInstance().clear();
       ProcliticImpl.getInstance().clear();
     }
   }
