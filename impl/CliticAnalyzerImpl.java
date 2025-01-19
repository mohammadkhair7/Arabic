  package net.oujda_nlp_team.impl;
  
  import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.util.Validator;
  
  public class CliticAnalyzerImpl {
     private static final CliticAnalyzerImpl instance = new CliticAnalyzerImpl();
    
    public static CliticAnalyzerImpl getInstance() {
       return instance;
    }
    
    public Result analyzedSegment(String normalizedWord, String unvoweledWord, Segment segment) {
       Result r = new Result();
       String vpref = segment.getProclitic().getVoweledform();
       String vsuff = segment.getEnclitic().getVoweledform();
       String vowledWord = vpref + vsuff;
       if (!Validator.getInstance().notCompatible(normalizedWord, vowledWord, segment, false)) {
         String decPref = vpref.equals("") ? "#" : (vpref + ":" + segment.getProclitic().getDesc());
         String decSuff = vsuff.equals("") ? "#" : (vsuff + ":" + segment.getEnclitic().getDesc());
         r = new Result(vowledWord, decPref, "#", "#", "#", "#", "#", "#", "#", "#", decSuff, "0");
      } 
       return r;
    }
  }
