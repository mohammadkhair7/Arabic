package net.oujda_nlp_team;
  
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.oujda_nlp_team.config.Database;
import net.oujda_nlp_team.entity.Result;
import net.oujda_nlp_team.entity.ResultList;
import net.oujda_nlp_team.entity.Segment;
import net.oujda_nlp_team.impl.CliticAnalyzerImpl;
import net.oujda_nlp_team.impl.ExceptionalAnalyzerImpl;
import net.oujda_nlp_team.impl.NominalAnalyzerImpl;
import net.oujda_nlp_team.impl.PropernounAnalyzerImpl;
import net.oujda_nlp_team.impl.PropernounUnderivedImpl;
import net.oujda_nlp_team.impl.ToolwordsAnalyzerImpl;
import net.oujda_nlp_team.impl.ToolwordsUnderivedImpl;
import net.oujda_nlp_team.impl.VerbalAnalyzerImpl;
import net.oujda_nlp_team.interfaces.IUnderived;
import net.oujda_nlp_team.util.ArabicStringUtil;
import net.oujda_nlp_team.util.IOFile;
import net.oujda_nlp_team.util.Stemming;
  
  public class AlKhalil2Analyzer {
     public Map allResults = new HashMap<>();
    
     public Map allResultsBis = new HashMap<>();
    
    public Map<String, Double> map;
    
     private static final AlKhalil2Analyzer instance = new AlKhalil2Analyzer();
    
    public static AlKhalil2Analyzer getInstance() {
       return instance;
    }
    
    public void addRootStatistic() {
       String data = Database.getInstance().getPath() + "/" + Database.getInstance().getResources().getProperty("Data.root");
       this.map = IOFile.getInstance().deserializeMap(data);
    }
    
    public synchronized List analyzerToken(String normalizedWord) {
    	 String unvoweledWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(normalizedWord);
         normalizedWord = ArabicStringUtil.getInstance().correctErreur(normalizedWord);
         List<Result> result = ExceptionalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord);
         if (!result.isEmpty())
           return result; 
         List<Segment> Segments = Stemming.getInstance().getListsSegment(unvoweledWord);
         Iterator<Segment> it_Seg = Segments.iterator();
         while (it_Seg.hasNext()) {
           Segment segment = it_Seg.next();
           if (Database.getInstance().getResources().getProperty("Data.Output.Propernouns").equals("true"))
             result.addAll(PropernounAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment)); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Toolwords").equals("true"))
        	   /**
                * Samir BELAYACHI
                * Filtre ResultList pour les mots avec HAMZA
                */
               /*============================================================================*/        	   
        	   result.addAll(filtreResultWithHamza(normalizedWord,ToolwordsAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment))); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Nouns").equals("true"))
             result.addAll(NominalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment)); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Verbs").equals("true"))
        	   /**
                * Samir BELAYACHI
                * Filtre ResultList pour les mots avec HAMZA
                */
               /*============================================================================*/     
             result.addAll(filtreResultWithHamza(normalizedWord,VerbalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment))); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Clitics").equals("true"))
             result.add(CliticAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment)); 
        } 
      
      
       return result;
    }
    
    public synchronized ResultList processToken(String normalizedWord) {
    	 ResultList result = new ResultList();
         String unvoweledWord = ArabicStringUtil.getInstance().removeAllDiacriticsOfWord(normalizedWord);
         normalizedWord = ArabicStringUtil.getInstance().correctErreur(normalizedWord);
         result.addAll(ExceptionalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord));
         if (result.isAnalyzed())
           return result; 
         List<Segment> Segments = Stemming.getInstance().getListsSegment(unvoweledWord);
         Iterator<Segment> it_Seg = Segments.iterator();
         while (it_Seg.hasNext()) {
           Segment segment = it_Seg.next();
           if (Database.getInstance().getResources().getProperty("Data.Output.Propernouns").equals("true"))
             result.addAll(PropernounAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment)); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Toolwords").equals("true"))
        	   /**
                * Samir BELAYACHI
                * Filtre ResultList pour les mots avec HAMZA
                */
               /*============================================================================*/
             result.addAll(filtreResultWithHamza(normalizedWord,ToolwordsAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment))); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Nouns").equals("true"))
             result.addAll(filtreResultWithHamza(normalizedWord,NominalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment))); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Verbs").equals("true"))
        	   /**
                * Samir BELAYACHI
                * Filtre ResultList pour les mots avec HAMZA
                */
               /*============================================================================*/
             result.addAll(filtreResultWithHamza(normalizedWord,VerbalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment))); 
           if (Database.getInstance().getResources().getProperty("Data.Output.Clitics").equals("true"))
             result.add(CliticAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment)); 
        } 

         if (!result.isAnalyzed() && normalizedWord.endsWith("ًا") && !normalizedWord.endsWith("ًّا")) {
           normalizedWord = normalizedWord.replaceAll("ًا$", "ًّا");
           it_Seg = Segments.iterator();
           while (it_Seg.hasNext()) {
             Segment segment = it_Seg.next();
             if (Database.getInstance().getResources().getProperty("Data.Output.Propernouns").equals("true"))
               result.addAll(PropernounAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, unvoweledWord, segment)); 
             if (Database.getInstance().getResources().getProperty("Data.Output.Nouns").equals("true"))
               result.addAll(NominalAnalyzerImpl.getInstance().analyzedSegment(normalizedWord, segment)); 
          } 
        } 
       return result;
    }
    
    
    public void clear() {
       NominalAnalyzerImpl.getInstance().clear();
       VerbalAnalyzerImpl.getInstance().clear();
       Stemming.getInstance().clear();
       ExceptionalAnalyzerImpl.getInstance().clear();
       PropernounAnalyzerImpl.getInstance().clear((IUnderived)PropernounUnderivedImpl.getInstance());
       ToolwordsAnalyzerImpl.getInstance().clear((IUnderived)ToolwordsUnderivedImpl.getInstance());
    }
    /*============================================================================*/
    /**
     * Samir BELAYACHI
     * Filtre ResultList pour les mots avec HAMZA
     */
    /*============================================================================*/

    public  synchronized ResultList filtreResultListWithHamza(String normalizedWord,   ResultList resultList) {
        ResultList resultFiltre = new ResultList();
    	String hamza = net.oujda_nlp_team.util.ArabicStringUtil.getInstance().getTypeHamzaFromWord(normalizedWord);
    	java.util.Iterator<Result> it = resultList.getAllResults().iterator();
    	while( it.hasNext() ) {
    		Result result = it.next();
    		String stem = result.getStem();
    		String lemma = result.getLemma();
    		if(stem.contains(hamza)) {
    			resultFiltre.add(result);
    		}
    	}
    	return resultFiltre;
    	
    }
    /*============================================================================*/
    /**
     * Samir BELAYACHI
     * Filtre ResultList pour les mots avec HAMZA
     */
    /*============================================================================*/
    public  synchronized List<Result> filtreResultWithHamza(String normalizedWord,     List<Result> result ) {
    	List<Result> resultFiltre = new java.util.ArrayList<>();
      	String hamza = net.oujda_nlp_team.util.ArabicStringUtil.getInstance().getTypeHamzaFromWord(normalizedWord);
      	java.util.Iterator<Result> it = result.iterator();
      	while( it.hasNext() ) {
      		Result rs = it.next();
      		String stem = rs.getStem();
    		String lemma = rs.getLemma();
      		if(stem.contains(hamza)) {
      			resultFiltre.add(rs);
      		}
      	}
      	return resultFiltre;
    	
    }
    /*============================================================================*/
  }


