   package net.oujda_nlp_team.factory;
   
   import java.util.List;

import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.interfaces.IUnderived;
   
   public abstract class UnderivedAnalyzerFactory {
     public abstract List analyzedSegment(String paramString1, String paramString2, Segment paramSegment);
     
     public void clear(IUnderived underived) {
       underived.clear();
     }
   }
