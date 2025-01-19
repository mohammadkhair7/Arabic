   package net.oujda_nlp_team.impl;
   
   import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.oujda_nlp_team.entity.Clitic;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.entity.Toolwords;
import net.oujda_nlp_team.factory.UnderivedAnalyzerFactory;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Settings;
import net.oujda_nlp_team.util.Validator;
   
   public class ToolwordsAnalyzerImpl extends UnderivedAnalyzerFactory {
     private static final UnderivedAnalyzerFactory instance = new ToolwordsAnalyzerImpl();
     
     public static UnderivedAnalyzerFactory getInstance() {
       return instance;
     }
     
     public synchronized List analyzedSegment(String normalizedWord, String unvoweledWord, Segment segment) {
       String stem = segment.getStem();
       String procClass = segment.getProclitic().getClasse();
       String encClass = segment.getEnclitic().getClasse();
       return getPossiblesSolutions(normalizedWord, unvoweledWord, segment);
     }
     
     public List getPossiblesSolutions(String normalizedWord, String unvoweledWord, Segment segment) {
       List<Result> result = new ArrayList();
       Clitic enC = segment.getEnclitic();
       Clitic proC = segment.getProclitic();
       List<Toolwords> toolwordlist = possibleToolWords(segment);
       Iterator<Toolwords> it_TW = toolwordlist.iterator();
       while (it_TW.hasNext()) {
         String type;
         Toolwords tw = it_TW.next();
         if (enC.getUnvoweledform().equals("ه")) {
           if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
             enC.setVoweledform("هِ");
           } else {
             enC.setVoweledform("هُ");
           } 
         } else if (enC.getUnvoweledform().equals("هما")) {
           if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
             enC.setVoweledform("هِمَا");
           } else {
             enC.setVoweledform("هُمَا");
           } 
         } else if (enC.getUnvoweledform().equals("هم")) {
           if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
             enC.setVoweledform("هِمْ");
           } else {
             enC.setVoweledform("هُمْ");
           } 
         } else if (enC.getUnvoweledform().equals("هن")) {
           if (tw.getVoweledform().endsWith("ي") || tw.getVoweledform().endsWith("يْ") || tw.getVoweledform().endsWith("ِ")) {
             enC.setVoweledform("هِنَّ");
           } else {
             enC.setVoweledform("هُنَّ");
           } 
         } 
         String lemma = tw.getLemma();
         String Root = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(tw.getRoot());
         String root = tw.getRoot().equals(Root) ? tw.getRoot() : "-";
                String vowledWord = "";
         if (tw.getClassProclitic().contains("N1") || tw
           .getClassProclitic().contains("N2") || tw
           .getClassProclitic().contains("N3") || tw
           .getClassProclitic().contains("N5")) {
           String res = tw.getVoweledform();
           if (Validator.getInstance().isSolar(res.charAt(0))) {
             vowledWord  = proC.getVoweledform() + 'ْ' + res.substring(0, 1) + 'ّ' + res.substring(1) + enC.getVoweledform();
           } else {
             vowledWord = proC.getVoweledform() + 'ْ' + res + enC.getVoweledform();
           } 
         } else {
           vowledWord = proC.getVoweledform() + tw.getVoweledform() + enC.getVoweledform();
         } 
         String pf = proC.getVoweledform();
         String sf = enC.getVoweledform();
         String pfNoDec = proC.getVoweledform();
         String sfNoDec = enC.getVoweledform();
         if (!Settings.prefchoice || proC.getVoweledform().equals("")) {
           pf = "#";
           pfNoDec = "#";
         } else {
           pf = pf + " : " + proC.getDesc();
         } 
         if (!Settings.suffixchoice || enC.getVoweledform().equals("")) {
           sf = "#";
           sfNoDec = "#";
         } else {
           sf = sf + " : " + enC.getDesc();
         } 
         if (Settings.choicePartOfSpeech) {
           int id = Integer.parseInt(tw.getPartOfSpeech());
           type = (String)ToolwordsUnderivedImpl.getInstance().getPartOfSpeech(id);
         } else {
           type = "#";
         } 
         vowledWord = vowledWord.replaceAll("لَلْلّ", "لَلّ").replaceAll("لِلْلّ", "لِلّ");
         vowledWord = vowledWord.replaceAll("لِالّ", "لِلّ").replaceAll("لَالّ", "لَلّ");
         if (!Validator.getInstance().notCompatible(normalizedWord, vowledWord)) {
           Result r = new Result(vowledWord, pf, pfNoDec, ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(tw.getVoweledform()), type, "-", "-", lemma, "-", root, "-", sf, sfNoDec, "0.0001100101");
           result.add(r);
         } 
       } 
			
       return result;
     }
     
     public List possibleToolWords(Segment segment) {
       List<Toolwords> result = new ArrayList();
       String stem = segment.getStem();
       if (ToolwordsUnderivedImpl.getInstance().containsVal(stem)) {
         List<String> TW = Arrays.asList(ToolwordsUnderivedImpl.getInstance().getUnvoweledFormValue(stem).split(" "));
         Iterator<String> it_TW = TW.iterator();
         while (it_TW.hasNext()) {
           int id = Integer.parseInt(it_TW.next());
           Toolwords tw = ToolwordsUnderivedImpl.getInstance().getVoweledFormValue(id);
           if (tw.getClassProclitic().contains(segment.getProclitic().getClasse()) && tw.getClassEnclitic().contains(segment.getEnclitic().getClasse()))
             result.add(tw); 
         } 
       } 
       return result;
     }
   }

