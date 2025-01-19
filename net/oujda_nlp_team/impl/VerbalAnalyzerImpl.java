package net.oujda_nlp_team.impl;
   
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.Formulas;
import net.oujda_nlp_team.entity.PartOfSpeech;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.entity.VEntity;
import net.oujda_nlp_team.factory.DerivedAnalyzerFactory;
import net.oujda_nlp_team.interfaces.IDerivedAnalyzer;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.CreatHamza;
import net.oujda_nlp_team.util.Settings;
import net.oujda_nlp_team.util.Static;
import net.oujda_nlp_team.util.Validator;
   
   public class VerbalAnalyzerImpl extends DerivedAnalyzerFactory implements IDerivedAnalyzer {
     private static final IDerivedAnalyzer instance = new VerbalAnalyzerImpl();
     
     public static IDerivedAnalyzer getInstance() {
       return instance;
     }
     
     public VerbalAnalyzerImpl() {
       this.possiblePattern = new HashMap<>();
     }
     
     public synchronized List analyzedSegment(String word, Segment segment) {
       int Verbs_Pattern_Min = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Verbs.Patterns.Min"));
       int Verbs_Pattern_Max = Integer.parseInt(Database.getInstance().getResources().getProperty("Val.Verbs.Patterns.Max"));
       String stem = segment.getStem();
       String procClass = segment.getProclitic().getClasse();
       String encClass = segment.getEnclitic().getClasse();
       return (stem.length() >= Verbs_Pattern_Min && stem.length() <= Verbs_Pattern_Max && 
         !procClass.contains("N") && 
         !encClass.contains("N")) ? 
         getPossiblesSolutions(word, segment) : new ArrayList();
     }
     
     public synchronized List getPossiblesSolutions(String normalizedWord, Segment segment) {
       List<Result> result = new ArrayList();
       String Stem = segment.getStem();
       Map<String, Map> possibleSolutions = this.possiblePattern.containsKey(Stem) ? (Map<String, Map>)this.possiblePattern.get(Stem) : possibleSolutions(normalizedWord, segment, VerbalDerivedImpl.getInstance());
       Map<String, Set> iMapRes = getAllRoot_Formulas(possibleSolutions);
       for (Map.Entry<String, Set> entry : iMapRes.entrySet()) {
         String s_root = entry.getKey();
         Iterator<Formulas> it_solPatterns = ((Set<Formulas>)entry.getValue()).iterator();
         while (it_solPatterns.hasNext()) {
           Formulas sol = it_solPatterns.next();
           int lenStem = segment.getStem().length();
           String[] Solutions = getInfoResults(sol, lenStem, VerbalDerivedImpl.getInstance());
           int i = 0;
           String diac = Solutions[i];
           i++;
           double diacFreq = Double.parseDouble(Solutions[i]);
           i++;
           String canonicPattern = "";
           double canonicPatternFreq = 0.0D;
           if (Settings.choicePatternStem) {
             canonicPattern = Solutions[i];
             i++;
             canonicPatternFreq = Double.parseDouble(Solutions[i]);
             i++;
           } 
           String lemma = "";
           double lemmaFreq = 0.0D;
           if (Settings.choiceLemma) {
             lemma = ArabicStringUtil.getInstance().getWordFromRootAndPattern(s_root, Solutions[i]);
             i++;
             lemmaFreq = Double.parseDouble(Solutions[i]);
             i++;
           } 
           String lemmapattern = "";
           double lemmapatternFreq = 0.0D;
           if (Settings.choicePatternLemma) {
             lemmapattern = Solutions[i];
             i++;
             lemmapatternFreq = Double.parseDouble(Solutions[i]);
             i++;
           } 
           int idpartofspeech = Integer.parseInt(Solutions[i]);
           double partOfSpeechFreq = Double.parseDouble(VerbalDerivedImpl.getInstance().getPartOfSpeech(idpartofspeech).getFreq());
           VEntity[] rPartOfSpeech = getPartOfSpeech(idpartofspeech);
           VEntity stMain = rPartOfSpeech[0];
           VEntity stType = rPartOfSpeech[1];
           VEntity stAugmented = rPartOfSpeech[2];
           VEntity stEmphasized = rPartOfSpeech[3];
           VEntity stNbRoot = rPartOfSpeech[4];
           VEntity stPerson = rPartOfSpeech[5];
           VEntity stPerson2 = rPartOfSpeech[6];
           VEntity stTransitivity = rPartOfSpeech[7];
           VEntity stVoice = rPartOfSpeech[8];
           String stPartOfSpeech = stMain.getValAR() + "|" + stType.getValAR() + "|" + stEmphasized.getValAR() + "|" + stVoice.getValAR() + "|" + stNbRoot.getValAR() + "|" + stAugmented.getValAR() + "|" + stPerson.getValAR() + "|" + stPerson2.getValAR() + "|" + stTransitivity.getValAR();
           i++;
           int idCaseOrMood = Integer.parseInt(Solutions[i]);
           VEntity stCaseOrMood = VerbalDerivedImpl.getInstance().getCaseOrMood(idCaseOrMood);
           String[] vowledWords = ArabicStringUtil.getInstance().Vowelize(segment, diac);
           String vowledWord = vowledWords[0];
           if (isValidVerbalSolution(segment, vowledWords, normalizedWord, stType.getValAR(), stVoice.getValAR(), stPerson2.getValAR(), stCaseOrMood.getValAR(), stTransitivity.getValAR())) {
             String[] Sol = Interpret(segment, s_root, stType.getValAR(), stPerson.getValAR(), stPerson2.getValAR(), stEmphasized.getValAR(), stCaseOrMood.getValAR());
             String stem = CreatHamza.correctStemHamza(segment.getStem());
             double freq = (diacFreq + canonicPatternFreq + lemmaFreq + lemmapatternFreq + partOfSpeechFreq) / 5.0D;
             DecimalFormat df = new DecimalFormat();
             df.setMaximumFractionDigits(10);
             df.setMinimumFractionDigits(10);
             df.setDecimalSeparatorAlwaysShown(true);
             Sol[4] = "" + df.format(freq).replaceAll(",", ".");
             if (Static.StaticUnvoweled) {
               if (vowledWord.equals(normalizedWord)) {
                 Result result1 = new Result(vowledWord, Sol[0], Sol[5], stem, stPartOfSpeech, diac, canonicPattern, lemma, lemmapattern, s_root, stCaseOrMood.getValAR(), Sol[3], Sol[6], Sol[4]);
                 result.add(result1);
               } 
               continue;
             } 
             Result r = new Result(vowledWord, Sol[0], Sol[5], stem, stPartOfSpeech, diac, canonicPattern, lemma, lemmapattern, s_root, stCaseOrMood.getValAR(), Sol[3], Sol[6], Sol[4]);
             result.add(r);
           } 
         } 
       } 
       return result;
     }
     
     private boolean isValidVerbalSolution(Segment segment, String[] vowledWords, String normalizedWord, String type, String voice, String person2, String caseormood, String transitivity) {
       boolean valid = true;
       String prefcalss = segment.getProclitic().getClasse();
       String suffcalss = segment.getEnclitic().getClasse();
       String vowledWord = vowledWords[0];
       if (transitivity.equals("لازم") && !segment.getEnclitic().getUnvoweledform().equals("")) {
         valid = false;
         return valid;
       } 
       if (prefcalss.equals("V1") && !type.equals("مضارع") && !caseormood.equals("مرفوع")) {
         valid = false;
         return valid;
       } 
       if (prefcalss.equals("V2") && !type.equals("مضارع") && !caseormood.equals("منصوب")) {
         valid = false;
         return valid;
       } 
       if (prefcalss.equals("V3") && !type.equals("مضارع") && !caseormood.equals("مجزوم")) {
         valid = false;
         return valid;
       } 
       if (prefcalss.equals("C2") && type.equals("أمر")) {
         valid = false;
         return valid;
       } 
       if ((suffcalss.equals("V2") || suffcalss.equals("V3")) && type.equals("أمر")) {
         valid = false;
         return valid;
       } 
       if (suffcalss.equals("V4") && !person2.equals("أنتم")) {
         valid = false;
         return valid;
       } 
       if (Validator.getInstance().notCompatible(normalizedWord, vowledWord, segment, false)) {
         valid = false;
         return valid;
       } 
       return valid;
     }
     
     public Map<String, Set> getAllRoot_Formulas(Map<String, Map> solutions) {
       Map<String, Set> iMapRes = new HashMap<>();
       for (Map.Entry<String, Map> unvoweled : solutions.entrySet()) {
         String s_unvoweled = unvoweled.getKey();
         Map<String, List> Roots = unvoweled.getValue();
         for (Map.Entry<String, List> roots : Roots.entrySet()) {
           String s_root = roots.getKey();
           List solutionPatterns = roots.getValue();
           if (iMapRes.containsKey(s_root)) {
             ((Set)iMapRes.get(s_root)).addAll(solutionPatterns);
             continue;
           } 
           Set set = new HashSet();
           set.addAll(solutionPatterns);
           iMapRes.put(s_root, set);
         } 
       } 
       return iMapRes;
     }
     
     public VEntity[] getPartOfSpeech(int idpartofspeech) {
       VEntity[] result = new VEntity[9];
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechType();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechAugmented();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechEmphasized();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechMain();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechNbRoot();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechPerson();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechPerson2();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechTransitivity();
       VerbalDerivedImpl.getInstance().addDescPartOfSpeechVoice();
       PartOfSpeech pos = VerbalDerivedImpl.getInstance().getPartOfSpeech(idpartofspeech);
       result[0] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechMain(Integer.parseInt(pos.getMain()));
       result[1] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechType(Integer.parseInt(pos.getType()));
       result[2] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechAugmented(Integer.parseInt(pos.getAugmented()));
       result[3] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechEmphasized(Integer.parseInt(pos.getEmphasized()));
       result[4] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechNbRoot(Integer.parseInt(pos.getNbroot()));
       result[5] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechPerson(Integer.parseInt(pos.getPerson()));
       result[6] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechPerson2(Integer.parseInt(pos.getPerson2()));
       result[7] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechTransitivity(Integer.parseInt(pos.getTransitivity()));
       result[8] = VerbalDerivedImpl.getInstance().getDescPartOfSpeechVoice(Integer.parseInt(pos.getVoice()));
       return result;
     }
     
     public String[] Interpret(Segment seg, String root, String type, String person, String person2, String emphasized, String caseormood) {
       String prefix1, suffix1, result[] = new String[7];
       String priority = "2";
       if (seg.getProclitic().getVoweledform().equals("")) {
         prefix1 = "#";
         result[5] = "#";
       } else {
         prefix1 = seg.getProclitic().getVoweledform() + "|" + seg.getProclitic().getDesc();
         result[5] = seg.getProclitic().getVoweledform();
       } 
       if (seg.getEnclitic().getVoweledform().equals("")) {
         suffix1 = "#";
         result[6] = "#";
       } else {
         suffix1 = seg.getEnclitic().getVoweledform() + "|" + seg.getEnclitic().getDesc();
         result[6] = seg.getEnclitic().getVoweledform();
       } 
       String prefix = getProcliticValue(type, person, person2);
       String suffix = getEncliticValue(type, person, person2, emphasized, caseormood);
       result[4] = priority;
       if (!prefix.equals("")) {
         if (prefix1.equals("#")) {
           result[0] = prefix;
         } else {
           result[0] = prefix1 + "+" + prefix;
         } 
       } else {
         result[0] = prefix1;
       } 
       if (!suffix.equals("")) {
         if (suffix1.equals("#")) {
           result[3] = suffix;
         } else {
           result[3] = suffix + "+" + suffix1;
         } 
       } else {
         result[3] = suffix1;
       } 
       return result;
     }
     
     public String getProcliticValue(String type, String person, String person2) {
       if (type.equals("مضارع")) {
         if (person.equals("مخاطب"))
           return "ت|حرف المضارعة"; 
         if (person2.equals("أنا"))
           return "أ|حرف المضارعة"; 
         if (person2.equals("نحن"))
           return "ن|حرف المضارعة"; 
         if (person2.equals("هي"))
           return "ت|تاء الغائبة"; 
         if (person2.equals("هما(ة)"))
           return "ت|حرف المضارعة"; 
         return "ي|حرف المضارعة";
       } 
       return "";
     }
     
     public String getEncliticValue(String type, String person, String person2, String emphasized, String caseormood) {
       if (emphasized.equals("مؤكد")) {
         if (person.equals("مخاطب"))
           return "ن|نون التوكيد"; 
         return "";
       } 
       if (type.equals("أمر")) {
         if (person.equals("مخاطب") && !person2.equals("أنتَ")) {
           if (person2.equals("أنتِ"))
             return "ي|ياء المخاطبة"; 
           if (person2.equals("أنتما"))
             return "ا|ألف المثنى"; 
           if (person2.equals("أنتم"))
             return "وا|واو الجماعة"; 
           if (person2.equals("أنتن"))
             return "ن|نون النسوة"; 
           return "";
         } 
         return "";
       } 
       if (type.equals("ماض")) {
         if (person2.equals("أنا"))
           return "ت|تاء المتكلم"; 
         if (person2.equals("نحن"))
           return "نا|نون المتكلمين"; 
         if (person2.equals("أنتَ"))
           return "ت|تاء المخاطب"; 
         if (person2.equals("أنتِ"))
           return "ت|تاء المخاطبة"; 
         if (person2.equals("أنتما"))
           return "تما|تاء المخاطبين"; 
         if (person2.equals("أنتم"))
           return "تم|تاء المخاطبين"; 
         if (person2.equals("أنتن"))
           return "تن|تاء المخاطبات"; 
         if (person2.equals("هي"))
           return "ت|تاء التأنيث الساكنة"; 
         if (person2.equals("هما"))
           return "ا|ألف الاثنين"; 
         if (person2.equals("هما(ة)"))
           return "تا|تاء التأنيت وألف الاثنين"; 
         if (person2.equals("هم"))
           return "وا|واو الجماعة"; 
         if (person2.equals("هن"))
           return "ن|نون النسوة"; 
         return "";
       } 
       if (type.equals("مضارع")) {
         if (person2.equals("هن") || person2.equals("أنتن"))
           return "ن|نون النسوة"; 
         if (person2.equals("أنتِ")) {
           if (caseormood.equals("مرفوع"))
             return "ين|ياء المخاطبة والنون علامة الرفع"; 
           return "ي|ياء المخاطبة";
         } 
         if (person2.equals("أنتما") || person2
           .equals("هما(ة)") || person2
           .equals("هما")) {
           if (caseormood.equals("مرفوع"))
             return "ان|ألف المثنى والنون علامة الرفع"; 
           return "ا|ألف المثنى";
         } 
         if (person2.equals("أنتم") || person2.equals("هم")) {
           if (caseormood.equals("مرفوع"))
             return "ون|واو الجماعة والنون علامة الرفع"; 
           return "وا|واو الجماعة";
         } 
         return "";
       } 
       return "";
     }
     
     public void clear() {
       VerbalDerivedImpl.getInstance().clear();
       this.possiblePattern = new HashMap<>();
     }
   }

