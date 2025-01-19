   package net.oujda_nlp_team.factory;
   
   import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.oujda_nlp_team.util.xml.XMLClitic;
   
   public abstract class CliticFactory {
     protected Map<String, List> clitic;
     
     protected XMLClitic xml;
     
     public List possibleClitics(String _affix) {
       return this.clitic.containsKey(_affix) ? this.clitic.get(_affix) : new ArrayList();
     }
     
     public void clear() {
       this.clitic.clear();
     }
   }

