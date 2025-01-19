   package net.oujda_nlp_team.impl;
   
   import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.entity.Voweled;
import net.oujda_nlp_team.factory.UnderivedAnalyzerFactory;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.Settings;
import net.oujda_nlp_team.util.Validator;
   
   public class PropernounAnalyzerImpl extends UnderivedAnalyzerFactory {
     private static final UnderivedAnalyzerFactory instance = new PropernounAnalyzerImpl();
     
     public static UnderivedAnalyzerFactory getInstance() {
       return instance;
     }
     
     public synchronized List analyzedSegment(String normalizedWord, String unvoweledWord, Segment segment) {
       String stem = segment.getStem();
       String procClass = segment.getProclitic().getClasse();
       String encClass = segment.getEnclitic().getClasse();
       return (!procClass.contains("V") && 
         !encClass.contains("V")) ? 
         getPossiblesSolutions(normalizedWord, unvoweledWord, segment) : new ArrayList();
     }
     
     public List getPossiblesSolutions(String normalizedWord, String unvoweledWord, Segment segment) {
       List<Result> result = new ArrayList();
       String stem = segment.getStem();
       if (PropernounUnderivedImpl.getInstance().containsVal(stem)) {
         String[] lpn = PropernounUnderivedImpl.getInstance().getUnvoweledFormValue(segment.getStem()).split(" ");
         List listPN = new ArrayList();
         listPN.addAll(Arrays.asList(lpn));
         Iterator<String> it_pn = listPN.iterator();
         while (it_pn.hasNext()) {
           int Ipn = Integer.parseInt(it_pn.next());
           Voweled pn = new Voweled();
           boolean valid = false;
           String vowledWord = "";
           String pf = segment.getProclitic().getVoweledform();
           String sf = segment.getEnclitic().getVoweledform();
           String pfNoDec = segment.getProclitic().getVoweledform();
           String sfNoDec = segment.getEnclitic().getVoweledform();
           if (Ipn <= PropernounUnderivedImpl.getInstance().size()) {
             pn = PropernounUnderivedImpl.getInstance().getVoweledFormValue(Ipn);
             if (segment.getEnclitic().getUnvoweledform().equals("ه"))
               if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                 segment.getEnclitic().setVoweledform("هِ");
               } else {
                 segment.getEnclitic().setVoweledform("هُ");
               }  
             if (segment.getEnclitic().getUnvoweledform().equals("هما"))
               if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                 segment.getEnclitic().setVoweledform("هِمَا");
               } else {
                 segment.getEnclitic().setVoweledform("هُمَا");
               }  
             if (segment.getEnclitic().getUnvoweledform().equals("هم"))
               if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                 segment.getEnclitic().setVoweledform("هِمْ");
               } else {
                 segment.getEnclitic().setVoweledform("هُمْ");
               }  
             if (segment.getEnclitic().getUnvoweledform().equals("هن"))
               if (pn.getVoweledform().endsWith("ي") || pn.getVoweledform().endsWith("يْ") || pn.getVoweledform().endsWith("ِ")) {
                 segment.getEnclitic().setVoweledform("هِنَّ");
               } else {
                 segment.getEnclitic().setVoweledform("هُنَّ");
               }  
             pf = segment.getProclitic().getVoweledform();
             sf = segment.getEnclitic().getVoweledform();
             pfNoDec = segment.getProclitic().getVoweledform();
             sfNoDec = segment.getEnclitic().getVoweledform();
             String pfClass = segment.getProclitic().getClasse();
             if (segment.getProclitic().getClasse().equals("N4") && segment.getProclitic().getUnvoweledform().endsWith("ل") && pn.getVoweledform().startsWith("ا")) {
               String val = pn.getVoweledform();
               if (!segment.getEnclitic().getUnvoweledform().equals(""))
                 val = val.replace('ة', 'ت'); 
               vowledWord = pf + val.substring(1) + sf;
             } else if (Validator.getInstance().isDefinit(pfClass)) {
               String res = pn.getVoweledform();
               if (!segment.getEnclitic().getUnvoweledform().equals(""))
                 res = res.replace('ة', 'ت'); 
               if (Validator.getInstance().isSolar(res.charAt(0))) {
                 vowledWord = pf + 'ْ' + res.substring(0, 1) + 'ّ' + res.substring(1) + sf;
               } else {
                 vowledWord = pf + 'ْ' + res + sf;
               } 
             } else {
               String res = pn.getVoweledform();
               if (!segment.getEnclitic().getUnvoweledform().equals(""))
                 res = res.replace('ة', 'ت'); 
               vowledWord = pf + res + sf;
             } 
             valid = true;
           } 
           if (valid && !Validator.getInstance().notCompatible(normalizedWord, vowledWord, segment, false)) {
             String type, cas, lemma = pn.getLemma();
             String Root = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(pn.getRoot());
             String root = pn.getRoot().equals(Root) ? pn.getRoot() : "-";
             if (!Settings.prefchoice || pf.equals("")) {
               pf = "#";
               pfNoDec = "#";
             } else {
               pf = pf + " : " + segment.getProclitic().getDesc();
             } 
             if (!Settings.suffixchoice || sf.equals("")) {
               sf = "#";
               sfNoDec = "#";
             } else {
               sf = sf + " : " + segment.getEnclitic().getDesc();
             } 
             if (Settings.choicePartOfSpeech) {
               PartOfSpeech pos = (PartOfSpeech)PropernounUnderivedImpl.getInstance().getPartOfSpeech(Integer.parseInt(pn.getPartOfSpeech()));
               type = pos.getMain() + "|" + pos.getType() + "|" + pos.getGender() + "|" + pos.getNumber();
             } else {
               type = "#";
             } 
             if (Settings.choiceCaseOrMood) {
               cas = PropernounUnderivedImpl.getInstance().getCaseOrMood(Integer.parseInt(pn.getCase())).getValAR();
             } else {
               cas = "#";
             } 
             if (isValidNominalSolution(segment, cas, vowledWord, normalizedWord))
               result.add(new Result(vowledWord, pf, pfNoDec, 
                     
                     ArabicStringUtil.getInstance().removeLastDiacriticsOfWord(pn.getVoweledform()), type, "-", "-", lemma, "-", root, cas, sf, sfNoDec, "0.01100101")); 
           } 
         } 
       } 
       return result;
     }
     
     private boolean isValidNominalSolution(Segment segment, String caseormood, String vowledWord, String normalizedWord) {
       String prefcalss = segment.getProclitic().getClasse();
       if ((vowledWord.indexOf('ً') != -1 || vowledWord
         .indexOf('ٍ') != -1 || vowledWord
         .indexOf('ٌ') != -1) && (
         
         !segment.getEnclitic().getUnvoweledform().equals("") || Validator.getInstance().isDefinit(prefcalss)))
         return false; 
       if ((prefcalss.equals("N2") || prefcalss.equals("C2") || prefcalss.equals("C3")) && caseormood
         .equals("مجرور"))
         return false; 
       if ((prefcalss.equals("N4") || prefcalss.equals("N5")) && 
         !caseormood.equals("مجرور"))
         return false; 
       return true;
     }
   }
