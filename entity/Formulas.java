/*    */ package net.oujda_nlp_team.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Formulas implements Serializable {
/*    */   private static final long serialVersionUID = 123L;
/*    */   
/*    */   private String idRoot;
/*    */   
/*    */   private String idDiacPatternStem;
/*    */   
/*    */   private String idCanonicPatternStem;
/*    */   
/*    */   private String idDiacPatternLemma;
/*    */   
/*    */   private String idCanonicPatternLemma;
/*    */   
/*    */   private String idPartOfSpeech;
/*    */   
/*    */   private String idCaseOrMood;
/*    */   
/*    */   public void setIdRoot(String _idRoot) {
/* 37 */     this.idRoot = _idRoot;
/*    */   }
/*    */   
/*    */   public void setIdDiacPatternStem(String _idDPS) {
/* 38 */     this.idDiacPatternStem = _idDPS;
/*    */   }
/*    */   
/*    */   public void setIdCanonicPatternStem(String _idCPS) {
/* 39 */     this.idCanonicPatternStem = _idCPS;
/*    */   }
/*    */   
/*    */   public void setIdDiacPatternLemma(String _idDPL) {
/* 40 */     this.idDiacPatternLemma = _idDPL;
/*    */   }
/*    */   
/*    */   public void setIdCanonicPatternLemma(String _idCPL) {
/* 41 */     this.idCanonicPatternLemma = _idCPL;
/*    */   }
/*    */   
/*    */   public void setIdPartOfSpeech(String _idPOS) {
/* 42 */     this.idPartOfSpeech = _idPOS;
/*    */   }
/*    */   
/*    */   public void setIdCaseOrMood(String _idCOM) {
/* 43 */     this.idCaseOrMood = _idCOM;
/*    */   }
/*    */   
/*    */   public String getIdRoot() {
/* 45 */     return this.idRoot;
/*    */   }
/*    */   
/*    */   public String getIdDiacPatternStem() {
/* 46 */     return this.idDiacPatternStem;
/*    */   }
/*    */   
/*    */   public String getIdCanonicPatternStem() {
/* 47 */     return this.idCanonicPatternStem;
/*    */   }
/*    */   
/*    */   public String getIdDiacPatternLemma() {
/* 48 */     return this.idDiacPatternLemma;
/*    */   }
/*    */   
/*    */   public String getIdCanonicPatternLemma() {
/* 49 */     return this.idCanonicPatternLemma;
/*    */   }
/*    */   
/*    */   public String getIdPartOfSpeech() {
/* 50 */     return this.idPartOfSpeech;
/*    */   }
/*    */   
/*    */   public String getIdCaseOrMood() {
/* 51 */     return this.idCaseOrMood;
/*    */   }
/*    */
@Override
public String toString() {
	return "Formulas [idRoot=" + idRoot + ", idDiacPatternStem=" + idDiacPatternStem + ", idCanonicPatternStem="
			+ idCanonicPatternStem + ", idDiacPatternLemma=" + idDiacPatternLemma + ", idCanonicPatternLemma="
			+ idCanonicPatternLemma + ", idPartOfSpeech=" + idPartOfSpeech + ", idCaseOrMood=" + idCaseOrMood + "]";
} }


