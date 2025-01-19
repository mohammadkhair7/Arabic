/*    */ package net.oujda_nlp_team.entity;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ 
/*    */ public class Clitic implements Serializable {
/*    */   private static final long serialVersionUID = 12421L;
/*    */   
/*    */   private String unvoweledform;
/*    */   
/*    */   private String voweledform;
/*    */   
/*    */   private String desc;
/*    */   
/*    */   private String classe;
/*    */   
/*    */   public String getUnvoweledform() {
/* 34 */     return this.unvoweledform;
/*    */   }
/*    */   
/*    */   public String getVoweledform() {
/* 35 */     return this.voweledform;
/*    */   }
/*    */   
/*    */   public String getDesc() {
/* 36 */     return this.desc;
/*    */   }
/*    */   
/*    */   public String getClasse() {
/* 37 */     return this.classe;
/*    */   }
/*    */   
/*    */   public void setUnvoweledform(String _unvoweledform) {
/* 39 */     this.unvoweledform = _unvoweledform;
/*    */   }
/*    */   
/*    */   public void setVoweledform(String _voweledform) {
/* 40 */     this.voweledform = _voweledform;
/*    */   }
/*    */   
/*    */   public void setDesc(String _desc) {
/* 41 */     this.desc = _desc;
/*    */   }
/*    */   
/*    */   public void setClasse(String _classe) {
/* 42 */     this.classe = _classe;
/*    */   }
/*    */ }


/* Location:              /home/samir/eclipse-workspace/Belayachi-20200130/Belayachi-20200130/ADAT-Analyzer-20180101.v2.jar!/net/oujda_nlp_team/entity/Clitic.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */