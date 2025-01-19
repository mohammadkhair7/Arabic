/*    */ package net.oujda_nlp_team.impl;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.oujda_nlp_team.config.Database;
/*    */ import net.oujda_nlp_team.entity.Exceptional;
/*    */ import net.oujda_nlp_team.util.IOFile;
/*    */ 
/*    */ public class ExceptionalBuildImpl {
/*    */   private Map<String, Exceptional> exceptional;
/*    */   
/* 32 */   private static final ExceptionalBuildImpl instance = new ExceptionalBuildImpl();
/*    */   
/*    */   public static ExceptionalBuildImpl getInstance() {
/* 34 */     return instance;
/*    */   }
/*    */   
/*    */   private ExceptionalBuildImpl() {
/* 37 */     this.exceptional = new HashMap<>();
/* 38 */     addExceptionalForm();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 41 */     return this.exceptional.isEmpty();
/*    */   }
/*    */   
/*    */   public void addExceptionalForm() {
/* 44 */     if (isEmpty()) {
/* 46 */       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.Exceptional");
/* 47 */       this.exceptional = IOFile.getInstance().deserializeMap(data);
/*    */     } 
/*    */   }
/*    */   
/*    */   public Exceptional getExceptionalFormValue(String unvoweled) {
/* 51 */     return this.exceptional.get(unvoweled);
/*    */   }
/*    */   
/*    */   public boolean containsValue(String unvoweled) {
/* 53 */     return this.exceptional.containsKey(unvoweled);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 55 */     this.exceptional.clear();
/*    */   }
/*    */ }


/* Location:              /home/samir/eclipse-workspace/Belayachi-20200130/Belayachi-20200130/ADAT-Analyzer-20180101.v2.jar!/net/oujda_nlp_team/impl/ExceptionalBuildImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */