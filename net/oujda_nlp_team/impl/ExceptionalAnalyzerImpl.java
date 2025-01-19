/*    */ package net.oujda_nlp_team.impl;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;

/*    */ import net.oujda_nlp_team.entity.Exceptional;
/*    */ import net.oujda_nlp_team.entity.Result;
/*    */ 
/*    */ public class ExceptionalAnalyzerImpl {
/* 30 */   private static final ExceptionalAnalyzerImpl instance = new ExceptionalAnalyzerImpl();
/*    */   
/*    */   public static ExceptionalAnalyzerImpl getInstance() {
/* 32 */     return instance;
/*    */   }
/*    */   
/*    */   public synchronized List analyzedSegment(String normalizedWord, String unvoweledWord) {
/* 37 */     List<Result> result = new ArrayList();
/* 38 */     if (ExceptionalBuildImpl.getInstance().containsValue(unvoweledWord)) {
/* 39 */       Exceptional ew = ExceptionalBuildImpl.getInstance().getExceptionalFormValue(unvoweledWord);
/* 40 */       String lemma = "الله";
/* 41 */       result.add(new Result(ew
/* 42 */             .getVoweledform(), ew
/* 43 */             .getProclitic(), ew
/* 44 */             .getProclitic(), ew
/* 45 */             .getStem(), ew
/* 46 */             .getPartOfSpeech(), "-", "-", lemma, "-", "-", "-", ew
/*    */             
/* 53 */             .getEnclitic(), ew
/* 54 */             .getEnclitic(), "1"));
/*    */     } 
/* 58 */     return result;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 62 */     ExceptionalBuildImpl.getInstance().clear();
/*    */   }
/*    */ }


/* Location:              /home/samir/eclipse-workspace/Belayachi-20200130/Belayachi-20200130/ADAT-Analyzer-20180101.v2.jar!/net/oujda_nlp_team/impl/ExceptionalAnalyzerImpl.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */