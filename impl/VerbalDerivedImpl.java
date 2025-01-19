   package net.oujda_nlp_team.impl;
   
   import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.VEntity;
import net.oujda_nlp_team.factory.DerivedFactory;
import net.oujda_nlp_team.interfaces.IDerived;
import net.oujda_nlp_team.util.IOFile;
   
   public class VerbalDerivedImpl extends DerivedFactory implements IDerived {
     private static final VerbalDerivedImpl instance = new VerbalDerivedImpl();
     
     private List descPartOfSpeechMain;
     
     private List descPartOfSpeechType;
     
     private List descPartOfSpeechPerson;
     
     private List descPartOfSpeechPerson2;
     
     private List descPartOfSpeechVoice;
     
     private List descPartOfSpeechEmphasized;
     
     private List descPartOfSpeechTransitivity;
     
     private List descPartOfSpeechAugmented;
     
     private List descPartOfSpeechNbRoot;
     
     public static VerbalDerivedImpl getInstance() {
       return instance;
     }
     
     private VerbalDerivedImpl() {
       this.iCaseOrMood = new ArrayList();
       this.vector = new HashMap<>();
       this.partOfSpeech = new ArrayList();
       this.descPartOfSpeechMain = new ArrayList();
       this.descPartOfSpeechType = new ArrayList();
       this.descPartOfSpeechPerson = new ArrayList();
       this.descPartOfSpeechPerson2 = new ArrayList();
       this.descPartOfSpeechVoice = new ArrayList();
       this.descPartOfSpeechEmphasized = new ArrayList();
       this.descPartOfSpeechTransitivity = new ArrayList();
       this.descPartOfSpeechAugmented = new ArrayList();
       this.descPartOfSpeechNbRoot = new ArrayList();
       this.quadriliteralRootMap = new HashMap<>();
       this.trilateralRootMap = new HashMap<>();
       this.quadriliteralRootList = new HashMap<>();
       this.trilateralRootList = new HashMap<>();
       this.unvoweledPatternStem = new HashMap<>();
       this.voweledDiacPatternLemmaMap = new HashMap<>();
       this.voweledCanonicPatternLemmaMap = new HashMap<>();
       this.voweledDiacPatternStemMap = new HashMap<>();
       this.voweledCanonicPatternStemMap = new HashMap<>();
       addVerbalDerivedImpl();
     }
     
     private void addVerbalDerivedImpl() {
       addCaseOrMood();
       addPartOfSpeech();
       addDescPartOfSpeechMain();
       addDescPartOfSpeechType();
       addDescPartOfSpeechPerson();
       addDescPartOfSpeechPerson2();
       addDescPartOfSpeechVoice();
       addDescPartOfSpeechEmphasized();
       addDescPartOfSpeechTransitivity();
       addDescPartOfSpeechNbRoot();
       addDescPartOfSpeechAugmented();
       addAllVectorList();
       addQuadriliteralRootList();
       addTrilateralRootList();
       addAllTrilateralRootMap();
       addAllQuadriliteralRootMap();
       addAllUnvoweledPatternMap();
       addAllVoweledDiacPatternLemmaMap();
       addAllVoweledCanonicPatternLemmaMap();
       addAllVoweledDiacPatternStemMap();
       addAllVoweledCanonicPatternStemMap();
     }
     
     public void addCaseOrMood() {
       if (isEmptyCaseOrMood()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.CaseOrMood");
         this.iCaseOrMood = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addAllVectorList() {
       if (isEmptyVector()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Formulas");
         this.vector = IOFile.getInstance().deserializeMap(data);
       } 
     }
     
     public void addPartOfSpeech() {
       if (isEmptyPartOfSpeech()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech");
         this.partOfSpeech = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addQuadriliteralRootList() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.id.Quadriliteral");
       this.quadriliteralRootList = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addTrilateralRootList() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.id.Trilateral");
       this.trilateralRootList = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addAllTrilateralRootMap() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.Trilateral");
       this.trilateralRootMap = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addAllQuadriliteralRootMap() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Roots.Quadriliteral");
       this.quadriliteralRootMap = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addAllUnvoweledPatternMap() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Stems.Unvoweled");
       this.unvoweledPatternStem = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addAllVoweledDiacPatternLemmaMap() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Lemmas.Diac");
       this.voweledDiacPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addAllVoweledCanonicPatternLemmaMap() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Lemmas.Canonic");
       this.voweledCanonicPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addAllVoweledDiacPatternStemMap() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Stems.Diac");
       this.voweledDiacPatternStemMap = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addAllVoweledCanonicPatternStemMap() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.Patterns.Stems.Canonic");
       this.voweledCanonicPatternStemMap = IOFile.getInstance().deserializeMap(data);
     }
     
     public void addDescPartOfSpeechMain() {
       if (this.descPartOfSpeechMain.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Main");
         this.descPartOfSpeechMain = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechType() {
       if (this.descPartOfSpeechType.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Type");
         this.descPartOfSpeechType = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechPerson() {
       if (this.descPartOfSpeechPerson.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Person");
         this.descPartOfSpeechPerson = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechPerson2() {
       if (this.descPartOfSpeechPerson2.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Person2");
         this.descPartOfSpeechPerson2 = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechVoice() {
       if (this.descPartOfSpeechVoice.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Voice");
         this.descPartOfSpeechVoice = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechEmphasized() {
       if (this.descPartOfSpeechEmphasized.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Emphasized");
         this.descPartOfSpeechEmphasized = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechTransitivity() {
       if (this.descPartOfSpeechTransitivity.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Transitivity");
         this.descPartOfSpeechTransitivity = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechNbRoot() {
       if (this.descPartOfSpeechNbRoot.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.NbRoot");
         this.descPartOfSpeechNbRoot = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public void addDescPartOfSpeechAugmented() {
       if (this.descPartOfSpeechAugmented.isEmpty()) {
         String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Verbs.PartOfSpeech.Augmented");
         this.descPartOfSpeechAugmented = IOFile.getInstance().deserializeList(data);
       } 
     }
     
     public VEntity getDescPartOfSpeechMain(int index) {
       return (VEntity) this.descPartOfSpeechMain.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechType(int index) {
       return  (VEntity)  this.descPartOfSpeechType.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechPerson(int index) {
       return (VEntity)  this.descPartOfSpeechPerson.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechPerson2(int index) {
       return (VEntity) this.descPartOfSpeechPerson2.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechVoice(int index) {
       return (VEntity) this.descPartOfSpeechVoice.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechEmphasized(int index) {
       return (VEntity) this.descPartOfSpeechEmphasized.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechTransitivity(int index) {
       return (VEntity) this.descPartOfSpeechTransitivity.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechAugmented(int index) {
       return (VEntity) this.descPartOfSpeechAugmented.get(index - 1);
     }
     
     public VEntity getDescPartOfSpeechNbRoot(int index) {
       return (VEntity) this.descPartOfSpeechNbRoot.get(index - 1);
     }
   }

