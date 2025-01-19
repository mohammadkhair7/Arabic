/*     */ package net.oujda_nlp_team.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;

/*     */ import net.oujda_nlp_team.config.Database;
/*     */ import net.oujda_nlp_team.entity.VEntity;
/*     */ import net.oujda_nlp_team.factory.DerivedFactory;
/*     */ import net.oujda_nlp_team.interfaces.IDerived;
/*     */ import net.oujda_nlp_team.util.IOFile;
/*     */ 
/*     */ public class NominalDerivedImpl extends DerivedFactory implements IDerived {
/*     */   private List descPartOfSpeechType;
/*     */   
/*     */   private List descPartOfSpeechDefinit;
/*     */   
/*     */   private List descPartOfSpeechGender;
/*     */   
/*     */   private List descPartOfSpeechMain;
/*     */   
/*     */   private List descPartOfSpeechNbRoot;
/*     */   
/*     */   private List descPartOfSpeechNumber;
/*     */   
/*  40 */   private static final IDerived instance = new NominalDerivedImpl();
/*     */   
/*     */   public static IDerived getInstance() {
/*  42 */     return instance;
/*     */   }
/*     */   
/*     */   private NominalDerivedImpl() {
/*  46 */     this.iCaseOrMood = new ArrayList();
/*  47 */     this.vector = new HashMap<>();
/*  48 */     this.partOfSpeech = new ArrayList();
/*  49 */     this.descPartOfSpeechType = new ArrayList();
/*  50 */     this.descPartOfSpeechDefinit = new ArrayList();
/*  51 */     this.descPartOfSpeechGender = new ArrayList();
/*  52 */     this.descPartOfSpeechMain = new ArrayList();
/*  53 */     this.descPartOfSpeechNbRoot = new ArrayList();
/*  54 */     this.descPartOfSpeechNumber = new ArrayList();
/*  55 */     this.unvoweledPatternStem = new HashMap<>();
/*  56 */     this.voweledDiacPatternLemmaMap = new HashMap<>();
/*  57 */     this.voweledCanonicPatternLemmaMap = new HashMap<>();
/*  58 */     this.voweledDiacPatternStemMap = new HashMap<>();
/*  59 */     this.voweledCanonicPatternStemMap = new HashMap<>();
/*  60 */     addNominalDerivedImpl();
/*     */   }
/*     */   
/*     */   private void addNominalDerivedImpl() {
/*  64 */     addCaseOrMood();
/*  65 */     addPartOfSpeech();
/*  66 */     addDescPartOfSpeechType();
/*  67 */     addDescPartOfSpeechDefinit();
/*  68 */     addDescPartOfSpeechGender();
/*  69 */     addDescPartOfSpeechMain();
/*  70 */     addDescPartOfSpeechNbRoot();
/*  71 */     addDescPartOfSpeechNumber();
/*  72 */     addAllVectorList();
/*  73 */     addQuadriliteralRootList();
/*  74 */     addTrilateralRootList();
/*  75 */     addAllTrilateralRootMap();
/*  76 */     addAllQuadriliteralRootMap();
/*  77 */     addAllUnvoweledPatternMap();
/*  78 */     addAllVoweledDiacPatternLemmaMap();
/*  79 */     addAllVoweledCanonicPatternLemmaMap();
/*  80 */     addAllVoweledDiacPatternStemMap();
/*  81 */     addAllVoweledCanonicPatternStemMap();
/*     */   }
/*     */   
/*     */   public void addDescPartOfSpeechType() {
/*  85 */     if (this.descPartOfSpeechType.isEmpty()) {
/*  87 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Type");
/*  88 */       this.descPartOfSpeechType = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addDescPartOfSpeechDefinit() {
/*  93 */     if (this.descPartOfSpeechDefinit.isEmpty()) {
/*  95 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Definit");
/*  96 */       this.descPartOfSpeechDefinit = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addDescPartOfSpeechGender() {
/* 101 */     if (this.descPartOfSpeechGender.isEmpty()) {
/* 103 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Gender");
/* 104 */       this.descPartOfSpeechGender = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addDescPartOfSpeechMain() {
/* 109 */     if (this.descPartOfSpeechMain.isEmpty()) {
/* 111 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Main");
/* 112 */       this.descPartOfSpeechMain = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addDescPartOfSpeechNbRoot() {
/* 117 */     if (this.descPartOfSpeechNbRoot.isEmpty()) {
/* 119 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.NbRoot");
/* 120 */       this.descPartOfSpeechNbRoot = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addDescPartOfSpeechNumber() {
/* 125 */     if (this.descPartOfSpeechNumber.isEmpty()) {
/* 127 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech.Number");
/* 128 */       this.descPartOfSpeechNumber = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addCaseOrMood() {
/* 133 */     if (isEmptyCaseOrMood()) {
/* 135 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.CaseOrMood");
/* 136 */       this.iCaseOrMood = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addAllVectorList() {
/* 142 */     if (isEmptyVector()) {
/* 144 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Formulas");
/* 145 */       this.vector = IOFile.getInstance().deserializeMap(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPartOfSpeech() {
/* 151 */     if (isEmptyPartOfSpeech()) {
/* 153 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.PartOfSpeech");
/* 154 */       this.partOfSpeech = IOFile.getInstance().deserializeList(data);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addAllTrilateralRootMap() {
/* 161 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.Trilateral");
/* 162 */     this.trilateralRootMap = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addAllQuadriliteralRootMap() {
/* 168 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.Quadriliteral");
/* 169 */     this.quadriliteralRootMap = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addQuadriliteralRootList() {
/* 175 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.id.Quadriliteral");
/* 176 */     this.quadriliteralRootList = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addTrilateralRootList() {
/* 182 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Roots.id.Trilateral");
/* 183 */     this.trilateralRootList = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addAllUnvoweledPatternMap() {
/* 189 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Stems.Unvoweled");
/* 190 */     this.unvoweledPatternStem = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addAllVoweledDiacPatternLemmaMap() {
/* 196 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Lemmas.Diac");
/* 197 */     this.voweledDiacPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addAllVoweledCanonicPatternLemmaMap() {
/* 203 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Lemmas.Canonic");
/* 204 */     this.voweledCanonicPatternLemmaMap = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addAllVoweledDiacPatternStemMap() {
/* 210 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Stems.Diac");
/* 211 */     this.voweledDiacPatternStemMap = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public void addAllVoweledCanonicPatternStemMap() {
/* 217 */     String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Nouns.Patterns.Stems.Canonic");
/* 218 */     this.voweledCanonicPatternStemMap = IOFile.getInstance().deserializeMap(data);
/*     */   }
/*     */   
/*     */   public VEntity getDescPartOfSpeechType(int index) {
/* 221 */     return (VEntity) this.descPartOfSpeechType.get(index - 1);
/*     */   }
/*     */   
/*     */   public VEntity getDescPartOfSpeechDefinit(int index) {
/* 222 */     return (VEntity) this.descPartOfSpeechDefinit.get(index - 1);
/*     */   }
/*     */   
/*     */   public VEntity getDescPartOfSpeechGender(int index) {
/* 223 */     return (VEntity) this.descPartOfSpeechGender.get(index - 1);
/*     */   }
/*     */   
/*     */   public VEntity getDescPartOfSpeechMain(int index) {
/* 224 */     return (VEntity) this.descPartOfSpeechMain.get(index - 1);
/*     */   }
/*     */   
/*     */   public VEntity getDescPartOfSpeechNbRoot(int index) {
/* 225 */     return (VEntity) this.descPartOfSpeechNbRoot.get(index - 1);
/*     */   }
/*     */   
/*     */   public VEntity getDescPartOfSpeechNumber(int index) {
/* 226 */     return (VEntity) this.descPartOfSpeechNumber.get(index - 1);
/*     */   }
/*     */ }

