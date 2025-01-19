/*    */ package net.oujda_nlp_team.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Exceptional implements Serializable {
/*    */   private static final long serialVersionUID = 12422L;
/*    */   
/*    */   private String unvoweledform;
/*    */   
/*    */   private String voweledform;
/*    */   
/*    */   private String proclitic;
/*    */   
/*    */   private String enclitic;
/*    */   
/*    */   private String stem;
/*    */   
/*    */   private String pos;
/*    */   
/*    */   public void setUnvoweledform(String _unvow) {
/* 36 */     this.unvoweledform = _unvow;
/*    */   }
/*    */   
/*    */   public void setVoweledform(String _vow) {
/* 37 */     this.voweledform = _vow;
/*    */   }
/*    */   
/*    */   public void setProclitic(String _proc) {
/* 38 */     this.proclitic = _proc;
/*    */   }
/*    */   
/*    */   public void setEnclitic(String _enc) {
/* 39 */     this.enclitic = _enc;
/*    */   }
/*    */   
/*    */   public void setStem(String _stem) {
/* 40 */     this.stem = _stem;
/*    */   }
/*    */   
/*    */   public void setPartOfSpeech(String _pos) {
/* 41 */     this.pos = _pos;
/*    */   }
/*    */   
/*    */   public String getUnvoweledform() {
/* 43 */     return this.unvoweledform;
/*    */   }
/*    */   
/*    */   public String getVoweledform() {
/* 44 */     return this.voweledform;
/*    */   }
/*    */   
/*    */   public String getProclitic() {
/* 45 */     return this.proclitic;
/*    */   }
/*    */   
/*    */   public String getEnclitic() {
/* 46 */     return this.enclitic;
/*    */   }
/*    */   
/*    */   public String getStem() {
/* 47 */     return this.stem;
/*    */   }
/*    */   
/*    */   public String getPartOfSpeech() {
/* 48 */     return this.pos;
/*    */   }
/*    */ }


/* Location:              /home/samir/eclipse-workspace/Belayachi-20200130/Belayachi-20200130/ADAT-Analyzer-20180101.v2.jar!/net/oujda_nlp_team/entity/Exceptional.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */